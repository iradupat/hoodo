package com.example.hodoo.service;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserLocationService extends Service implements LocationListener {
    private double longitude = 0.0;
    private double latitude = 0.0;
    private Context mContext;
    protected LocationManager locationManager;

    public UserLocationService(Context context) {
        mContext = context;
        locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);
        setLongitudeAndLatitude();

    }


    private void setLongitudeAndLatitude() {

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 60 * 3, 10, this);
            Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (gpsLocation != null) {
                latitude = gpsLocation.getLatitude();
                longitude = gpsLocation.getLongitude();
            }

            Log.i("Location", "Latitude: " + longitude);
            Log.i("Location", "Longitude: " + latitude);
        }
    }

    public String getLocationName() {

        if (longitude == 0.0 && latitude == 0.0) {
            setLongitudeAndLatitude();
        }

        String locationName = "";
        try {

            Log.i( "Location","Latitude1: " + latitude);
            Log.i("Location","Longitude1: "+longitude);
            Geocoder geo = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                locationName = "Empty Location";
            }
            else { // addresses.get(0).getPostalCode()+", "+ // for postal code, it is some times null hence not included in the alocation name
                locationName = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();

                Log.i("Location","name: "+locationName);
            }
        } catch (Exception e) {

        }
        return locationName;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        System.out.println(location);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}
