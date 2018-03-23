package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import com.example.android.test.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoPathFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NodeNotFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.util.DijkstraAlgorithm;

/**
 * Created by iasatan on 2018.03.23..
 */
public class NavigationTest {


    Navigation navigation;
    private List<Position> nodes;
    private List<Edge> edges;
    private DijkstraAlgorithm dijkstra;


    @Before
    public void setUp() throws Exception {
        nodes = new ArrayList<>();
        Map<Integer, Position> positions = new HashMap<>();
        positions.put(1, new Position(1, 35, 20, 6, "101 előtt", R.drawable.p3520ek, R.drawable.p3520r, R.drawable.p3520dny, 0));
        positions.put(2, new Position(2, 35, 8, 6, "KL előtt", R.drawable.p358ek, 0, R.drawable.p358dny, R.drawable.p358eny));
        positions.put(3, new Position(3, 18, 8, 6, "Konyha előtt"));
        positions.put(4, new Position(4, 6, 8, 6, "labor előtt"));
        positions.put(5, new Position(5, 5, 8, 6));
        positions.put(6, new Position(6, 7, 8, 6));
        positions.put(7, new Position(7, 13, 8, 6));
        positions.put(8, new Position(8, 17, 8, 6));
        positions.put(9, new Position(9, 8, 8, 6, "Elzárt folyosó labornál lévő ajtaja", R.drawable.p88ek, 0, R.drawable.p88dny, R.drawable.p88eny));
        positions.put(10, new Position(10, 8, 20, 6, R.drawable.p820ek, R.drawable.p820dk, R.drawable.p820dny, 0));
        positions.put(11, new Position(11, 35, 12, 6, "lépcső", R.drawable.stairs, R.drawable.p3512dk, 0, R.drawable.p3512eny));
        positions.put(12, new Position(12, 8, 10, 6));
        positions.put(13, new Position(13, 8, 15, 6, 0, R.drawable.p815r, 0, R.drawable.p815l));
        positions.put(14, new Position(14, 7, 20, 6));
        positions.put(15, new Position(15, 23, 20, 6));
        positions.put(16, new Position(16, 11, 20, 6));
        positions.put(17, new Position(17, 12, 20, 6));
        positions.put(18, new Position(18, 19, 20, 6));
        positions.put(19, new Position(19, 21, 20, 6, R.drawable.p2120f, 0, R.drawable.p2120b, 0));
        positions.put(20, new Position(20, 28, 20, 6, R.drawable.p2820f, 0, R.drawable.p2820b, 0));
        positions.put(21, new Position(21, 33, 20, 6));
        positions.put(22, new Position(22, 39, 20, 6, R.drawable.p3920f, 0, 0, 0));
        positions.put(23, new Position(23, 39, 8, 6));
        positions.put(24, new Position(24, 36, 8, 6));
        positions.put(25, new Position(25, 32, 8, 6));
        positions.put(27, new Position(27, 28, 8, 6));
        positions.put(26, new Position(26, 29, 8, 6, 0, 0, R.drawable.p298dny, 0));
        positions.put(28, new Position(28, 23, 8, 6));
        positions.put(29, new Position(29, 21, 8, 6, R.drawable.p218f, 0, R.drawable.p218b, 0));
        positions.put(30, new Position(30, 14, 8, 6, R.drawable.p148f, 0, R.drawable.p148f, 0));
        positions.put(31, new Position(31, 15, 8, 6));
        positions.put(32, new Position(32, 6, 20, 6));
        positions.put(33, new Position(33, 15, 20, 6, R.drawable.p1520f, 0, R.drawable.p1520b, 0));
        for (Map.Entry<Integer, Position> position : positions.entrySet()) {
            nodes.add(position.getValue());
        }
        edges = new ArrayList<>();
        edges.add(new Edge(1, positions.get(22), positions.get(1)));
        edges.add(new Edge(2, positions.get(1), positions.get(21)));
        edges.add(new Edge(3, positions.get(21), positions.get(20)));
        edges.add(new Edge(4, positions.get(20), positions.get(15)));
        edges.add(new Edge(5, positions.get(15), positions.get(19)));
        edges.add(new Edge(6, positions.get(19), positions.get(18)));
        edges.add(new Edge(7, positions.get(18), positions.get(17)));
        edges.add(new Edge(8, positions.get(17), positions.get(16)));
        edges.add(new Edge(9, positions.get(16), positions.get(10)));
        edges.add(new Edge(10, positions.get(10), positions.get(14)));
        edges.add(new Edge(11, positions.get(14), positions.get(32)));
        edges.add(new Edge(12, positions.get(10), positions.get(13)));
        edges.add(new Edge(13, positions.get(13), positions.get(12)));
        edges.add(new Edge(14, positions.get(12), positions.get(9)));
        edges.add(new Edge(15, positions.get(9), positions.get(6)));
        edges.add(new Edge(16, positions.get(6), positions.get(4)));
        edges.add(new Edge(17, positions.get(4), positions.get(5)));
        edges.add(new Edge(18, positions.get(9), positions.get(7)));
        edges.add(new Edge(19, positions.get(30), positions.get(7)));
        edges.add(new Edge(20, positions.get(30), positions.get(31)));
        edges.add(new Edge(21, positions.get(31), positions.get(8)));
        edges.add(new Edge(22, positions.get(8), positions.get(3)));
        edges.add(new Edge(23, positions.get(3), positions.get(29)));
        edges.add(new Edge(24, positions.get(29), positions.get(28)));
        edges.add(new Edge(25, positions.get(28), positions.get(27)));
        edges.add(new Edge(26, positions.get(26), positions.get(27)));
        edges.add(new Edge(27, positions.get(26), positions.get(25)));
        edges.add(new Edge(28, positions.get(25), positions.get(2)));
        edges.add(new Edge(29, positions.get(2), positions.get(24)));
        edges.add(new Edge(30, positions.get(24), positions.get(23)));
        edges.add(new Edge(31, positions.get(11), positions.get(2)));
        edges.add(new Edge(32, positions.get(11), positions.get(1)));
        Room room = new Room(24, 106, positions.get(4));
        dijkstra = new DijkstraAlgorithm(edges, nodes);
        dijkstra.buildShortestPaths(room.getPosition());
        navigation = new Navigation(dijkstra, room);
    }

    @Test
    public void getPicture() throws Exception {
        navigation.navigateImageBased(new Position(6, 7, 8, 6));
        int picture = navigation.getPicture();
        int expected = R.drawable.p88ek;
        Assert.assertEquals(expected, picture);
    }

    @Test
    public void getDistance() throws Exception {
        navigation.navigateImageBased(new Position(6, 7, 8, 6));
        double distance = navigation.getDistance();
        double expected = 1.0;
        Assert.assertEquals(expected, distance, 0.001);
    }

    @Test
    public void getDistace2() throws NodeNotFoundException, NoPathFoundException {
        navigation.navigateImageBased(new Position(30, 8, 20, 6));
        double distance = navigation.getDistance();
        double expected = 12.0;
        Assert.assertEquals(expected, distance, 0.001);
    }

    @Test
    public void getCorrectionDegree() throws Exception {
        navigation.navigateDirectionBased(new Position(30, 8, 20, 6));
        float correctionDegree = navigation.getCorrectionDegree();
        float expected = 125;
        Assert.assertEquals(expected, correctionDegree, 0.01);
    }

    @Test
    public void getNextPositionText() throws Exception {
        navigation.navigateImageBased(new Position(6, 7, 8, 6));
        String nextPositionText = navigation.getNextPositionText();
        String expected = new Position(4, 6, 8, 6).toString();
        Assert.assertEquals(expected, nextPositionText);
    }

    @Test
    public void getTotalDistance() throws Exception {
        navigation.navigateImageBased(new Position(6, 7, 8, 6));
        double distance = navigation.getTotalDistance();
        double expected = 1.0;
        Assert.assertEquals(expected, distance, 0.001);
    }

    @Test
    public void testArrival() throws NodeNotFoundException, NoPathFoundException {
        navigation.navigateImageBased(new Position(4, 6, 8, 6));
        double distance = navigation.getDistance();
        double expectedDistance = 0.0;
        Assert.assertEquals(expectedDistance, distance, 0.001);

        int picture = navigation.getPicture();
        int expectedPicture = R.drawable.arrived;
        Assert.assertEquals(expectedPicture, picture);

        int nextPositionText = Integer.parseInt(navigation.getNextPositionText());
        int expectedNextPositionText = R.string.arrived;
        Assert.assertEquals(expectedNextPositionText, nextPositionText);
    }

}