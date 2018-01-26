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
import android.view.View;

import com.example.android.test.R;
import com.example.android.test.databinding.ActivityNavigationBinding;

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

public class NavigationActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
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
                    if (proximityPosition.equals(room.getPosition())) {
                        activityNavigationBinding.nextPositon.setText(R.string.arrived);
                    } else {

                        LinkedList<Position> path = dijkstraAlgorithm.getPath(proximityPosition);
                        if (path != null) {
                            activityNavigationBinding.nextPositon.setText(path.get(path.size() - 2).toString());
                        }
                        activityNavigationBinding.setPosition(proximityPosition.toString());
                    }
                } catch (NoCloseBeaconException e) {
                } catch (NodeNotFoundException e) {
                    activityNavigationBinding.setRoomNumber("the developer is fucking retarded");
                } catch (NoPathFoundException e) {
                    activityNavigationBinding.setRoomNumber("the developer is fucking retarded");

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler db;
        BluetoothManager btManager;
        db = new DatabaseHandler(getApplicationContext());
        devices = new HashMap<>();
        user = db.getUser(android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address"));

        for (Device device : db.getAllDevice()) {
            devices.put(device.getMAC(), device);
        }
        positions = db.getAllPosition();
        edges = db.getAllEdge();


        try {
            room = db.getRoom(getIntent().getExtras().getInt("room"));

        } catch (NullPointerException e) {
            startActivity(new Intent(NavigationActivity.this, SearchActivity.class));
        }
        dijkstraAlgorithm = new DijkstraAlgorithm(edges, positions);


        activityNavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);
        activityNavigationBinding.setRoomNumber(room.getNumber().toString());
        activityNavigationBinding.setPosition("");
        try {
            dijkstraAlgorithm.buildShortestPaths(room.getPosition());
        } catch (NodeNotFoundException e) {
            activityNavigationBinding.setRoomNumber("the developer is fucking retarded");
        }
        activityNavigationBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkedList<Position> path = null;
                try {
                    path = dijkstraAlgorithm.getPath(proximityPosition);
                } catch (NodeNotFoundException e) {
                    activityNavigationBinding.setRoomNumber("the developer is fucking retarded");
                } catch (NoPathFoundException e) {
                    activityNavigationBinding.setRoomNumber("the developer is fucking retarded");
                }
                if (path != null) {
                    activityNavigationBinding.nextPositon.setText(path.getFirst().toString());
                } else {
                    activityNavigationBinding.nextPositon.setText("nope");
                }
            }
        });
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
        startScanning();

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
