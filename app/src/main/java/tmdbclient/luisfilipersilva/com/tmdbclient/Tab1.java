package tmdbclient.luisfilipersilva.com.tmdbclient;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {


    public Tab1() {
        // Required empty public constructor
    }

    //GridView gridView;
    public String[ ] imageStock;
    public String[ ] listaid;
    public String[ ] listaNome;
    public String[ ] listaNotasTmdb;
    public String[ ] listaVotosTmdb;

    FloatingActionButton fab;
    RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab1, container, false);

        //gridView = view.findViewById(R.id.gridDados);

        MainActivity activity = (MainActivity) getActivity( );
        imageStock = activity.getMyData( );
        listaid = activity.getTmdbId( );
        listaNome = activity.getLabelFilmes( );
        listaNotasTmdb = activity.getNotasTmdb( );
        listaVotosTmdb = activity.getVotosTmdb( );

        //fab = view.findViewById(R.id.floatingSearch);
        //fab.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View v) {
       ////         Intent intent = new Intent( getActivity( ), SearchActivity.class );
            //    startActivity(intent);
      //      }
     //   });

        recyclerView = view.findViewById(R.id.recyclerviewTab1);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),3 );
        recyclerView.setLayoutManager(layoutManager);

        ArrayList androidVersions = prepareData();
        DataAdapter adapter = new DataAdapter(view.getContext(),androidVersions);
        recyclerView.setAdapter(adapter);

        return view;
    }

    /*
    private void initViews(){
        //RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2 );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(15),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList androidVersions = prepareData();
        DataAdapter adapter = new DataAdapter(view.getContext(),androidVersions);
        recyclerView.setAdapter(adapter);
    }*/

    private ArrayList prepareData(){

        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<listaid.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            //name_movies = getNameData( );
            //image_urls = getImageData( );
            androidVersion.setMovie_id(listaid[i]);
            androidVersion.setAndroid_image_url(imageStock[i]);
            androidVersion.setAndroid_version_name(listaNome[i]);
            androidVersion.setNota_tmdb(listaNotasTmdb[i]);
            androidVersion.setVotos_tmdb(listaVotosTmdb[i]);
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

    //GridView gridView = getView().findViewById(R.id.gridDados);
}
