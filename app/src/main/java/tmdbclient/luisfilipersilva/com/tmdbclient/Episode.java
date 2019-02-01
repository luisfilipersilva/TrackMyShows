package tmdbclient.luisfilipersilva.com.tmdbclient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OI310049 on 02/01/2018.
 */

public class Episode implements Comparable<Episode>{

    private String show;
    private String season_num;
    private String epsiode_num;
    private String episode_name;
    private String air_date;
    private String episode_id;
    private String image;

    public String getShow_id() {
        return show_id;
    }

    public void setShow_id(String show_id) {
        this.show_id = show_id;
    }

    private String show_id;

    private String overview;

    //Setters
    public void setShow(String show) {
        this.show = show;
    }

    public void setSeason_num(String season_num) {
        this.season_num = season_num;
    }

    public void setEpsiode_num(String epsiode_num) {
        this.epsiode_num = epsiode_num;
    }

    public void setEpisode_name(String episode_name) {
        this.episode_name = episode_name;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public void setEpisode_id(String episode_id) {
        this.episode_id = episode_id;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    //Getters
    public String getShow() {
        return show;
    }

    public String getSeason_num() {
        return season_num;
    }

    public String getEpsiode_num() {
        return epsiode_num;
    }

    public String getEpisode_name() {
        return episode_name;
    }

    public String getAir_date() {
        return air_date;
    }

    public String getEpisode_id() {
        return episode_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getOverview() {
        return overview;
    }


    @Override
    public int compareTo(Episode ep) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date air_date1 = null;
        Date air_date2 = null;
        try {
            air_date1 = format.parse(this.getAir_date());
            air_date2 = format.parse(ep.getAir_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(air_date1.compareTo(air_date2) > 1){
            return 1;
        }
        else if (air_date1.compareTo(air_date2) == 1){
            return 0;
        }else{
            return -1;
        }
    }
}
