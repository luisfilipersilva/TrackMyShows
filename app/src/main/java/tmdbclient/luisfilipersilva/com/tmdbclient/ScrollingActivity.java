package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScrollingActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;

    public String textoFilme;
    public String plot;
    public String year;
    public String runtime;

    public String dadosBrutos;

    public String dadosImagens;
    public String imagemFundo;
    public static ArrayList<String> listaImagens = new ArrayList<String>( );

    public String imdbId;

    public String imdbRating;
    public String metacritcsRating;
    public String rottenRating;
    public String tmdbRating;
    public String mediaType;


    private ImageView imageView;
    private TextView textoPlot;
    private TextView textoInfo;
    private TextView valorTmdb;
    private TextView valorImdb;
    private ImageView logoRotten;
    private TextView valorRotten;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private FloatingActionButton fab_watchlist;
    private FloatingActionButton fab_rate;

    public String id;
    public String imagemPrincipal;
    public String media_type;

    public String idaccount;
    public String session_id;

    public String favorite = "false";
    public String watchlist = "false";

    Moviedata moviedata = new Moviedata();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //elementos da tela
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        imageView = findViewById(R.id.imagePoster);
        textoPlot = findViewById(R.id.textoPlot);
        textoInfo = findViewById(R.id.textInfo);
        valorImdb = findViewById(R.id.valorImdb);
        valorTmdb = findViewById(R.id.valorTmdb);
        logoRotten = findViewById(R.id.logoRotten);
        valorRotten = findViewById(R.id.valorRotten);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_watchlist = (FloatingActionButton) findViewById(R.id.fab_watchlist);
        fab_rate = (FloatingActionButton) findViewById(R.id.fab_rate);
        //textRatings = findViewById(R.id.textRatings);
        //

        Bundle extra = getIntent().getExtras();
        id = extra.getString("id");
        imagemPrincipal = extra.getString("imagem");
        media_type = extra.getString("media_type");

        if(media_type == null){
            media_type = "movie";
        }
    }

    protected void onResume() {
        super.onResume();

        Picasso.with(getApplicationContext()).load(imagemPrincipal).into(imageView);

        String urlFinal = moviedata.montarUrlSearch(id,media_type);

        //script para pegar imagem de fundo
        String linkImagemFundo = moviedata.pegarImagemFundo(id,media_type);
        try{
            dadosImagens = new getUrl().execute(linkImagemFundo).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        // fim do script

        try{
            JSONObject dadosImagensJson = new JSONObject(dadosImagens);
            JSONArray arrayDados = dadosImagensJson.getJSONArray("backdrops");

            for (int i = 0; i < arrayDados.length(); i++) {
                JSONObject filme = arrayDados.getJSONObject(i);

                listaImagens.add(filme.getString("file_path"));
                //imagemFundo = filme.getString("file_path");
            }

            Random random = new Random( );
            imagemFundo = listaImagens.get(random.nextInt(listaImagens.size()));
            imagemFundo = "https://image.tmdb.org/t/p/w500"+imagemFundo;


            //Codigo Especial do Picasso para carregar a imagem de fundo
            final ImageView img = new ImageView(this);
            Picasso.with(img.getContext())
                    .load(imagemFundo)
                    .into(img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            collapsingToolbarLayout.setBackgroundDrawable(img.getDrawable());
                        }

                        @Override
                        public void onError() {
                        }
                    });
            // fim do codigo

            //Picasso.with(getApplicationContext()).load(imagemFundo).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }catch (Exception e){

        }



        try {
            dadosBrutos = new getUrl().execute(urlFinal).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject dadosJson = new JSONObject(dadosBrutos);

            if (media_type.equals("tv")){
                textoFilme = dadosJson.getString("original_name");
                year = dadosJson.getString("first_air_date");
                year = year.substring(0,4);
            }
            else{
                textoFilme = dadosJson.getString("original_title");
                year = dadosJson.getString("release_date");
                year = year.substring(0,4);
                runtime = dadosJson.getString("runtime");
                imdbId = dadosJson.getString("imdb_id");
            }
            plot = dadosJson.getString("overview");
            tmdbRating = dadosJson.getString("vote_average");
            id = dadosJson.getString("id");


        }catch (Exception e){
            e.printStackTrace();
        }
        //textView.setText(textoFilme);
        toolbar.setTitle(textoFilme);
        //toolbar.setSubtitle(textoFilme);
        textoPlot.setText("Plot: "+plot);
        textoInfo.setText(year+" "+runtime);

        //listaNotas.clear();
        listaImagens.clear();
        //listaGenre.clear();
        //textoFilme = "";

        String urlOmdb = moviedata.montarUrlOmdb(imdbId);
        try{
            dadosBrutos = new getUrl().execute(urlOmdb).get();
        }catch (Exception e ) {
        }

        try {
            JSONObject dadosJson = new JSONObject(dadosBrutos);

            imdbRating = dadosJson.getString("imdbRating");
            metacritcsRating = dadosJson.getString("Metascore");

            JSONArray ratings = dadosJson.getJSONArray("Ratings");
            JSONObject sources = ratings.getJSONObject(1);
            rottenRating = sources.getString("Value");

        }catch (Exception e){
            e.printStackTrace();
        }
        valorTmdb.setText(tmdbRating);

        valorImdb.setText(imdbRating);
        valorRotten.setText(rottenRating);

        SharedPreferences sp = getSharedPreferences("Userdata", MODE_PRIVATE);
        idaccount = sp.getString("idaccount", null);
        session_id = sp.getString("session_id", null);

        //getting account states

        String account_states_url = moviedata.getAccountStates(id,media_type,session_id);

        try{
            String account_states_data = new getUrl().execute(account_states_url).get();
            JSONObject account_statesJson = new JSONObject(account_states_data);

            favorite = account_statesJson.getString("favorite");
            watchlist = account_statesJson.getString("watchlist");

            if(favorite.equals("true")){
                fab.setImageResource(R.drawable.ic_favorite_white_48dp);
                fab.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.favoriteTrue), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            if(watchlist.equals("true")){
                fab_watchlist.setImageResource(R.drawable.ic_bookmark_white_48dp);
                fab_watchlist.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.watchlistTrue), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(idaccount == null){
            // Snackbar.make(view, "Account Null", Snackbar.LENGTH_LONG)
            //       .setAction("Action", null).show();
            idaccount = "7676793";
            Toast.makeText(getApplicationContext(),"idAccount vazio",Toast.LENGTH_LONG).show();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(favorite.equals("false")){

                    fab.setImageResource(R.drawable.ic_favorite_white_48dp);
                    fab.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.favoriteTrue), android.graphics.PorterDuff.Mode.MULTIPLY);

                    String url = "https://api.themoviedb.org/3/account/"+idaccount+"/favorite?api_key=012d9359357024d4a012077fa7b93581&session_id="+session_id;

                    try {
                        new postUrl().execute(url,id,media_type).get();

                        Snackbar.make(view, R.string.fmsgFavoriteOK, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                        Snackbar.make(view, R.string.msgError, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (ExecutionException e) {
                        e.printStackTrace();

                        Snackbar.make(view, R.string.msgError, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    favorite = "true";

                }else{

                    fab.setImageResource(R.drawable.ic_favorite_border_white_48dp);
                    fab.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.iconDisabled), android.graphics.PorterDuff.Mode.MULTIPLY);

                    String url = "https://api.themoviedb.org/3/account/"+idaccount+"/favorite?api_key=012d9359357024d4a012077fa7b93581&session_id="+session_id;

                    try {
                        new removeFromFavorite().execute(url,id,media_type).get();

                        Snackbar.make(view,"Removido de Favoritos", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                        Snackbar.make(view, R.string.msgError, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (ExecutionException e) {
                        e.printStackTrace();

                        Snackbar.make(view, R.string.msgError, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                    favorite = "false";
                }
            }
        });

        fab_watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(watchlist.equals("false")){

                    fab_watchlist.setImageResource(R.drawable.ic_bookmark_white_48dp);
                    fab_watchlist.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.watchlistTrue), android.graphics.PorterDuff.Mode.MULTIPLY);

                    String urladdToWatchlist = "https://api.themoviedb.org/3/account/"+idaccount+"/watchlist?api_key=012d9359357024d4a012077fa7b93581&session_id="+session_id;
                    try {
                        new addToWatchlist().execute(urladdToWatchlist,id,media_type).get();

                        Snackbar.make(v, R.string.fmsgWatchlistOK, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                        Snackbar.make(v, R.string.msgError, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (ExecutionException e) {
                        e.printStackTrace();

                        Snackbar.make(v, R.string.msgError, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    watchlist = "true";

                }else{

                    fab_watchlist.setImageResource(R.drawable.ic_bookmark_border_white_48dp);
                    fab_watchlist.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.iconDisabled), android.graphics.PorterDuff.Mode.MULTIPLY);

                    String urladdToWatchlist = "https://api.themoviedb.org/3/account/"+idaccount+"/watchlist?api_key=012d9359357024d4a012077fa7b93581&session_id="+session_id;

                    try {
                        new removerFromWatchlist().execute(urladdToWatchlist,id,media_type).get();

                        Snackbar.make(v, "Removido da Watchlist", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                        Snackbar.make(v, R.string.msgError, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (ExecutionException e) {
                        e.printStackTrace();

                        Snackbar.make(v, R.string.msgError, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                    watchlist = "false";
                }
            }
        });
        fab_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fab_rate.setImageResource(R.drawable.ic_star_white_48dp);
                fab_rate.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.rateTrue), android.graphics.PorterDuff.Mode.MULTIPLY);

                Snackbar.make(v, R.string.fmsgRateOK, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        //logoRotten.setImageDrawable(getResources().getDrawable(R.drawable.certified_fresh));

        /*
        String percent = valorRotten.toString().substring(0,2);
        Integer percentNum = Integer.parseInt(percent);

        if(percentNum >= 60){
            if(percentNum >= 75){
                logoRotten.setImageDrawable(getResources().getDrawable(R.drawable.certified_fresh));
            }else{
                logoRotten.setImageDrawable(getResources().getDrawable(R.drawable.tomato_50));
            }
        }
        else{
            logoRotten.setImageDrawable(getResources().getDrawable(R.drawable.rotten_tomatoes_50_splash));
        }*/

        /*
        //background metacritics
        Integer percentMetascore = Integer.parseInt(metascore);
        if(percentMetascore <= 50){
            notaMeta.setBackgroundColor(Color.parseColor("#FF5555"));
        }else{
            notaMeta.setBackgroundColor(Color.parseColor("#2ECC71"));
        }*/

        //textRatings.setText(tmdbRating+" - "+imdbRating+" - "+metacritcsRating+" - "+rottenRating);
    }

    private class loadContent extends AsyncTask<String,String,String>{
        protected String doInBackground(String... params){

            String url = new String(params[0]);




            return null;
        }
    }

    private class postUrl extends AsyncTask<String,String,String>{

        protected String doInBackground(String... theUrl){
            StringBuilder content = new StringBuilder();

            OkHttpClient client = new OkHttpClient();

            //"{\"media_type\":\"movie\",\"media_id\":550,\"favorite\":true}"
            String url = new String(theUrl[0]);
            String idMedia = new String(theUrl[1]);
            String Media = new String(theUrl[2]);
            JSONObject jsonParam = new JSONObject();

            try {
                jsonParam.put("media_type", Media);
                jsonParam.put("media_id", idMedia);
                jsonParam.put("favorite", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //https://api.themoviedb.org/3/account/7676793/favorite?session_id=5f3fd8f3bd73479dba656e9747c6ceb8fa44a17d&api_key=012d9359357024d4a012077fa7b93581
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonParam.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json;charset=utf-8")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private class addToWatchlist extends AsyncTask<String,String,String>{

        protected String doInBackground(String... theUrl){
            StringBuilder content = new StringBuilder();

            OkHttpClient client = new OkHttpClient();

            //"{\"media_type\":\"movie\",\"media_id\":550,\"favorite\":true}"
            String url = new String(theUrl[0]);
            String idMedia = new String(theUrl[1]);
            String Media = new String(theUrl[2]);
            JSONObject jsonParam = new JSONObject();

            try {
                jsonParam.put("media_type", Media);
                jsonParam.put("media_id", idMedia);
                jsonParam.put("watchlist", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonParam.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json;charset=utf-8")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class removerFromWatchlist extends AsyncTask<String,String,String>{

        protected String doInBackground(String... theUrl){
            StringBuilder content = new StringBuilder();

            OkHttpClient client = new OkHttpClient();

            //"{\"media_type\":\"movie\",\"media_id\":550,\"favorite\":true}"
            String url = new String(theUrl[0]);
            String idMedia = new String(theUrl[1]);
            String Media = new String(theUrl[2]);
            JSONObject jsonParam = new JSONObject();

            try {
                jsonParam.put("media_type", Media);
                jsonParam.put("media_id", idMedia);
                jsonParam.put("watchlist", false);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonParam.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json;charset=utf-8")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private class removeFromFavorite extends AsyncTask<String,String,String>{

        protected String doInBackground(String... theUrl){
            StringBuilder content = new StringBuilder();

            OkHttpClient client = new OkHttpClient();

            String url = new String(theUrl[0]);
            String idMedia = new String(theUrl[1]);
            String Media = new String(theUrl[2]);
            JSONObject jsonParam = new JSONObject();

            try {
                jsonParam.put("media_type", Media);
                jsonParam.put("media_id", idMedia);
                jsonParam.put("favorite", false);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //https://api.themoviedb.org/3/account/7676793/favorite?session_id=5f3fd8f3bd73479dba656e9747c6ceb8fa44a17d&api_key=012d9359357024d4a012077fa7b93581
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonParam.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json;charset=utf-8")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
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
