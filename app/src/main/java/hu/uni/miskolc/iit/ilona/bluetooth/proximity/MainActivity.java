
package hu.uni.miskolc.iit.ilona.bluetooth.proximity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.test.R;
import com.example.android.test.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter.ResidentsRecycleViewAdapter;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoCloseBeaconException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;

public class MainActivity extends AppCompatActivity {

    static final String CLOSESTROOM = "closestRoom";
    static final String RESIDENTS = "residents";
    static final String STARTED = "started";
    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    ActivityMainBinding activityMainBinding;
    BluetoothManager btManager;
    boolean started;
    String currentClosestRoomNumber = "";
    String currentResidents = "";
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    TextView residents;
    Map<String, Device> devices;
    List<ScanFilter> filter;
    DatabaseHandler db;
    List<Room> rooms;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    // Device scan callback.
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            String address = result.getDevice().getAddress();
            Device device = devices.get(address);
            device.addRSSI(result.getRssi());
            if (device.getPrevRSSIs().size() >= 3) {
                Room room = null;
                try {
                    room = getPosition().getClosestRoom(rooms);
                    currentClosestRoomNumber = room.getNumber().toString();
                    recyclerViewAdapter = new ResidentsRecycleViewAdapter(room.getPeople());
                    activityMainBinding.residentsRecyclerView.setAdapter(recyclerViewAdapter);

                    activityMainBinding.setClosestRoom(currentClosestRoomNumber);
                } catch (NoCloseBeaconException e) {
                }
            }
        }
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        devices = new HashMap<>();
        db = new DatabaseHandler(getApplicationContext(), "dobbipa33", 1);
        if (db.getDeviceCount() < 1) {
            db.populateDatabase();
        }

        for (Device device : db.getAllDevice()) {
            devices.put(device.getMAC(), device);
        }
        rooms = db.getAllRoom();


        activityMainBinding.StartScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activityMainBinding.StartScanButton.setVisibility(View.INVISIBLE);
                activityMainBinding.StopScanButton.setVisibility(View.VISIBLE);
                startScanning();

            }
        });
        activityMainBinding.StopScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activityMainBinding.StartScanButton.setVisibility(View.VISIBLE);
                activityMainBinding.StopScanButton.setVisibility(View.INVISIBLE);
                stopScanning();
            }
        });

        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();


        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect peripherals.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }


        activityMainBinding.StartScanButton.setVisibility(View.INVISIBLE);
        activityMainBinding.StopScanButton.setVisibility(View.VISIBLE);
        startScanning();

        activityMainBinding.residentsRecyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        activityMainBinding.residentsRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new ResidentsRecycleViewAdapter(new ArrayList<Person>());
        activityMainBinding.residentsRecyclerView.setAdapter(recyclerViewAdapter);

        if (savedInstanceState != null) {
            currentClosestRoomNumber = savedInstanceState.getString(CLOSESTROOM);
            currentResidents = savedInstanceState.getString(RESIDENTS);
            activityMainBinding.setClosestRoom(currentClosestRoomNumber);
            activityMainBinding.setResidents(currentResidents);
            if (savedInstanceState.getBoolean(STARTED)) {
                activityMainBinding.StartScanButton.setVisibility(View.INVISIBLE);
                activityMainBinding.StopScanButton.setVisibility(View.VISIBLE);
                startScanning();
            } else {
                activityMainBinding.StartScanButton.setVisibility(View.VISIBLE);
                activityMainBinding.StopScanButton.setVisibility(View.INVISIBLE);
                stopScanning();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (currentClosestRoomNumber != null) {
            savedInstanceState.putString(CLOSESTROOM, currentClosestRoomNumber);
        }
        if (currentResidents != null) {
            savedInstanceState.putString(RESIDENTS, currentResidents);
        }
        savedInstanceState.putBoolean(STARTED, started);

        super.onSaveInstanceState(savedInstanceState);
    }

    private Map<String, Device> getNearDevices(Map<String, Device> devices) {
        Map<String, Device> nearDevices = new HashMap<String, Device>();
        for (Map.Entry<String, Device> device : devices.entrySet()) {
            if (device.getValue().getAverageRSSI() != 0) {
                if (device.getValue().getDistanceFrom() < 12) { //TODO szervezd ki a 12métert db-be, building szinten
                    nearDevices.put(device.getKey(), device.getValue());
                }
            }
        }
        return nearDevices;
    }

    private List<Device> closestTwoDevice(Map<String, Device> nearDevices) {
        Device[] result = new Device[2];
        result[0] = (Device) nearDevices.values().toArray()[0];
        result[1] = result[0];
        for (Device currentDevice : nearDevices.values()) {
            if (currentDevice.getDistanceFrom() < result[1].getDistanceFrom()) {
                result[1] = currentDevice;
            }
            if (currentDevice.getDistanceFrom() < result[0].getDistanceFrom()) {
                result[1] = result[0];
                result[0] = currentDevice;
            }
        }
        return Arrays.asList(result);
    }


    private Position getPosition() throws NoCloseBeaconException {
        Position position = new Position();
        position.setId(0);
        position.setZ(4.4);
        Map<String, Device> nearDevices = getNearDevices(devices);

        if (nearDevices.size() == 0) {
            throw new NoCloseBeaconException();
        }
        if (nearDevices.size() == 1) {
            List<String> keys = new ArrayList<String>(nearDevices.keySet());
            Device nearestDevice = nearDevices.get(keys.get(0));
            switch (nearestDevice.getAlignment()) {
                case RIGHT:
                    position.setY(nearestDevice.getPosition().getY());
                    position.setX(nearestDevice.getPosition().getX() - nearestDevice.getDistanceFrom());
                    break;
                case LEFT:
                    position.setY(nearestDevice.getPosition().getY());
                    position.setX(nearestDevice.getPosition().getX() + nearestDevice.getDistanceFrom());
                    break;
                case FRONT:
                    position.setY(nearestDevice.getPosition().getY() - nearestDevice.getDistanceFrom());
                    position.setX(nearestDevice.getPosition().getX());

                    break;
                case BEHIND:
                    position.setY(nearestDevice.getPosition().getY() + nearestDevice.getDistanceFrom());
                    position.setX(nearestDevice.getPosition().getX());
                    break;
            }
        } else {
            List<Device> closestDevices = closestTwoDevice(nearDevices);
            if (closestDevices.get(0).getPosition().getX() == closestDevices.get(1).getPosition().getX()) {
                double xCoordinate = 12 / (closestDevices.get(0).getDistanceFrom() + closestDevices.get(1).getDistanceFrom());
                xCoordinate = xCoordinate * closestDevices.get(0).getDistanceFrom() + closestDevices.get(0).getPosition().getX();
                position.setX(xCoordinate);
                position.setY(closestDevices.get(0).getPosition().getY());
            } else if (closestDevices.get(0).getPosition().getY() == closestDevices.get(1).getPosition().getY()) {
                double yCoordinate = 12 / (closestDevices.get(0).getDistanceFrom() + closestDevices.get(1).getDistanceFrom());
                yCoordinate = yCoordinate * closestDevices.get(0).getDistanceFrom() + closestDevices.get(0).getPosition().getY();
                position.setY(yCoordinate);
                position.setX(closestDevices.get(0).getPosition().getX());
            }
        }
        return position;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    public void startScanning() {
        started = true;
        filter = new ArrayList<ScanFilter>();
        for (Map.Entry<String, Device> device : devices.entrySet()) {
            filter.add(new ScanFilter.Builder().setDeviceAddress(device.getValue().getMAC()).build());
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.startScan(filter, new ScanSettings.Builder().setScanMode(ScanSettings.MATCH_MODE_AGGRESSIVE).build(), leScanCallback);
            }
        });
    }

    public void stopScanning() {
        started = false;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.stopScan(leScanCallback);
            }
        });
    }
}