package hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;
import java.util.HashSet;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.DeviceDAO;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.PositionDAO;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;

/**
 * Created by iasatan on 2017.11.13..
 */

class DeviceSQLiteDAO extends SQLiteOpenHelper implements DeviceDAO {
    public DeviceSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    /*PositionDAO positionDAO;

    public DeviceSQLiteDAO(Context context, String databaseName, Integer databaseVersion, PositionDAO positionDAO) {
        super(context, databaseName, null, databaseVersion);
        this.positionDAO = positionDAO;
    }

    public DeviceSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DeviceSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Device(id INTEGER PRIMARY KEY, baserssi INTEGER, mac TEXT, position INTEGER)");
        populateDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Devices");
        onCreate(db);
    }

    private void populateDatabase() {
       /* addDevice(new Device(1, 70, "0C:F3:EE:00:96:A0", positionDAO.getPosition(1)));
        addDevice(new Device(2, 57, "0C:F3:EE:00:82:4B", positionDAO.getPosition(2)));
        addDevice(new Device(3, 65, "0C:F3:EE:00:63:8C", positionDAO.getPosition(3)));
        addDevice(new Device(4, 63, "0C:F3:EE:00:96:44", positionDAO.getPosition(4)));
        addDevice(new Device(5, 51, "0C:F3:EE:00:83:96", positionDAO.getPosition(1)));
        addDevice(new Device(6, 73, "0C:F3:EE:00:87:EC", positionDAO.getPosition(1)));
        addDevice(new Device(7, 77, "0C:F3:EE:00:87:A8", positionDAO.getPosition(1)));
    }

    public void addDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("baserssi", device.getBaseRSSI());
        contentValues.put("mac", device.getMAC());
        contentValues.put("position", device.getPosition().getId());
        db.insert("Device", null, contentValues);
        db.close();
    }

    public Device getDevice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Device", null, "id=" + id, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Position position = positionDAO.getPosition(Integer.parseInt(cursor.getString(3)));
        Device device = new Device(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), position);

        return device;
    }

    public Device getDevice(String mac) {
        SQLiteDatabase db = this.getReadableDatabase();
        Device device = new Device();
        Cursor cursor = db.query("Device", null, "mac=" + "'" + mac + "'", null, null, null, null);
        if (cursor == null) {
            device.setId(0);
            device.setPosition(new Position(0, 0, 0, 0));
            device.setMAC("");
            device.setBaseRSSI(0);
            return device;
        }
        if (cursor != null)
            cursor.moveToFirst();
        device = new Device(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), positionDAO.getPosition(Integer.parseInt(cursor.getString(3))));

        return device;
    }

    public Collection<Device> getAllDevice() {
        Collection<Device> devices = new HashSet<Device>();
        String selectQuery = "Select * From Device";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                device.setId(Integer.parseInt(cursor.getString(0)));
                device.setBaseRSSI(Integer.parseInt(cursor.getString(1)));
                device.setMAC(cursor.getString(2));
                Position position = positionDAO.getPosition(Integer.parseInt(cursor.getString(3)));
                device.setPosition(position);
                devices.add(device);
            } while (cursor.moveToNext());
        }
        return devices;
    }

    public int getDeviceCount() {
        String countQuery = "SELECT * FROM Device";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updateDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("baserssi", device.getBaseRSSI());
        values.put("mac", device.getMAC());
        values.put("position", device.getPosition().getId());

        return db.update("Device", values, "id=" + device.getId(), null);
    }

    public void deleteDevice(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Device", "id=" + id, null);
        db.close();
    }
    */
}
