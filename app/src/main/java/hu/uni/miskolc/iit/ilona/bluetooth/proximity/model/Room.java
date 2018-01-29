package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iasatan on 2017.10.17..
 */

public class Room {
    //region variables
    private final int id;
    private final Integer number;
    private final Position position;
    private final String title;
    private final List<Person> people;
    private final Integer buildingId;

    //endregion
    //region constructors

    public Room(int id, Integer number, Position position, String title, List<Person> people) {
        this.id = id;
        this.number = number;
        this.position = position;
        this.title = title;
        this.people = people;
        buildingId = 1;
    }


    public Room(int id, Integer number, Position position, String title) {
        this.id = id;
        this.number = number;
        this.position = position;
        this.title = title;
        people = new ArrayList<>();
        buildingId = 1;
    }

    public Room(int id, int number, Position position) {
        this.id = id;
        this.number = number;
        this.position = position;
        title = "";
        people = new ArrayList<>();
        buildingId = 1;
    }

    public Room(int id, int number, Position position, Person person) {
        this.id = id;
        this.number = number;
        this.position = position;
        title = "";
        people = new ArrayList<>();
        people.add(person);
        buildingId = 1;
    }

    public Room(int id, Integer number, Position position, String title, List<Person> people, Integer buildingId) {
        this.id = id;
        this.number = number;
        this.position = position;
        this.title = title;
        this.people = people;
        this.buildingId = buildingId;
    }


    public Room(int id, Integer number, Position position, String title, Integer buildingId) {
        this.id = id;
        this.number = number;
        this.position = position;
        this.title = title;
        people = new ArrayList<>();
        this.buildingId = buildingId;
    }

    public Room(int id, int number, Position position, Integer buildingId) {
        this.id = id;
        this.number = number;
        this.position = position;
        title = "";
        people = new ArrayList<>();
        this.buildingId = buildingId;
    }

    public Room(int id, int number, Position position, Person person, Integer buildingId) {
        this.id = id;
        this.number = number;
        this.position = position;
        title = "";
        people = new ArrayList<>();
        people.add(person);
        this.buildingId = buildingId;
    }

    //endregion
    //region getters & setters
    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public Position getPosition() {
        return position;
    }

    public List<Person> getPeople() {
        return people;
    }

    //endregion
    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", title= " + title +
                ", people=" + people +
                '}';
    }
}
