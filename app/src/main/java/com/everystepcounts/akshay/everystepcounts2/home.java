package com.everystepcounts.akshay.everystepcounts2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;


public class home extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    Location initial=new Location("");
    Location destination=new Location("");
    boolean initialFlag=true;
    boolean destinationFlag=true;
    Criteria criteria = new Criteria();
    private MapView mapView;
    String TAG="OurdebugLog";

    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    float distanceTravelled;
    TextView distanceText;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(1000,1,criteria,locationListener,null);
            }
        }
    }




    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        distanceText=findViewById(R.id.distance);
        mapView = (MapView) this.findViewById(R.id.mapView);



        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(false);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);



        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                float[] distance=new float[1];
                if(initialFlag){
                    initial.setLatitude(location.getLatitude());
                    initial.setLongitude(location.getLongitude());
                    destination.setLatitude(location.getLatitude());
                    destination.setLongitude(location.getLongitude());
                    initialFlag=false;
                    Location.distanceBetween(initial.getLatitude(),initial.getLongitude(),destination.getLatitude(),destination.getLongitude(),distance);
                    if(distance[0]<2)
                    {
                        distanceTravelled=distanceTravelled+distance[0];
                        initial.setLatitude(location.getLatitude());
                        initial.setLongitude(location.getLongitude());

                    }
                }
                else
                {
                    destination.setLatitude(location.getLatitude());
                    destination.setLongitude(location.getLongitude());
                    Location.distanceBetween(initial.getLatitude(),initial.getLongitude(),destination.getLatitude(),destination.getLongitude(),distance);
                    if(distance[0]<2)
                    {
                        distanceTravelled=distanceTravelled+distance[0];
                        initial.setLatitude(location.getLatitude());
                        initial.setLongitude(location.getLongitude());

                    }
                }
                Log.i("location", location.toString());
                Log.i("distance", Float.toString(distance[0]));


                distanceText.setText(Float.toString(distanceTravelled));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
//                if (provider.equals(LocationManager.GPS_PROVIDER)) {
//                    if (status == LocationProvider.OUT_OF_SERVICE) {
//                        notifyLocationProviderStatusUpdated(false);
//                    } else {
//                        notifyLocationProviderStatusUpdated(true);
//                    }
//                }
            }

            @Override
            public void onProviderEnabled(String provider) {
//                if (provider.equals(LocationManager.GPS_PROVIDER)) {
//                    notifyLocationProviderStatusUpdated(true);
//                }
            }

            @Override
            public void onProviderDisabled(String provider) {
//                if (provider.equals(LocationManager.GPS_PROVIDER)) {
//                    notifyLocationProviderStatusUpdated(false);
//                }

            }
        };
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(1000,1,criteria,locationListener,null);
        } else {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


                    } else {
                        locationManager.requestLocationUpdates(1000,1,criteria,locationListener,null);
                    }
            }


    }
}
//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);