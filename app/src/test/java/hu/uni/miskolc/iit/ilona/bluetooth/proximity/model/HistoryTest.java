package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by iasatan on 2018.03.07..
 */
public class HistoryTest {
    History history1;
    History history2;

    @Before
    public void setUp() throws Exception {
        history1 = new History(1, 1, "asd", 1, 1.0f, (long) 12);
        history2 = new History(2, "dsa", 1, 1.0f);
    }

    @Test
    public void getId() {
        Assert.assertEquals((int) history1.getId(), 1);
        Assert.assertEquals((int) history2.getId(), 2);
    }

    @Test
    public void getGroupId() throws Exception {
        Assert.assertEquals((int) history1.getGroupId(), 1);
        Assert.assertEquals((int) history2.getGroupId(), 0);
    }

    @Test
    public void getName() throws Exception {
        Assert.assertEquals(history2.getName(), "dsa");
        Assert.assertEquals(history1.getName(), "asd");
    }

    @Test
    public void getDate() throws Exception {
        Assert.assertEquals((long) history1.getDate(), (long) 12);
    }

    @Test
    public void getPositionId() throws Exception {
        Assert.assertEquals((int) history1.getPosition(), (int) 1);
    }

    @Test
    public void getDirection() throws Exception {
        Assert.assertEquals((float) history1.getDirection(), (float) 1.0f, 0.001);
    }

}