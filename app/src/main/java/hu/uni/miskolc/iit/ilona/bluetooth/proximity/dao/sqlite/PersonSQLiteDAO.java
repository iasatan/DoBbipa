package hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao.PersonDAO;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;

/**
 * Created by iasatan on 2017.11.13..
 */

public class PersonSQLiteDAO extends SQLiteOpenHelper implements PersonDAO {

    public PersonSQLiteDAO(Context context, String databaseName, Integer databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    public PersonSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PersonSQLiteDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE People(id INTEGER PRIMARY KEY, name TEXT, roomId INTEGER)");
        populateDatabase();
    }

    private void populateDatabase() {
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS People");
        onCreate(db);
    }

    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", person.getName());
        contentValues.put("roomId", person.getRoomId());
        db.insert("People", null, contentValues);
        db.close();
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

    public List<Person> getPeopleInRoom(int roomId) {
        List<Person> people = new ArrayList<Person>();
        String selectQuery = "Select * From People WHERE roomId = " + roomId;
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
