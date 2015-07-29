package com.tang.kevin.projectar;

import android.app.Activity;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import java.io.IOException;


public class MainActivity extends AbstractArchitectCamActivity {

    /**
     * last time the calibration toast was shown, this avoids too many toast shown when compass needs calibration
     */
    private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();

    @Override
    public void onCreate( final Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onPostCreate( final Bundle savedInstanceState ) {
        super.onPostCreate(savedInstanceState);

        try {
            this.architectView.load(this.getARchitectWorldPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getARchitectWorldPath() {
        return "http://46.101.24.203/ar-manager/api/v1.0/?key=78e2185582b101b8ee0c78a60c5acaf9&world=1";
    }

    @Override
    public String getActivityTitle() {
        return "AR World";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public int getArchitectViewId() {
        return R.id.architectView;
    }

    @Override
    public String getWikitudeSDKLicenseKey() {
        String key = "gd3tYdWI8VaaEU0g8VjW7K3mF7OHcMLx7VT9kNrJgKXrGzCirUCsi7BK4VZ+H6+vEoWnr8JITtVT7H8g5swZFv3fbri7/AHosvilbmRIqwbw31d4zP0L4M6gtIddRGqfGHrNtd9TVNvplk30ojP/" +
                "TGG5SUmW3B2Cld+u6Cq9hThTYWx0ZWRfX7HuCsBvCuGc8ZJ8D59YJ6l9ruXyksc3tedfA4wpqc7tvlBSlaAXKCqELDUcGOiTQYEDb96Wuf+DEov3ZLJ0/ldmDsZaR7pAGnmPM0/TPVALBZf3702p470ANRsrHH0n9kzV4G" +
                "N85rk7lAwnANS1FUA2L8YCWq2nbyXvYyLhRtfp/4StZcGyAVwnEOq7ixdeDC/0OI91tF2TcSm9+/oITgi4TN5L6QMHnvKryzjTjJXEMmACCakHve+z+hTwk9mXNO/wkqwVGyfLS4oH0GXtjDrpaw50DSH1hd0JXfleaBvg" +
                "5HWzr2TLJX9DAAqnF+HxNDzRjVfPFj9LylEzJHIkjcEaVdJRuKSjNf6EWP/athP3sU88kAfsyGvtZ/6oJZ3m6WEA5vKlZtTjWjpdjmMB/C/Em3HVfb+X4trBoNzr7AjPOKzaCdfY/qOv75RYvP7A/OGs2a0I+bUm4p2RBN" +
                "pFk144lZNcsCVdTiYIen9zB9BlLlm2wFmjmD8=";
        return key;
    }

    @Override
    public ArchitectView.SensorAccuracyChangeListener getSensorAccuracyListener() {
        return new ArchitectView.SensorAccuracyChangeListener() {
            @Override
            public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
                if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && !MainActivity.this.isFinishing() && System.currentTimeMillis() - MainActivity.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
                    Toast.makeText( MainActivity.this, "Compass accuracy is low..", Toast.LENGTH_LONG ).show();
                    MainActivity.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
                }
            }
        };
    }

    @Override
    public ArchitectView.ArchitectUrlListener getUrlListener() {
        return new ArchitectView.ArchitectUrlListener() {

            @Override
            public boolean urlWasInvoked(String uriString) {
                return true;
            }
        };
    }

    @Override
    public ILocationProvider getLocationProvider(final LocationListener locationListener) {
        return new LocationProvider(this, locationListener);
    }

    @Override
    public float getInitialCullingDistanceMeters() {
        return ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS;
    }

    @Override
    protected boolean hasGeo() {
        return true;
    }

    @Override
    protected boolean hasIR() {
        return false;
    }

    @Override
    protected StartupConfiguration.CameraPosition getCameraPosition() {
        return StartupConfiguration.CameraPosition.DEFAULT;
    }
}
