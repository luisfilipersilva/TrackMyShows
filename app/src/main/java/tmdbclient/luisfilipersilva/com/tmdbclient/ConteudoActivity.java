package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ConteudoActivity extends AppCompatActivity {}

    /*
    private ImageView imageView;
    private ImageView imageViewPoster;
    private TextView textView;
    private TextView textoPlot;

    public String textoFilme;
    public String plot;

    public String dadosBrutos;

    public String dadosImagens;
    public String imagemFundo;
    public static ArrayList<String> listaImagens = new ArrayList<String>( );

  //  @Override
  //  protected void onCreate(Bundle savedInstanceState) {
        /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);

        imageView = findViewById(R.id.app_bar_image);
        textView = findViewById(R.id.textTitulo);
        imageViewPoster = findViewById(R.id.imagemPrincipal);
        textoPlot = findViewById(R.id.plot);

        Bundle extra = getIntent().getExtras();
        String id = extra.getString("id");
        String imagemPrincipal = extra.getString("imagem");
        //textView.setText("texto de Exemplo da App Bar");

        //Picasso.with(getApplicationContext()).load(imagemPrincipal).into(imageView);
        Picasso.with(getApplicationContext()).load(imagemPrincipal).into(imageViewPoster);

        Moviedata moviedata = new Moviedata();
        String urlFinal = moviedata.montarUrl(id);

        //script para pegar imagem de fundo
        //String linkImagemFundo = moviedata.pegarImagemFundo(id);
        try{
           // dadosImagens = new getUrl().execute(linkImagemFundo).get();
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

            Picasso.with(getApplicationContext()).load(imagemFundo).placeholder(R.drawable.ic_launcher_background).into(imageView);


        }catch (Exception e){

        }

        //Fim do Codigo de Imagens de Fundo



        try {
            dadosBrutos = new getUrl().execute(urlFinal).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject dadosJson = new JSONObject(dadosBrutos);

            textoFilme = dadosJson.getString("original_title");
            plot = dadosJson.getString("overview");


        }catch (Exception e){
            e.printStackTrace();
        }

        textView.setText(textoFilme);
        textoPlot.setText("Plot: "+plot);

        //listaNotas.clear();
        listaImagens.clear();
        //listaGenre.clear();
        //textoFilme = "";

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
            //return content.toString();
       // }
   // }
//}
