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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CustomLocationListener implements LocationListener {
    private static int REQUEST_CODE = 0;
    private static Activity activity;

    static Location currentLocation; // здесь будет всегда доступна самая последняя информация о местоположении пользователя.

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        CustomLocationListener.activity = activity;
    }

    // FIXME: check permission
    @SuppressLint("MissingPermission")
    public static void SetUpLocationListener(Context context, Activity act) // это нужно запустить в самом начале работы программы
    {
        activity = act;
        int permissionCheck = ContextCompat.checkSelfPermission(act,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            Log.d("dev_location","no permission");

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

            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }


    public static Location getCurrentLocation() {
        return currentLocation;
    }


    public static void setCurrentLocation(Location currentLocation) {
        CustomLocationListener.currentLocation = currentLocation;
    }

    @Override
    public void onLocationChanged(Location loc) {
        currentLocation = loc;
    }
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
