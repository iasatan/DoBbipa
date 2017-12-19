
package hu.uni.miskolc.iit.ilona.bluetooth.proximity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.test.R;

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

    private static final String databaseName = "dobbipa40";
    private static final Integer databaseVersion = 1;
    Context context;

    public DatabaseHandler(Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Position(id INTEGER PRIMARY KEY, x INTEGER, y INTEGER, z INTEGER, comment TEXT, buildingid INTEGER)");
        db.execSQL("CREATE TABLE Device(id INTEGER PRIMARY KEY, baserssi INTEGER, mac TEXT, position INTEGER, alignment TEXT)");
        db.execSQL("CREATE TABLE Room(id INTEGER PRIMARY KEY, number INTEGER, position INTEGER, title TEXT)");
        db.execSQL("CREATE TABLE People(id INTEGER PRIMARY KEY, name TEXT, roomId INTEGER, image INTEGER, title TEXT)");
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
        addPosition(new Position(1, 35, 20, 6, "101 előtt"));
        addPosition(new Position(2, 35, 8, 6, "KL előtt"));
        addPosition(new Position(3, 18, 8, 6, "Konyha előtt"));
        addPosition(new Position(4, 6, 8, 6, "labor előtt"));
        addPosition(new Position(5, 5, 8, 4.4));
        addPosition(new Position(6, 6, 8, 4.4));
        addPosition(new Position(7, 13, 8, 4.4));
        addPosition(new Position(8, 17, 8, 4.4));
        addPosition(new Position(9, 18, 8, 4.4));
        addPosition(new Position(10, 8, 8, 4.4, "Elzárt folyosó labornál lévő ajtaja"));
        addPosition(new Position(11, 8, 20, 4.4));
        addPosition(new Position(12, 35, 12, 4.4, "lépcső"));
        addPosition(new Position(13, 35, 8, 4.4));
        addPosition(new Position(14, 8, 10, 4.4));
        addPosition(new Position(15, 8, 15, 4.4));
        addPosition(new Position(16, 20, 7, 4.4));
        addPosition(new Position(17, 20, 6, 4.4));
        addPosition(new Position(18, 20, 11, 4.4));
        addPosition(new Position(19, 20, 12, 4.4));
        addPosition(new Position(20, 20, 19, 4.4));
        addPosition(new Position(21, 20, 21, 4.4));
        addPosition(new Position(22, 20, 28, 4.4));
        addPosition(new Position(23, 20, 33, 4.4));
        addPosition(new Position(24, 20, 39, 4.4));
        addPosition(new Position(25, 8, 39, 4.4));
        addPosition(new Position(26, 8, 36, 4.4));
        addPosition(new Position(27, 8, 32, 4.4));
        addPosition(new Position(28, 8, 28, 4.4));
        addPosition(new Position(29, 8, 26, 4.4));
        addPosition(new Position(30, 8, 23, 4.4));
        addPosition(new Position(31, 8, 21, 4.4));
        addPosition(new Position(32, 8, 6, 4.4));
        addPosition(new Position(33, 23, 20, 6));
        addPosition(new Position(34, 11, 20, 6));
        addPosition(new Position(35, 15, 8, 6));

        addDevice(new Device(1, 70, "0C:F3:EE:00:96:A0", getPosition(1), Alignment.LEFT));
        addDevice(new Device(2, 57, "0C:F3:EE:00:82:4B", getPosition(2), Alignment.LEFT));
        addDevice(new Device(3, 65, "0C:F3:EE:00:63:8C", getPosition(3), Alignment.LEFT));
        addDevice(new Device(4, 63, "0C:F3:EE:00:96:44", getPosition(4), Alignment.RIGHT));
        addDevice(new Device(5, 51, "0C:F3:EE:00:83:96", getPosition(35), Alignment.FRONT));
        addDevice(new Device(6, 73, "0C:F3:EE:00:87:EC", getPosition(34), Alignment.RIGHT));
        addDevice(new Device(7, 77, "0C:F3:EE:00:87:A8", getPosition(33), Alignment.LEFT));

        addRoom(new Room(1, 107, getPosition(5)));
        addRoom(new Room(2, 1072, getPosition(6)));
        addRoom(new Room(3, 108, getPosition(7)));
        addRoom(new Room(4, 109, getPosition(8)));
        addRoom(new Room(5, 123, getPosition(9), "Teakonyha"));
        addRoom(new Room(6, 121, getPosition(14), "Fiú WC"));
        addRoom(new Room(7, 122, getPosition(15), "Lány WC"));
        addRoom(new Room(8, 104, getPosition(16)));
        addRoom(new Room(9, 105, getPosition(17)));
        addRoom(new Room(10, 119, getPosition(18)));
        addRoom(new Room(11, 103, getPosition(19), "Számítógépes Laboratórium"));
        addRoom(new Room(12, 116, getPosition(20), "Családbarát Helyiség"));
        addRoom(new Room(13, 115, getPosition(21), "Technikai Előkészítő"));
        addRoom(new Room(14, 102, getPosition(22), "Számítógépes Laboratórium"));
        addRoom(new Room(15, 101, getPosition(23), "Számítógépes Laboratórium"));
        addRoom(new Room(16, 100, getPosition(24)));
        addRoom(new Room(17, 114, getPosition(25), "Könyvtár tárgyaló"));
        addRoom(new Room(18, 113, getPosition(26), "Adminisztráció"));
        addRoom(new Room(19, 112, getPosition(27), "Tanszékvezető"));
        addRoom(new Room(20, 111, getPosition(28)));
        addRoom(new Room(21, 125, getPosition(29), "Fénymásoló Helyiség"));
        addRoom(new Room(22, 110, getPosition(30)));
        addRoom(new Room(23, 124, getPosition(31), "Raktár"));
        addRoom(new Room(24, 106, getPosition(32)));

        addPerson(new Person(1, "Tóth Zsolt", 1, R.drawable.tzs0, "Egyetemi adjunktus", context));
        addPerson(new Person(2, "Tamás Judit", 1, R.drawable.tj0, "Doktorandusz", context));
        addPerson(new Person(3, "Vincze Dávid", 1, R.drawable.vd0, "Egyetemi docens", context));
        addPerson(new Person(4, "Kovács Szilveszter", 2, R.drawable.ksz0, "Egyetemi docens", context));
        addPerson(new Person(5, "Krizsán Zoltán", 2, R.drawable.kz0, "Egyetemi docens", context));
        addPerson(new Person(6, "Bulla Dávid", 3, R.drawable.bd0, "Mérnöktanár", context));
        addPerson(new Person(7, "Szűcs Miklós", 3, R.drawable.szm0, "Mesteroktató", context));
        addPerson(new Person(8, "Baksáné Varga Erika", 4, R.drawable.bve0, "Egyetemi docens", context));
        addPerson(new Person(9, "Barabás Péter", 4, R.drawable.bp0, "Egyetemi adjunktus", context));
        addPerson(new Person(10, "Sasvári Péter", 4, R.drawable.sp0, "Egyetemi docens", context));
        addPerson(new Person(11, "Tompa Tamás", 9, R.drawable.tt0, "PhD hallgató", context)); //kép
        addPerson(new Person(12, "Nagy Miklósné", 17, R.drawable.nf404, "Könyvtáros", context));
        addPerson(new Person(13, "Göncziné Halász Éva", 18, R.drawable.ghe0, "Tanulmányi igazgatási ügyintéző", context));
        addPerson(new Person(14, "Kovács László", 19, R.drawable.kl0, "Egyetemi Docens", context));
        addPerson(new Person(15, "Wagner György", 20, R.drawable.wgy0, "Mesteroktató", context));
        addPerson(new Person(16, "Mileff Péter", 22, R.drawable.mp0, "Egyetemi Docens", context));
        addPerson(new Person(17, "Smid László", 22, R.drawable.sl0, "Egyetemi Tanársegéd", context));
        addPerson(new Person(18, "Sátán Ádám", 24, R.drawable.sa0, "hallgató", context));
        addPerson(new Person(19, "Bogdándy Bence", 24, R.drawable.bb0, "hallgató", context));
        addPerson(new Person(20, "Molnár Dávid", 24, R.drawable.md0, "hallgató", context));
        addPerson(new Person(21, "Pintér Tamás", 24, R.drawable.pt0, "hallgató", context));
        addPerson(new Person(22, "Boros Tamás", 24, R.drawable.bt0, "hallgató", context));
        addPerson(new Person(23, "Tóth Ádám", 24, R.drawable.ta0, "hallgató", context));
        addPerson(new Person(24, "Ilku Krisztián", 24, R.drawable.ik0, "hallgató", context));
        addPerson(new Person(25, "Chiraz Bachtarzi", 24, R.drawable.cb0, "hallgató", context));
    }

    public void addRoom(Room room) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", room.getNumber());
        values.put("position", room.getPosition().getId());
        values.put("title", room.getTitle());
        db.insert("Room", null, values);
    }

    public Room getRoom(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Room", null, "id=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Position position = getPosition(Integer.parseInt(cursor.getString(2)));
        Room room = new Room();
        room.setId(Integer.parseInt(cursor.getString(0)));
        room.setPosition(position);
        room.setNumber(Integer.parseInt(cursor.getString(1)));
        room.setTitle(cursor.getString(3));
        room.setPeople(getPeopleInRoom(id));
        return room;
    }

    public Room getRoomByNumber(int number) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Room", null, "number=" + number, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Room room = new Room(0, 0, new Position(0, 0.0, 0.0, 0.0), new Person(0, context.getString(R.string.noSuchRoom), 0, R.drawable.nf404, "", context));
        if (cursor.getCount() != 0) {
            room = new Room();
            Position position = getPosition(Integer.parseInt(cursor.getString(2)));
            room.setId(Integer.parseInt(cursor.getString(0)));
            room.setPosition(position);
            room.setNumber(Integer.parseInt(cursor.getString(1)));
            room.setPeople(getPeopleInRoom(room.getId()));
            room.setTitle(cursor.getString(3));

        }
        return room;
    }

    public List<Room> getAllRoom() {
        List<Room> rooms = new ArrayList<Room>();
        String selectQuery = "Select * From Room";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(Integer.parseInt(cursor.getString(0)));
                room.setNumber(Integer.parseInt(cursor.getString(1)));
                room.setPosition(getPosition(Integer.parseInt(cursor.getString(2))));
                room.setPeople(getPeopleInRoom(Integer.parseInt(cursor.getString(0))));
                room.setTitle(cursor.getString(3));

                rooms.add(room);
            } while (cursor.moveToNext());
        }
        return rooms;
    }


    public void addPosition(Position position) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("x", position.getX());
        contentValues.put("y", position.getY());
        contentValues.put("z", position.getZ());
        contentValues.put("comment", position.getComment());
        db.insert("Position", null, contentValues);
    }

    public Position getPosition(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Position", null/*new String[]{"id", "x", "y", "z", "comment"}*/, "id=" + id, null/*new String[]{String.valueOf(id)}*/, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Position position = new Position(Integer.parseInt(cursor.getString(0)), Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)), cursor.getString(4));

        return position;
    }

    public List<Position> getAllPosition() {
        List<Position> positions = new ArrayList<Position>();
        String selectQuery = "Select * From Position";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Position position = new Position();
                position.setId(Integer.parseInt(cursor.getString(0)));
                position.setX(Double.parseDouble(cursor.getString(1)));
                position.setY(Double.parseDouble(cursor.getString(2)));
                position.setZ(Double.parseDouble(cursor.getString(3)));
                position.setComment(cursor.getString(4));
                positions.add(position);
            } while (cursor.moveToNext());
        }
        return positions;
    }


    public void addDevice(Device device) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("baserssi", device.getBaseRSSI());
        contentValues.put("mac", device.getMAC());
        contentValues.put("position", device.getPosition().getId());
        contentValues.put("alignment", device.getAlignment().name());
        db.insert("Device", null, contentValues);
        db.close();
    }


    public Collection<Device> getAllDevice() {
        Collection<Device> devices = new HashSet<Device>();
        String selectQuery = "Select * From Device";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();
                device.setId(Integer.parseInt(cursor.getString(0)));
                device.setBaseRSSI(Integer.parseInt(cursor.getString(1)));
                device.setMAC(cursor.getString(2));
                Position position = getPosition(Integer.parseInt(cursor.getString(3)));
                device.setAlignment(Alignment.valueOf(cursor.getString(4)));
                device.setPosition(position);
                devices.add(device);
            } while (cursor.moveToNext());
        }
        return devices;
    }

    public int getDeviceCount() {
        String countQuery = "SELECT * FROM Device";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int temp = cursor.getCount();
        cursor.close();
        return temp;
    }


    public void addPerson(Person person) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", person.getName());
        contentValues.put("roomId", person.getRoomId());
        contentValues.put("image", person.getImageId());
        contentValues.put("title", person.getTitle());
        db.insert("People", null, contentValues);
    }


    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<Person>();
        String selectQuery = "Select * From People";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(context);
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setRoomId(Integer.parseInt(cursor.getString(2)));
                person.setImage(Integer.parseInt(cursor.getString(3)));
                person.setTitle(cursor.getString(4));
                people.add(person);
            } while (cursor.moveToNext());
        }
        return people;
    }

    public List<Person> getPeopleInRoom(Integer roomId) {
        List<Person> people = new ArrayList<Person>();
        String selectQuery = "Select * From People WHERE roomId = '" + roomId + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(context);
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setRoomId(Integer.parseInt(cursor.getString(2)));
                person.setImage(Integer.parseInt(cursor.getString(3)));
                person.setTitle(cursor.getString(4));
                people.add(person);
            } while (cursor.moveToNext());
        }
        return people;
    }



    /*public int getRoomCount() {
        String countQuery = "SELECT * FROM Room";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }


    public int updateRoom(Room room) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", room.getNumber());
        values.put("position", room.getPosition().getId());
        values.put("title", room.getTitle());
        return db.update("Room", values, "id=" + room.getId(), null);
    }

    public void deleteRoom(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Room", "id=" + id, null);
    }


    public int getPeopleCount() {
        String countQuery = "SELECT * FROM People";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updatePerson(Person person) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", person.getName());
        values.put("roomId", person.getRoomId());

        return db.update("People", values, "id=" + person.getId(), null);
    }

    public void deletePerson(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("People", "id=" + id, null);
        db.close();
    }
    public Person getPerson(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("People", null, "id=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Person person = new Person(context);
        person.setId(Integer.parseInt(cursor.getString(0)));
        person.setName(cursor.getString(1));
        person.setRoomId(Integer.parseInt(cursor.getString(2)));
        return person;
    }

     public int updateDevice(Device device) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("baserssi", device.getBaseRSSI());
        values.put("mac", device.getMAC());
        values.put("position", device.getPosition().getId());

        return db.update("Device", values, "id=" + device.getId(), null);
    }

    public void deleteDevice(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Device", "id=" + id, null);
        db.close();
    }

        public int getPositionCount() {
        String countQuery = "SELECT * FROM Position";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updatePosition(Position position) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("x", position.getX());
        values.put("y", position.getY());
        values.put("z", position.getZ());
        values.put("comment", position.getComment());

        return db.update("Position", values, "id=?", new String[]{String.valueOf(position.getId())});
    }

    public void deletePosition(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Position", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public Device getDevice(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Device", null, "id=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Position position = getPosition(Integer.parseInt(cursor.getString(3)));
        Device device = new Device(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), position, Alignment.valueOf(cursor.getString(4)));

        return device;
    }

    public Device getDevice(String mac) {
        SQLiteDatabase db = getReadableDatabase();
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
        if (cursor != null) {
            cursor.moveToFirst();
        }
        device = new Device(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), getPosition(Integer.parseInt(cursor.getString(3))), Alignment.valueOf(cursor.getString(4)));

        return device;
    }

    */
}
