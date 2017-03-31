package com.test.freefood.freefood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.test.freefood.freefood.R.id.map;

/*
import static com.test.freefood.freefood.R.id.map;
*/
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, LocationListener
{

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient; //added this

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1; //added this

    private Location mLastLocation;     //this is used to get your location


    public Button button1;

    public void init ()     //Switch Activities
    {
        button1= (Button)findViewById(R.id.buttontest);     //Id of the button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent switchActivities = new Intent(MapsActivity.this,Calender.class);

                startActivity(switchActivities);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);


        if (mGoogleApiClient == null)       //Added this if statement
        {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        init();
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

    //This is where the marker is placed
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //We could make a array of LatLng and then access them here by using [0], [1], etc.
        //Could separate Lat from lang (separate arrays) and call them like this ... = new LatLng(Lat[1], Lang[1]); and then do that for every value. <-- not sure how to do that part.

        LatLng Atlas = new LatLng(40.0077, -105.2699);          //This could be where we import the coordinates of the food locations
        mMap.addMarker(new MarkerOptions().position(Atlas).title("Location Test Atlas").snippet("location details test").snippet("location"));       //Could place the details here ??? (food, club, time, etc) Maybe replace ".title" with something
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Atlas, 12)); //centers the camera on your marker. 12 controls how zoomed in it is (also need "..LngZoom(...)")

        LatLng Engineering = new LatLng(40.0072, -105.2620);
        mMap.addMarker(new MarkerOptions().position(Engineering).title("Location Test Engineering").snippet("location details test")); //.snippet("location"));

        mMap.getUiSettings().setZoomControlsEnabled(true);  //Allows the user to click on a marker and it will zoom in
        mMap.setOnMarkerClickListener(this);                //same      //could have it pop up a window when you do this that contains the details of ___.
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        setUpMap();
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void setUpMap ()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
/*
        mMap.setMyLocationEnabled(true);    //"draws" your marker position on the map
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable())
        {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null)
            {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));     //12 sets how zoomed in it is
            }
        }*/         //Personal Code (not working)

        // 1
        mMap.setMyLocationEnabled(true);

// 2
        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            // 3
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // 4
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }

    }
}
