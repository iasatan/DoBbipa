package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoCloseBeaconException;

/**
 * Created by iasatan on 2017.12.14..
 */

public class User {
    private Position position;
    private List<Position> prevPositions;
    private Context context;

    public User(Context context) {
        prevPositions = new ArrayList<>();
        this.context = context;
    }

    public User(Position position, List<Position> prevPositions) {
        this.position = position;
        this.prevPositions = prevPositions;
        prevPositions.add(position);
    }

    public List<Position> getPrevPositions() {
        return prevPositions;
    }

    public void setPrevPositions(List<Position> prevPositions) {
        this.prevPositions = prevPositions;
    }

    public Position getPosition() {

        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void addPosition(Position position) {
        prevPositions.add(position);
    }

    public void addPosition(Map<String, Device> devices) throws NoCloseBeaconException {
        if (prevPositions.size() > 10) {
            prevPositions.remove(0);
        }
        Position position = new Position();
        position.setId(0);
        position.setZ(4.4);
        Map<String, Device> nearDevices = Device.getNearDevices(devices);

        if (nearDevices.size() == 0) {
            throw new NoCloseBeaconException();
        }
        if (nearDevices.size() == 1) {
            List<String> keys = new ArrayList<>(nearDevices.keySet());
            Device nearestDevice = nearDevices.get(keys.get(0));
            switch (nearestDevice.getAlignment()) {
                case RIGHT:
                    position.setY(nearestDevice.getPosition().getY());
                    position.setX(nearestDevice.getPosition().getX() - nearestDevice.getDistanceFromDevice());
                    break;
                case LEFT:
                    position.setY(nearestDevice.getPosition().getY());
                    position.setX(nearestDevice.getPosition().getX() + nearestDevice.getDistanceFromDevice());
                    break;
                case FRONT:
                    position.setY(nearestDevice.getPosition().getY() - nearestDevice.getDistanceFromDevice());
                    position.setX(nearestDevice.getPosition().getX());

                    break;
                case BEHIND:
                    position.setY(nearestDevice.getPosition().getY() + nearestDevice.getDistanceFromDevice());
                    position.setX(nearestDevice.getPosition().getX());
                    break;
            }
        } else {
            List<Device> closestDevices = Device.closestTwoDevice(nearDevices);
            Double distanceBetweenBeacons = closestDevices.get(0).getPosition().getDistance(closestDevices.get(1).getPosition());
            List<Double> distances = new ArrayList<>();
            distances.add(closestDevices.get(0).getDistanceFromDevice());
            distances.add(closestDevices.get(1).getDistanceFromDevice());
            if (closestDevices.get(0).getPosition().getY() == closestDevices.get(1).getPosition().getY()) {
                double xCoordinate = distanceBetweenBeacons / (distances.get(0) + distances.get(1));
                xCoordinate = xCoordinate * closestDevices.get(0).getDistanceFromDevice() + closestDevices.get(0).getPosition().getX();
                position.setX(xCoordinate);
                position.setY(closestDevices.get(0).getPosition().getY());
            } else if (closestDevices.get(0).getPosition().getX() == closestDevices.get(1).getPosition().getX()) {
                double yCoordinate = distanceBetweenBeacons / (closestDevices.get(0).getDistanceFromDevice() + closestDevices.get(1).getDistanceFromDevice());
                yCoordinate = yCoordinate * closestDevices.get(0).getDistanceFromDevice() + closestDevices.get(0).getPosition().getY();
                position.setY(yCoordinate);
                position.setX(closestDevices.get(0).getPosition().getX());
            }
        }
        this.position = position;
        prevPositions.add(position);
    }

    public Room getClosestRoom(List<Room> rooms) {
        return position.getClosestRoom(rooms);
    }


}
