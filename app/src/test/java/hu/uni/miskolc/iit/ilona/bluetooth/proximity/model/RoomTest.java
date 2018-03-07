package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import com.example.android.test.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iasatan on 2018.03.07..
 */
public class RoomTest {
    Room room;
    Room roomWithPeople;
    Room roomWithPerson;
    List<Person> people;

    @Before
    public void setUp() throws Exception {
        room = new Room(19, 112, new Position(25, 32, 8, 6), "Tanszékvezető");
        people = new ArrayList<>();
        people.add(new Person(1, "Adam Satan", 6, R.drawable.sa0, "Demonstrátor"));
        roomWithPeople = new Room(2, 106, new Position(1, 1, 1, 1), "", people);
        roomWithPerson = new Room(3, 106, new Position(1, 1, 1, 1), new Person(1, "Adam Satan", 6, R.drawable.sa0, "Demonstrátor"));
    }

    @Test
    public void getTitle() throws Exception {
        Assert.assertEquals(room.getTitle(), "Tanszékvezető");
    }

    @Test
    public void getId() throws Exception {
        Assert.assertEquals(room.getId(), 19);
    }

    @Test
    public void getNumber() throws Exception {
        Assert.assertEquals((int) room.getNumber(), 112);
    }

    @Test
    public void getPosition() throws Exception {
        Assert.assertEquals(room.getPosition(), new Position(0, 32, 8, 6));
    }

    @Test
    public void getPeople() throws Exception {
        Assert.assertEquals(roomWithPeople.getPeople(), new ArrayList<Person>(people));
        Assert.assertEquals(roomWithPerson.getPeople().get(0), new ArrayList<Person>(people).get(0));
    }

    @Test
    public void toStringTest() throws Exception {
        Assert.assertEquals(roomWithPeople.toString(), "Room{number=106, title= , people=[Adam Satan]}");
    }

}