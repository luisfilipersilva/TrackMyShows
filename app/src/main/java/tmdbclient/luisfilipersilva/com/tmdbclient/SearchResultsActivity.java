package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    //private TextView txtQuery;
    private RecyclerView recyclerView;

    public String dadosBrutos;

    public static ArrayList<String> listaNomes = new ArrayList<String>( );
    public static ArrayList<String> listaImagens = new ArrayList<String>( );
    public static ArrayList<String> listaId = new ArrayList<String>( );
    public static ArrayList<String> listaPosters = new ArrayList<String>( );

    public static ArrayList<String> listaNotaTmdb = new ArrayList<String>( );
    public static ArrayList<String> listaVotosTmdb = new ArrayList<String>( );

    public static ArrayList<String> listaTipo = new ArrayList<String>( );
    public static ArrayList<String> listaAno = new ArrayList<String>( );

    public String[ ] idlista;
    public View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // get the action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);

        //txtQuery
        //txtQuery = findViewById(R.id.txtQuery);

        recyclerView = findViewById(R.id.recyclerviewSearch);

        String query = getIntent().getStringExtra(SearchManager.QUERY);
        query = query.replace(" ","+");

        //Moviedata moviedata = new Moviedata();
        String urlCompleta = Moviedata.montarUrlBusca(query);
        try{
            dadosBrutos =  new Moviedata.getUrl().execute(urlCompleta).get();

        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject dadosJson = new JSONObject(dadosBrutos);
            JSONArray tituloJson = dadosJson.getJSONArray("results"); // Omdb: "Search"


            int delimiter = 30;
            for (int i = 0; i < delimiter; i++) {
                JSONObject filme = tituloJson.getJSONObject(i);

                String tipo = filme.getString("media_type");

                if(tipo.equals("person")){
                    listaNomes.add(filme.getString("name"));
                    listaPosters.add(filme.getString("profile_path"));
                    listaAno.add("");
                }
                if (tipo.equals("movie")){
                    listaNomes.add(filme.getString("title"));
                    listaPosters.add(filme.getString("poster_path"));
                    listaAno.add(filme.getString("release_date").substring(0,4));

                }
                if (tipo.equals("tv") ){
                    listaNomes.add(filme.getString("name"));
                    listaPosters.add(filme.getString("poster_path"));
                    listaAno.add(filme.getString("first_air_date").substring(0,4));
                }

                listaId.add(filme.getString("id"));
                listaTipo.add(filme.getString("media_type"));
                //listaNotaTmdb.add(filme.getString("vote_average"));
                //listaVotosTmdb.add(filme.getString("vote_count"));

            }
        }catch (Exception e ){
            e.printStackTrace();
        }

        String linkImages = "https://image.tmdb.org/t/p/w500";
        for (int i = 0; i < listaNomes.size(); i++) {

            //listaFilmes.add(listaNomes.get(i));
            listaImagens.add(linkImages+listaPosters.get(i));
        }

        idlista = listaId.toArray(new String[listaId.size()]);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SearchResultsActivity.this,3 );
        recyclerView.setLayoutManager(layoutManager);

        ArrayList androidVersions = prepareData();

        listaNomes.clear();
        listaImagens.clear();
        listaId.clear();
        listaPosters.clear();
        listaTipo.clear();
        listaAno.clear();

        SearchDataAdapter adapter = new SearchDataAdapter(SearchResultsActivity.this,androidVersions);
        recyclerView.setAdapter(adapter);


        //return view;

        //handleIntent(getIntent());
    }
    /**
     * Handling intent data
     */
    /*
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            /**
             * Use this query to display search results like
             * 1. Getting the data from SQLite and showing in listview
             * 2. Making webrequest and displaying the data
             * For now we just display the query only
             */

    /*
            //Moviedata moviedata = new Moviedata();
            String urlCompleta = Moviedata.montarUrlBusca(query);
            try{
                dadosBrutos =  new Moviedata.getUrl().execute(urlCompleta).get();

            }catch(Exception e){
                e.printStackTrace();
            }


            try{
                JSONObject dadosJson = new JSONObject(dadosBrutos);
                JSONArray tituloJson = dadosJson.getJSONArray("results"); // Omdb: "Search"


                for (int i = 0; i < tituloJson.length(); i++) {
                    JSONObject filme = tituloJson.getJSONObject(i);

                    String tipo = filme.getString("media_type");

                    if(tipo.equals("person")){
                        listaNomes.add(filme.getString("name")); // Title
                        listaPosters.add(filme.getString("profile_path"));
                    }
                    if (tipo.equals("movie")){
                        listaNomes.add(filme.getString("title")); // Title
                        listaPosters.add(filme.getString("poster_path")); // Title
                    }

                    listaId.add(filme.getString("id"));
                    listaNotaTmdb.add(filme.getString("vote_average"));
                    listaVotosTmdb.add(filme.getString("vote_count"));

                }
            }catch (Exception e ){
                e.printStackTrace();
            }

            String linkImages = "https://image.tmdb.org/t/p/w500";
            for (int i = 0; i < listaNomes.size(); i++) {

                //listaFilmes.add(listaNomes.get(i));
                listaImagens.add(linkImages+listaPosters.get(i));
            }

            //txtQuery.setText(listaNomes.get(0).toString());

            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SearchResultsActivity.this,3 );
            recyclerView.setLayoutManager(layoutManager);

            ArrayList androidVersions = prepareData();
            DataAdapter adapter = new DataAdapter(SearchResultsActivity.this,androidVersions);
            recyclerView.setAdapter(adapter);

            listaNomes.clear();
            listaImagens.clear();
            listaId.clear();
            listaPosters.clear();
        }
    }
    */

    private ArrayList prepareData(){

        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<idlista.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            //name_movies = getNameData( );
            //image_urls = getImageData( );
            androidVersion.setMovie_id(listaId.get(i).toString());
            androidVersion.setAndroid_image_url(listaImagens.get(i).toString());
            androidVersion.setAndroid_version_name(listaNomes.get(i).toString());
            androidVersion.setTipo(listaTipo.get(i).toString());
            androidVersion.setAno(listaAno.get(i).toString());
            androidVersion.setNota_tmdb("");
            androidVersion.setVotos_tmdb("");
            android_version.add(androidVersion);
        }
        return android_version;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
