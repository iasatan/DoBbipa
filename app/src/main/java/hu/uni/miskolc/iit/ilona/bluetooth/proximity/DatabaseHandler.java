
package hu.uni.miskolc.iit.ilona.bluetooth.proximity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.test.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.UserAlreadyExist;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Alignment;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Edge;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.SecurityClearance;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.User;

/**
 * Created by iasatan on 2017.10.17..
 * Database for the application, contains the position, device, room and people entities.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String databaseName = "dobbipa52";
    private static final Integer databaseVersion = 1;
    private final Context context;

    public DatabaseHandler(Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    public DatabaseHandler() {
        super(null, databaseName, null, databaseVersion);
        context = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Position(id INTEGER PRIMARY KEY, x INTEGER, y INTEGER, z DOUBLE, comment TEXT, buildingid INTEGER, clearance TEXT)");
        db.execSQL("CREATE TABLE Device(id INTEGER PRIMARY KEY, baserssi INTEGER, mac TEXT, position INTEGER, alignment TEXT)");
        db.execSQL("CREATE TABLE Room(id INTEGER PRIMARY KEY, number INTEGER, position INTEGER, title TEXT)");
        db.execSQL("CREATE TABLE People(id INTEGER PRIMARY KEY, name TEXT, roomId INTEGER, image INTEGER, title TEXT)");
        db.execSQL("CREATE TABLE Edge(id INTEGER PRIMARY KEY, node1 INTEGER, node2 INTEGER, distance DOUBLE)");
        db.execSQL("CREATE TABLE User(macaddressbl TEXT PRIMARY KEY, clearance TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Position");
        db.execSQL("DROP TABLE IF EXISTS Devices");
        db.execSQL("DROP TABLE IF EXISTS Room");
        db.execSQL("DROP TABLE IF EXISTS People");
        db.execSQL("DROP TABLE IF EXISTS Edge");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    public void populateDatabase() {
        try {
            addUser(new User("00:7F:00:E2:BF:CA".replace(":", ""), SecurityClearance.LEVEl1));
        } catch (UserAlreadyExist userAlreadyExist) {
        }
        Map<Integer, Position> positions = new HashMap<>();
        positions.put(1, new Position(1, 35, 20, 6, "101 előtt"));
        positions.put(2, new Position(2, 35, 8, 6, "KL előtt"));
        positions.put(3, new Position(3, 18, 8, 6, SecurityClearance.LEVEl1, "Konyha előtt"));
        positions.put(4, new Position(4, 6, 8, 6, SecurityClearance.LEVEl1, "labor előtt"));
        positions.put(5, new Position(5, 5, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(6, new Position(6, 7, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(7, new Position(7, 13, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(8, new Position(8, 17, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(9, new Position(9, 18, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(10, new Position(10, 8, 8, 4.4, SecurityClearance.LEVEl1, "Elzárt folyosó labornál lévő ajtaja"));
        positions.put(11, new Position(11, 8, 20, 4.4));
        positions.put(12, new Position(12, 35, 12, 4.4, "lépcső"));
        positions.put(13, new Position(13, 35, 8, 4.4));
        positions.put(14, new Position(14, 8, 10, 4.4));
        positions.put(15, new Position(15, 8, 15, 4.4));
        positions.put(16, new Position(16, 7, 7, 4.4));
        positions.put(17, new Position(17, 6, 6, 4.4));
        positions.put(18, new Position(18, 11, 20, 4.4));
        positions.put(19, new Position(19, 12, 20, 4.4));
        positions.put(20, new Position(20, 19, 20, 4.4));
        positions.put(21, new Position(21, 21, 20, 4.4));
        positions.put(22, new Position(22, 28, 20, 4.4));
        positions.put(23, new Position(23, 33, 20, 4.4));
        positions.put(24, new Position(24, 39, 20, 4.4));
        positions.put(25, new Position(25, 39, 8, 4.4));
        positions.put(26, new Position(26, 36, 8, 4.4));
        positions.put(27, new Position(27, 32, 8, 4.4));
        positions.put(28, new Position(28, 28, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(29, new Position(29, 26, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(30, new Position(30, 23, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(31, new Position(31, 21, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(32, new Position(32, 6, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(33, new Position(33, 23, 20, 6));
        positions.put(34, new Position(34, 11, 20, 6));
        positions.put(35, new Position(35, 8, 15, 6));
        positions.put(36, new Position(36, 14, 8, 4.4, SecurityClearance.LEVEl1));
        positions.put(37, new Position(37, 15, 8, 4.4, SecurityClearance.LEVEl1));
        for (Map.Entry<Integer, Position> position : positions.entrySet()) {
            addPosition(position.getValue());
        }

        addEdge(new Edge(1, positions.get(5), positions.get(32)));
        addEdge(new Edge(2, positions.get(32), positions.get(6)));
        addEdge(new Edge(3, positions.get(6), positions.get(10)));
        addEdge(new Edge(4, positions.get(7), positions.get(10)));
        addEdge(new Edge(5, positions.get(7), positions.get(37)));
        addEdge(new Edge(6, positions.get(37), positions.get(36)));
        addEdge(new Edge(7, positions.get(36), positions.get(8)));
        addEdge(new Edge(8, positions.get(8), positions.get(9)));
        addEdge(new Edge(9, positions.get(9), positions.get(31)));
        addEdge(new Edge(10, positions.get(31), positions.get(30)));
        addEdge(new Edge(11, positions.get(28), positions.get(30)));
        addEdge(new Edge(12, positions.get(28), positions.get(29)));
        addEdge(new Edge(13, positions.get(29), positions.get(27)));
        addEdge(new Edge(14, positions.get(27), positions.get(13)));
        addEdge(new Edge(15, positions.get(13), positions.get(26)));
        addEdge(new Edge(16, positions.get(26), positions.get(25)));
        addEdge(new Edge(17, positions.get(13), positions.get(12)));
        addEdge(new Edge(18, positions.get(12), positions.get(1)));
        addEdge(new Edge(19, positions.get(1), positions.get(24)));
        addEdge(new Edge(20, positions.get(1), positions.get(23)));
        addEdge(new Edge(21, positions.get(22), positions.get(23)));
        addEdge(new Edge(22, positions.get(22), positions.get(33)));
        addEdge(new Edge(23, positions.get(33), positions.get(21)));
        addEdge(new Edge(24, positions.get(21), positions.get(20)));
        addEdge(new Edge(25, positions.get(20), positions.get(19)));
        addEdge(new Edge(26, positions.get(19), positions.get(18)));
        addEdge(new Edge(27, positions.get(18), positions.get(11)));
        addEdge(new Edge(28, positions.get(11), positions.get(16)));
        addEdge(new Edge(29, positions.get(16), positions.get(17)));
        addEdge(new Edge(30, positions.get(11), positions.get(15)));
        addEdge(new Edge(31, positions.get(14), positions.get(15)));
        addEdge(new Edge(32, positions.get(14), positions.get(10)));


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
        addRoom(new Room(25, 117, getPosition(36), "Tanári Női Mozsdó"));
        addRoom(new Room(26, 118, getPosition(37), "Tanári Férfi Mozsdó"));
        addRoom(new Room(27, 0, getPosition(12), "Lépcső"));

        addPerson(new Person(1, "Tóth Zsolt", 1, R.drawable.tzs0, context.getString(R.string.seniorLecturer), context));
        addPerson(new Person(2, "Tamás Judit", 1, R.drawable.tj0, context.getString(R.string.phdStudentOne), context));
        addPerson(new Person(3, "Vincze Dávid", 1, R.drawable.vd0, context.getString(R.string.associateProfessor), context));
        addPerson(new Person(4, "Kovács Szilveszter", 2, R.drawable.ksz0, context.getString(R.string.associateProfessor), context));
        addPerson(new Person(5, "Krizsán Zoltán", 2, R.drawable.kz0, context.getString(R.string.associateProfessor), context));
        addPerson(new Person(6, "Bulla Dávid", 3, R.drawable.bd0, "Mérnöktanár", context));
        addPerson(new Person(7, "Szűcs Miklós", 3, R.drawable.szm0, "Mesteroktató", context));
        addPerson(new Person(8, "Baksáné Varga Erika", 4, R.drawable.bve0, context.getString(R.string.associateProfessor), context));
        addPerson(new Person(9, "Barabás Péter", 4, R.drawable.bp0, context.getString(R.string.seniorLecturer), context));
        addPerson(new Person(10, "Sasvári Péter", 4, R.drawable.sp0, context.getString(R.string.associateProfessor), context));
        addPerson(new Person(11, "Tompa Tamás", 9, R.drawable.tt0, context.getString(R.string.phdStudentTwo), context)); //kép
        addPerson(new Person(12, "Nagy Miklósné", 17, R.drawable.nf404, context.getString(R.string.librarian), context));
        addPerson(new Person(13, "Göncziné Halász Éva", 18, R.drawable.ghe0, "Tanulmányi igazgatási ügyintéző", context));
        addPerson(new Person(14, "Kovács László", 19, R.drawable.kl0, context.getString(R.string.associateProfessor), context));
        addPerson(new Person(15, "Wagner György", 20, R.drawable.wgy0, "Mesteroktató", context));
        addPerson(new Person(16, "Mileff Péter", 22, R.drawable.mp0, context.getString(R.string.associateProfessor), context));
        addPerson(new Person(17, "Smid László", 22, R.drawable.sl0, context.getString(R.string.assistantLecturer), context));
        addPerson(new Person(18, "Sátán Ádám", 24, R.drawable.sa0, context.getString(R.string.student), context));
        addPerson(new Person(19, "Bogdándy Bence", 24, R.drawable.bb0, context.getString(R.string.student), context));
        addPerson(new Person(20, "Molnár Dávid", 24, R.drawable.md0, context.getString(R.string.student), context));
        addPerson(new Person(21, "Pintér Tamás", 24, R.drawable.pt0, context.getString(R.string.student), context));
        addPerson(new Person(22, "Boros Tamás", 24, R.drawable.bt0, context.getString(R.string.student), context));
        addPerson(new Person(23, "Tóth Ádám", 24, R.drawable.ta0, context.getString(R.string.student), context));
        addPerson(new Person(24, "Ilku Krisztián", 24, R.drawable.ik0, context.getString(R.string.student), context));
        addPerson(new Person(25, "Chiraz Bachtarzi", 24, R.drawable.cb0, context.getString(R.string.student), context));
    }

    //region User
    public void addUser(User user) throws UserAlreadyExist {
        if (userExists(user.getMacAddressBL())) {
            throw new UserAlreadyExist();
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("macaddressbl", user.getMacAddressBL());
        contentValues.put("clearance", user.getSecurityClearance().name());
        db.insert("User", null, contentValues);
        db.close();
    }


    public User getUser(String macAddressBL) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("User", null, "macaddressbl=?", new String[]{macAddressBL}, null, null, null);
        User user = new User("00:00:00:00:00", SecurityClearance.NONE);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            user = new User(cursor.getString(0), SecurityClearance.valueOf(cursor.getString(1)));
        }


        cursor.close();
        return user;
    }

    public boolean userExists(String macAddressBL) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("User", null, "macaddressbl=?", new String[]{macAddressBL}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Integer count = cursor.getCount();
        if (count == 1) {
            return true;
        }
        return false;
    }

    //endregion
    //region Edge

    public void addEdge(Edge edge) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", edge.getId());
        values.put("node1", edge.getNode1().getId());
        values.put("node2", edge.getNode2().getId());
        values.put("distance", edge.getDistance());
        db.insert("Edge", null, values);
    }

    public List<Edge> getAllEdge() {
        List<Edge> edges = new ArrayList<>();
        String selectQuery = "Select * From Edge";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Edge edge = new Edge();
                edge.setId(Integer.parseInt(cursor.getString(0)));
                edge.setNode1(getPosition(Integer.parseInt(cursor.getString(1))));
                edge.setNode2(getPosition(Integer.parseInt(cursor.getString(2))));
                edge.setDistance(Double.parseDouble(cursor.getString(3)));
                edges.add(edge);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return edges;
    }

    public Edge getEdgeById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Edge", null, "id=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Edge edge = new Edge();
        if (cursor.getCount() != 0) {
            edge.setId(Integer.parseInt(cursor.getString(0)));
            edge.setNode1(getPosition(Integer.parseInt(cursor.getString(1))));
            edge.setNode2(getPosition(Integer.parseInt(cursor.getString(2))));
            edge.setDistance(Double.parseDouble(cursor.getString(3)));
        }
        cursor.close();
        return edge;
    }

    //endregion
    //region Room
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
        cursor.close();
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
        cursor.close();
        return room;
    }

    public List<Room> getAllRoom() {
        List<Room> rooms = new ArrayList<>();
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
        cursor.close();
        return rooms;
    }

    public int getRoomCount() {
        String countQuery = "SELECT * FROM Room";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Integer count = cursor.getCount();
        cursor.close();
        return count;
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

    //endregion
    //region Position
    public void addPosition(Position position) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("x", position.getX());
        contentValues.put("y", position.getY());
        contentValues.put("z", position.getZ());
        contentValues.put("comment", position.getComment());
        contentValues.put("clearance", position.getSecurityClearance().name());
        db.insert("Position", null, contentValues);
    }

    public Position getPosition(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Position", null/*new String[]{"id", "x", "y", "z", "comment"}*/, "id=" + id, null/*new String[]{String.valueOf(id)}*/, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Position position = new Position(Integer.parseInt(cursor.getString(0)), Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)), cursor.getString(4));
        cursor.close();
        return position;
    }

    public List<Position> getAllPosition() {
        List<Position> positions = new ArrayList<>();
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
        cursor.close();
        return positions;
    }
    //endregion
    //region Device

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
        Collection<Device> devices = new HashSet<>();
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
        cursor.close();
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

    //endregion
    //region Person
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
        List<Person> people = new ArrayList<>();
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
        cursor.close();
        return people;
    }

    public List<Person> getPeopleInRoom(Integer roomId) {
        List<Person> people = new ArrayList<>();
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
        cursor.close();
        return people;
    }
    //endregion
}
