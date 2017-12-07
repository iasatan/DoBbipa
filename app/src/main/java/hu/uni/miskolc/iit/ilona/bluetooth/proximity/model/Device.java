package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by iasatan on 2017.10.13..
 */

public class Device {
    int id;
    int baseRSSI;
    String MAC;
    Position position;
    List<Integer> prevRSSI;
    Alignment alignment;


    public Device() {
        prevRSSI = new ArrayList<>();
    }

    public Device(int baseRSSI, String MAC, Position position, Alignment alignment) {
        this.baseRSSI = baseRSSI;
        this.MAC = MAC;
        this.position = position;
        prevRSSI = new ArrayList<>();
        this.alignment = alignment;
    }

    public Device(int id, int baseRSSI, String MAC, Position position, Alignment alignment) {
        this.id = id;
        this.baseRSSI = baseRSSI;
        this.MAC = MAC;
        this.position = position;
        this.alignment = alignment;
        prevRSSI = new ArrayList<>();
    }

    public void addRSSI(Integer RSSI) {
        prevRSSI.add(RSSI);
    }

    private void removeOutstandingRSSIs() {
        Collections.sort(prevRSSI);
        prevRSSI.remove(0);
        prevRSSI.remove(prevRSSI.size() - 1);

    }

    public List<Integer> getPrevRSSIs() {
        return prevRSSI;
    }

    public Double getAverageRSSI() {
        if (prevRSSI.size() == 0) {
            return 0.0;
        }
        if (prevRSSI.size() > 5) {
            removeOutstandingRSSIs();
        }
        Double average = 0.0;
        for (int previousRSSI : prevRSSI) {
            average += previousRSSI;
        }

        average = average / prevRSSI.size();
        return average;
    }

    public double getDistanceFrom() {
        if (getAverageRSSI() == 0) {
            return 1000.0;
        }
        Double distance = Math.pow(10, (getAverageRSSI() + baseRSSI) / (-20));
        if (distance < 1.6) {
            return distance;
        } else {
            return Math.sqrt(Math.pow(distance, 2) - Math.pow(1.6, 2));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBaseRSSI() {
        return baseRSSI;
    }

    public void setBaseRSSI(int baseRSSI) {
        this.baseRSSI = baseRSSI;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", baseRSSI=" + baseRSSI +
                ", MAC='" + MAC + '\'' +
                ", position=" + position +
                '}';
    }
}
