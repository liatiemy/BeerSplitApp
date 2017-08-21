package tiemy.android.br.com.beersplitapp;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BeerMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latlng = new LatLng(-23.5583107, -46.6659817);
        mMap.addMarker(new MarkerOptions().position(latlng).title("O'Malley's"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));

        mMap.setOnMyLocationButtonClickListener(myLocationChangeListener);
    }

    private GoogleMap.OnMyLocationButtonClickListener myLocationChangeListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
        @Override
        public boolean onMyLocationButtonClick() {
            /*LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = service.getBestProvider(criteria, false);

            BUILD_MAP = false;
            checkPermission();

            Location location = service.getLastKnownLocation(provider);
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.addMarker(new MarkerOptions().position(loc).title("NOVO"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 17));*/


            //teste da aula
            LatLng latlng = new LatLng(-23.5583107, -46.6659817); //teste O'malley's
            mMap.addMarker(new MarkerOptions().position(latlng).title("O'Malley's"));
            if(latlng!=null)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11));

            return true;
        }
    };
}
