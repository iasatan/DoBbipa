package hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao;

import java.util.Collection;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;

/**
 * Created by iasatan on 2017.11.13..
 */

public interface PositionDAO {

    void addPosition(Position position);

    Collection<Position> getAllPosition();

    Position getPosition(int id);

    int getPositionCount();

    int updatePosition(Position position);

    void deletePosition(int id);
}
