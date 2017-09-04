package tiemy.android.br.com.beersplitapp.api;

import tiemy.android.br.com.beersplitapp.dao.GooglePlacesResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitMaps {

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDwBKu_Pp85spSPItxSLgVADq8Ai9eh6wQ")
    Call<GooglePlacesResult> getNearbyPlaces(@Query("type") String type,
                                             @Query("location") String location,
                                             @Query("radius") int radius);

}