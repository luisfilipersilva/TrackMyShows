package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class AuthenticationActivity extends AppCompatActivity {

    public String request;
    public String request_token;

    public String session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
    }

    protected void onStart( ){
        super.onStart();

        //Step 1: Create a request token

        String urlToken = "https://api.themoviedb.org/3/authentication/token/new?api_key=012d9359357024d4a012077fa7b93581";
        try{
            request = new getUrl().execute(urlToken).get();
            JSONObject dadosJson = new JSONObject(request);
            request_token = dadosJson.getString("request_token");
            //String sucess = dadosJson.getString("success");

            SharedPreferences sp =getSharedPreferences("Userdata", MODE_PRIVATE);
            SharedPreferences.Editor Ed =sp.edit();
            Ed.putString("request_token",request_token );
            Ed.putBoolean("authentication_request",true);
            Ed.commit();

        }catch (Exception e ){
           e.printStackTrace();
        }

        //Step 2: Ask the user for permission

        String ask_permission = "https://www.themoviedb.org/authenticate/"+request_token;
        openWebURL(ask_permission);
        finish();
    }

    public void openWebURL( String inURL ) {
        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( inURL ) ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);

        startActivity( browse );
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
