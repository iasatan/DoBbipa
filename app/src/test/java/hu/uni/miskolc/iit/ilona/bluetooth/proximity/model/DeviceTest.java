package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import com.example.android.test.R;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by iasatan on 2018.03.07..
 */
public class DeviceTest {
    Map<String, Device> nearDevices;
    private Device device;

    @Before
    public void setUp() throws Exception {
        device = new Device(1, 70, "0C:F3:EE:00:96:A0", new Position(1, 35, 20, 6, "101 előtt", R.drawable.p3520ek, R.drawable.p3520r, R.drawable.p3520dny, 0), Alignment.LEFT);
        nearDevices = new HashMap<>();
        //nearDevices.put()
    }

    @Test
    public void addRSSITest() throws Exception {
        assertEquals(device.getPrevRSSIs().size(), 0);
        device.addRSSI(54);
        assertEquals(device.getPrevRSSIs().size(), 1);

    }

    @Test
    public void getPrevRSSIsTest() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(54);
        device.addRSSI(54);
        assertEquals(device.getPrevRSSIs(), list);
    }

    @Test
    public void getDistanceFromDeviceTest() throws Exception {
        device.addRSSI(-90);
        device.addRSSI(-65);
        device.addRSSI(-78);
        device.addRSSI(-43);
        device.addRSSI(-76);
        device.addRSSI(-75);
        device.addRSSI(-87);
        assertEquals(device.getDistanceFromDevice(), 1.2683429483792452, 0.01);
    }

    @Test
    public void getIdTest() {
        assertEquals(device.getId(), 1);
    }

    @Test
    public void getBaseRSSITest() {
        assertEquals(device.getBaseRSSI(), 70);
    }

    @Test
    public void getMACTest() {
        assertEquals(device.getMAC(), "0C:F3:EE:00:96:A0");
    }

    @Test
    public void getPositionTest() {
        assertEquals(device.getPosition(), new Position(2, 35, 20, 6));
    }

    @Test
    public void getAlignmentTest() {
        assertEquals(device.getAlignment(), Alignment.LEFT);
    }

    @Test
    public void toStringTest() {
        assertEquals(device.toString(), "Device{id=1, baseRSSI=70, MAC='0C:F3:EE:00:96:A0', position=Position{id: 1, x=35.0, y=20.0, z=6.0}}");
    }

}