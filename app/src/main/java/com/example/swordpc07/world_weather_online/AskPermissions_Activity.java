package com.example.swordpc07.world_weather_online;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;

public class AskPermissions_Activity extends AppCompatActivity {

    public String WEATHER_API = "http://api.worldweatheronline.com/premium/v1/weather.ashx?";
    public String API_KEY = "da98314d6a16444e84d42542172908";

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    String TAG = "SplashScreen";
    LocationManager mLocationManager;

    double lat, longt;

    boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_permissions_);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = false;
        network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception ex) {
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            if (!gps_enabled || !network_enabled) {

                // android version < M so no need for permission
                //show dailog to turn on gps location
                displayLocationSettingsRequest(this);
            } else {
//                startService(new Intent(AskPermissions_Activity.this, DownloadWeatherService.class));

                Location loc = getLastKnownLocation();

                if (loc != null) {
                    lat = loc.getLatitude();
                    longt = loc.getLongitude();
                }

                final String url = getUrl(lat, longt);
                Log.d("url = ", url);

                Object[] DataTransfer = new Object[1];

                DataTransfer[0] = url;
                new AsyncTaskToFetchWhetherDetails.GetWeatherDetails(AskPermissions_Activity.this).execute(url);
            }

        } else {

            checkLocationPermission();
        }
    }

    public void checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
           /* if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Need Location Permission")
                        .setMessage("To fetch current location to enhance User Interaction with application")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AskPermissions_Activity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }*/

            final SharedPreferences userDetailsprefrences = getSharedPreferences("installedapp", MODE_PRIVATE);
            String user_id = userDetailsprefrences.getString("install","");
            if(user_id.equalsIgnoreCase("yes")){

                startActivity(new Intent(AskPermissions_Activity.this, SplashScreen.class));
                finish();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

        } else {
            // permission already accpetd to allow location.
            /*startActivity(new Intent(AskPermissions_Activity.this, SplashScreen.class));
            finish();*/
            displayLocationSettingsRequest(AskPermissions_Activity.this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        SharedPreferences userDetailsprefrences = getSharedPreferences("installedapp", MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetailsprefrences.edit();

        editor.putString("install", "yes");
        editor.commit();

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        /*startActivity(new Intent(AskPermissions_Activity.this, SplashScreen.class));
                        finish();*/
                        if (!gps_enabled && !network_enabled) {
                            displayLocationSettingsRequest(AskPermissions_Activity.this);
                        } else{

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

//                                startService(new Intent(AskPermissions_Activity.this, DownloadWeatherService.class));

                                    Location loc = getLastKnownLocation();

                                    if (loc != null) {
                                        lat = loc.getLatitude();
                                        longt = loc.getLongitude();
                                    }

                                    final String url = getUrl(lat, longt);
                                    Log.d("url = ", url);

                                    Object[] DataTransfer = new Object[1];

                                    DataTransfer[0] = url;
                                    new AsyncTaskToFetchWhetherDetails.GetWeatherDetails(AskPermissions_Activity.this).execute(url);
                                }
                            });
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    startActivity(new Intent(AskPermissions_Activity.this, SplashScreen.class));
                    finish();
                }
                return;
            }

        }
    }

    private void displayLocationSettingsRequest(final Context context) {


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
               /* .addConnectionCallbacks(context)
                .addOnConnectionFailedListener(context)*/
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.
                SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        //GetCurrentLocation.getInstance(DriverHome.this).startServices(DriverHome.this, user_id);
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(AskPermissions_Activity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        //Toast.makeText(Splashscreen.this, "Click on yes", Toast.LENGTH_SHORT).show();
                        //user accpetd to turn on GPS service
                        //start service to fetch user location and get whether details from an api.

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                                startService(new Intent(AskPermissions_Activity.this, DownloadWeatherService.class));

                                Location loc = getLastKnownLocation();

                                if (loc != null) {
                                    lat = loc.getLatitude();
                                    longt = loc.getLongitude();
                                }

                                final String url = getUrl(lat, longt);
                                Log.d("url = ", url);

                                Object[] DataTransfer = new Object[1];

                                DataTransfer[0] = url;
                                new AsyncTaskToFetchWhetherDetails.GetWeatherDetails(AskPermissions_Activity.this).execute(url);
                            }


                        });

                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        //Toast.makeText(Splashscreen.this, "Click on no", Toast.LENGTH_SHORT).show();

                        // used denied to turn on GPS
                        //show autocomplete to get user's location.
                        startActivity(new Intent(AskPermissions_Activity.this, SplashScreen.class));
                        finish();

                        break;
                }
                break;
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    public Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(AskPermissions_Activity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AskPermissions_Activity.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public String getUrl(double latitude, double lonngitutde) {

        String url = WEATHER_API + "key=" + API_KEY + "&num_of_days=1&tp=3&q=" + latitude + "," + lonngitutde + "&format=json&lang=hi";
        return url;
    }

}
