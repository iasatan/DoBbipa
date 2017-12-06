package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by iasatan on 2017.10.17..
 */

public class Room {

    int id;
    int number;
    Position position;
    Collection<Person> people;

    public Room() {
    }

    public Room(int id, int number, Position position) {
        this.id = id;
        this.number = number;
        this.position = position;
    }

    public Room(int id, int number, Position position, Collection<Person> people) {
        this.id = id;
        this.number = number;
        this.position = position;
        this.people = people;
    }

    public Room(int id, int number, Position position, Person person) {
        this.id = id;
        this.number = number;
        this.position = position;
        people = new ArrayList<Person>();
        people.add(person);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Collection<Person> getPeople() {
        return people;
    }

    public void setPeople(Collection<Person> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", people=" + people +
                '}';
    }

    public static Room getClosestRoom(List<Room> rooms, Position position) {
        Double minDistance = position.getDistanceSquareFrom(rooms.get(0).getPosition());
        Double distance;
        int j = 0;


        for (int i = 1; i < rooms.size(); i++) {
            distance = position.getDistanceSquareFrom(rooms.get(i).getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                j = i;
            }
        }
        return rooms.get(j);

    }
}
