package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by iasatan on 2018.01.23..
 */
public class PositionTest {
    Position position1 = new Position(0, 1, 1, 1);
    Position position2 = new Position(1, 2, 2, 2);

    @Test
    public void getDistance() throws Exception {
        Assert.assertEquals(position1.getDistance(position2), position2.getDistance(position1), 0.01);
    }

    @Test
    public void getClosestRoom() throws Exception {
    }

}