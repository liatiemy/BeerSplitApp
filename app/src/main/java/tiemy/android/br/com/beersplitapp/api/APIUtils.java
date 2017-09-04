package tiemy.android.br.com.beersplitapp.api;

public class APIUtils {
    public static final String URL_MAPS = "https://maps.googleapis.com/maps/";
    public static final String BASE_URL = "http://www.mocky.io";

    public static UsuarioAPI getUsuarioAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(UsuarioAPI.class);
    }

    public static RetrofitMaps getLocation() {
        return RetrofitClient.getClient(URL_MAPS).create(RetrofitMaps.class);
    }
}

