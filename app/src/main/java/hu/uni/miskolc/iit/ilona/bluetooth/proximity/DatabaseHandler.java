
package hu.uni.miskolc.iit.ilona.bluetooth.proximity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.test.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoEdgeFound;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Alignment;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Edge;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.History;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;

/**
 * Created by iasatan on 2017.10.17..
 * Database for the application, contains the position, device, room and people entities.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String databaseName = "dobbipa70";
    private static final Integer databaseVersion = 1;
    private final Context context;
    private int historyId;
    private int groupid;

    public DatabaseHandler(Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
        historyId = getHistoryCount();
        groupid = getHistoryLastGroupId();
        groupid++;
    }

    public DatabaseHandler() {
        super(null, databaseName, null, databaseVersion);
        context = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Position(id INTEGER PRIMARY KEY, x INTEGER, y INTEGER, z REAL, comment TEXT, front INTEGER, leftimage INTEGER, right INTEGER, behind INTEGER)");
        db.execSQL("CREATE TABLE Device(id INTEGER PRIMARY KEY, baserssi INTEGER, mac TEXT, position INTEGER, alignment TEXT)");
        db.execSQL("CREATE TABLE Room(id INTEGER PRIMARY KEY, number INTEGER, position INTEGER, title TEXT)");
        db.execSQL("CREATE TABLE People(id INTEGER PRIMARY KEY, name TEXT, roomId INTEGER, image INTEGER, title TEXT)");
        db.execSQL("CREATE TABLE Edge(id INTEGER PRIMARY KEY, node1 INTEGER, node2 INTEGER, distance REAL)");
        db.execSQL("CREATE TABLE History(id INTEGER PRIMARY KEY, groupid INTEGER, user TEXT, position INTEGER, direction INTEGER, date REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Position");
        db.execSQL("DROP TABLE IF EXISTS Devices");
        db.execSQL("DROP TABLE IF EXISTS Room");
        db.execSQL("DROP TABLE IF EXISTS People");
        db.execSQL("DROP TABLE IF EXISTS Edge");
        db.execSQL("DROP TABLE IF EXISTS Building");
        db.execSQL("DROP TABLE IF EXISTS History");
        onCreate(db);
    }

    public void populateDatabase() {
        //region position
        Map<Integer, Position> positions = new HashMap<>();
        positions.put(1, new Position(1, 35, 20, 6, "101 előtt", R.drawable.p3520ek, R.drawable.p3520r, R.drawable.p3520dny, 0));
        positions.put(2, new Position(2, 35, 8, 6, "KL előtt", R.drawable.p358ek, 0, R.drawable.p358dny, R.drawable.p358eny));
        positions.put(3, new Position(3, 18, 8, 6, "Konyha előtt"));
        positions.put(4, new Position(4, 6, 8, 6, "labor előtt"));
        positions.put(5, new Position(5, 5, 8, 6));
        positions.put(6, new Position(6, 7, 8, 6));
        positions.put(7, new Position(7, 13, 8, 6));
        positions.put(8, new Position(8, 17, 8, 6));
        positions.put(9, new Position(9, 8, 8, 6, "Elzárt folyosó labornál lévő ajtaja", R.drawable.p88ek, 0, R.drawable.p88dny, R.drawable.p88eny));
        positions.put(10, new Position(10, 8, 20, 6, R.drawable.p820ek, R.drawable.p820dk, R.drawable.p820dny, 0));
        positions.put(11, new Position(11, 35, 12, 6, "lépcső", R.drawable.stairs, R.drawable.p3512dk, 0, R.drawable.p3512eny));
        positions.put(12, new Position(12, 8, 10, 6));
        positions.put(13, new Position(13, 8, 15, 6, 0, R.drawable.p815r, 0, R.drawable.p815l));
        positions.put(14, new Position(14, 7, 20, 6));
        positions.put(15, new Position(15, 23, 20, 6));
        positions.put(16, new Position(16, 11, 20, 6));
        positions.put(17, new Position(17, 12, 20, 6));
        positions.put(18, new Position(18, 19, 20, 6));
        positions.put(19, new Position(19, 21, 20, 6, R.drawable.p2120f, 0, R.drawable.p2120b, 0));
        positions.put(20, new Position(20, 28, 20, 6, R.drawable.p2820f, 0, R.drawable.p2820b, 0));
        positions.put(21, new Position(21, 33, 20, 6));
        positions.put(22, new Position(22, 39, 20, 6, R.drawable.p3920f, 0, 0, 0));
        positions.put(23, new Position(23, 39, 8, 6));
        positions.put(24, new Position(24, 36, 8, 6));
        positions.put(25, new Position(25, 32, 8, 6));
        positions.put(27, new Position(27, 28, 8, 6));
        positions.put(26, new Position(26, 29, 8, 6, 0, 0, R.drawable.p298dny, 0));
        positions.put(28, new Position(28, 23, 8, 6));
        positions.put(29, new Position(29, 21, 8, 6, R.drawable.p218f, 0, R.drawable.p218b, 0));
        positions.put(30, new Position(30, 14, 8, 6, R.drawable.p148f, 0, R.drawable.p148f, 0));
        positions.put(31, new Position(31, 15, 8, 6));
        positions.put(32, new Position(32, 6, 20, 6));
        positions.put(33, new Position(33, 15, 20, 6, R.drawable.p1520f, 0, R.drawable.p1520b, 0));
        for (Map.Entry<Integer, Position> position : positions.entrySet()) {
            addPosition(position.getValue());
        }
        //endregion
        //region Edge
        addEdge(new Edge(1, positions.get(22), positions.get(1)));
        addEdge(new Edge(2, positions.get(1), positions.get(21)));
        addEdge(new Edge(3, positions.get(21), positions.get(20)));
        addEdge(new Edge(4, positions.get(20), positions.get(15)));
        addEdge(new Edge(5, positions.get(15), positions.get(19)));
        addEdge(new Edge(6, positions.get(19), positions.get(18)));
        addEdge(new Edge(7, positions.get(18), positions.get(33)));
        addEdge(new Edge(8, positions.get(17), positions.get(16)));
        addEdge(new Edge(9, positions.get(16), positions.get(10)));
        addEdge(new Edge(10, positions.get(10), positions.get(14)));
        addEdge(new Edge(11, positions.get(14), positions.get(32)));
        addEdge(new Edge(12, positions.get(10), positions.get(13)));
        addEdge(new Edge(13, positions.get(13), positions.get(12)));
        addEdge(new Edge(14, positions.get(12), positions.get(9)));
        addEdge(new Edge(15, positions.get(9), positions.get(6)));
        addEdge(new Edge(16, positions.get(6), positions.get(4)));
        addEdge(new Edge(17, positions.get(4), positions.get(5)));
        addEdge(new Edge(18, positions.get(9), positions.get(7)));
        addEdge(new Edge(19, positions.get(30), positions.get(7)));
        addEdge(new Edge(20, positions.get(30), positions.get(31)));
        addEdge(new Edge(21, positions.get(31), positions.get(8)));
        addEdge(new Edge(22, positions.get(8), positions.get(3)));
        addEdge(new Edge(23, positions.get(3), positions.get(29)));
        addEdge(new Edge(24, positions.get(29), positions.get(28)));
        addEdge(new Edge(25, positions.get(28), positions.get(27)));
        addEdge(new Edge(26, positions.get(26), positions.get(27)));
        addEdge(new Edge(27, positions.get(26), positions.get(25)));
        addEdge(new Edge(28, positions.get(25), positions.get(2)));
        addEdge(new Edge(29, positions.get(2), positions.get(24)));
        addEdge(new Edge(30, positions.get(24), positions.get(23)));
        addEdge(new Edge(31, positions.get(11), positions.get(2)));
        addEdge(new Edge(32, positions.get(11), positions.get(1)));
        addEdge(new Edge(33, positions.get(33), positions.get(17)));
        //endregion
        //region Device
        addDevice(new Device(1, 70, "0C:F3:EE:00:96:A0", getPosition(1), Alignment.LEFT));
        addDevice(new Device(2, 57, "0C:F3:EE:00:82:4B", getPosition(2), Alignment.LEFT));
        addDevice(new Device(3, 65, "0C:F3:EE:00:63:8C", getPosition(3), Alignment.LEFT));
        addDevice(new Device(4, 63, "0C:F3:EE:00:96:44", getPosition(4), Alignment.RIGHT));
        addDevice(new Device(5, 51, "0C:F3:EE:00:83:96", getPosition(13), Alignment.FRONT));
        addDevice(new Device(6, 73, "0C:F3:EE:00:87:EC", getPosition(16), Alignment.RIGHT));
        addDevice(new Device(7, 77, "0C:F3:EE:00:87:A8", getPosition(15), Alignment.LEFT));
        //endregion
        //region Room
        addRoom(new Room(1, 107, getPosition(5)));
        addRoom(new Room(2, 1072, getPosition(6)));
        addRoom(new Room(3, 108, getPosition(7)));
        addRoom(new Room(4, 109, getPosition(8)));
        addRoom(new Room(5, 123, getPosition(3), "Teakonyha"));
        addRoom(new Room(6, 121, getPosition(12), "Fiú WC"));
        addRoom(new Room(7, 122, getPosition(13), "Lány WC"));
        addRoom(new Room(8, 104, getPosition(14)));
        addRoom(new Room(9, 105, getPosition(32)));
        addRoom(new Room(10, 119, getPosition(16)));
        addRoom(new Room(11, 103, getPosition(17), "Számítógépes Laboratórium"));
        addRoom(new Room(12, 116, getPosition(18), "Családbarát Helyiség"));
        addRoom(new Room(13, 115, getPosition(19), "Technikai Előkészítő"));
        addRoom(new Room(14, 102, getPosition(20), "Számítógépes Laboratórium"));
        addRoom(new Room(15, 101, getPosition(21), "Számítógépes Laboratórium"));
        addRoom(new Room(16, 100, getPosition(22)));
        addRoom(new Room(17, 114, getPosition(23), "Könyvtár tárgyaló"));
        addRoom(new Room(18, 113, getPosition(24), "Adminisztráció"));
        addRoom(new Room(19, 112, getPosition(25), "Tanszékvezető"));
        addRoom(new Room(20, 111, getPosition(27)));
        addRoom(new Room(21, 125, getPosition(26), "Fénymásoló Helyiség"));
        addRoom(new Room(22, 110, getPosition(28)));
        addRoom(new Room(23, 124, getPosition(29), "Raktár"));
        addRoom(new Room(24, 106, getPosition(4)));
        addRoom(new Room(25, 117, getPosition(31), "Tanári Női Mozsdó"));
        addRoom(new Room(26, 118, getPosition(30), "Tanári Férfi Mozsdó"));
        addRoom(new Room(27, 199, getPosition(11), "Lépcső"));
        //endregion
        //region Person
        addPerson(new Person(1, "Tóth Zsolt", 1, R.drawable.tzs0, context.getString(R.string.seniorLecturer)));
        addPerson(new Person(2, "Tamás Judit", 1, R.drawable.tj0, context.getString(R.string.phdStudentOne)));
        addPerson(new Person(3, "Vincze Dávid", 1, R.drawable.vd0, context.getString(R.string.associateProfessor)));
        addPerson(new Person(4, "Kovács Szilveszter", 2, R.drawable.ksz0, context.getString(R.string.associateProfessor)));
        addPerson(new Person(5, "Krizsán Zoltán", 2, R.drawable.kz0, context.getString(R.string.associateProfessor)));
        addPerson(new Person(6, "Bulla Dávid", 3, R.drawable.bd0, "Mérnöktanár"));
        addPerson(new Person(7, "Szűcs Miklós", 3, R.drawable.szm0, "Mesteroktató"));
        addPerson(new Person(8, "Baksáné Varga Erika", 4, R.drawable.bve0, context.getString(R.string.associateProfessor)));
        addPerson(new Person(9, "Barabás Péter", 4, R.drawable.bp0, context.getString(R.string.seniorLecturer)));
        addPerson(new Person(10, "Sasvári Péter", 4, R.drawable.sp0, context.getString(R.string.associateProfessor)));
        addPerson(new Person(11, "Tompa Tamás", 9, R.drawable.tt0, context.getString(R.string.phdStudentTwo)));
        addPerson(new Person(12, "Nagy Miklósné", 17, R.drawable.nf404, context.getString(R.string.librarian)));
        addPerson(new Person(13, "Göncziné Halász Éva", 18, R.drawable.ghe0, "Tanulmányi igazgatási ügyintéző"));
        addPerson(new Person(14, "Kovács László", 19, R.drawable.kl0, context.getString(R.string.associateProfessor)));
        addPerson(new Person(15, "Wagner György", 20, R.drawable.wgy0, "Mesteroktató"));
        addPerson(new Person(16, "Mileff Péter", 22, R.drawable.mp0, context.getString(R.string.associateProfessor)));
        addPerson(new Person(17, "Smid László", 22, R.drawable.sl0, context.getString(R.string.assistantLecturer)));
        addPerson(new Person(18, "Sátán Ádám", 24, R.drawable.sa0, context.getString(R.string.student)));
        addPerson(new Person(19, "Bogdándy Bence", 24, R.drawable.bb0, context.getString(R.string.student)));
        addPerson(new Person(20, "Molnár Dávid", 24, R.drawable.md0, context.getString(R.string.student)));
        addPerson(new Person(21, "Pintér Tamás", 24, R.drawable.pt0, context.getString(R.string.student)));
        addPerson(new Person(22, "Boros Tamás", 24, R.drawable.bt0, context.getString(R.string.student)));
        addPerson(new Person(23, "Tóth Ádám", 24, R.drawable.ta0, context.getString(R.string.student)));
        addPerson(new Person(24, "Ilku Krisztián", 24, R.drawable.ik0, context.getString(R.string.student)));
        addPerson(new Person(25, "Chiraz Bachtarzi", 24, R.drawable.cb0, context.getString(R.string.student)));
        //endregion
    }

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
                Edge edge = new Edge(Integer.parseInt(cursor.getString(0)), getPosition(Integer.parseInt(cursor.getString(1))), getPosition(Integer.parseInt(cursor.getString(2))));
                edges.add(edge);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return edges;
    }

    public Edge getEdgeById(Integer id) throws NoEdgeFound {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Edge", null, "id=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Edge edge = null;
        if (cursor.getCount() != 0) {
            edge = new Edge(Integer.parseInt(cursor.getString(0)), getPosition(Integer.parseInt(cursor.getString(1))), getPosition(Integer.parseInt(cursor.getString(2))));
        }
        cursor.close();
        if (edge == null) {
            throw new NoEdgeFound();
        }
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
        Room room = new Room(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), getPosition(Integer.parseInt(cursor.getString(2))), cursor.getString(3), getPeopleInRoom(id));
        cursor.close();
        return room;
    }

    public Room getRoomByPosition(int positionId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Room", null, "position=" + positionId, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Room room = new Room(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), getPosition(Integer.parseInt(cursor.getString(2))), cursor.getString(3), getPeopleInRoom(Integer.parseInt(cursor.getString(0))));
        cursor.close();
        return room;
    }

    public Room getRoomByNumber(int number) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Room", null, "number=" + number, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Room room = new Room(0, 0, new Position(0, 0.0, 0.0, 0.0), new Person(0, context.getString(R.string.noSuchRoom), 0, R.drawable.nf404, ""));
        if (cursor.getCount() != 0) {
            room = new Room(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), getPosition(Integer.parseInt(cursor.getString(2))), cursor.getString(3), getPeopleInRoom(Integer.parseInt(cursor.getString(0))));
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
                Room room = new Room(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), getPosition(Integer.parseInt(cursor.getString(2))), cursor.getString(3), getPeopleInRoom(Integer.parseInt(cursor.getString(0))));
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
        contentValues.put("id", position.getId());
        contentValues.put("x", position.getX());
        contentValues.put("y", position.getY());
        contentValues.put("z", position.getZ());
        contentValues.put("comment", position.getComment());
        contentValues.put("front", position.getFrontId());
        contentValues.put("leftimage", position.getLeftId());
        contentValues.put("right", position.getRightId());
        contentValues.put("behind", position.getBehindId());
        db.insert("Position", null, contentValues);
    }

    public Position getPosition(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Position", null, "id=" + id, null/*new String[]{String.valueOf(id)}*/, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Position position = new Position(Integer.parseInt(cursor.getString(0)), Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)),
                Double.parseDouble(cursor.getString(3)), cursor.getString(4), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));
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
                Position position = new Position(Integer.parseInt(cursor.getString(0)), Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)),
                        Double.parseDouble(cursor.getString(3)), cursor.getString(4), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));
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
                Device device = new Device(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), getPosition(Integer.parseInt(cursor.getString(3))), Alignment.valueOf(cursor.getString(4)));
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
                Person person = new Person(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(4));
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
                Person person = new Person(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(4));
                people.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return people;
    }

    //endregion
    //region History

    public void addHistory(History history) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        historyId++;
        values.put("id", historyId);
        values.put("groupid", groupid);
        values.put("user", history.getName());
        values.put("position", history.getPosition());
        values.put("date", new Date().getTime());
        values.put("direction", history.getDirection());
        db.insert("History", null, values);
    }

    public List<History> getAllHistory() {
        List<History> histories = new ArrayList<>();
        String selectQuery = "Select * From History";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                histories.add(new History(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Float.parseFloat(cursor.getString(4)), Long.parseLong(cursor.getString(5))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return histories;
    }

    public List<History> getHistoryByGroupId(Integer id) throws NoEdgeFound {
        List<History> histories = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Edge", null, "groupid=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (cursor.moveToFirst()) {
            do {
                histories.add(new History(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Float.parseFloat(cursor.getString(4)), Long.parseLong(cursor.getString(5))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return histories;

    }

    private int getHistoryCount() {
        String countQuery = "SELECT * FROM History";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Integer count = cursor.getCount();
        cursor.close();
        return count;
    }

    private int getHistoryLastGroupId() {
        String countQuery = "SELECT MAX(groupid) FROM History";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Integer groupId = null;
        if (cursor != null) {
            cursor.moveToFirst();
            groupId = cursor.getInt(0);
        }
        cursor.close();
        return groupId;
    }
}
