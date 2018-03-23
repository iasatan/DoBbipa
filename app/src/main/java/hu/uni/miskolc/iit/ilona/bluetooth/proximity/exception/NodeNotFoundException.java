package hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception;

/**
 * Created by iasatan on 2018.01.26..
 */

public class NodeNotFoundException extends Exception {
    public NodeNotFoundException() {
        super("No such node found in the Graph");
    }
}
