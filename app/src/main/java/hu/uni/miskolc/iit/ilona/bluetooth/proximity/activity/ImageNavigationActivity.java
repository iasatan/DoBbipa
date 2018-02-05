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

import com.example.android.test.R;
import com.example.android.test.databinding.ActivityImageNavigationBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.DatabaseHandler;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoCloseBeaconException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoPathFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NodeNotFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Edge;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.User;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.util.DijkstraAlgorithm;

public class ImageNavigationActivity extends AppCompatActivity implements SensorEventListener {
    private final static int REQUEST_ENABLE_BT = 1;
    Double totalDistance;
    private String macAddress;
    private DatabaseHandler db;
    private List<Edge> edges;
    private ActivityImageNavigationBinding activityImageNavigationBinding;
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
    private Double distance = 1000.0;
    private SensorManager sensorManager;
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            String address = result.getDevice().getAddress();
            Device device = devices.get(address);
            device.addRSSI(result.getRssi());
            if (device.getPrevRSSIs().size() >= 3) {
                try {
                    user.addPosition(devices);
                    proximityPosition = user.getClosestPosition(positions);
                    //db.addHistory(new History(0, macAddress, proximityPosition.getId(), currentDegree));
                    if (proximityPosition.equals(room.getPosition()) || distance < 1) {
                        activityImageNavigationBinding.nextPosition.setText(getString(R.string.arrived));
                        activityImageNavigationBinding.imageView.setImageDrawable(getDrawable(R.drawable.arrived));
                    } else {
                        LinkedList<Position> path = dijkstraAlgorithm.getPath(proximityPosition);
                        Position nextPosition = path.get(path.size() - 2);
                        //Position picturePosition = dijkstraAlgorithm.getClosestPositionInPathWithPicture(proximityPosition);
                        if (path != null) {
                            distance = 0.0;
                            int picture = 0;
                            totalDistance = dijkstraAlgorithm.getTotalDistance(proximityPosition);
                            activityImageNavigationBinding.nextPosition.setText(nextPosition.toString());
                            if (proximityPosition.getX() == nextPosition.getX()) {
                                if (proximityPosition.getY() < nextPosition.getY()) {
                                    picture = dijkstraAlgorithm.getClosestPositionInPathWithPicture(nextPosition, 1).getFrontId();
                                } else {
                                    picture = dijkstraAlgorithm.getClosestPositionInPathWithPicture(nextPosition, 3).getBehindId();
                                }
                                for (int i = 0; i < path.size() - 2; i++) {
                                    if (path.get(i).getX() == proximityPosition.getX()) {
                                        distance = Math.abs(path.get(i).getY() - proximityPosition.getY());
                                        break;
                                    }
                                }

                            } else if (proximityPosition.getY() == nextPosition.getY()) {
                                if (proximityPosition.getX() < nextPosition.getX()) {
                                    picture = dijkstraAlgorithm.getClosestPositionInPathWithPicture(nextPosition, 4).getLeftId();
                                } else {
                                    picture = dijkstraAlgorithm.getClosestPositionInPathWithPicture(nextPosition, 2).getRightId();
                                }

                                for (int i = 0; i < path.size() - 2; i++) {
                                    if (path.get(i).getY() == proximityPosition.getY()) {
                                        distance = Math.abs(path.get(i).getX() - proximityPosition.getX());
                                        break;
                                    }
                                }

                            }
                            activityImageNavigationBinding.imageView.setImageDrawable(getDrawable(picture));
                        }
                        activityImageNavigationBinding.distance.setText(distance.toString() + "m / " + totalDistance.toString() + "m");
                        activityImageNavigationBinding.setPosition(proximityPosition.toString());
                    }
                } catch (NoCloseBeaconException e) {
                } catch (NodeNotFoundException e) {
                    activityImageNavigationBinding.setRoomNumber("the developer is fucking retarded");
                } catch (NoPathFoundException e) {
                    activityImageNavigationBinding.setRoomNumber("the developer is fucking retarded");

                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BluetoothManager btManager;
        macAddress = android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address");
        db = new DatabaseHandler(getApplicationContext());
        devices = new HashMap<>();
        user = new User();

        for (Device device : db.getAllDevice()) {
            devices.put(device.getMAC(), device);
        }
        positions = db.getAllPosition();
        edges = db.getAllEdge();


        try {
            room = db.getRoom(getIntent().getExtras().getInt("room"));

        } catch (NullPointerException e) {
            startActivity(new Intent(ImageNavigationActivity.this, SearchActivity.class));
        }
        dijkstraAlgorithm = new DijkstraAlgorithm(edges, positions);


        activityImageNavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_navigation);
        activityImageNavigationBinding.setRoomNumber(room.getNumber().toString());
        activityImageNavigationBinding.setPosition("");
        try {
            dijkstraAlgorithm.buildShortestPaths(room.getPosition());
        } catch (NodeNotFoundException e) {
            activityImageNavigationBinding.setRoomNumber("the developer is fucking retarded");
        }
        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        try {
            btAdapter = btManager.getAdapter();
        } catch (NullPointerException e) {
            startActivity(new Intent(ImageNavigationActivity.this, SearchActivity.class));
        }
        btScanner = btAdapter.getBluetoothLeScanner();
        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        startScanning();
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
        currentDegree = Math.round(event.values[0]);
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
