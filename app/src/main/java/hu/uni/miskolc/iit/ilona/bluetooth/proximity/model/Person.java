package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

/**
 * Created by iasatan on 2017.10.17..
 */

public class Person {
    private Integer id;
    private String name;
    private Integer roomId;

    public Person() {
    }

    public Person(Integer id, String name, Integer roomId) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return name;
    }
}
