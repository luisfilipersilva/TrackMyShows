package tmdbclient.luisfilipersilva.com.tmdbclient;

/**
 * Created by OI310049 on 02/01/2018.
 */

public class AndroidVersion {

    private String android_version_name;
    private String android_image_url;
    private String movie_id;
    private String nota_tmdb;
    private String votos_tmdb;
    private String tipo;
    private String ano;

    public String getAndroid_version_name() {
        return android_version_name;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setAndroid_version_name(String android_version_name) {
        this.android_version_name = android_version_name;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getAndroid_image_url() {
        return android_image_url;
    }

    public void setAndroid_image_url(String android_image_url) {
        this.android_image_url = android_image_url;
    }

    public void setNota_tmdb(String nota_tmdb) {
        this.nota_tmdb = nota_tmdb;
    }

    public String getNota_tmdb() {
        return nota_tmdb;
    }

    public void setVotos_tmdb(String votos_tmdb) {
        this.votos_tmdb = votos_tmdb;
    }

    public String getVotos_tmdb() {
        return votos_tmdb;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }


    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getAno() {
        return ano;
    }
}
