package hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception;

/**
 * Created by iasatan on 2018.01.26..
 */

public class NoEdgeFound extends Exception {
    public NoEdgeFound() {
        super("No Edge Found in the database by that ID");
    }

    public NoEdgeFound(String message) {
        super(message);
    }

    public NoEdgeFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEdgeFound(Throwable cause) {
        super(cause);
    }

    public NoEdgeFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
