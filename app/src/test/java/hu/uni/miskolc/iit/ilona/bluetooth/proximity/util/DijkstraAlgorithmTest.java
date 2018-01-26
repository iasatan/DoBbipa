package hu.uni.miskolc.iit.ilona.bluetooth.proximity.util;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoPathFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NodeNotFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Edge;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.SecurityClearance;

/**
 * Created by iasatan on 2018.01.25..
 */
public class DijkstraAlgorithmTest {
    private List<Position> nodes;
    private List<Edge> edges;

    @Test
    public void execute() throws NodeNotFoundException, NoPathFoundException {
        nodes = new ArrayList<>();
        Map<Integer, Position> positions = new HashMap<>();
        positions.put(1, new Position(1, 35, 20, 6, "101 előtt"));
        positions.put(2, new Position(2, 35, 8, 6, "KL előtt"));
        positions.put(3, new Position(3, 18, 8, 6, SecurityClearance.LEVEl1, "Konyha előtt"));
        positions.put(4, new Position(4, 6, 8, 6, SecurityClearance.LEVEl1, "labor előtt"));
        positions.put(5, new Position(5, 5, 8, 6, SecurityClearance.LEVEl1));
        positions.put(6, new Position(6, 7, 8, 6, SecurityClearance.LEVEl1));
        positions.put(7, new Position(7, 13, 8, 6, SecurityClearance.LEVEl1));
        positions.put(8, new Position(8, 17, 8, 6, SecurityClearance.LEVEl1));
        positions.put(9, new Position(9, 8, 8, 6, SecurityClearance.LEVEl1, "Elzárt folyosó labornál lévő ajtaja"));
        positions.put(10, new Position(10, 8, 20, 6));
        positions.put(11, new Position(11, 35, 12, 6, "lépcső"));
        positions.put(12, new Position(12, 8, 10, 6));
        positions.put(13, new Position(13, 8, 15, 6));
        positions.put(14, new Position(14, 7, 20, 6));
        positions.put(15, new Position(15, 23, 20, 6));
        positions.put(16, new Position(16, 11, 20, 6));
        positions.put(17, new Position(17, 12, 20, 6));
        positions.put(18, new Position(18, 19, 20, 6));
        positions.put(19, new Position(19, 21, 20, 6));
        positions.put(20, new Position(20, 28, 20, 6));
        positions.put(21, new Position(21, 33, 20, 6));
        positions.put(22, new Position(22, 39, 20, 6));
        positions.put(23, new Position(23, 39, 8, 6));
        positions.put(24, new Position(24, 36, 8, 6));
        positions.put(25, new Position(25, 32, 8, 6));
        positions.put(27, new Position(27, 28, 8, 6, SecurityClearance.LEVEl1));
        positions.put(26, new Position(26, 29, 8, 6, SecurityClearance.LEVEl1));
        positions.put(28, new Position(28, 23, 8, 6, SecurityClearance.LEVEl1));
        positions.put(29, new Position(29, 21, 8, 6, SecurityClearance.LEVEl1));
        positions.put(30, new Position(30, 14, 8, 6, SecurityClearance.LEVEl1));
        positions.put(31, new Position(31, 15, 8, 6, SecurityClearance.LEVEl1));
        positions.put(32, new Position(32, 6, 20, 6));
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
        edges.add(new Edge(26, positions.get(28), positions.get(27)));
        edges.add(new Edge(27, positions.get(26), positions.get(25)));
        edges.add(new Edge(28, positions.get(25), positions.get(2)));
        edges.add(new Edge(29, positions.get(2), positions.get(24)));
        edges.add(new Edge(30, positions.get(24), positions.get(23)));
        edges.add(new Edge(31, positions.get(11), positions.get(2)));
        edges.add(new Edge(32, positions.get(11), positions.get(1)));
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(edges, nodes);
        dijkstra.buildShortestPaths(new Position(0, 6, 8, 6));
        LinkedList<Position> path = dijkstra.getPath(new Position(0, 8, 15, 4.4));

        Assert.assertTrue(path.size() > 0);

        for (Position position : path) {
            System.out.println(position);
        }
    }
}