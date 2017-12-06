package hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.PositionDAO;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;

/**
 * Created by iasatan on 2017.11.13..
 */

public class PositionSQLiteDAO extends SQLiteOpenHelper implements PositionDAO {
    public PositionSQLiteDAO(Context context, String databaseName, Integer databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    public PositionSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PositionSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Position(id INTEGER PRIMARY KEY, x INTEGER, y INTEGER, z INTEGER, comment TEXT, buildingid INTEGER)");
        populateDatabase();
    }

    private void populateDatabase() {
        addPosition(new Position(1, 32, 20, 6, "101 előtt"));
        addPosition(new Position(2, 32, 8, 6, "KL előtt"));
        addPosition(new Position(3, 18, 8, 6, "Konyha előtt"));
        addPosition(new Position(4, 6, 8, 6, "labor előtt"));
        addPosition(new Position(5, 5, 6, 4.4));
        addPosition(new Position(6, 6, 6, 4.4));
        addPosition(new Position(7, 13, 7, 4.4));
        addPosition(new Position(8, 18, 7, 4.4));
        addPosition(new Position(9, 18, 8, 4.4, "Teakonyha"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Position");
        onCreate(db);
    }

    @Override
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
        Cursor cursor = db.query("Position", null/*new String[]{"id", "x", "y", "z", "comment"}*/, "id=1", null/*new String[]{String.valueOf(id)}*/, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Position position = new Position(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(4));

        return position;
    }

    public Collection<Position> getAllPosition() {
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
    }


}
