package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.support.test.espresso.core.deps.guava.collect.Iterables;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by iasatan on 2017.12.14..
 */
public class UserTest {
    User user;
    User user2;

    @Before
    public void setUp() {
        user = new User();
        user2 = new User(new Position(0, 0.0, 0.0, 0.0), Arrays.asList(new Position(1, 1.0, 1.0, 1.0)));

    }

    @Test
    public void getPrevPositions() throws Exception {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 0.0, 0.0, 0.0));
        positions.add(new Position(1, 1.0, 1.0, 1.0));
        Assert.assertTrue(Iterables.elementsEqual(positions, user2.getPrevPositions()));

    }

    @Test
    public void setPrevPositions() throws Exception {
    }

    @Test
    public void getPosition() throws Exception {
    }

    @Test
    public void setPosition() throws Exception {
    }

    @Test
    public void addPosition() throws Exception {
    }

    @Test
    public void addPosition1() throws Exception {
    }

    @Test
    public void getClosestRoom() throws Exception {
    }

}