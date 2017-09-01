package com.example.swordpc07.world_weather_online;

import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by swordpc07 on 8/29/2017.
 */

public class AskForTurnOnGPS {

    Context context;
    ProgressBar progressBar;

    String TAG = "AskForTurnOnGps";

    public AskForTurnOnGPS(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    /*private void displayLocationSettingsRequest(final Context context) {


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
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
                            status.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
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
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            switch (requestCode) {
                // Check for the integer request code originally supplied to startResolutionForResult().
                case REQUEST_CHECK_SETTINGS:
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            Log.i(TAG, "User agreed to make required location settings changes.");
                            //Toast.makeText(Splashscreen.this, "Click on yes", Toast.LENGTH_SHORT).show();
                            //user accpetd to turn on GPS service
                            //start service to fetch user location and get whether details from an api.

                            *//*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    startService(new Intent(SplashScreen.this, DownloadWeatherService.class));
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressBar.setBackgroundColor(2);

                                }


                            });
*//*
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
}
