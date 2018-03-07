package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import com.example.android.test.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by iasatan on 2018.03.07..
 */
public class PersonTest {


    Person adam;

    @Before
    public void setUp() throws Exception {
        adam = new Person(1, "Satan Adam", 106, R.drawable.sa0, "Demonstrátor");
    }

    @Test
    public void getTitle() throws Exception {
        Assert.assertEquals(adam.getTitle(), "Demonstrátor");
    }

    @Test
    public void getImageId() throws Exception {
        Assert.assertEquals((int) adam.getImageId(), R.drawable.sa0);
    }

    @Test
    public void getId() throws Exception {
        Assert.assertEquals((int) adam.getId(), 1);
    }

    @Test
    public void getName() throws Exception {
        Assert.assertEquals(adam.getName(), "Satan Adam");
    }

    @Test
    public void getRoomId() throws Exception {
        Assert.assertEquals((int) adam.getRoomId(), 106);
    }

    @Test
    public void toStringTest() throws Exception {
        Assert.assertEquals(adam.toString(), "Satan Adam");
    }

    @Test
    public void equals() throws Exception {
        Assert.assertTrue(adam.equals(adam));
        Assert.assertTrue(adam.equals(new Person(2, "Satan Adam", 106, R.drawable.sa0, "Demonstrátor")));
        Assert.assertFalse(adam.equals(1));
        Assert.assertFalse(adam.equals(new Person(2, "Satan Ad", 106, R.drawable.sa0, "Demonstrátor")));
        Assert.assertFalse(adam.equals(new Person(2, "Satan Adam", 10, R.drawable.sa0, "Demonstrátor")));
        Assert.assertFalse(adam.equals(new Person(2, "Satan Adam", 106, R.drawable.tzs0, "Demonstrátor")));
        Assert.assertFalse(adam.equals(new Person(2, "Satan Adam", 106, R.drawable.sa0, "Demonstráto")));
    }

    @Test
    public void testHashCode() {
        Assert.assertEquals(adam.hashCode(), 1905719790);
    }

}