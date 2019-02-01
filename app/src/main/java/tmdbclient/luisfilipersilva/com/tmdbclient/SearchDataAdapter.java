package tmdbclient.luisfilipersilva.com.tmdbclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by OI310049 on 02/01/2018.
 */

public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataAdapter.ViewHolder>{

    private ArrayList<AndroidVersion> android_versions;
    private Context context;

    public SearchDataAdapter(Context context, ArrayList<AndroidVersion> android_versions) {
        this.context = context;
        this.android_versions = android_versions;

    }

    @Override
    public SearchDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_busca, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name( ));
        viewHolder.tipo.setText(android_versions.get(i).getTipo());
        viewHolder.ano.setText(android_versions.get(i).getAno());
        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).fit().into(viewHolder.img_android);

        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(context,android_versions.get(i).getMovie_id(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ScrollingActivity.class );
                intent.putExtra("id",android_versions.get(i).getMovie_id());
                intent.putExtra("imagem", android_versions.get(i).getAndroid_image_url());
                intent.putExtra("media_type", android_versions.get(i).getTipo());
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
        TextView tipo;
        TextView ano;

        public ViewHolder(View view) {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);
            tipo = view.findViewById(R.id.txtData);
            ano = view.findViewById(R.id.txtAno);

            //OnClick

            /*
            view.setOnClickListener(new View.OnClickListener( ){
                @Override
                public void onClick(View v) {
                    //item clicked
                    int position = getAdapterPosition();
                    Toast.makeText(context,android_versions.get(position).toString(),Toast.LENGTH_LONG).show();
                }
            });
            */
        }
    }
}
