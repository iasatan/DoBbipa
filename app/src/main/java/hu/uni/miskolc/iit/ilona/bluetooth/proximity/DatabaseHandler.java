
package hu.uni.miskolc.iit.ilona.bluetooth.proximity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Alignment;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;

/**
 * Created by iasatan on 2017.10.17..
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String databaseName = "dobbipa";
    private static final Integer databaseVersion = 4;
    Context context;

    public DatabaseHandler(Context context, String databaseName, Integer databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    public DatabaseHandler(Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Position(id INTEGER PRIMARY KEY, x INTEGER, y INTEGER, z INTEGER, comment TEXT, buildingid INTEGER)");
        db.execSQL("CREATE TABLE Device(id INTEGER PRIMARY KEY, baserssi INTEGER, mac TEXT, position INTEGER, alignment TEXT)");
        db.execSQL("CREATE TABLE Room(id INTEGER PRIMARY KEY, number INTEGER, position INTEGER)");
        db.execSQL("CREATE TABLE People(id INTEGER PRIMARY KEY, name TEXT, roomId INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Position");
        db.execSQL("DROP TABLE IF EXISTS Devices");
        db.execSQL("DROP TABLE IF EXISTS Room");
        db.execSQL("DROP TABLE IF EXISTS People");
        onCreate(db);
    }

    public void populateDatabase() {
        addPosition(new Position(1, 32, 20, 6, "101 előtt"));
        addPosition(new Position(2, 32, 8, 6, "KL előtt"));
        addPosition(new Position(3, 18, 8, 6, "Konyha előtt"));
        addPosition(new Position(4, 6, 8, 6, "labor előtt"));
        addPosition(new Position(5, 5, 6, 4.4));
        addPosition(new Position(6, 6, 6, 4.4));
        addPosition(new Position(7, 13, 7, 4.4));
        addPosition(new Position(8, 18, 7, 4.4));
        addPosition(new Position(9, 18, 8, 4.4, "Teakonyha"));
        addDevice(new Device(1, 70, "0C:F3:EE:00:96:A0", getPosition(1), Alignment.LEFT));
        addDevice(new Device(2, 57, "0C:F3:EE:00:82:4B", getPosition(2), Alignment.BEHIND));
        addDevice(new Device(3, 65, "0C:F3:EE:00:63:8C", getPosition(3), Alignment.LEFT));
        addDevice(new Device(4, 63, "0C:F3:EE:00:96:44", getPosition(4), Alignment.RIGHT));
        //addDevice(new Device(5, 51, "0C:F3:EE:00:83:96", getPosition(1)));
        //addDevice(new Device(6, 73, "0C:F3:EE:00:87:EC", getPosition(1)));
        //addDevice(new Device(7, 77, "0C:F3:EE:00:87:A8", getPosition(1)));

        addRoom(new Room(1, 107, getPosition(5)));

        addRoom(new Room(2, 107, getPosition(6)));

        addRoom(new Room(3, 108, getPosition(7)));

        addRoom(new Room(4, 109, getPosition(8)));

        addRoom(new Room(5, 123, getPosition(9)));

        addPerson(new Person(1, "Tóth Zsolt", 1));

        addPerson(new Person(2, "Tamás Judit", 1));

        addPerson(new Person(3, "Vincze Dávid", 1));

        addPerson(new Person(4, "Kovács Szilveszter", 2));

        addPerson(new Person(5, "Krizsán Zoltán", 2));

        addPerson(new Person(6, "Bulla Dávid", 3));

        addPerson(new Person(7, "Szűcs Miklós", 3));

        addPerson(new Person(8, "Baksáné Varga Erika", 4));

        addPerson(new Person(9, "Barabás Péter", 4));

        addPerson(new Person(10, "Sasvári Péter", 4));
    }

    public void addRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", room.getNumber());
        values.put("position", room.getPosition().getId());
        db.insert("Room", null, values);
    }

    public Room getRoom(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Room", null, "id=" + id, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Position position = getPosition(Integer.parseInt(cursor.getString(2)));
        Room room = new Room();
        room.setId(Integer.parseInt(cursor.getString(0)));
        room.setPosition(position);
        room.setNumber(Integer.parseInt(cursor.getString(1)));
        room.setPeople(getPeopleInRoom(id));
        return room;
    }

    public List<Room> getAllRoom() {
        List<Room> rooms = new ArrayList<Room>();
        String selectQuery = "Select * From Room";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Toast.makeText(context, "getting the rooms", Toast.LENGTH_LONG);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(Integer.parseInt(cursor.getString(0)));
                room.setNumber(Integer.parseInt(cursor.getString(1)));
                room.setPosition(getPosition(Integer.parseInt(cursor.getString(2))));
                room.setPeople(getPeopleInRoom(Integer.parseInt(cursor.getString(0))));
                rooms.add(room);
            } while (cursor.moveToNext());
        }
        return rooms;
    }

    public int getRoomCount() {
        String countQuery = "SELECT * FROM Room";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updateRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", room.getNumber());
        values.put("position", room.getPosition().getId());
        return db.update("Room", values, "id=" + room.getId(), null);
    }

    public void deleteRoom(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Room", "id=" + id, null);
    }

    public void addPosition(Position position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("x", position.getX());
        contentValues.put("y", position.getY());
        contentValues.put("z", position.getZ());
        contentValues.put("comment", position.getComment());
        db.insert("Position", null, contentValues);
    }

    public Position getPosition(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Position", null/*new String[]{"id", "x", "y", "z", "comment"}*/, "id="+id, null/*new String[]{String.valueOf(id)}*/, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Position position = new Position(Integer.parseInt(cursor.getString(0)), Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)), cursor.getString(4));

        return position;
    }

    public List<Position> getAllPosition() {
        List<Position> positions = new ArrayList<Position>();
        String selectQuery = "Select * From Position";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Position position = new Position();
                position.setId(Integer.parseInt(cursor.getString(0)));
                position.setX(Integer.parseInt(cursor.getString(1)));
                position.setY(Integer.parseInt(cursor.getString(2)));
                position.setZ(Integer.parseInt(cursor.getString(3)));
                position.setComment(cursor.getString(4));
                positions.add(position);
            } while (cursor.moveToNext());
        }
        return positions;
    }

    public int getPositionCount() {
        String countQuery = "SELECT * FROM Position";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updatePosition(Position position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("x", position.getX());
        values.put("y", position.getY());
        values.put("z", position.getZ());
        values.put("comment", position.getComment());

        return db.update("Position", values, "id=?", new String[]{String.valueOf(position.getId())});
    }

    public void deletePosition(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Position", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void addDevice(Device device) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("baserssi", device.getBaseRSSI());
        contentValues.put("mac", device.getMAC());
        contentValues.put("position", device.getPosition().getId());
        contentValues.put("alignment", device.getAlignment().name());
        db.insert("Device", null, contentValues);
        db.close();
    }

    public Device getDevice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Device", null, "id=" + id, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Position position = getPosition(Integer.parseInt(cursor.getString(3)));
        Device device = new Device(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), position, Alignment.valueOf(cursor.getString(4)));

        return device;
    }

    public Device getDevice(String mac) {
        SQLiteDatabase db = this.getReadableDatabase();
        Device device = new Device();
        Cursor cursor = db.query("Device", null, "mac=" + "'" + mac + "'", null, null, null, null);
        if (cursor == null) {
            Log.d("read", "null");
            device.setId(0);
            device.setPosition(new Position(0, 0, 0, 0));
            device.setMAC("");
            device.setBaseRSSI(0);
            return device;
        }
        Log.d("read", "wtf? ha nem null miért működik");
        if (cursor != null)
            cursor.moveToFirst();
        device = new Device(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), getPosition(Integer.parseInt(cursor.getString(3))), Alignment.valueOf(cursor.getString(4)));

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
                Position position = getPosition(Integer.parseInt(cursor.getString(3)));
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
        int temp = cursor.getCount();
        cursor.close();
        return temp;
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

    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", person.getName());
        contentValues.put("roomId", person.getRoomId());
        db.insert("People", null, contentValues);
    }

    public Person getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("People", null, "id=" + id, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Person person = new Person();
        person.setId(Integer.parseInt(cursor.getString(0)));
        person.setName(cursor.getString(1));
        person.setRoomId(Integer.parseInt(cursor.getString(2)));
        return person;
    }

    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<Person>();
        String selectQuery = "Select * From People";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setRoomId(Integer.parseInt(cursor.getString(2)));
                people.add(person);
            } while (cursor.moveToNext());
        }
        return people;
    }

    public List<Person> getPeopleInRoom(Integer roomId) {
        List<Person> people = new ArrayList<Person>();
        String selectQuery = "Select * From People WHERE roomId = '" + roomId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setRoomId(Integer.parseInt(cursor.getString(2)));
                people.add(person);
            } while (cursor.moveToNext());
        }
        return people;
    }

    public int getPeopleCount() {
        String countQuery = "SELECT * FROM People";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", person.getName());
        values.put("roomId", person.getRoomId());

        return db.update("People", values, "id=" + person.getId(), null);
    }

    public void deletePerson(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("People", "id=" + id, null);
        db.close();
    }
}
