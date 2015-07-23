package com.tang.kevin.projectar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;


public class WorldActivity extends ActionBarActivity{
    ArchitectView architectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);
        this.architectView = (ArchitectView)findViewById(R.id.architectView);
        final StartupConfiguration config = new StartupConfiguration("gd3tYdWI8VaaEU0g8VjW7K3mF7OHcMLx7VT9kNrJgKXrGzCirUCsi7BK4VZ+H6+vEoWnr8JITtVT7H8g5swZFv3fbri7/AHosvilbmRIqwbw31d4zP0L4M6gtIddRGqfGHrNtd9TVNvplk30ojP/" +
                "TGG5SUmW3B2Cld+u6Cq9hThTYWx0ZWRfX7HuCsBvCuGc8ZJ8D59YJ6l9ruXyksc3tedfA4wpqc7tvlBSlaAXKCqELDUcGOiTQYEDb96Wuf+DEov3ZLJ0/ldmDsZaR7pAGnmPM0/TPVALBZf3702p470ANRsrHH0n9kzV4G" +
                "N85rk7lAwnANS1FUA2L8YCWq2nbyXvYyLhRtfp/4StZcGyAVwnEOq7ixdeDC/0OI91tF2TcSm9+/oITgi4TN5L6QMHnvKryzjTjJXEMmACCakHve+z+hTwk9mXNO/wkqwVGyfLS4oH0GXtjDrpaw50DSH1hd0JXfleaBvg" +
                "5HWzr2TLJX9DAAqnF+HxNDzRjVfPFj9LylEzJHIkjcEaVdJRuKSjNf6EWP/athP3sU88kAfsyGvtZ/6oJZ3m6WEA5vKlZtTjWjpdjmMB/C/Em3HVfb+X4trBoNzr7AjPOKzaCdfY/qOv75RYvP7A/OGs2a0I+bUm4p2RBN" +
                "pFk144lZNcsCVdTiYIen9zB9BlLlm2wFmjmD8=");
        this.architectView.onCreate(config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        //this,architectView.onPostCreate();
        //this.architectView.load("arexperience.html");
    }
}
