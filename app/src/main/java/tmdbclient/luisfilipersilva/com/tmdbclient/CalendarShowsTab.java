package tmdbclient.luisfilipersilva.com.tmdbclient;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarShowsTab extends Fragment {


    public CalendarShowsTab() {
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
    public static ArrayList<String> listaEpisodeNumber = new ArrayList<String>( );
    public static ArrayList<String> listaSeasonNumber = new ArrayList<String>( );
    public static ArrayList<String> listaAirDate = new ArrayList<String>( );
    public static ArrayList<String> listaOverview = new ArrayList<String>( );

    public static ArrayList<String> listaIds = new ArrayList<String>( );
    public static ArrayList<String> listaStatus = new ArrayList<String>( );

    public static ArrayList<String> listaDadosTvShows = new ArrayList<String>( );
    public String[ ] imageStock;
    public String[ ] listaid;
    public String[ ] idlista;
    public String[ ] listaNome;
    public String[ ] listaNotasTmdb;
    public String[ ] listaVotosTmdb;

    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd");
    Calendar c = Calendar.getInstance();

    FloatingActionButton fab;
    RecyclerView recyclerView;
    View view;

    public String dadosBrutos;
    public Integer seasonN = null;
    public  String seasonNumber = null;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendarshowstab, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewCalendarShowsTab);

        //seta o fragment como visible para carregar os dados.
        if(isMenuVisible()) {
            setUserVisibleHint(true);
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && view != null) {
            ShowsActivity activity = (ShowsActivity) getActivity();
            dadosBrutos = activity.getDadosUrl();

            Integer cont = null;

            try {
                JSONObject dadosJson = null;
                dadosJson = new JSONObject(dadosBrutos);
                JSONArray tituloJson = dadosJson.getJSONArray("results"); // Omdb: "Search"
                cont = tituloJson.length();

                for (int i = 0; i < tituloJson.length(); i++) {
                    JSONObject filme = tituloJson.getJSONObject(i);

                    String idTvShow = filme.getString("id");
                    //listaIds.add(idTvShow);
                    String urltvshow = "http://api.themoviedb.org/3/tv/"+idTvShow+"?api_key=012d9359357024d4a012077fa7b93581";

                    String tvshowdata = new ShowsActivity.getUrl().execute(urltvshow).get();
                    JSONObject dadosJsonShow = new JSONObject(tvshowdata);
                    String production = dadosJsonShow.getString("in_production");
                    if(production.equals("true")){
                        String numSeasons = dadosJsonShow.getString("number_of_seasons");
                        String name = dadosJsonShow.getString("original_name");

                        urltvshow = "http://api.themoviedb.org/3/tv/" + idTvShow + "/season/"+numSeasons+"?api_key=012d9359357024d4a012077fa7b93581";

                        tvshowdata = new ShowsActivity.getUrl().execute(urltvshow).get();
                        JSONObject dadosJsonEpisode = new JSONObject(tvshowdata);
                        JSONArray tituloJsonEpisode = dadosJsonEpisode.getJSONArray("episodes"); // Omdb: "Search"

                        for (int i2 = 0; i2 < tituloJsonEpisode.length(); i2++) {
                            JSONObject episode = tituloJsonEpisode.getJSONObject(i2);

                            String air_date = episode.getString("air_date");
                            String nameEpisode = episode.getString("name");

                            String getCurrentDateTime = simpleDateFormat.format(c.getTime());

                            //retorna positivo quando o dia atual (getCurrentDateTime) e maior que a
                            //data do episodio (air_date)
                            if(getCurrentDateTime.compareTo(air_date) <= 0){

                                //seasonN = Integer.parseInt(episode.getString("season_number"));
                                //if(seasonN > 10){
                                //  seasonNumber = "0"+episode.getString("season_number");
                                //}

                                listaDadosTvShows.add(episode.getString("name")); // nome episodio
                                //listaDadosTvShows.add(episode.getString("air_date"));
                                listaPosts.add(dadosJsonEpisode.getString("poster_path")); //Poster Season
                                listaSeasonNumber.add(dadosJsonEpisode.getString("name")); // Title Show
                                listaIds.add(episode.getString("id")); //id episode
                                listaEpisodeNumber.add("S"+episode.getString("season_number")+"E"+episode.getString("episode_number"));
                                listaNomes.add(name);
                                listaAirDate.add(air_date);
                                listaOverview.add(episode.getString("overview"));
                            }
                        }
                    }
                }

                //gerando link de imagens
                String linkImages = "https://image.tmdb.org/t/p/w500";
                for (int i = 0; i < listaSeasonNumber.size(); i++) {

                    listaFilmes.add(listaSeasonNumber.get(i));
                    listaImagens.add(linkImages + listaPosts.get(i));
                }

                idlista = listaIds.toArray(new String[listaIds.size()]);
            }catch (Exception e ){
                e.printStackTrace();
            }
            //textView.setText(listaIds.toString()+" "+listaImagens.toString()+" "+listaDadosTvShows.toString()+" "+listaNomes.toString());

            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),1 );
            recyclerView.setLayoutManager(layoutManager);

            ArrayList episodes = prepareData();

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

            Collections.sort(episodes, new Comparator<Episode>(){

                @Override
                public int compare(Episode o1, Episode o2) {
                    return o1.getAir_date().compareTo(o2.getAir_date());
                }
            });

            EpisodeAdapter adapter = new EpisodeAdapter(view.getContext(),episodes);
            recyclerView.setAdapter(adapter);

        }
    }

    private ArrayList prepareData(){

        ArrayList episodes = new ArrayList<>();
        for(int i=0;i<idlista.length;i++){
            Episode episode = new Episode();

            episode.setShow(listaNomes.get(i).toString());
            episode.setEpisode_id(listaIds.get(i).toString());
            episode.setEpisode_name(listaDadosTvShows.get(i).toString());
            episode.setImage(listaImagens.get(i).toString());
            episode.setSeason_num(listaSeasonNumber.get(i).toString());
            episode.setEpsiode_num(listaEpisodeNumber.get(i).toString());
            episode.setAir_date(listaAirDate.get(i).toString());
            episode.setOverview(listaOverview.get(i).toString());
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
}
