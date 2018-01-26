package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoCloseBeaconException;

/**
 * Created by iasatan on 2017.12.14..
 */

public class User {
    private final String macAddressBL;
    private Position position;
    private List<Position> prevPositions;
    private SecurityClearance securityClearance;

    public User(String macAddressBL, SecurityClearance securityClearance) {
        prevPositions = new ArrayList<>();
        this.macAddressBL = macAddressBL;
        this.securityClearance = securityClearance;
        position = new Position();
    }

    //region getters & setters

    public String getMacAddressBL() {
        return macAddressBL;
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

    public Room getClosestRoom(List<Room> rooms) {
        return position.getClosestRoom(rooms);
    }

    public Position getClosestPosition(List<Position> positions) {
        return position.getClosestPosition(positions);
    }

    public SecurityClearance getSecurityClearance() {
        return securityClearance;
    }

    public void setSecurityClearance(SecurityClearance securityClearance) {
        this.securityClearance = securityClearance;
    }

    //endregion
    public void addPosition(Position position) {
        prevPositions.add(position);
    }

    public void addPosition(Map<String, Device> devices) throws NoCloseBeaconException {
        if (prevPositions.size() > 10) {
            prevPositions.remove(0);
        }
        position.setZ(4.4); //average height a user holds it's phone
        Map<String, Device> nearDevices = Device.getNearDevices(devices);

        if (nearDevices.size() == 0) {
            throw new NoCloseBeaconException();
        }
        if (nearDevices.size() == 1) {
            List<String> keys = new ArrayList<>(nearDevices.keySet());
            closeToOneBeacon(nearDevices.get(keys.get(0)));
        } else { //checkes if the user is between beacons
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
            } else { //if they do not share coordinates, it calculates the position from the closer beacon
                if (distances.get(0) <= distances.get(1)) {
                    closeToOneBeacon(closestDevices.get(0));
                } else {
                    closeToOneBeacon(closestDevices.get(1));
                }
            }

        }
        prevPositions.add(position);
    }

    private void closeToOneBeacon(Device nearestDevice) {
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
    }

    @Override
    public String toString() {
        return "User{" +
                "macAddressBL='" + macAddressBL + '\'' +
                ", position=" + position +
                ", securityClearance=" + securityClearance +
                '}';
    }
}
