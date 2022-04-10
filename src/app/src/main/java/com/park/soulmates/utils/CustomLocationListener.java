package com.park.soulmates.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class CustomLocationListener implements LocationListener {
    static Location sCurrentLocation; // здесь будет всегда доступна самая последняя информация о местоположении пользователя.
    private static final int REQUEST_CODE = 0;
    private static Activity sActivity;

    public static Activity getActivity() {
        return sActivity;
    }

    public static void setActivity(Activity activity) {
        CustomLocationListener.sActivity = activity;
    }

    // FIXME: check permission
    @SuppressLint("MissingPermission")
    public static void SetUpLocationListener(Context context, Activity act) // это нужно запустить в самом начале работы программы
    {
        sActivity = act;
        int permissionCheck = ContextCompat.checkSelfPermission(act,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            Log.d("dev_location", "no permission");

//            ActivityCompat.requestPermissions(act,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_CODE);
        } else {
            LocationManager locationManager = (LocationManager)
                    context.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new CustomLocationListener();

//            locationManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    190,
//                    300,
//                    locationListener); // здесь можно указать другие более подходящие вам параметры
            locationManager.requestSingleUpdate(
                    LocationManager.GPS_PROVIDER,
                    locationListener,
                    Looper.getMainLooper()
            );

            sCurrentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }


    public static Location getCurrentLocation() {
        return sCurrentLocation;
    }


    public static void setCurrentLocation(Location currentLocation) {
        CustomLocationListener.sCurrentLocation = currentLocation;
    }

    @Override
    public void onLocationChanged(Location loc) {
        sCurrentLocation = loc;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
