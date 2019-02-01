package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Luis Filipe on 01/01/2018.
 */

public class Moviedata {

    private static final String URL_API_1 = "http://api.themoviedb.org/3/movie/";
    private static final String URL_API_2 = "?api_key=012d9359357024d4a012077fa7b93581&external_source=imdb_id";

    private static final String API_KEY = "fa92ab6c";
    private static final String URL_API_OMDB = "http://www.omdbapi.com/?apikey="+API_KEY+"&i=";

    public static String montarUrlSearch(String id, String type){

        String urlCompleta = "http://api.themoviedb.org/3/"+type+"/"+id+URL_API_2;

        return urlCompleta;
    }


    public static String getAccountStates(String id, String type, String session_id){

        String urlCompleta = "http://api.themoviedb.org/3/"+type+"/"+id+"/account_states"+URL_API_2+
                "&session_id="+session_id;

        return urlCompleta;
    }

    public static String montarUrl(String imdbId){

        //filme = filme.replaceAll(" ","_");

        String urlCompleta = URL_API_1+imdbId+URL_API_2;

        return urlCompleta;
    }

    public static String montarUrlOmdb( String imdbId ){

        String urlCompleta = URL_API_OMDB+imdbId;

        return urlCompleta;

    }

    public static String montarUrlBusca( String query){

        String urlCompleta = "http://api.themoviedb.org/3/search/multi?api_key=012d9359357024d4a012077fa7b93581&query="+query;
        return urlCompleta;
    }


    public static class getUrl extends AsyncTask<String,String,String> {

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

    //query para pegar imagens de fundo

    public static String pegarImagemFundo(String id, String type){

        String URL_IMAGEM = "http://api.themoviedb.org/3/"+type+"/"+id+"/images?api_key=012d9359357024d4a012077fa7b93581&external_source=imdb_id";

        return URL_IMAGEM;
    }
}
