package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Luis Filipe on 01/01/2018.
 */

public class GridViewAdapterPopular extends ArrayAdapter {


    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls;
    private String[] labelFilmes;


    public GridViewAdapterPopular(Context context, String[] imageUrls) {
        super(context, R.layout.fragment_tab2, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;
        //this.labelFilmes = labelFilmes;

        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        //Holder holder = new Holder( );

        View rowview = inflater.inflate(R.layout.fragment_tab2,null);
        //holder.os_text = rowview.findViewById(R.id.grid_text);
        // holder.os_text.setText(labelFilmes[position]);

        if (convertView == null) {
            imageView = new ImageView(context);
            //textView = new TextView(context);
            //textView.setText(labelFilmes[position]);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(context).load(imageUrls[position]).resize(600,900).noFade().centerCrop().into(imageView);

        return imageView;
    }

}
