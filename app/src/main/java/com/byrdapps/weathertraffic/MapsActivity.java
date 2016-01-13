package com.byrdapps.weathertraffic;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap googleMap2;
    private GoogleMap mMap2;

    GoogleMap googleMap;
    private GoogleMap mMap;


    private Boolean weatherOn=false;
    RelativeLayout menu1;
    RelativeLayout menu2;
    RelativeLayout trafficMap;
    RelativeLayout weatherMap;
    RelativeLayout blocker1;
    RelativeLayout blocker2;

    //Buttons
    TextView weather;
    TextView traffic;
    ImageView menu;
    ToggleButton satSwitch;
    ToggleButton satSwitch2;
    RadioButton standardBtn;
    RadioButton satBtn;
    RadioButton hybridBtn;
    RadioGroup mapGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        setContentView(R.layout.activity_maps);

        //Views
        menu1= (RelativeLayout)findViewById(R.id.Menu1Layer);
        menu2= (RelativeLayout)findViewById(R.id.Menu2Layer);
        trafficMap=(RelativeLayout)findViewById(R.id.TrafficMap);
        weatherMap=(RelativeLayout)findViewById(R.id.WeatherMap);
        //Buttons
        weather= (TextView)findViewById(R.id.weather);
        traffic= (TextView)findViewById(R.id.traffic);
        menu=(ImageView)findViewById(R.id.menuBtn);

        blocker1=(RelativeLayout)findViewById(R.id.blocker);
        blocker2=(RelativeLayout)findViewById(R.id.blocker2);


        standardBtn=(RadioButton)findViewById(R.id.StandardBtn);
        satBtn=(RadioButton)findViewById(R.id.SateliteBtn);
        hybridBtn=(RadioButton)findViewById(R.id.HybridBtn);

        satSwitch2=(ToggleButton)findViewById(R.id.satSwitch2);


        standardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //turn off traffic
                Log.i("Test", "standardBtn=");
                standardBtn.setBackgroundColor(getResources().getColor(R.color.radioOn));
                standardBtn.setTextColor(getResources().getColor(R.color.radioTextOn));

                hybridBtn.setBackgroundColor(getResources().getColor(R.color.radioOff));
                hybridBtn.setTextColor(getResources().getColor(R.color.radioTextOff));

                satBtn.setBackgroundColor(getResources().getColor(R.color.radioOff));
                satBtn.setTextColor(getResources().getColor(R.color.radioTextOff));

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        hybridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //turn off traffic
                standardBtn.setBackgroundColor(getResources().getColor(R.color.radioOff));
                standardBtn.setTextColor(getResources().getColor(R.color.radioTextOff));

                hybridBtn.setBackgroundColor(getResources().getColor(R.color.radioOn));
                hybridBtn.setTextColor(getResources().getColor(R.color.radioTextOn));

                satBtn.setBackgroundColor(getResources().getColor(R.color.radioOff));
                satBtn.setTextColor(getResources().getColor(R.color.radioTextOff));
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Log.i("Test", "hybridBtn=");
            }
        });
        satBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //turn off traffic
                standardBtn.setBackgroundColor(getResources().getColor(R.color.radioOff));
                standardBtn.setTextColor(getResources().getColor(R.color.radioTextOff));

                hybridBtn.setBackgroundColor(getResources().getColor(R.color.radioOff));
                hybridBtn.setTextColor(getResources().getColor(R.color.radioTextOff));

                satBtn.setBackgroundColor(getResources().getColor(R.color.radioOn));
                satBtn.setTextColor(getResources().getColor(R.color.radioTextOn));

                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Log.i("Test", "satBtn=");
            }
        });

        blocker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //turn off traffic
                menu1.setVisibility(View.INVISIBLE);
            }
        });
        blocker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu2.setVisibility(View.INVISIBLE);
            }
        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //turn off traffic
                trafficMap.setVisibility(View.INVISIBLE);
                traffic.setBackgroundColor(Color.parseColor("#9d9999"));

                //turn on weather
                weatherMap.setVisibility(View.VISIBLE);
                weather.setBackgroundColor(Color.parseColor("#ffffff"));
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(bestProvider);
                //if (location != null) {
                    onLocationChanged2(location);
                //}
                weatherOn=true;
            }
        });
        traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //turn off weather
                weatherMap.setVisibility(View.INVISIBLE);
                weather.setBackgroundColor(Color.parseColor("#9d9999"));
                //turn on traffic
                trafficMap.setVisibility(View.VISIBLE);
                traffic.setBackgroundColor(Color.parseColor("#ffffff"));
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(bestProvider);
                //if (location != null) {
                    onLocationChanged(location);
                //}
                weatherOn=false;
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weatherOn) {//open menu1
                    menu2.setVisibility(View.VISIBLE);
                } else {//open menu2
                    menu1.setVisibility(View.VISIBLE);
                }
            }
        });





        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        googleMap = supportMapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }


        //setContentView(R.layout.activity_maps);
        SupportMapFragment supportMapFragment2 =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap2);
        googleMap2 = supportMapFragment2.getMap();
        googleMap2.setMyLocationEnabled(true);
        LocationManager locationManager2 = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria2 = new Criteria();
        String bestProvider2 = locationManager2.getBestProvider(criteria2, true);
        Location location2 = locationManager2.getLastKnownLocation(bestProvider2);
        if (location != null) {
            onLocationChanged2(location2);
        }
        //locationManager.requestLocationUpdates(bestProvider, 20000, 0, (LocationListener) this);


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


    public void toggleclick2(View v) {
        if (satSwitch2.isChecked()) {
            googleMap2.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            //Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();
        } else{
            googleMap2.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLocationChanged(Location location) {
        //TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
        googleMap.setTrafficEnabled(true);
    }

    public void onLocationChanged2(Location location) {
        //TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap2.clear();
        googleMap2.addMarker(new MarkerOptions().position(latLng));
        googleMap2.getUiSettings().setMapToolbarEnabled(false);
        googleMap2.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap2.animateCamera(CameraUpdateFactory.zoomTo(15));
        //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
        //googleMap.setTrafficEnabled(true);
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
