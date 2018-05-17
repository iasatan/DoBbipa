package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by iasatan on 2017.10.13..
 */

public class Device {
    //region variables
    private final int id;
    private final int baseRSSI;
    private final String MAC;
    private final Position position;
    private final Alignment alignment;
    private List<Integer> prevRSSI;

    //endregion
    //region constructors

    public Device(int id, int baseRSSI, String MAC, Position position, Alignment alignment) {
        this.id = id;
        this.baseRSSI = baseRSSI;
        this.MAC = MAC;
        this.position = position;
        this.alignment = alignment;
        prevRSSI = new ArrayList<>();
    }
    //endregion

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
        Double average = 0.0;
        if (prevRSSI.size() == 0) { //if no previous RSSI measured than returns 0
            return average;
        }
        if (prevRSSI.size() > 5) {
            removeOutstandingRSSIs(); //filters out the maximum and minimum value from the list
        }

        for (int previousRSSI : prevRSSI) {
            average += previousRSSI;
        }

        average = average / prevRSSI.size();
        return average;
    }

    public double getDistanceFromDevice() {
        Double averageRSSI = getAverageRSSI();
        if (averageRSSI == 0) {
            return 1000.0;
        }
        Double distance = Math.pow(10, (averageRSSI + baseRSSI) / (-20)); //Log-Distance Path Loss model
        if (distance < 1.6) {
            return distance;
        } else {
            return Math.sqrt(Math.pow(distance, 2) - Math.pow(1.6, 2));
        }
    }

    //region getter & setter
    public int getId() {
        return id;
    }

    public int getBaseRSSI() {
        return baseRSSI;
    }

    public String getMAC() {
        return MAC;
    }

    public Position getPosition() {
        return position;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    //endregion
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
