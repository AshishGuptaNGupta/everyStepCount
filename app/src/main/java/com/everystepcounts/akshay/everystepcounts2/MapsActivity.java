package com.everystepcounts.akshay.everystepcounts2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    LocationManager locationManager;
    LocationListener locationListener;
    Location initial1=new Location("");
    Location initial=new Location("");
    LatLng destination=new LatLng(0,0);
    boolean initialFlag=true;
    boolean destinationFlag=true;
    float distanceTravelled;
    Criteria criteria = new Criteria();
    private GoogleMap mMap;
    LatLng currentLocation=new LatLng(0.0,0.0);
    List<LatLng> points= new ArrayList<>();
    Circle circle;
    int activity;
    int acceptanceDistance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    Map<String, Object> userMap = new HashMap<>();
    String formattedDate;
    String formattedTime;
    TextView distanceTextView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(1000,1,criteria,locationListener,null);
            }
        }
    }
    public void createCircle(LatLng location)
    {
         circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(location.latitude, location.longitude))
                .radius(5)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

    }

    public void stopActivity(View view){
        userMap.put("distance Travelled",distanceTravelled);
        userMap.put("date ",formattedDate);
        userMap.put("Time ",formattedTime);
        db.collection("workout").document(user.getUid())
                .collection(formattedDate).document(formattedTime)
                .set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("doc", "success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("doc", "fail",e);
            }
        });
        Intent intent=new Intent(getApplicationContext(),DashBoard.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(true);

//API level 9 and up
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        Intent intent = getIntent();
        activity= Integer.parseInt(intent.getStringExtra("Activity"));
        user=mAuth.getCurrentUser();
        distanceTextView=findViewById(R.id.distance);

        Date time=Calendar.getInstance().getTime();
        SimpleDateFormat tm = new SimpleDateFormat("HH-mm-ss");
        formattedTime=tm.format(time);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(date);


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

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(activity==0){
            acceptanceDistance=2;
        }else
            acceptanceDistance=4;
        mMap = googleMap;
        final Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(0, 0), new LatLng(0,0 ))
                .width(5)
                .color(Color.RED));


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                mMap.clear();
                float[] distance = new float[1];
                if(initialFlag){
                    initial.setLatitude(location.getLatitude());
                    initial.setLongitude(location.getLongitude());
                    initial1.setLatitude(location.getLatitude());
                    initial1.setLongitude(location.getLongitude());
                    destination= new LatLng(location.getLatitude(),location.getLongitude());
                    createCircle(currentLocation);
                    initialFlag=false;
                    Location.distanceBetween(initial.getLatitude(),initial.getLongitude(),destination.latitude,destination.longitude,distance);
                    if(distance[0]<acceptanceDistance)
                    {
                        distanceTravelled=distanceTravelled+(distance[0]-distanceTravelled);
                        initial.setLatitude(location.getLatitude());
                        initial.setLongitude(location.getLongitude());
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.setMaxZoomPreference(18);
                        mMap.setMinZoomPreference(18);
                        points.add(destination);
                        line.setPoints(points);
                        distanceTextView.setText(Float.toString(distanceTravelled));



                    }
                }
                else
                {
                    destination=new LatLng(location.getLatitude(),location.getLongitude());
                    createCircle(currentLocation);
                    Location.distanceBetween(initial.getLatitude(),initial.getLongitude(),destination.latitude,destination.longitude,distance);
                    if(distance[0]<acceptanceDistance)
                    {
                        distanceTravelled=distanceTravelled+(distance[0]-distanceTravelled);
                        initial.setLatitude(location.getLatitude());
                        initial.setLongitude(location.getLongitude());
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.setMaxZoomPreference(18);
                        mMap.setMinZoomPreference(18);
                        points.add(destination);
                        line.setPoints(points);
                        distanceTextView.setText(Float.toString(distanceTravelled));


                    }
                }
                Log.i("location", location.toString());
                Log.i("distance", Float.toString(distance[0]));
                Log.i("distance travelled", Float.toString(distanceTravelled));




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
