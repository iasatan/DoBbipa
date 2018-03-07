package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by iasatan on 2018.03.07..
 */
public class EdgeTest {
    Position source;
    Position destination;
    Edge edge;

    @Before
    public void setUp() throws Exception {
        source = new Position(4, 6, 8, 6, "labor előtt");
        destination = new Position(5, 5, 8, 6);
        edge = new Edge(1, source, destination);
    }

    @Test
    public void getNode1() {
        Assert.assertEquals(edge.getNode1(), source);
    }

    @Test
    public void getId() {
        Assert.assertEquals((int) edge.getId(), 1);
    }

    @Test
    public void getNode2() {
        Assert.assertEquals(edge.getNode2(), destination);
    }

    @Test
    public void getDistance() {
        Assert.assertEquals(edge.getDistance(), 1.0);
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals(edge.toString(), "Edge{node1=Position{id: 4, x=6.0, y=8.0, z=6.0}, node2=Position{id: 5, x=5.0, y=8.0, z=6.0}, distance=1.0}");
    }

}