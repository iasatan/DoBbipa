package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import com.example.android.test.R;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iasatan on 2018.01.23..
 */
public class PositionTest {


    Position position1 = new Position(0, 1, 1, 1);
    Position position2 = new Position(1, 2, 2, 2);
    List<Room> rooms;
    List<Position> positions;

    @Before
    public void setUp() {
        positions = new ArrayList<>();
        Map<Integer, Position> positionMap = new HashMap<>();
        positionMap.put(1, new Position(1, 35, 20, 6, "101 előtt", R.drawable.p3520ek, R.drawable.p3520r, R.drawable.p3520dny, 0));
        positionMap.put(2, new Position(2, 35, 8, 6, R.drawable.p358ek, 0, R.drawable.p358dny, R.drawable.p358eny));
        positionMap.put(3, new Position(3, 18, 8, 6, "Konyha előtt"));
        positionMap.put(4, new Position(4, 6, 8, 6, "labor előtt"));
        positionMap.put(5, new Position(5, 5, 8, 6));
        positionMap.put(6, new Position(6, 7, 8, 6));
        positionMap.put(7, new Position(7, 13, 8, 6));
        positionMap.put(8, new Position(8, 17, 8, 6));
        positionMap.put(9, new Position(9, 8, 8, 6, "Elzárt folyosó labornál lévő ajtaja", R.drawable.p88ek, 0, R.drawable.p88dny, R.drawable.p88eny));
        positionMap.put(10, new Position(10, 8, 20, 6, R.drawable.p820ek, R.drawable.p820dk, R.drawable.p820dny, 0));
        positionMap.put(11, new Position(11, 35, 12, 6, "lépcső", R.drawable.stairs, R.drawable.p3512dk, 0, R.drawable.p3512eny));
        positionMap.put(12, new Position(12, 8, 10, 6));
        positionMap.put(13, new Position(13, 8, 15, 6, 0, R.drawable.p815r, 0, R.drawable.p815l));
        positionMap.put(14, new Position(14, 7, 20, 6));
        positionMap.put(15, new Position(15, 23, 20, 6));
        positionMap.put(16, new Position(16, 11, 20, 6));
        positionMap.put(17, new Position(17, 12, 20, 6));
        positionMap.put(18, new Position(18, 19, 20, 6));
        positionMap.put(19, new Position(19, 21, 20, 6, R.drawable.p2120f, 0, R.drawable.p2120b, 0));
        positionMap.put(20, new Position(20, 28, 20, 6, R.drawable.p2820f, 0, R.drawable.p2820b, 0));
        positionMap.put(21, new Position(21, 33, 20, 6));
        positionMap.put(22, new Position(22, 39, 20, 6, R.drawable.p3920f, 0, 0, 0));
        positionMap.put(23, new Position(23, 39, 8, 6));
        positionMap.put(24, new Position(24, 36, 8, 6));
        positionMap.put(25, new Position(25, 32, 8, 6));
        positionMap.put(27, new Position(27, 28, 8, 6));
        positionMap.put(26, new Position(26, 29, 8, 6, 0, 0, R.drawable.p298dny, 0));
        positionMap.put(28, new Position(28, 23, 8, 6));
        positionMap.put(29, new Position(29, 21, 8, 6, R.drawable.p218f, 0, R.drawable.p218b, 0));
        positionMap.put(30, new Position(30, 14, 8, 6, R.drawable.p148f, 0, R.drawable.p148f, 0));
        positionMap.put(31, new Position(31, 15, 8, 6));
        positionMap.put(32, new Position(32, 6, 20, 6));
        positionMap.put(33, new Position(33, 15, 20, 6, R.drawable.p1520f, 0, R.drawable.p1520b, 0));
        for (Map.Entry<Integer, Position> position : positionMap.entrySet()) {
            positions.add(position.getValue());
        }

        rooms = new ArrayList<>();
        rooms.add(new Room(1, 107, positionMap.get(5)));
        rooms.add(new Room(2, 1072, positionMap.get(6)));
        rooms.add(new Room(3, 108, positionMap.get(7)));
        rooms.add(new Room(4, 109, positionMap.get(8)));
        rooms.add(new Room(5, 123, positionMap.get(3), "Teakonyha"));
        rooms.add(new Room(6, 121, positionMap.get(12), "Fiú WC"));
        rooms.add(new Room(7, 122, positionMap.get(13), "Lány WC"));
        rooms.add(new Room(8, 104, positionMap.get(14)));
        rooms.add(new Room(9, 105, positionMap.get(32)));
        rooms.add(new Room(10, 119, positionMap.get(16)));
        rooms.add(new Room(11, 103, positionMap.get(17), "Számítógépes Laboratórium"));
        rooms.add(new Room(12, 116, positionMap.get(18), "Családbarát Helyiség"));
        rooms.add(new Room(13, 115, positionMap.get(19), "Technikai Előkészítő"));
        rooms.add(new Room(14, 102, positionMap.get(20), "Számítógépes Laboratórium"));
        rooms.add(new Room(15, 101, positionMap.get(21), "Számítógépes Laboratórium"));
        rooms.add(new Room(16, 100, positionMap.get(22)));
        rooms.add(new Room(17, 114, positionMap.get(23), "Könyvtár tárgyaló"));
        rooms.add(new Room(18, 113, positionMap.get(24), "Adminisztráció"));
        rooms.add(new Room(19, 112, positionMap.get(25), "Tanszékvezető"));
        rooms.add(new Room(20, 111, positionMap.get(27)));
        rooms.add(new Room(21, 125, positionMap.get(26), "Fénymásoló Helyiség"));
        rooms.add(new Room(22, 110, positionMap.get(28)));
        rooms.add(new Room(23, 124, positionMap.get(29), "Raktár"));
        rooms.add(new Room(24, 106, positionMap.get(4)));
        rooms.add(new Room(25, 117, positionMap.get(31), "Tanári Női Mozsdó"));
        rooms.add(new Room(26, 118, positionMap.get(30), "Tanári Férfi Mozsdó"));
        rooms.add(new Room(27, 199, positionMap.get(11), "Lépcső"));

    }

    @Test
    public void getDistance() {
        Assert.assertEquals(position1.getDistance(position2), 1.4142135623730951, 0.001);
        Assert.assertEquals(position1.getDistance(position1), 0.0, 0.001);
        Assert.assertEquals(position1.getDistance(position2), position2.getDistance(position1), 0.001);
        Assert.assertFalse(position1.getDistance(position2) < 1);
    }

    @Test
    public void getClosestRoom() throws Exception {
        Assert.assertEquals(position1.getClosestRoom(rooms), rooms.get(0));
    }

    @Test
    public void getClosestPosition() throws Exception {
        Assert.assertEquals(position1.getClosestPosition(positions), positions.get(4));
    }

    @Test
    public void getComment() throws Exception {
        Assert.assertEquals(positions.get(3).getComment(), "labor előtt");
    }

    @Test
    public void getFrontId() throws Exception {
        Assert.assertEquals(positions.get(32).getFrontId(), R.drawable.p1520f);
    }

    @Test
    public void getRightId() throws Exception {
        Assert.assertEquals(positions.get(10).getRightId(), R.drawable.p3512dk);
    }

    @Test
    public void getBehindId() throws Exception {
        Assert.assertEquals(positions.get(32).getBehindId(), R.drawable.p1520b);
    }

    @Test
    public void getLeftId() throws Exception {
        Assert.assertEquals(positions.get(10).getLeftId(), R.drawable.p3512eny);
    }
}