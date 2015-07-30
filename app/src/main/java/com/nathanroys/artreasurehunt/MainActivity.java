package com.nathanroys.artreasurehunt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nathanroys.artreasurehunt.artreasurehunt.R;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
        String key = "dtnhjx0ZY637aEcMOkRExNvk4AuMBbzrAf4aq2nUxxcUKBIZui5pONyXLXVN0dN8FPLgBcZfILeB" +
                "gurRwziyXCji2zPgIjrooBLYztjUa3Z0nUXN9eczHEYfa6gseIy+qLG0sZYfvaKbVhP09Kaf/hO2HrpDC" +
                "MGEAc9rooiCyAxTYWx0ZWRfX8+TNsVMDlvLQ92rERUVumVRujP80/R5zrGtXKtRomtYsQP2gOWxF3Pp+k" +
                "lngpdJr8v1Iyab2YRkFD3eyPQIMZmymayeBw2MH9ZlqsFwSPww7qd1qqq8E/Zd83Sv+nJdF3qbPWU41Bg" +
                "3UusQeRDgdFFBLnq2GG5uJrsEm+FYhi9dO0FyH+0HDvvobo+uWfMws+QZ7yhuF8RfGFdx5vA6a3bdw6AE" +
                "SGe26dtKMbEpcvS8kJBMPKqVs8ifUI2M70fh//cZfPu36fRdjnMNln1M8UUf08dHc/NJd2nHDQ2gU5ZIG" +
                "a9O+lmJ3snblZjCUpXg3GMpuo7FO/O7pC6zJG+NcBkyGgvpu/BMoNVxJ3Hp4b5o7mKeUSQyhcLuJiXsQ2" +
                "9t4PRsi3CaCaHXLMvBOGofDdEvGe0oxDGsqmxoiL5QTDWPU545eVINe6WLB2z2D5SAwzOqznrCzoI6Ncx" +
                "J+AvZVKC8lKJydAFeZRWHyNWscPZnnqW+jUbfbrQzuHtIP7CkAeGwH864h5374UXyd6VvoLUKz98vKg3B" +
                "KiHFrFdE6ABdtAMiEjt8e5S1fc2y0yfw+kFVMgKJjVvt";
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
                //architectsdk://MarkerSelected?id=4&longitude=-0.370354&latitude=53.770016&desc=aHR0cDovLzQ2LjEwMS4yNC4yMDMvYXItbWFuYWdlci9hcGkvdjEuMC9tYXJrZXItZGVzY3JpcHRpb24ucGhwP2tleT03OGUyMTg1NTgyYjEwMWI4ZWUwYzc4YTYwYzVhY2FmOSZ3b3JsZD0xJmlkPTQ=
                // id=4
                // longitude=-0.370354
                // latitude=53.770016
                // desc=aHR0cDovLzQ2LjEwMS4yNC4yMDMvYXItbWFuYWdlci9hcGkvdjEuMC9tYXJrZXItZGVzY3JpcHRpb24ucGhwP2tleT03OGUyMTg1NTgyYjEwMWI4ZWUwYzc4YTYwYzVhY2FmOSZ3b3JsZD0xJmlkPTQ=
                if(uriString.contains("architectsdk://MarkerSelected?"))
                {
                    // Format the uri
                    uriString = uriString.replace("architectsdk://MarkerSelected?", "");
                    String[] args = uriString.split("&");

                    // Put into easier to use format
                    Map argumentMap = getMap(args);

                    // Decodee the URL from base64
                    byte[] urlBytes = Base64.decode((String) argumentMap.get("desc"), -1);
                    String url = new String(urlBytes);

                    // Show webview and load url
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                    WebView wv = new WebView(MainActivity.this);
                    wv.setBackgroundColor(Color.TRANSPARENT);

                    WebSettings webSettings = wv.getSettings();
                    webSettings.setJavaScriptEnabled(true);

                    wv.loadUrl(url);
                    wv.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);

                            return true;
                        }
                    });

                    alert.setView(wv);
                    alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = alert.show();
                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                    lp.windowAnimations = R.style.Animations_SmileWindow;

                }
                return true;
            }
        };
    }

    private Map<String, String> getMap(String[] args)
    {
        Map<String, String> returnMap = new HashMap<String, String>();

        for(int i = 0; i < args.length; i++)
        {
            String[] arg = args[i].split("=");
            returnMap.put(arg[0], arg[1]);
        }
        return returnMap;
    }

    @Override
    public ILocationProvider getLocationProvider(final LocationListener locationListener) {
        return new LocationProvider(this, locationListener);
    }

    @Override
    public float getInitialCullingDistanceMeters() {
        return CULLING_DISTANCE_DEFAULT_METERS;
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
