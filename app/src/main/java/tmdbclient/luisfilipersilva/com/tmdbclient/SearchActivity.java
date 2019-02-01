package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    //RecyclerView recyclerView;

    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    public String[] image_urls;
    public String[] name_movies;

    public String dadosBrutos;

    public static ArrayList<String> listaNomes = new ArrayList<String>( );
    public static ArrayList<String> listaAnos = new ArrayList<String>( );
    public static ArrayList<String> listaFilmes = new ArrayList<String>( );
    public static ArrayList<String> listaPosts = new ArrayList<String>( );
    public static ArrayList<String> listaImagens = new ArrayList<String>( );
    public static ArrayList<String> listaImdbId = new ArrayList<String>( );

    //Tab Popular
    public static ArrayList<String> listaPostersPopular = new ArrayList<String>( );
    public static ArrayList<String> listaImagensPopular = new ArrayList<String>( );

    public String linkImages = "https://image.tmdb.org/t/p/w500";


    public String[ ] labelFilmes;
    public String[ ] imageStock;
    public String[ ] imagePopular;
    public String[ ] imageTopRated;

    public String[ ] listaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*

        // codigo que pega os filmes atualmente no cinema e carrega na gridview da primeira Tab (Nos Cinemas)
        // Autor: Luis data: 31/12/2017
        String urlNosCinemas = "https://api.themoviedb.org/3/movie/now_playing?api_key=012d9359357024d4a012077fa7b93581&language=en-US&page=1";
        try{
            dadosBrutos = new getUrl().execute(urlNosCinemas).get();

        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject dadosJson = new JSONObject(dadosBrutos);
            JSONArray tituloJson = dadosJson.getJSONArray("results"); // Omdb: "Search"

            for (int i = 0; i < tituloJson.length(); i++) {
                JSONObject filme = tituloJson.getJSONObject(i);

                listaNomes.add(filme.getString("title")); // Title
                //listaAnos.add(filme.getString("Year"));
                listaPosts.add(filme.getString("poster_path")); //Poster
                listaImdbId.add(filme.getString("id")); //imdbID

            }
        }catch (Exception e ){
            e.printStackTrace();
        }

        //gerando link de imagens
        String linkImages = "https://image.tmdb.org/t/p/w500";
        for (int i = 0; i < listaNomes.size(); i++) {

            listaFilmes.add(listaNomes.get(i));
            listaImagens.add(linkImages+listaPosts.get(i));
        }

        imageStock = listaImagens.toArray(new String[listaImagens.size()]);
        labelFilmes = listaFilmes.toArray(new String[listaFilmes.size()]);
        listaId = listaImdbId.toArray(new String[listaImdbId.size()]);

        listaImagens.clear();
        listaFilmes.clear();
        listaPosts.clear( );
        listaImdbId.clear();
        listaNomes.clear();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        //recyclerView.


        //fim do codigo/

        //image_urls = android_image_urls;

        //recyclerView = findViewById(R.id.recyclerView);
        initViews();
        */
    }

    public String[] getImageData() {
        return imageStock;
    }
    public String[] getNameData() {
        return labelFilmes;
    }



    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2 );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(15),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList androidVersions = prepareData();
        DataAdapter adapter = new DataAdapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList prepareData(){

        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            name_movies = getNameData( );
            image_urls = getImageData( );
            androidVersion.setAndroid_version_name(name_movies[i]);
            androidVersion.setAndroid_image_url(image_urls[i]);
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

    private class getUrl extends AsyncTask<String,String,String> {

        protected String doInBackground(String... theUrl){
            StringBuilder content = new StringBuilder();

            try{

                URL url = new URL(theUrl[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //URLConnection urlConnection = url.openConnection( );

                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(urlConnection.getInputStream()));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line + "\n");
                }
                bufferedReader.close();
            }catch (Exception e ) {
                //tratando exceptions
                e.printStackTrace();
            }
            /*if(content.toString().isEmpty()){
                //return mensagemErro = theUrl;
            }
            else{
                return content.toString();
            }*/
            return content.toString();
        }
    }
}
