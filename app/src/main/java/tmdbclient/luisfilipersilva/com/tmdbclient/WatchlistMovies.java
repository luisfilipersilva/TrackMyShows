package tmdbclient.luisfilipersilva.com.tmdbclient;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class WatchlistMovies extends Fragment {


    public WatchlistMovies() {
        // Required empty public constructor
    }

    public static ArrayList<String> listaNomes = new ArrayList<String>( );
    public static ArrayList<String> listaAnos = new ArrayList<String>( );
    public static ArrayList<String> listaFilmes = new ArrayList<String>( );
    public static ArrayList<String> listaPosts = new ArrayList<String>( );
    public static ArrayList<String> listaImagens = new ArrayList<String>( );
    public static ArrayList<String> listaImdbId = new ArrayList<String>( );
    public static ArrayList<String> listaNotaTmdb = new ArrayList<String>( );
    public static ArrayList<String> listaVotosCountTmdb = new ArrayList<String>( );
    public static ArrayList<String> listaAno = new ArrayList<String>( );

    public static ArrayList<String> listaIds = new ArrayList<String>( );

    //GridView gridView;
    public String[ ] imageStock;
    public String[ ] listaid;
    public String[ ] listaNome;
    public String[ ] listaNotasTmdb;
    public String[ ] listaVotosTmdb;

    public String dadosBrutos = null;

    public String[ ] idlista;

    FloatingActionButton fab;
    RecyclerView recyclerView;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_watchlistmovies, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewWatchlistMoviesTab);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
        if(isMenuVisible()) {
            setUserVisibleHint(true);
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && view != null) {

            WatchlistActivity activity = (WatchlistActivity) getActivity();

            SharedPreferences sp = activity.getSharedPreferences("Userdata", MODE_PRIVATE);

            String session_id = sp.getString("session_id", null);
            String idAccount = sp.getString("idaccount", null);

            String url = "https://api.themoviedb.org/3/account/"+idAccount+"/watchlist/" +
                    "movies?api_key=012d9359357024d4a012077fa7b93581&language=en-US&" +
                    "session_id="+session_id+"&sort_by=created_at.asc";
            try{
                if (dadosBrutos == null) {
                    dadosBrutos = new WatchlistActivity.getUrl().execute(url).get();
                }

            }catch(Exception e){
                e.printStackTrace();
            }

            try {
                JSONObject dadosJson = null;
                dadosJson = new JSONObject(dadosBrutos);
                JSONArray tituloJson = dadosJson.getJSONArray("results"); // Omdb: "Search"

                for (int i = 0; i < tituloJson.length(); i++) {
                    JSONObject filme = tituloJson.getJSONObject(i);

                    listaNomes.add(filme.getString("original_title")); // Title
                    listaPosts.add(filme.getString("poster_path")); //Poster
                    listaIds.add(filme.getString("id")); //id
                    listaNotaTmdb.add(filme.getString("vote_average"));
                    listaVotosCountTmdb.add(filme.getString("vote_count"));
                    listaAno.add(filme.getString("release_date").substring(0, 4));
                }

                //gerando link de imagens
                String linkImages = "https://image.tmdb.org/t/p/w500";
                for (int i = 0; i < listaNomes.size(); i++) {

                    listaFilmes.add(listaNomes.get(i));
                    listaImagens.add(linkImages + listaPosts.get(i));
                }

                idlista = listaIds.toArray(new String[listaIds.size()]);


                Integer lenght = idlista.length;
                String l = lenght.toString();
                //textView.setText(l);

                recyclerView.setHasFixedSize(true);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 3);
                recyclerView.setLayoutManager(layoutManager);

                ArrayList androidVersions = prepareData();

                listaNomes.clear();
                listaImagens.clear();
                listaIds.clear();
                listaAno.clear();
                listaPosts.clear();

                DataAdapter adapter = new DataAdapter(view.getContext(),androidVersions);
                recyclerView.setAdapter(adapter);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    void refreshItems() {

        WatchlistActivity activity = (WatchlistActivity) getActivity();

        SharedPreferences sp = activity.getSharedPreferences("Userdata", MODE_PRIVATE);

        String session_id = sp.getString("session_id", null);
        String idAccount = sp.getString("idaccount", null);


        String url = "https://api.themoviedb.org/3/account/"+idAccount+"/watchlist/" +
                "movies?api_key=012d9359357024d4a012077fa7b93581&language=en-US&" +
                "session_id="+session_id+"&sort_by=created_at.asc";
        try{
            dadosBrutos = new WatchlistActivity.getUrl().execute(url).get();

        }catch(Exception e){
            e.printStackTrace();
        }

        // Load complete

        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        Boolean visible = true;
        setUserVisibleHint(visible);

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    private ArrayList prepareData(){

        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<listaIds.size();i++){
            AndroidVersion androidVersion = new AndroidVersion();
            //name_movies = getNameData( );
            //image_urls = getImageData( );
            androidVersion.setMovie_id(listaIds.get(i));
            androidVersion.setAndroid_image_url(listaImagens.get(i));
            androidVersion.setAndroid_version_name(listaNomes.get(i));
            androidVersion.setNota_tmdb(listaNotaTmdb.get(i));
            androidVersion.setVotos_tmdb(listaVotosCountTmdb.get(i));
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
