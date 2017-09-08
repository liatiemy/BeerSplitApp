package tiemy.android.br.com.beersplitapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tiemy.android.br.com.beersplitapp.api.APIUtils;
import tiemy.android.br.com.beersplitapp.api.RetrofitMaps;
import tiemy.android.br.com.beersplitapp.dao.GooglePlacesResult;


public class BeerMapActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;

    private static String[] PERMISSIONS_LOCATION = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION};

    private GoogleApiClient mGoogleApiClient;
    private static final int RADIUS = 5000;
    private static final String TYPE = "bar";

    LocationManager service;
    LocationListener mlocatioLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, PERMISSIONS_LOCATION, 1);
        buildGoogleApiClient();

        service = (LocationManager) getSystemService(LOCATION_SERVICE);
        mlocatioLocationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        service.requestLocationUpdates(service.GPS_PROVIDER, 1000, 0, mlocatioLocationListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.clear();

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mMap.clear();

                Criteria criteria = new Criteria();
                String provider = service.getBestProvider(criteria, false);

                if (ActivityCompat.checkSelfPermission(getBaseContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getBaseContext(),
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }

                Location location = service.getLastKnownLocation(provider);
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

                mMap.addMarker(new MarkerOptions().position(loc));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));

                RetrofitMaps retrofitMaps = APIUtils.getLocation();
                retrofitMaps.getNearbyPlaces(TYPE, location.getLatitude()+","+location.getLongitude(), RADIUS).enqueue(new Callback<GooglePlacesResult>() {
                    @Override
                    public void onResponse(Call<GooglePlacesResult> call, Response<GooglePlacesResult> response) {
                        if(response.isSuccessful()) {
                            for(int i = 0; i < response.body().getResults().size(); i++){
                                String name = response.body().getResults().get(i).getName();
                                String endereco = response.body().getResults().get(i).getVicinity();
                                double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                                double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                                LatLng loc = new LatLng(lat, lng);
                                mMap.addMarker(new MarkerOptions().position(loc).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GooglePlacesResult> call, Throwable t) {
                        Log.e("Failed to get map", t.toString());
                    }
                });

                return true;
            }
        });
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        service.removeUpdates(mlocatioLocationListener);
    }


}
