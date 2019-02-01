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

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder>{

    //private ArrayList<AndroidVersion> android_versions;
    private ArrayList<Episode> episode;
    private Context context;

    public EpisodeAdapter(Context context, ArrayList<Episode> episode) {
        this.context = context;
        this.episode = episode;

    }

    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_calendar, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {


        viewHolder.showName.setText(episode.get(i).getShow());
        viewHolder.textShow.setText(episode.get(i).getSeason_num());
        viewHolder.episode_num.setText(episode.get(i).getEpsiode_num());
        viewHolder.tv_android.setText(episode.get(i).getEpisode_name());
        viewHolder.air_date.setText(episode.get(i).getAir_date());
        viewHolder.overview.setText(episode.get(i).getOverview());
        //viewHolder.ano.setText(android_versions.get(i).getAno());
        Picasso.with(context).load(episode.get(i).getImage()).fit().into(viewHolder.img_android);

        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(context,android_versions.get(i).getMovie_id(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ScrollingActivity.class );
                intent.putExtra("id",episode.get(i).getEpisode_id());
                intent.putExtra("imagem", episode.get(i).getImage());
                intent.putExtra("media_type", episode.get(i).getShow());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return episode.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView showName;
        TextView textShow;
        TextView episode_num;
        TextView overview;
        TextView tv_android;
        ImageView img_android;
        TextView air_date;
        TextView ano;

        public ViewHolder(View view) {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);
            air_date = view.findViewById(R.id.txtData);
            ano = view.findViewById(R.id.txtAno);
            textShow = view.findViewById(R.id.textShow);
            episode_num = view.findViewById(R.id.txtEpisodeNum);
            showName = view.findViewById(R.id.txtShowName);
            overview = view.findViewById(R.id.txtOverview);
        }
    }
}
