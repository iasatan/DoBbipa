package hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.PersonDAO;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.PositionDAO;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.RoomDAO;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;

/**
 * Created by iasatan on 2017.11.13..
 */

public class RoomSQLiteDAO extends SQLiteOpenHelper implements RoomDAO {

    private PositionDAO positionDAO;
    private PersonDAO personDAO;

    public RoomSQLiteDAO(Context context, String databaseName, Integer databaseVersion, PositionDAO positionDAO, PersonDAO personDAO) {
        super(context, databaseName, null, databaseVersion);
        this.positionDAO = positionDAO;
        this.personDAO = personDAO;
    }

    public RoomSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public RoomSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Room(id INTEGER PRIMARY KEY, number INTEGER, position INTEGER)");
        populateDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Room");
        onCreate(db);
    }

    private void populateDatabase() {
        //SystemClock.sleep(100);
        Log.d("Insert", "inserting position");

        addRoom(new Room(1, 107, positionDAO.getPosition(5)));

        addRoom(new Room(2, 107, positionDAO.getPosition(6)));

        addRoom(new Room(3, 108, positionDAO.getPosition(7)));

        addRoom(new Room(4, 109, positionDAO.getPosition(8)));

        addRoom(new Room(5, 123, positionDAO.getPosition(9)));
    }

    public void addRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", room.getNumber());
        values.put("position", room.getPosition().getId());
        db.insert("Room", null, values);
        db.close();
    }

    public Room getRoom(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Room", null, "id=" + id, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Position position = positionDAO.getPosition(Integer.parseInt(cursor.getString(2)));
        Room room = new Room();
        room.setId(Integer.parseInt(cursor.getString(0)));
        room.setPosition(position);
        room.setNumber(Integer.parseInt(cursor.getString(1)));
        room.setPeople(personDAO.getPeopleInRoom(id));
        return room;
    }

    public List<Room> getAllRoom() {
        List<Room> rooms = new ArrayList<Room>();
        String selectQuery = "Select * From Room";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(Integer.parseInt(cursor.getString(0)));
                room.setNumber(Integer.parseInt(cursor.getString(1)));
                room.setPosition(positionDAO.getPosition(Integer.parseInt(cursor.getString(2))));
                room.setPeople(personDAO.getPeopleInRoom(Integer.parseInt(cursor.getString(0))));
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
}
