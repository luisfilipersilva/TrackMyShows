package tmdbclient.luisfilipersilva.com.tmdbclient;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodeShows extends Fragment {


    public EpisodeShows() {
        // Required empty public constructor
    }


    public String[ ] imageStock;
    public String[ ] listaid;
    public String[ ] listaNome;
    public String[ ] listaNotasTmdb;
    public String[ ] listaVotosTmdb;

    public static ArrayList<String> epsiodesToWatch = new ArrayList<String>( );
    public static ArrayList<String> epsiodesSeasonToWatch = new ArrayList<String>( );

    public static ArrayList<String> listaNomes = new ArrayList<String>( );
 ;
    public static ArrayList<String> listaFilmes = new ArrayList<String>( );
    public static ArrayList<String> listaPosts = new ArrayList<String>( );
    public static ArrayList<String> listaImagens = new ArrayList<String>( );
    public static ArrayList<String> listaImdbId = new ArrayList<String>( );
    public static ArrayList<String> listaNotaTmdb = new ArrayList<String>( );
    public static ArrayList<String> listaVotosCountTmdb = new ArrayList<String>( );
    public static ArrayList<String> listaAno = new ArrayList<String>( );
    public static ArrayList<String> listaEpisodeNumber = new ArrayList<String>( );
    public static ArrayList<String> listaSeasonNumber = new ArrayList<String>( );
    public static ArrayList<String> listaAirDate = new ArrayList<String>( );
    public static ArrayList<String> listaOverview = new ArrayList<String>( );

    public static ArrayList<String> listaIds = new ArrayList<String>( );

    public static ArrayList<String> listaDadosTvShows = new ArrayList<String>( );

    public static ArrayList<String> listaNextEpsiodeName = new ArrayList<String>( );


    public static ArrayList<String> listaTraktId = new ArrayList<String>( );
    public static ArrayList<String> listaTmdbId = new ArrayList<String>( );

    //FloatingActionButton fab;
    RecyclerView recyclerView;
    View view;
    TextView textView;

    ArrayList<Episode> episodesToWatch = null;
    ArrayList<Episode> episodesToWatchNew = null;

    //dados para cache

    String dadosBrutosTrakt = null;
    String dadosTmdb = null;
    String dadosTrakt = null;

    public String dadosBrutos;

    ShowsActivity activity;
    SharedPreferences CachedData;


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_episodeshows, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewEpisodeShows);

        textView = view.findViewById(R.id.textEpisodeShowsTab);

        //seta o fragment como visible para carregar os dados.
        if(isMenuVisible()) {
            setUserVisibleHint(true);
        }

        return view;
    }

    public void passVar(){

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && view != null) {

            activity = (ShowsActivity) getActivity();

            CachedData = activity.getSharedPreferences("CachedData", MODE_PRIVATE);
            //SharedPreferences.Editor Editor = CachedData.edit();

            String retorna = null;
            try {
                //retorna = new LoadingData().execute().get();
                episodesToWatch  = new LoadingData().execute().get();
                //new LoadingData().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }



                recyclerView.setHasFixedSize(true);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
                recyclerView.setLayoutManager(layoutManager);

                //

                EpisodeToWatchAdapter adapter = new EpisodeToWatchAdapter(view.getContext(), episodesToWatch);
                recyclerView.setAdapter(adapter);









           // Snackbar.make(view, "setUserVisibleHint", Snackbar.LENGTH_SHORT)
               //     .setAction("Action", null).show();


            //ShowsActivity activity = (ShowsActivity) getActivity();

                    /*
                    JSONArray tituloJson = new JSONArray(dadosBrutosTrakt);
                    for (int i = 0; i < tituloJson.length(); i++) {

                        JSONObject jsonObj = tituloJson.getJSONObject(i);

                        JSONObject show = jsonObj.getJSONObject("show");
                        JSONObject ids = show.getJSONObject("ids");

                        int traktIdInt = ids.getInt("trakt");
                        listaTraktId.add(String.valueOf(traktIdInt));

                        int tmdbIdInt = ids.getInt("tmdb");
                        //listaTmdbId.add(String.valueOf(tmdbIdInt));

                        String urlTmdb = "http://api.themoviedb.org/3/tv/" + String.valueOf(tmdbIdInt) + "?api_key=012d9359357024d4a012077fa7b93581";
                        String urlTrakt = "https://api.trakt.tv/shows/" + String.valueOf(traktIdInt) + "/progress/watched";

                        try {
                            //carrega informacoes do Tmdb
                            if (SyncTmdb == null) {
                                dadosTmdb = new ShowsActivity.getUrl().execute(urlTmdb).get();
                                Editor.putString("dadosTmdb", dadosTmdb);
                            } else {
                                dadosTmdb = SyncTmdb;
                            }

                            JSONObject tmdbShow = new JSONObject(dadosTmdb);

                            String nameShow = tmdbShow.getString("original_name");
                            String posterPath = tmdbShow.getString("poster_path");

                            //   Snackbar.make(view, "Loading "+nameShow+" data", Snackbar.LENGTH_SHORT)
                            //           .setAction("Action", null).show();

                            listaNomes.add(nameShow);
                            listaImagens.add("https://image.tmdb.org/t/p/w500" + posterPath);
                            listaIds.add(tmdbShow.getString("id"));
                            // fim das informacoes do Tmdb


                            //carrega informacoes do Trakt
                            if (SyncTmdb == null) {
                                dadosTrakt = new getWatched().execute(urlTrakt).get();
                                Editor.putString("dadosTrakt", dadosTrakt);
                            } else {
                                dadosTrakt = SyncTraktEpisodes;
                            }

                            JSONObject dadosJsonTrakt = new JSONObject(dadosTrakt);

                            JSONObject nextEpisode = dadosJsonTrakt.getJSONObject("next_episode");

                            listaNextEpsiodeName.add(nextEpisode.getString("title"));
                            listaSeasonNumber.add(nextEpisode.getString("season"));
                            listaEpisodeNumber.add(nextEpisode.getString("number"));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    Snackbar.make(view, "Loading Page", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();



                    ArrayList episodes = prepareData();

                    //textView.setText(listaNomes.toString()+" "+listaNextEpsiodeName.toString());
                    listaNomes.clear();
                    listaImagens.clear();
                    listaIds.clear();
                    listaNextEpsiodeName.clear();
                    listaEpisodeNumber.clear();
                    listaSeasonNumber.clear();


                    */


            }
      //  }
    }
            /*


           //

            listaNomes.clear();
            listaImagens.clear();
            listaIds.clear();
            listaAno.clear();
            listaDadosTvShows.clear();
            listaSeasonNumber.clear();
            listaEpisodeNumber.clear();
            listaAirDate.clear();
            listaOverview.clear();
            listaPosts.clear();

           // EpisodeToWatchAdapter adapter = new EpisodeToWatchAdapter(view.getContext(),episodes);
           // recyclerView.setAdapter(adapter);
           */


    private class LoadingData extends AsyncTask<Void, String, ArrayList<Episode>> {


        ProgressDialog load;
        ArrayList<Episode> episodes = new ArrayList<Episode>( );

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("AsyncTask", "onPreExecute" + Thread.currentThread().getName());
            load = ProgressDialog.show(getContext(), "Por favor Aguarde ...",
                    "Baixando dados ...");
        }

        @Override
        protected ArrayList<Episode> doInBackground(Void... voids) {

            String urlGetWatched = "https://api.trakt.tv/sync/watched/shows?extended=noseasons";

            String SyncWatchedTrakt = CachedData.getString("dadosBrutosTrakt", null);
            String SyncTmdb = CachedData.getString("dadosTmdb", null);
            String SyncTraktEpisodes = CachedData.getString("dadosTrakt", null);

            SharedPreferences.Editor Editor = CachedData.edit();

            listaNomes.clear();
            listaImagens.clear();
            listaIds.clear();
            listaAno.clear();
            listaDadosTvShows.clear();
            listaSeasonNumber.clear();
            listaEpisodeNumber.clear();
            listaAirDate.clear();
            listaOverview.clear();
            listaPosts.clear();


            try {
                if (SyncWatchedTrakt == null) {
                    //dadosBrutosTrakt = new getWatched().execute(urlGetWatched).get();
                    dadosBrutosTrakt = getWatchedGo(urlGetWatched);
                    Editor.putString("dadosBrutosTrakt", dadosBrutosTrakt);
                    Editor.commit();
                } else {
                    dadosBrutosTrakt = SyncWatchedTrakt;
                    //Snackbar.make(view, "dadosBrutosTrakt", Snackbar.LENGTH_SHORT)
                    //    .setAction("Action", null).show();
                }

                JSONArray tituloJson = new JSONArray(dadosBrutosTrakt);

                for (int i = 0; i < 4; i++) {

                    JSONObject jsonObj = tituloJson.getJSONObject(i);

                    JSONObject show = jsonObj.getJSONObject("show");
                    JSONObject ids = show.getJSONObject("ids");

                    int traktIdInt = ids.getInt("trakt");
                    listaTraktId.add(String.valueOf(traktIdInt));

                    int tmdbIdInt = ids.getInt("tmdb");
                    //listaTmdbId.add(String.valueOf(tmdbIdInt));

                    //listaIds.add(traktIdInt.toString());

                    String urlTmdb = "http://api.themoviedb.org/3/tv/" + String.valueOf(tmdbIdInt) + "?api_key=012d9359357024d4a012077fa7b93581";
                    String urlTrakt = "https://api.trakt.tv/shows/" + String.valueOf(traktIdInt) + "/progress/watched";
                    try {
                        SyncTmdb = null;
                        //if (SyncTmdb == null) {
                            dadosTmdb = getWatchedGo(urlTmdb);
                        //    Editor.putString("dadosTmdb", dadosTmdb);
                       // } else {
                          //  dadosTmdb = SyncTmdb;
                        //}

                        //carrega informacoes do Trakt

                      //  if (SyncTraktEpisodes == null) {
                            dadosTrakt = getWatchedGo(urlTrakt);
                       //     Editor.putString("dadosTrakt", dadosTrakt);
                       //     Editor.commit();

                         //   Snackbar.make(view, "dadosTrakt null ", Snackbar.LENGTH_SHORT)
                                  //  .setAction("Action", null).show();
                       // } else {
                         //   dadosTrakt = SyncTraktEpisodes;
                        //    CachedData.edit().remove("dadosTrakt").commit();
                            //Snackbar.make(view, dadosTrakt, Snackbar.LENGTH_SHORT)
                                  //  .setAction("Action", null).show();
                      //  }


                        //dados TMDb

                        JSONObject tmdbShow = new JSONObject(dadosTmdb);

                        String nameShow = tmdbShow.getString("original_name");
                        String posterPath = tmdbShow.getString("poster_path");
                        listaNomes.add(nameShow);
                        listaImagens.add("https://image.tmdb.org/t/p/w500" + posterPath);
                        listaIds.add(tmdbShow.getString("id"));


                        JSONObject dadosJsonTrakt = new JSONObject(dadosTrakt);

                        JSONObject nextEpisode = dadosJsonTrakt.getJSONObject("next_episode");

                        listaNextEpsiodeName.add(nextEpisode.getString("title"));
                        listaSeasonNumber.add(nextEpisode.getString("season"));
                        listaEpisodeNumber.add(nextEpisode.getString("number"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            episodes = prepareData();

            return episodes;
        }


        @Override
        protected void onPostExecute(ArrayList episodes) {
            super.onPostExecute(episodes);

            if(episodes != null){
                //textView.setText("valor setado "+listaNextEpsiodeName.toString());

                episodesToWatchNew = episodes;

                Log.i("AsyncTask", "onPostExecute" + Thread.currentThread().getName());
                Snackbar.make(view, "Episodes Loaded Succesfully", Snackbar.LENGTH_SHORT)
                   .setAction("Action", null).show();
            }
            load.dismiss();
        }

        /*
                    String urlTmdb = "http://api.themoviedb.org/3/tv/" + String.valueOf(tmdbIdInt) + "?api_key=012d9359357024d4a012077fa7b93581";


                    try {
                        //carrega informacoes do Tmdb
                        if (SyncTmdb == null) {
                            dadosTmdb = new ShowsActivity.getUrl().execute(urlTmdb).get();
                            Editor.putString("dadosTmdb", dadosTmdb);
                            Editor.commit();

                            Snackbar.make(view, "dadosTmdb null", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        } else {
                            dadosTmdb = SyncTmdb;

                            Snackbar.make(view, "dadosTmdb", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }

                        JSONObject tmdbShow = new JSONObject(dadosTmdb);

                        String nameShow = tmdbShow.getString("original_name");
                        String posterPath = tmdbShow.getString("poster_path");

                           //Snackbar.make(view, "Loading "+nameShow+" data", Snackbar.LENGTH_SHORT)
                            //      .setAction("Action", null).show();

                        listaNomes.add(nameShow);
                        listaImagens.add("https://image.tmdb.org/t/p/w500" + posterPath);
                        listaIds.add(tmdbShow.getString("id"));
                        // fim das informacoes do Tmdb

                }
            }catch (Exception e ){

            }*/
    }



    private ArrayList prepareData(){

        ArrayList episodes = new ArrayList<>();
        for(int i=0;i<listaIds.size();i++){
            Episode episode = new Episode();

            episode.setShow(listaNomes.get(i).toString());
            episode.setShow_id(listaIds.get(i).toString());
            episode.setEpisode_name(listaNextEpsiodeName.get(i).toString());
            episode.setImage(listaImagens.get(i).toString());
            episode.setSeason_num(listaSeasonNumber.get(i).toString());
            episode.setEpsiode_num(listaEpisodeNumber.get(i).toString());
            //episode.setAir_date(listaAirDate.get(i).toString());
            //episode.setOverview(listaOverview.get(i).toString());
            episodes.add(episode);
        }
        return episodes;
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



    private class getWatched extends AsyncTask<String,String,String> {

        protected String doInBackground(String... theUrl){
            StringBuilder content = new StringBuilder();

            try{

                URL url = new URL(theUrl[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer cb87384d096983219126e98855b483d956f15621c5e33040fb5cfc89284654b7");
                urlConnection.setRequestProperty("trakt-api-version", "2");
                urlConnection.setRequestProperty("trakt-api-key", "66814d4492b483819836c918de4a0566af0d026d5d505dbc68276cf2fe04f5fe");

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

            return content.toString();
        }
    }

    private String getWatchedGo(String theurl){
        StringBuilder content = new StringBuilder();

        try{

            URL url = new URL(theurl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer cb87384d096983219126e98855b483d956f15621c5e33040fb5cfc89284654b7");
            urlConnection.setRequestProperty("trakt-api-version", "2");
            urlConnection.setRequestProperty("trakt-api-key", "66814d4492b483819836c918de4a0566af0d026d5d505dbc68276cf2fe04f5fe");

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

        return content.toString();
    }


    private class getShowFromTrakt extends AsyncTask<String,String,String> {

        protected String doInBackground(String... theUrl){
            StringBuilder content = new StringBuilder();

            try{

                URL url = new URL(theUrl[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer cb87384d096983219126e98855b483d956f15621c5e33040fb5cfc89284654b7");
                urlConnection.setRequestProperty("trakt-api-version", "2");
                urlConnection.setRequestProperty("trakt-api-key", "66814d4492b483819836c918de4a0566af0d026d5d505dbc68276cf2fe04f5fe");

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

            return content.toString();
        }
    }
}
