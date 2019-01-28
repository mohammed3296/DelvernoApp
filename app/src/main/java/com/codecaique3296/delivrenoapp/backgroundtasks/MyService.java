package com.codecaique3296.delivrenoapp.backgroundtasks;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.models.object.Tayar;
import com.codecaique3296.delivrenoapp.models.response.PublicResponse;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.codecaique3296.delivrenoapp.utils.Sesstion;
import com.codecaique3296.delivrenoapp.view.MainActivity;
import com.codecaique3296.delivrenoapp.view.ProfileActivity;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import retrofit2.Call;
import retrofit2.Callback;

import static java.security.AccessController.getContext;

public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {
    LocationManager locationManager;
    String latitude;
    String longtude;
    LocationListener locationListener;
    PendingResult<LocationSettingsResult> result;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    public final static int REQUEST_LOCATION = 199;
    private static final String TAG = "BOOMBOOMTESTGPS";
    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
//        super.onStartCommand(intent, flags, startId);
     //   onTaskRemoved(intent);
   //     onCreate();
        initializeLocationManager();
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(MyService.this)
                .addOnConnectionFailedListener(MyService.this).build();
        googleApiClient.connect();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5 * 1000);
        locationRequest.setFastestInterval(2 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
//                final LocationSettingsStates state = result.getLocationSettingsStates();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 100, locationListener);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                          //  MainActivity mainActivity = new MainActivity();
                            status.startResolutionForResult((Activity) getApplicationContext(), 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    latitude = String.valueOf(location.getLatitude());
                    longtude = String.valueOf(location.getLongitude());
                    if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longtude) || latitude == null || longtude == null) {
                        Log.i(TAG , "you must be out door !");

                    } else {
                        Log.i(TAG , latitude + "    " + longtude);
                        setof(latitude , longtude);
                    }

                } catch (Exception e) {
                    //  Toast.makeText(getContext(), "you must be out door !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
             Log.i(TAG  , "onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

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


    private void setof(String lati , String longi) {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PublicResponse> call1 = apiService1.editLocation("1",
                Sesstion.getInstance(this).getTayar().getId() , longi , lati);
        call1.enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                // Toast.makeText(ProfileActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
                Log.i(TAG ,  response.body().getAck());
            }
            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {
                Log.e(TAG , "failer" );
            }
        });
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext() , this.getClass());
        restartService.setPackage(getPackageName());
        startService(restartService);
        super.onTaskRemoved(rootIntent);
    }
}
