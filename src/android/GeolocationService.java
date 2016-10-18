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

  private class ViperLocationListener implements LocationListener {
    private final static String TAG = "VIPER_LOCATION";

    @Override
    public void onLocationChanged(Location loc) {
      Log.d(TAG, "===========================================================================");
      Log.d(TAG, "Location changed : Lat: " + loc.getLatitude()+ " Lng: " + loc.getLongitude());
      lastLocation = loc;
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
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
  }

    @Override
    protected JSONObject doWork() {
        JSONObject result = new JSONObject();

        try {
      if (this.lastLocation != null) {
        result.put("accuracy", this.lastLocation.getAccuracy());
        result.put("bearing", this.lastLocation.getBearing());
        result.put("lat", this.lastLocation.getLatitude());
        result.put("lng", this.lastLocation.getLongitude());
        result.put("provider", this.lastLocation.getProvider());
        result.put("speed", this.lastLocation.getSpeed());
        result.put("time", this.lastLocation.getTime());
        result.put("lng", this.lastLocation.getLongitude());
      } else {
        Log.d(TAG, "/////// doWork() does not have location");
      }
        } catch (JSONException e) {
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
