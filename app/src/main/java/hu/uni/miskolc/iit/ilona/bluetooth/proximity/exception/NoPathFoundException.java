package hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception;

/**
 * Created by iasatan on 2018.01.26..
 */

public class NoPathFoundException extends Exception {
    public NoPathFoundException() {
        super("No Path Found With that source node");
    }

    public NoPathFoundException(String message) {
        super(message);
    }

    public NoPathFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPathFoundException(Throwable cause) {
        super(cause);
    }

    public NoPathFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
