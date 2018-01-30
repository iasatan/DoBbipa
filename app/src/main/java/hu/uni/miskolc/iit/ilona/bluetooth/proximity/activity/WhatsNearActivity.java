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
import com.example.android.test.databinding.ActivityWhatsNearBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.DatabaseHandler;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter.SearchRecycleViewAdapter;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoCloseBeaconException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.SearchResult;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.User;

public class WhatsNearActivity extends AppCompatActivity {
    //region ble transmission
    /*AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
        }
    };*/
    //endregion
    //region variables
    private static final String STARTED = "started";
    private static final String CLOSESTROOMID = "closestRoomId";
    private final static int REQUEST_ENABLE_BT = 1;
    private List<Room> rooms;
    private List<ScanFilter> filter;
    private ActivityWhatsNearBinding activityWhatsNearBinding;
    private boolean started;
    private User user;
    private Room currentClosestRoom;
    private BluetoothLeScanner btScanner;
    private BluetoothAdapter btAdapter;
    private Map<String, Device> devices;
    private RecyclerView.Adapter searchRecyclerViewAdapter;

    //endregion
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            String address = result.getDevice().getAddress();
            Device device = devices.get(address);
            device.addRSSI(result.getRssi());
            if (device.getPrevRSSIs().size() >= 3) {
                try {
                    user.addPosition(devices);
                    currentClosestRoom = user.getClosestRoom(rooms);
                    List<Person> people = currentClosestRoom.getPeople();
                    List<SearchResult> results = new ArrayList<>();
                    for (Person person : people) {
                        results.add(new SearchResult(person.getImage(), person.getName(), person.getTitle(), currentClosestRoom.getId()));
                    }
                    searchRecyclerViewAdapter = new SearchRecycleViewAdapter(results);
                    activityWhatsNearBinding.residentsRecyclerView.setAdapter(searchRecyclerViewAdapter);
                    activityWhatsNearBinding.setClosestRoom(currentClosestRoom.getNumber().toString());
                    activityWhatsNearBinding.setRoomTitle(currentClosestRoom.getTitle());
                } catch (NoCloseBeaconException e) {
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //region bluetooth init
        BluetoothManager btManager;
        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        if (!btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        btScanner = btAdapter.getBluetoothLeScanner();
        //endregion
        //region database
        DatabaseHandler db;
        devices = new HashMap<>();
        db = new DatabaseHandler(getApplicationContext());
        for (Device device : db.getAllDevice()) {
            devices.put(device.getMAC(), device);
        }
        user = new User();
        rooms = db.getAllRoom();
        //endregion
        //region contenView
        activityWhatsNearBinding = DataBindingUtil.setContentView(this, R.layout.activity_whats_near);

        activityWhatsNearBinding.StartScanButton.setVisibility(View.INVISIBLE);
        activityWhatsNearBinding.StopScanButton.setVisibility(View.VISIBLE);
        startScanning();
        RecyclerView.LayoutManager searchRecyclerViewLayoutManager;

        activityWhatsNearBinding.residentsRecyclerView.setHasFixedSize(true);
        searchRecyclerViewLayoutManager = new LinearLayoutManager(this);
        activityWhatsNearBinding.residentsRecyclerView.setLayoutManager(searchRecyclerViewLayoutManager);
        searchRecyclerViewAdapter = new SearchRecycleViewAdapter(new ArrayList<SearchResult>());
        activityWhatsNearBinding.residentsRecyclerView.setAdapter(searchRecyclerViewAdapter);
        //endregion
        //region button listeners
        activityWhatsNearBinding.StartScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activityWhatsNearBinding.StartScanButton.setVisibility(View.INVISIBLE);
                activityWhatsNearBinding.StopScanButton.setVisibility(View.VISIBLE);
                startScanning();

            }
        });
        activityWhatsNearBinding.StopScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activityWhatsNearBinding.StartScanButton.setVisibility(View.VISIBLE);
                activityWhatsNearBinding.StopScanButton.setVisibility(View.INVISIBLE);
                stopScanning();
            }
        });
        //endregion
        if (savedInstanceState != null) {
            try {
                currentClosestRoom = db.getRoom(savedInstanceState.getInt(CLOSESTROOMID));
                List<Person> people = currentClosestRoom.getPeople();
                List<SearchResult> results = new ArrayList<>();
                for (Person person : people) {
                    results.add(new SearchResult(person.getImage(), person.getName(), person.getTitle(), currentClosestRoom.getId()));
                }
                searchRecyclerViewAdapter = new SearchRecycleViewAdapter(results);
                activityWhatsNearBinding.residentsRecyclerView.setAdapter(searchRecyclerViewAdapter);

                activityWhatsNearBinding.setClosestRoom(currentClosestRoom.getNumber().toString());
                activityWhatsNearBinding.setRoomTitle(currentClosestRoom.getTitle());
            } catch (Exception e) {
            }

            if (savedInstanceState.getBoolean(STARTED)) {
                activityWhatsNearBinding.StartScanButton.setVisibility(View.INVISIBLE);
                activityWhatsNearBinding.StopScanButton.setVisibility(View.VISIBLE);
                startScanning();
            } else {
                activityWhatsNearBinding.StartScanButton.setVisibility(View.VISIBLE);
                activityWhatsNearBinding.StopScanButton.setVisibility(View.INVISIBLE);
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

    @Override
    protected void onStop() {
        super.onStop();
        stopScanning();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startScanning();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScanning();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startScanning();
    }


    private void startScanning() {
        started = true;
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

    private void stopScanning() {
        started = false;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.stopScan(leScanCallback);
            }
        });
    }
}
