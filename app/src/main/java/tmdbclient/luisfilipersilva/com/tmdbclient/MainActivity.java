package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public String transferirValor  = "Valor transferido hahaha";

    public String dadosBrutos;

    public static ArrayList<String> listaNomes = new ArrayList<String>( );
    public static ArrayList<String> listaAnos = new ArrayList<String>( );
    public static ArrayList<String> listaFilmes = new ArrayList<String>( );
    public static ArrayList<String> listaPosts = new ArrayList<String>( );
    public static ArrayList<String> listaImagens = new ArrayList<String>( );
    public static ArrayList<String> listaImdbId = new ArrayList<String>( );
    public static ArrayList<String> listaNotaTmdb = new ArrayList<String>( );
    public static ArrayList<String> listaVotosTmdb = new ArrayList<String>( );

    //Tab Popular
    public static ArrayList<String> listaPostersPopular = new ArrayList<String>( );
    public static ArrayList<String> listaImagensPopular = new ArrayList<String>( );

    public String linkImages = "https://image.tmdb.org/t/p/w500";


    public String[ ] labelFilmes;
    public String[ ] imageStock;
    public String[ ] imagePopular;
    public String[ ] imageTopRated;
    public String[ ] notasTmdb;
    public String[ ] votosTmdb;


    public String[ ] listaId;
    public String[ ] listaIdPopular;
    public String[ ] listaIdTopRated;

    public String[ ] labelFilmesNosCinemas;

    //Ppular
    public String[ ] labelFilmesPopular;
    public String[ ] notasTmdbPopular;
    public String[ ] votosTmdbPopular;

    //TopRated
    public String[ ] labelFilmesTopRated;
    public String[ ] notasTmdbTopRated;
    public String[ ] votosTmdbTopRated;


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private String[] drawerListViewItems;
    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
                listaNotaTmdb.add(filme.getString("vote_average"));
                listaVotosTmdb.add(filme.getString("vote_count"));
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
        labelFilmesNosCinemas = listaFilmes.toArray(new String[listaFilmes.size()]);
        listaId = listaImdbId.toArray(new String[listaImdbId.size()]);
        notasTmdb = listaNotaTmdb.toArray(new String[listaNotaTmdb.size()]);
        votosTmdb = listaVotosTmdb.toArray(new String[listaVotosTmdb.size()]);

        listaImagens.clear();
        listaFilmes.clear();
        listaPosts.clear( );
        listaImdbId.clear();
        listaNomes.clear();
        listaNotaTmdb.clear();
        listaVotosTmdb.clear();

        //fim do codigo/

        //URl Popular
        String urlPopular = "https://api.themoviedb.org/3/movie/popular?api_key=012d9359357024d4a012077fa7b93581&language=en-US&page=1";
        try{
            dadosBrutos = new getUrl().execute(urlPopular).get();

        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject dadosJson = new JSONObject(dadosBrutos);
            JSONArray tituloJson = dadosJson.getJSONArray("results"); // Omdb: "Search"

            for (int i = 0; i < tituloJson.length(); i++) {
                JSONObject filme = tituloJson.getJSONObject(i);

                String FilmeNome = filme.getString("title");
                String ImagemFilmeNome = filme.getString("poster_path");

                listaNomes.add(filme.getString("title")); // Title
                //listaAnos.add(filme.getString("Year"));
                listaPosts.add(filme.getString("poster_path")); //Poster
                listaImdbId.add(filme.getString("id")); //imdbID
                listaNotaTmdb.add(filme.getString("vote_average"));
                listaVotosTmdb.add(filme.getString("vote_count"));

            }
        }catch (Exception e ){
            e.printStackTrace();
        }

        //gerando link de imagens
        String linkImagesPopular = "https://image.tmdb.org/t/p/w500";
        for (int i = 0; i < listaNomes.size(); i++) {

            listaFilmes.add(listaNomes.get(i));
            listaImagens.add(linkImagesPopular+listaPosts.get(i));
        }

        imagePopular = listaImagens.toArray(new String[listaImagens.size()]);
        labelFilmesPopular = listaFilmes.toArray(new String[listaFilmes.size()]);
        notasTmdbPopular = listaNotaTmdb.toArray(new String[listaNotaTmdb.size()]);
        votosTmdbPopular = listaVotosTmdb.toArray(new String[listaVotosTmdb.size()]);
        listaIdPopular = listaImdbId.toArray(new String[listaImdbId.size()]);

        listaImagens.clear();
        listaFilmes.clear();
        listaPosts.clear( );
        listaImdbId.clear();
        listaNomes.clear();
        listaNotaTmdb.clear();
        listaVotosTmdb.clear();

        //fim do codigo

        //URL Top Rated Movies
        String urlTopRated = "https://api.themoviedb.org/3/movie/top_rated?api_key=012d9359357024d4a012077fa7b93581&language=en-US&page=1";
        try{
            dadosBrutos = new getUrl().execute(urlTopRated).get();

        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject dadosJson = new JSONObject(dadosBrutos);
            JSONArray tituloJson = dadosJson.getJSONArray("results"); // Omdb: "Search"

            for (int i = 0; i < tituloJson.length(); i++) {
                JSONObject filme = tituloJson.getJSONObject(i);

                //String FilmeNome = filme.getString("title");
                //String ImagemFilmeNome = filme.getString("poster_path");

                listaNomes.add(filme.getString("title")); // Title
                //listaAnos.add(filme.getString("Year"));
                listaPosts.add(filme.getString("poster_path")); //Poster
                listaImdbId.add(filme.getString("id")); //imdbID
                listaNotaTmdb.add(filme.getString("vote_average"));
                listaVotosTmdb.add(filme.getString("vote_count"));

            }
        }catch (Exception e ){
            e.printStackTrace();
        }

        //gerando link de imagens
        linkImages = "https://image.tmdb.org/t/p/w500";
        for (int i = 0; i < listaNomes.size(); i++) {

            listaFilmes.add(listaNomes.get(i));
            listaImagens.add(linkImages+listaPosts.get(i));
        }

        imageTopRated = listaImagens.toArray(new String[listaImagens.size()]);
        labelFilmesTopRated = listaFilmes.toArray(new String[listaFilmes.size()]);
        notasTmdbTopRated = listaNotaTmdb.toArray(new String[listaNotaTmdb.size()]);
        votosTmdbTopRated = listaVotosTmdb.toArray(new String[listaVotosTmdb.size()]);
        listaIdTopRated = listaImdbId.toArray(new String[listaImdbId.size()]);

        listaImagens.clear();
        listaFilmes.clear();
        listaPosts.clear( );
        listaImdbId.clear();
        listaNomes.clear();
        listaNotaTmdb.clear();
        listaVotosTmdb.clear();

        //fim do codigo
    }


    protected void onResume(){
        super.onResume();
        SharedPreferences sp1= this.getSharedPreferences("Userdata", MODE_PRIVATE);

        //sp1.edit().remove("request_token").commit();
        //sp1.edit().remove("authentication_request").commit();

        Boolean authentication_request = sp1.getBoolean("authentication_request", false);

        //Step 3: Create a session ID
        if(authentication_request.equals(true)){

            String request_token = sp1.getString("request_token", null);

            String urlCreateSessoionId = "https://api.themoviedb.org/3/authentication/session/new?api_key=012d9359357024d4a012077fa7b93581&request_token="+request_token;
            try{
                String request = new getUrl().execute(urlCreateSessoionId).get();

                JSONObject dadosJson = new JSONObject(request);
                String session_id = dadosJson.getString("session_id");
                String sucess = dadosJson.getString("success");

                String username = null;
                String idAccount = null;
                if(sucess.equals("true")){
                    //pegar username
                    String urlgetdetails = "https://api.themoviedb.org/3/account?api_key=012d9359357024d4a012077fa7b93581&session_id="+session_id;
                    try{
                        String accountDetails = new getUrl().execute(urlgetdetails).get();

                        JSONObject accountJson = new JSONObject(accountDetails);
                        username = accountJson.getString("username");
                        idAccount = accountJson.getString("id");

                    }catch (Exception e ){
                        e.printStackTrace();
                    }

                    SharedPreferences sp = getSharedPreferences("Userdata", MODE_PRIVATE);
                    SharedPreferences.Editor Ed =sp.edit();
                    Ed.putString("session_id",session_id );
                    Ed.putString("username",username );
                    Ed.putString("idaccount",idAccount );
                    Ed.commit();

                    Toast.makeText(MainActivity.this,"Conectado ao TMDb como "+username,Toast.LENGTH_LONG).show();
                }

            }catch (Exception e ){
                e.printStackTrace();
            }
            sp1.edit().remove("authentication_request").commit();
        }else{
            //valida acesso previo
            SharedPreferences sp = getSharedPreferences("Userdata", MODE_PRIVATE);
            String request_token = sp.getString("request_token", null);
            String username = sp.getString("username", null);

            if(request_token != null){
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();
                menu.findItem(R.id.connect_tmdb).setTitle(username);

                //TextView navHeaderUsername = (TextView) menu.findItem(R.id.nav_header_username).setTitle("USERNAME");
            }


        }
    }

    protected void onPause(){
        super.onPause();

        //Toast.makeText(MainActivity.this,"onPause",Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this, ((TextView)view).getText(), Toast.LENGTH_LONG).show();
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    public String[] getLabelFilmes() {
        return labelFilmesNosCinemas;
    }

    public String[] getMyData() {
        return imageStock;
    }

    public String[] getTmdbId() {
        return listaId;
    }

    public String[] getPopular() {
        return imagePopular;
    }

    public String[] getTopRated() {
        return imageTopRated;
    }

    public String[] getNotasTmdb() {
        return notasTmdb;
    }

    public String[] getVotosTmdb() {
        return votosTmdb;
    }

    public String[] getLabelFilmesPopular() {
        return labelFilmesPopular;
    }

    public String[] getNotasTmdbPopular() {
        return notasTmdbPopular;
    }

    public String[] getVotosTmdbPopular() {
        return votosTmdbPopular;
    }
    public String[] getTmdbIdPopular() {
        return listaIdPopular;
    }

    public String[] getLabelFilmesTopRated() {
        return labelFilmesTopRated;
    }

    public String[] getNotasTmdbTopRated() {
        return notasTmdbTopRated;
    }

    public String[] getVotosTmdbTopRated() {
        return votosTmdbTopRated;
    }
    public String[] getTmdbIdTopRated() {
        return listaIdTopRated;
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.search){
           // Intent intent = new Intent(MainActivity.this,SearchActivity.class);
           // startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String nomeItem = item.getTitle().toString();

        if (id == R.id.nav_movies) {
            //pagina atual, nada a fazer

        } else if (id == R.id.nav_shows) {
            Intent intent = new Intent(MainActivity.this,ShowsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_fav) {
            //Toast.makeText(MainActivity.this,"SideShow",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this,WatchlistActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.connect_tmdb) {

            SharedPreferences sp1= this.getSharedPreferences("Userdata", MODE_PRIVATE);
            String request_token = sp1.getString("request_token", null);

            if(request_token == null){

                Intent intent = new Intent(MainActivity.this,AuthenticationActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this,"Conectado ao TMDb",Toast.LENGTH_SHORT).show();
            }
        }

        else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    Tab1 tab1 = new Tab1();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
                case 2:
                    Tab3 tab3 = new Tab3();
                    return tab3;
            }
            return null;
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


    //pegar Dados da API

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
