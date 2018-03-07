package hu.uni.miskolc.iit.ilona.bluetooth.proximity.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Device;

/**
 * Created by iasatan on 2018.03.07..
 */

public class DeviceUtil {
    private static final int MAXRANGE = 13;  //12m is the maximum range that produce good results of the bluetooth beacons

    public static List<Device> closestTwoDevice(Map<String, Device> nearDevices) {
        if (nearDevices.values().size() == 2) {
            return Arrays.asList((Device) nearDevices.values().toArray()[0], (Device) nearDevices.values().toArray()[1]);
        }
        Device[] result = new Device[2];
        result[0] = (Device) nearDevices.values().toArray()[0];
        result[1] = result[0];
        for (Device currentDevice : nearDevices.values()) {
            if (currentDevice.getDistanceFromDevice() < result[1].getDistanceFromDevice()) {
                result[1] = currentDevice;
            }
            if (currentDevice.getDistanceFromDevice() < result[0].getDistanceFromDevice()) {
                result[1] = result[0];
                result[0] = currentDevice;
            }
        }
        return Arrays.asList(result);
    }

    public static Map<String, Device> getNearDevices(Map<String, Device> devices) {
        Map<String, Device> nearDevices = new HashMap<>();
        for (Map.Entry<String, Device> device : devices.entrySet()) {
            if (device.getValue().getAverageRSSI() != 0) {
                if (device.getValue().getDistanceFromDevice() < MAXRANGE) {
                    nearDevices.put(device.getKey(), device.getValue());
                }
            }
        }
        return nearDevices;
    }
}
