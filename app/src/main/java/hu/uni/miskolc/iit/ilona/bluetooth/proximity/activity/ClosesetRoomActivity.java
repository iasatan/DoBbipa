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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.test.R;
import com.example.android.test.databinding.ActivityClosesetRoomBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.DatabaseHandler;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter.ResidentsRecycleViewAdapter;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoCloseBeaconException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.User;

public class ClosesetRoomActivity extends AppCompatActivity {

    static final String CLOSESTROOMID = "closestRoomId";
    static final String STARTED = "started";
    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    ActivityClosesetRoomBinding activityClosesetRoomBinding;
    BluetoothManager btManager;
    boolean started;
    User user;
    Room currentClosestRoom;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
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
                    user.addPosition(devices);
                    room = user.getClosestRoom(rooms);
                    currentClosestRoom = room;
                    recyclerViewAdapter = new ResidentsRecycleViewAdapter(currentClosestRoom.getPeople());
                    activityClosesetRoomBinding.residentsRecyclerView.setAdapter(recyclerViewAdapter);
                    activityClosesetRoomBinding.setClosestRoom(currentClosestRoom.getNumber().toString());
                } catch (NoCloseBeaconException e) {
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        user = new User(getApplicationContext());
        activityClosesetRoomBinding = DataBindingUtil.setContentView(this, R.layout.activity_closeset_room);
        devices = new HashMap<>();
        db = new DatabaseHandler(getApplicationContext());//, getString(R.string.databaseName), 1);
        for (Device device : db.getAllDevice()) {
            devices.put(device.getMAC(), device);
        }
        rooms = db.getAllRoom();


        activityClosesetRoomBinding.StartScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activityClosesetRoomBinding.StartScanButton.setVisibility(View.INVISIBLE);
                activityClosesetRoomBinding.StopScanButton.setVisibility(View.VISIBLE);
                startScanning();

            }
        });
        activityClosesetRoomBinding.StopScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activityClosesetRoomBinding.StartScanButton.setVisibility(View.VISIBLE);
                activityClosesetRoomBinding.StopScanButton.setVisibility(View.INVISIBLE);
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


        activityClosesetRoomBinding.StartScanButton.setVisibility(View.INVISIBLE);
        activityClosesetRoomBinding.StopScanButton.setVisibility(View.VISIBLE);
        startScanning();

        activityClosesetRoomBinding.residentsRecyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        activityClosesetRoomBinding.residentsRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new ResidentsRecycleViewAdapter(new ArrayList<Person>());
        activityClosesetRoomBinding.residentsRecyclerView.setAdapter(recyclerViewAdapter);

        if (savedInstanceState != null) {
            try {
                currentClosestRoom = db.getRoom(savedInstanceState.getInt(CLOSESTROOMID));
                recyclerViewAdapter = new ResidentsRecycleViewAdapter(currentClosestRoom.getPeople());
                activityClosesetRoomBinding.residentsRecyclerView.setAdapter(recyclerViewAdapter);

                activityClosesetRoomBinding.setClosestRoom(currentClosestRoom.getNumber().toString());
            } catch (Exception e) {
            }

            if (savedInstanceState.getBoolean(STARTED)) {
                activityClosesetRoomBinding.StartScanButton.setVisibility(View.INVISIBLE);
                activityClosesetRoomBinding.StopScanButton.setVisibility(View.VISIBLE);
                startScanning();
            } else {
                activityClosesetRoomBinding.StartScanButton.setVisibility(View.VISIBLE);
                activityClosesetRoomBinding.StopScanButton.setVisibility(View.INVISIBLE);
                stopScanning();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (currentClosestRoom != null) {
            savedInstanceState.putInt(CLOSESTROOMID, currentClosestRoom.getId());
        }
        savedInstanceState.putBoolean(STARTED, started);
        super.onSaveInstanceState(savedInstanceState);
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
                btScanner.startScan(filter, new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build(), leScanCallback);
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
