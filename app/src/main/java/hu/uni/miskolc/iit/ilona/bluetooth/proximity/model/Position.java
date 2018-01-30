package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import java.util.List;

/**
 * Created by iasatan on 2017.10.13..
 */

public class Position {
    //region variables
    private final int id;
    private final String comment;
    private final Integer frontId;
    private final Integer rightId;
    private final Integer behindId;
    private final Integer leftId;
    private double x;
    private double y;
    private double z;

    //endregion
    //region constructors
    public Position() {
        id = 0;
        comment = "";
        frontId = 0;
        rightId = 0;
        behindId = 0;
        leftId = 0;
    }

    public Position(int id, double x, double y, double z, String comment, Integer frontId, Integer rightId, Integer behindId, Integer leftId) {
        this.id = id;
        this.comment = comment;
        this.x = x;
        this.y = y;
        this.z = z;
        this.frontId = frontId;
        this.rightId = rightId;
        this.behindId = behindId;
        this.leftId = leftId;
    }

    public Position(int id, double x, double y, double z, Integer frontId, Integer rightId, Integer behindId, Integer leftId) {
        this.id = id;
        comment = "";
        this.x = x;
        this.y = y;
        this.z = z;
        this.frontId = frontId;
        this.rightId = rightId;
        this.behindId = behindId;
        this.leftId = leftId;
    }

    public Position(int id, double x, double y, double z, String comment) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.comment = comment;
        frontId = 0;
        rightId = 0;
        behindId = 0;
        leftId = 0;

    }

    public Position(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        comment = "";
        frontId = 0;
        rightId = 0;
        behindId = 0;
        leftId = 0;
    }


    public Position(Position position) {
        id = position.getId();
        setX(position.getX());
        setY(position.getY());
        setZ(position.getZ());
        comment = position.comment;
        frontId = position.frontId;
        rightId = position.rightId;
        behindId = position.behindId;
        leftId = position.leftId;

    }

    //endregion
    //region getters & setters

    public int getId() {
        return id;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getComment() {
        return comment;
    }

    public Integer getFrontId() {
        return frontId;
    }

    public Integer getRightId() {
        return rightId;
    }

    public Integer getBehindId() {
        return behindId;
    }

    public Integer getLeftId() {
        return leftId;
    }

    //endregion
    @Override
    public String toString() {
        return "Position{" +
                "id: " + id +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public double getDistance(Position position) {
        return Math.sqrt(getDistanceSquareFrom(position));
    }

    private double getDistanceSquareFrom(Position position) {
        return Math.pow(position.getX() - x, 2) + Math.pow(position.getY() - y, 2);

    }

    public Room getClosestRoom(List<Room> rooms) {
        Double minDistance = getDistanceSquareFrom(rooms.get(0).getPosition());
        Double distance;
        int j = 0;


        for (int i = 1; i < rooms.size(); i++) {
            distance = getDistanceSquareFrom(rooms.get(i).getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                j = i;
            }
        }
        return rooms.get(j);

    }


    public Position getClosestPosition(List<Position> positions) {
        Double minDistance = getDistanceSquareFrom(positions.get(0));
        Double distance;
        int j = 0;


        for (int i = 1; i < positions.size(); i++) {
            distance = getDistanceSquareFrom(positions.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                j = i;
            }
        }
        Position position = new Position(positions.get(j));
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;

        if (Double.compare(position.x, x) != 0) {
            return false;
        }
        if (Double.compare(position.y, y) != 0) {
            return false;
        }
        if (Math.abs(position.z - z) > 2) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
