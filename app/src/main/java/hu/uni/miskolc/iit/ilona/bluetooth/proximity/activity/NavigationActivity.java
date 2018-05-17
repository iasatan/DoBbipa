package hu.uni.miskolc.iit.ilona.bluetooth.proximity.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.example.android.test.R;
import com.example.android.test.databinding.ActivityNavigationBinding;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.DatabaseHandler;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoPathFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NodeNotFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Edge;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Navigation;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.User;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.util.DijkstraAlgorithm;

public class NavigationActivity extends AppCompatActivity implements SensorEventListener {

    private final static int REQUEST_ENABLE_BT = 1;
    private DatabaseHandler db;
    private List<Edge> edges;
    private ActivityNavigationBinding activityNavigationBinding;
    private Room room;
    private User user;
    private BluetoothAdapter btAdapter;
    private Map<String, Device> devices;
    private BluetoothLeScanner btScanner;
    private List<ScanFilter> filter;
    private List<Position> positions;
    private Position proximityPosition;
    private DijkstraAlgorithm dijkstraAlgorithm;
    private float currentDegree = 0f;
    private float correctionDegree;
    private SensorManager sensorManager;
    private Navigation navigation;
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            String address = result.getDevice().getAddress();
            Device device = devices.get(address);
            device.addRSSI(result.getRssi());
            if (device.getPrevRSSIs().size() >= 3) {
                user.addPosition(devices);
                proximityPosition = user.getClosestPosition(positions);
                //db.addHistory(new History(0, android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address"), proximityPosition.getId(), currentDegree));
                activityNavigationBinding.setPosition(proximityPosition.toString());
                try {
                    navigation.navigateDirectionBased(proximityPosition);
                } catch (NodeNotFoundException e) {

                } catch (NoPathFoundException e) {
                    e.printStackTrace();
                }
                try {
                    activityNavigationBinding.distance.setText(navigation.getDistance().toString() + "m / " + navigation.getTotalDistance().toString() + "m");
                } catch (Exception e) {

                }

                String nextPosition = navigation.getNextPositionText();
                if (NumberUtils.isCreatable(nextPosition)) {
                    activityNavigationBinding.nextPosition.setText(getString(Integer.parseInt(nextPosition)));
                } else {
                    activityNavigationBinding.nextPosition.setText(navigation.getNextPositionText());
                }
                correctionDegree = navigation.getCorrectionDegree();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHandler(getApplicationContext());
        devices = db.getDeviceMap();
        user = new User();
        positions = db.getAllPosition();
        edges = db.getAllEdge();


        try {
            room = db.getRoom(getIntent().getExtras().getInt("room"));

        } catch (NullPointerException e) {
            startActivity(new Intent(NavigationActivity.this, SearchActivity.class));
        }
        dijkstraAlgorithm = new DijkstraAlgorithm(edges, positions);


        dataBinding();
        try {
            dijkstraAlgorithm.buildShortestPaths(room.getPosition());
        } catch (NodeNotFoundException e) {
            activityNavigationBinding.setRoomNumber(getString(R.string.noRoomNumberException));
        }
        navigation = new Navigation(dijkstraAlgorithm, room);
        initBluetooth();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        startScanning();

    }

    private void initBluetooth() {
        BluetoothManager btManager;
        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        try {
            btAdapter = btManager.getAdapter();
        } catch (NullPointerException e) {
            startActivity(new Intent(NavigationActivity.this, SearchActivity.class));
        }
        btScanner = btAdapter.getBluetoothLeScanner();
        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    private void dataBinding() {
        activityNavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);
        activityNavigationBinding.setRoomNumber(room.getNumber().toString());
        activityNavigationBinding.setPosition("");
        activityNavigationBinding.imageNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ImageNavigationActivity.class);
                intent.putExtra("room", room.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        degree -= correctionDegree;

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        activityNavigationBinding.imageViewCompass.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    private void startScanning() {
        filter = new ArrayList<>();
        for (Map.Entry<String, Device> device : devices.entrySet()) {
            filter.add(new ScanFilter.Builder().setDeviceAddress(device.getValue().getMAC()).build());
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.startScan(filter, new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build(), leScanCallback);
            }
        });
    }
}
