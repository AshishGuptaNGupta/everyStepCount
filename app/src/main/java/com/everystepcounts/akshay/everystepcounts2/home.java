package com.everystepcounts.akshay.everystepcounts2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.protobuf.DescriptorProtos;
import com.google.type.LatLng;



public class home extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    Location initial=new Location("");
    Location destination=new Location("");
    boolean initialFlag=true;
    boolean destinationFlag=true;
    float[] distanceTravelled=new float[1];
    TextView distance;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        distance=findViewById(R.id.distance);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(initialFlag){
                    initial.setLatitude(location.getLatitude());
                    initial.setLongitude(location.getLongitude());
                    destination.setLatitude(location.getLatitude());
                    destination.setLongitude(location.getLongitude());
                    initialFlag=false;
                }
                else
                {
                    destination.setLatitude(location.getLatitude());
                    destination.setLongitude(location.getLongitude());
                }
                Location.distanceBetween(initial.getLatitude(),initial.getLongitude(),destination.getLatitude(),destination.getLongitude(),distanceTravelled);
                Log.i("location", location.toString());
                Log.i("distance", Float.toString(distanceTravelled[0]));
                distance.setText( Float.toString(distanceTravelled[0]));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


                    } else {
                        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                    }
            }
    }
}
