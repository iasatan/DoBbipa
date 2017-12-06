package hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao;

import java.util.Collection;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;

/**
 * Created by iasatan on 2017.11.13..
 */

public interface RoomDAO {

    void addRoom(Room room);

    Room getRoom(int id);

    Collection<Room> getAllRoom();

    int getRoomCount();

    int updateRoom(Room room);

    void deleteRoom(int id);

}


