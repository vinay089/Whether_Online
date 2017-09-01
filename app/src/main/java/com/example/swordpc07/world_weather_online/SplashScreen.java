package com.example.swordpc07.world_weather_online;

/*  http://api.worldweatheronline.com/premium/v1/weather.ashx?
    key=fecd9c48df6841609e9124048170501&num_of_days=1&
    q=28.6967019,77.1306743&format=json&lang=hi  */

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bugsee.library.Bugsee;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;


public class SplashScreen extends AppCompatActivity {

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    String TAG = "SplashScreen";

    public String WEATHER_API = "http://api.worldweatheronline.com/premium/v1/weather.ashx?";
    public String API_KEY = "da98314d6a16444e84d42542172908";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bugsee.launch(getApplication(), "dccd4c69-9be5-4acd-9d31-dc311ac11052");
        Bugsee.setEmail("vinayduderr@gmail.com");

        HashMap<String, Object> options = new HashMap<>();
        options.put(Bugsee.Option.NotificationBarTrigger, false);
        options.put(Bugsee.Option.ShakeToTrigger, true);
        options.put(Bugsee.Option.CrashReport, true);
        options.put(Bugsee.Option.VideoEnabled, false);
        Bugsee.launch(getApplication(), "dccd4c69-9be5-4acd-9d31-dc311ac11052", options);

        TextView autrocompleteTextView =(TextView) findViewById(R.id.autocompleteTextView);


//        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // check whether either gps or netwrok enabled to fetch user location.
        /*LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception ex) {
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            if (!gps_enabled && !network_enabled) {

                // android version < M so no need for permission
                //show dailog to turn on gps location
//                displayLocationSettingsRequest(this);
            } else {

                //gps is already enabled, you can do whatever you want to do.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startService(new Intent(SplashScreen.this, DownloadWeatherService.class));
//                        progressBar.setVisibility(View.VISIBLE);
//                        progressBar.setBackgroundColor(2);
                    }
                });
            }

        } else {

            // version is greater tha M
            //check whether permission is allowed or not.
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //No permission is given by user.
                //show location text so that user can enter its location.
            } else {

                if (!gps_enabled && !network_enabled) {

                    // android version < M so no need for permission
                    //show dailog to turn on gps location
//                    displayLocationSettingsRequest(this);
                } else {

                    //gps is already enabled, you can do whatever you want to do.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            startService(new Intent(SplashScreen.this, DownloadWeatherService.class));
                           *//* progressBar.setVisibility(View.VISIBLE);
                            progressBar.setBackgroundColor(2);*//*
                        }
                    });
                }
            }
        }
        if (SingeltonArrayList.addWeather.size() == 0) ;
        {
            Log.d("length=", String.valueOf(SingeltonArrayList.addWeather.size()));

        }

        *//*runOnUiThread(new Runnable() {
            @Override
            public void run() {

                startService(new Intent(SplashScreen.this, DownloadWeatherService.class));
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setBackgroundColor(2);

            }


        });
*/



    }
	
	private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /*private void displayLocationSettingsRequest(final Context context) {


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
               *//* .addConnectionCallbacks(context)
                .addOnConnectionFailedListener(context)*//*
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
                            status.startResolutionForResult(SplashScreen.this, REQUEST_CHECK_SETTINGS);
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

                                startService(new Intent(SplashScreen.this, DownloadWeatherService.class));
                                *//*progressBar.setVisibility(View.VISIBLE);
                                progressBar.setBackgroundColor(2);
*//*
                            }


                        });

                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        //Toast.makeText(Splashscreen.this, "Click on no", Toast.LENGTH_SHORT).show();

                        // used denied to turn on GPS
                        //show autocomplete to get user's location.


                        break;
                }
                break;
        }
    }*/
    public void findPlace(View view) {

        try {

            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);

            startActivityForResult(intent, 1);

        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                // retrive the data by using getPlace() method.

                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.d(TAG, "Place: " + place.getAddress() + place.getPhoneNumber() );
                LatLng latLng = place.getLatLng();

                double lat = latLng.latitude;
                double longt = latLng.longitude;
                final String url = getUrl(lat, longt);
                Log.d("url = ", url);

                Object[] DataTransfer = new Object[1];

                DataTransfer[0] = url;
                new AsyncTaskToFetchWhetherDetails.GetWeatherDetails(SplashScreen.this).execute(url);


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public String getUrl(double latitude, double lonngitutde) {

        String url = WEATHER_API + "key=" + API_KEY + "&num_of_days=1&tp=3&q=" + latitude + "," + lonngitutde + "&format=json&lang=hi";
        return url;
    }

}


