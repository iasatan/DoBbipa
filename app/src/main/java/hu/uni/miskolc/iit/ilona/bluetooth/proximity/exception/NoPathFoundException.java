package hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception;

/**
 * Created by iasatan on 2018.01.26..
 */

public class NoPathFoundException extends Exception {
    public NoPathFoundException() {
        super("No Path Found With that source node");
    }
}
