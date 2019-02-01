package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

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

    private ActionBarDrawerToggle actionBarDrawerToggle;

    public String dadosBrutos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsShows);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void onResume(){
        super.onResume();
        SharedPreferences sp = getSharedPreferences("Userdata", MODE_PRIVATE);

        String session_id = sp.getString("session_id", null);
        String idAccount = sp.getString("idaccount", null);

        //dados de cache

        SharedPreferences cache = getSharedPreferences("Cache", MODE_PRIVATE);
        String cachedata = cache.getString("cachedata",null);

        if(cachedata == null){

            String url = "https://api.themoviedb.org/3/account/"+idAccount+"/favorite/tv?api_key=012d9359357024d4a012077fa7b93581&language=" +
                    "en-US&page=1&session_id="+session_id+"&sort_by=created_at.asc&append_to_response=in_production";
            try{
                dadosBrutos = new getUrl().execute(url).get();

            }catch(Exception e){
                e.printStackTrace();
            }
            SharedPreferences.Editor EdCache = cache.edit();
            EdCache.putString("cachedata",dadosBrutos );
            EdCache.commit();
        }else{
            dadosBrutos = cachedata;
        }
    }

    public String getDadosUrl() {
        return dadosBrutos;
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

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shows, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String nomeItem = item.getTitle().toString();

        if (id == R.id.nav_movies) {

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_shows) {
            //pagina atual, nada a fazer

        } else if (id == R.id.nav_fav) {
            //Toast.makeText(getApplicationContext(),"SideShow",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ShowsActivity.this,WatchlistActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.connect_tmdb) {

            SharedPreferences sp1= this.getSharedPreferences("Userdata", MODE_PRIVATE);
            String request_token = sp1.getString("request_token", null);

            if(request_token == null){

                Intent intent = new Intent(getApplicationContext(),AuthenticationActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Conectado ao TMDb",Toast.LENGTH_SHORT).show();
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
            View rootView = inflater.inflate(R.layout.fragment_shows, container, false);
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
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    MyShowsTab tab1 = new MyShowsTab();
                    return tab1;
                case 1:
                    CalendarShowsTab tab2 = new CalendarShowsTab();
                    return tab2;
                case 2:
                    EpisodeShows tab3 = new EpisodeShows();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
