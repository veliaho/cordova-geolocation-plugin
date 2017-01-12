package com.vinka.cordova.plugin.geolocation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

public class GeolocationService extends BackgroundService {

    private static final int MIN_TIME = 5;  // location changes must be at least 5 seconds apart
    private static final int MIN_DIST = 10; // location changes must be at least 10 meters apart

    private class ViperLocationListener implements LocationListener {
        private final static String TAG = "VIPER_LOCATION";

        @Override
        public void onLocationChanged(Location loc) {
            Log.d(TAG, "===========================================================================");
            Log.d(TAG, "Location changed : Lat: " + loc.getLatitude() + " Lng: " + loc.getLongitude());
            lastLocation = loc;
            GeolocationService.this.handleLastResult(
                GeolocationService.this.formatGeopos(loc));
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    private Location lastLocation = null;
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    private final static String TAG = GeolocationService.class.getSimpleName();

    private String mHelloTo = "World";

    @Override
    public void onCreate() {

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        locationListener = new ViperLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
    }

    private JSONObject formatGeopos(Location loc) {
        JSONObject result = new JSONObject();

        try {
            result.put("accuracy", loc.getAccuracy());
            result.put("bearing", loc.getBearing());
            result.put("lat", loc.getLatitude());
            result.put("lng", loc.getLongitude());
            result.put("provider", loc.getProvider());
            result.put("speed", loc.getSpeed());
            result.put("time", loc.getTime());
            result.put("lng", loc.getLongitude());
        } catch (JSONException e) {
        }

        return result;
    }

    @Override
    protected JSONObject doWork() {
        JSONObject result = new JSONObject();

        if (this.lastLocation != null) {
            result = this.formatGeopos(this.lastLocation);
        } else {
            Log.d(TAG, "/////// doWork() does not have location");
        }

        return result;
    }

    @Override
    protected JSONObject getConfig() {
        JSONObject result = new JSONObject();

        try {
            result.put("HelloTo", this.mHelloTo);
        } catch (JSONException e) {
        }

        return result;
    }

    @Override
    protected void setConfig(JSONObject config) {
        try {
            if (config.has("HelloTo"))
                this.mHelloTo = config.getString("HelloTo");
        } catch (JSONException e) {
        }

    }

    @Override
    protected JSONObject initialiseLatestResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void onTimerEnabled() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onTimerDisabled() {
        // TODO Auto-generated method stub

    }
}
