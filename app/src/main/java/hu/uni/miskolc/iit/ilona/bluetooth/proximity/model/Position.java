package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import java.util.List;

/**
 * Created by iasatan on 2017.10.13..
 */

public class Position {
    int id;
    double x;
    double y;
    double z;
    String comment;

    public Position() {
    }

    public Position(int id, double x, double y, double z, String comment) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.comment = comment;
    }

    public Position(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Position{" +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", comment='" + comment + '\'' +
                '}';
    }

    public double getDistanceSquareFrom(Position position) {
        return Math.pow(position.getX() - this.getX(), 2) + Math.pow(position.getY() - this.getY(), 2);

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


}
