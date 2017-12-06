package hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception;

/**
 * Created by iasatan on 2017.12.06..
 */

public class NoCloseBeaconException extends Exception {
    public NoCloseBeaconException() {
        super("No near beacons found");
    }
}
