package com.sabin.simplenavigationview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    TextView ProximitySensor, data;
    SensorManager mySensorManager;
    Sensor myProximitySensor;

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textView;
    boolean running = false;
    float count;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ProximitySensor = (TextView) findViewById(R.id.proximitySensor);
        data = (TextView) findViewById(R.id.data);
        mySensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            ProximitySensor.setText("No Proximity Sensor!");
        } else {
            mySensorManager.registerListener(proximitySensorEventListener,
                    myProximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        textView = findViewById(R.id.steps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(sensor1==null)
            Toast.makeText(this, "DETECTOR NOT FOUND", Toast.LENGTH_SHORT).show();

        //count = sensor.getFifoReservedEventCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running  = true;
        if(sensor!=null){
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
        else
            Toast.makeText(this, "SENSOR NOT FOUND", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(running){
            textView.setText(String.valueOf(sensorEvent.values[0]-count));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    SensorEventListener proximitySensorEventListener
            = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            WindowManager.LayoutParams params = MainActivity.this.getWindow().getAttributes();
            if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){

                if(event.values[0]==0){
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = 0;
                    getWindow().setAttributes(params);
                }
                else{
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = -1f;
                    getWindow().setAttributes(params);
                }
            }
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.fname:
                Toast.makeText(MainActivity.this, "Selected Profile Picture", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lname:
                Toast.makeText(MainActivity.this, "first name", Toast.LENGTH_SHORT).show();
                break;
            case R.id.address:
                Toast.makeText(MainActivity.this, "address", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dob:
                Toast.makeText(MainActivity.this, "date", Toast.LENGTH_SHORT).show();
                break;
            case R.id.gender:
                Toast.makeText(MainActivity.this, "Gender", Toast.LENGTH_SHORT).show();
                break;

        }

        return false;
    }


}
