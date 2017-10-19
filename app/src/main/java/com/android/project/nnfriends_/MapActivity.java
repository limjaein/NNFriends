package com.android.project.nnfriends_;

import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.android.project.nnfriends_.R.id.map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMapClickListener{

    MapFragment mapFr;
    GoogleMap mMap;

    static final int REQ_PERMISSION = 1000;
    private static String[] permission_map = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };

    Geocoder gc;
    EditText place;
    double latitude, longitude;
    LatLng Loc;

    ArrayList<LatLng> mLatLng;

    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    LocationSettingsRequest.Builder builder;
    PendingResult<LocationSettingsResult> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        init();
        initMap();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    public void init() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000)
                .setSmallestDisplacement(30);
        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();
                // 생략
            }
        });
    }

    public void initMap() {
        mapFr = (MapFragment) getFragmentManager().findFragmentById(map);
        mapFr.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (!checkPermission(permission_map)) {
            askPermission(permission_map, REQ_PERMISSION);
            return;
        }
        mMap.setMyLocationEnabled(true);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        latitude = 37.554752;
        longitude = 126.970631;
        Loc = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Loc, 16));
    }

    public boolean getCurrentLocation(){
        if (checkPermission(permission_map)) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            //if (mLastLocation != null)
            //updateMapPic(mLastLocation);
            return true;
        }else
            return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION) {
            if (grantResults.length > 0) {
//                if (checkPermission(permission_map)) {
//                    mMap.setMyLocationEnabled(true);
//                } else {
//                    askPermission(permission_map, REQ_PERMISSION);
//                }
                if (!getCurrentLocation())
                    askPermission(permission_map, REQ_PERMISSION);
            }
        }
    }

    private boolean checkPermission(String[] requestPermission) {
        boolean[] requestResult = new boolean[requestPermission.length];
        for (int i = 0; i < requestResult.length; i++) {
            requestResult[i] = (ContextCompat.checkSelfPermission(this, requestPermission[i]) == PackageManager.PERMISSION_GRANTED);
            if (!requestResult[i]) {
                return false;
            }
        }
        return true;
    }

    private void askPermission(String[] requestPermission, int requestCode) {
        ActivityCompat.requestPermissions(
                this,
                requestPermission,
                requestCode
        );
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (!checkPermission(permission_map)) {
            askPermission(permission_map, REQ_PERMISSION);
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d("check", (String.valueOf("LastLocation"+mLastLocation.getLatitude()+"::"+mLastLocation.getLongitude())));
            LatLng lastLatlng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(lastLatlng).title("마커"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatlng, 16));
            Log.d("check", lastLatlng.latitude+"::"+lastLatlng.longitude);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        updateMap(location);
    }

    public void updateMap(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        final LatLng Loc = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Loc, 16));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}
