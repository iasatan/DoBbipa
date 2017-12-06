
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoCloseBeaconException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Alignment;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;

public class MainActivity extends AppCompatActivity {

    BluetoothManager btManager;
    static final String CLOSESTROOM = "closestRoom";
    static final String RESIDENTS = "residents";
    static final String STARTED = "started";
    boolean started;
    int currentClosestRoomNumber;
    ArrayList<String> currentResidents;

    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    Button startScanningButton;
    Button stopScanningButton;
    TextView closestRoom;
    TextView residents;
    Map<String, Device> devices;
    List<ScanFilter> filter;
    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    DatabaseHandler db;
    Position position = new Position();
    List<Room> rooms /*= new ArrayList<Room>()*/;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        closestRoom = (TextView) findViewById(R.id.closestRoom);
        residents = (TextView) findViewById(R.id.residents);
        residents.setText("");
        closestRoom.setText("");

        devices = new HashMap<>();
        db = new DatabaseHandler(this, "dobbipa11", 1);
        if(db.getDeviceCount()<1)
            db.populateDatabase();

        for (Device device : db.getAllDevice()) {
            devices.put(device.getMAC(), device);
        }
        rooms=db.getAllRoom();



        startScanningButton = (Button) findViewById(R.id.StartScanButton);
        startScanningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startScanning();
            }
        });

        stopScanningButton = (Button) findViewById(R.id.StopScanButton);
        stopScanningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopScanning();
            }
        });
        stopScanningButton.setVisibility(View.INVISIBLE);

        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();


        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        if(savedInstanceState!=null){
            currentClosestRoomNumber=savedInstanceState.getInt(CLOSESTROOM);
            currentResidents=savedInstanceState.getStringArrayList(RESIDENTS);
            closestRoom.setText(currentClosestRoomNumber+"");
            for(String names : currentResidents){
                residents.append(names+"\n");
            }
            if(savedInstanceState.getBoolean(STARTED)){
                startScanning();
            }
            else
                stopScanning();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(CLOSESTROOM, currentClosestRoomNumber);
        savedInstanceState.putStringArrayList(RESIDENTS, currentResidents);
        savedInstanceState.putBoolean(STARTED, started);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


    private Position getPosition() throws NoCloseBeaconException {
        Position position = new Position();
        position.setId(0);
        position.setZ(4.4);
        position.setY(7);
        Map<String, Device> devices = new HashMap<String, Device>();
        for (Map.Entry<String, Device> device : this.devices.entrySet()){
            if (device.getValue().getAverageRSSI()!=0){
                if(device.getValue().getDistanceFrom()<12){ //TODO szervezd ki db-be, building szinten
                    devices.put(device.getKey(), device.getValue());
                }
            }
        }
        if(devices.size()==0)
            throw new NoCloseBeaconException();
        if(devices.size()==1){
            for (Map.Entry<String, Device> device : devices.entrySet()){
                if(device.getValue().getAlignment()== Alignment.PLUS){
                    position.setX(device.getValue().getPosition().getX()+device.getValue().getDistanceFrom());
                }
                else{
                    position.setX(device.getValue().getPosition().getX()-device.getValue().getDistanceFrom());
                }
            }
        }
        else{
            List<Device> closestDevices = new ArrayList<Device>();
            Device closestDevice=new Device(0, 0, "", new Position(0, 0.0, 0.0, 0.0), Alignment.MINUS);

            for (Map.Entry<String, Device> device : devices.entrySet()){
                if(device.getValue().getDistanceFrom()<closestDevice.getDistanceFrom()) {
                    closestDevices.add(device.getValue());
                    devices.remove(device);
                    break;
                }
            }
            for (Map.Entry<String, Device> device : devices.entrySet()){
                if(device.getValue().getDistanceFrom()<closestDevice.getDistanceFrom()) {
                    closestDevices.add(device.getValue());
                    devices.remove(device);
                    break;
                }
            }

            double xCoordinate = 12 / (closestDevices.get(0).getDistanceFrom()+closestDevices.get(1).getDistanceFrom());
            xCoordinate = xCoordinate * closestDevices.get(0).getDistanceFrom() + closestDevices.get(0).getPosition().getX();
            position.setX(xCoordinate);
        }
        return position;
    }

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
                    currentClosestRoomNumber=room.getNumber();
                    closestRoom.setText(currentClosestRoomNumber+"");
                    residents.setText("");
                    currentResidents = new ArrayList<String>();
                    for(Person person : room.getPeople()){
                        residents.append(person.toString()+ "\n");
                        currentResidents.add(person.toString());
                    }
                } catch (NoCloseBeaconException e) {
                    closestRoom.setText("No near Beacons found");
                }
            }
        }
    };

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
        startScanningButton.setVisibility(View.INVISIBLE);
        stopScanningButton.setVisibility(View.VISIBLE);
        started=true;
        filter = new ArrayList<ScanFilter>();
        for (Map.Entry<String, Device> device : devices.entrySet()){
            filter.add(new ScanFilter.Builder().setDeviceAddress(device.getValue().getMAC()).build());
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.startScan(filter, new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_BALANCED).build(), leScanCallback);
            }
        });
    }

    public void stopScanning() {
        System.out.println("stopping scanning");
        startScanningButton.setVisibility(View.VISIBLE);
        stopScanningButton.setVisibility(View.INVISIBLE);
        started=false;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.stopScan(leScanCallback);
            }
        });
    }
}