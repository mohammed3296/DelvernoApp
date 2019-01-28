package com.codecaique3296.delivrenoapp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.backgroundtasks.MyService;
import com.codecaique3296.delivrenoapp.models.object.Resturant;
import com.codecaique3296.delivrenoapp.models.response.ResturantsJson;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG ="fkjjffkjfk" ;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private GoogleMap mMap;
    private ArrayList<Resturant> resturants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
        startService(new Intent(MapActivity.this, MyService.class));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        getResturants();
    }

    public void getResturants() {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        resturants = new ArrayList<>();
        Call<ResturantsJson> call1 = apiService1.getResturants("1");
        call1.enqueue(new Callback<ResturantsJson>() {
            @Override
            public void onResponse(Call<ResturantsJson> call, retrofit2.Response<ResturantsJson> response) {
                int statusCode = response.code();
                Toast.makeText(MapActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
                resturants = (ArrayList<Resturant>) response.body().getResturants();
                for (int i = 0; i < resturants.size(); i++) {
//                    createMarker(Double.valueOf(resturants.get(i).getLatitude()),
//                            Double.valueOf(resturants.get(i).getLongitude()),
//                            resturants.get(i).getName(), resturants.get(i).getAddress(),
//                            R.drawable.restmarker);
                    Log.e("Resturants", resturants.get(i).toString());

                    LatLng sydney = new LatLng(Double.parseDouble(resturants.get(i).getLatitude()), Double.parseDouble(
                            resturants.get(i).getLongitude()));
                    mMap.addMarker(new MarkerOptions().position(sydney)
                            .title(resturants.get(i).getName()));
                }
            }

            @Override
            public void onFailure(Call<ResturantsJson> call, Throwable t) {
                Toast.makeText(MapActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(this , ADashboardActivity.class));
    }



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            LatLng sydney = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            mMap.addMarker(new MarkerOptions().position(sydney)
                    .title("Me"));
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }
    /*
     * Called by Google Play services if the connection to GoogleApiClient drops because of an
     * error.
     */
    public void onDisconnected() {
        Log.i(TAG, "Disconnected");
    }
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

}
