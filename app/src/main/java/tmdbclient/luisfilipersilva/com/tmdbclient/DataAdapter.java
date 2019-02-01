package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by OI310049 on 02/01/2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{

    private ArrayList<AndroidVersion> android_versions;
    private Context context;

    public DataAdapter(Context context,ArrayList<AndroidVersion> android_versions) {
        this.context = context;
        this.android_versions = android_versions;

    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name( ));
        viewHolder.notaTmdb.setText(android_versions.get(i).getNota_tmdb());
        viewHolder.votosTmdb.setText("("+android_versions.get(i).getVotos_tmdb()+")");
        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).fit().into(viewHolder.img_android);

        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(context,android_versions.get(i).getMovie_id(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ScrollingActivity.class );
                intent.putExtra("id",android_versions.get(i).getMovie_id());
                intent.putExtra("imagem", android_versions.get(i).getAndroid_image_url());
                context.startActivity(intent);
                //v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_android;
        ImageView img_android;
        TextView notaTmdb;
        TextView votosTmdb;

        public ViewHolder(View view) {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);
            notaTmdb = view.findViewById(R.id.txtNota);
            votosTmdb = view.findViewById(R.id.counts);

            //OnClick

            view.setOnClickListener(new View.OnClickListener( ){
                @Override
                public void onClick(View v) {
                    //item clicked
                    int position = getAdapterPosition();
                    Toast.makeText(context,android_versions.get(position).toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
