package hu.uni.miskolc.iit.ilona.bluetooth.proximity.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoPathFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NodeNotFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Edge;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Position;

/**
 * Created by iasatan on 2018.01.25..
 */

public class DijkstraAlgorithm {
    private final List<Edge> edges;
    private Set<Position> settledNodes;
    private List<Position> unSettledNodes;
    private Map<Position, Position> predecessors;
    private Map<Position, Double> distances;

    public DijkstraAlgorithm(List<Edge> edges, List<Position> nodes) {
        this.edges = edges;
        unSettledNodes = new ArrayList<>(nodes);
    }

    public void buildShortestPaths(Position destination) throws NodeNotFoundException {
        settledNodes = new HashSet<>();
        distances = new HashMap<>();
        predecessors = new HashMap<>();
        Position position = null;
        for (Position position1 : unSettledNodes) {
            if (position1.equals(destination)) {
                position = position1;
                break;
            }
        }
        if (position == null) {
            throw new NodeNotFoundException();
        }
        distances.put(destination, 0.0);
        unSettledNodes.add(destination);
        while (unSettledNodes.size() > 0) {
            Position node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Position node) {
        List<Position> neighborNodes = getNeighbors(node);
        for (Position target : neighborNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distances.put(target, distances.get(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private Double getDistance(Position node, Position target) {
        for (Edge edge : edges) {
            if (edge.getNode1().equals(node) && edge.getNode2().equals(target) || edge.getNode2().equals(node) && edge.getNode1().equals(target)) {
                return edge.getDistance();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Position> getNeighbors(Position node) {
        List<Position> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getNode1().equals(node) && !isSettled(edge.getNode2())) {
                neighbors.add(edge.getNode2());
            } else if (edge.getNode2().equals(node) && !isSettled(edge.getNode1())) {
                neighbors.add(edge.getNode1());
            }
        }
        return neighbors;
    }

    private boolean isSettled(Position position) {
        return settledNodes.contains(position);
    }

    private Position getMinimum(List<Position> positions) {
        Position minimum = null;
        for (Position position : positions) {
            if (minimum == null) {
                minimum = position;
            } else {
                if (getShortestDistance(position) < getShortestDistance(minimum)) {
                    minimum = position;
                }
            }
        }
        return minimum;
    }

    private Double getShortestDistance(Position destination) {
        Double d = distances.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    /**
     * Returns the path to the source node from the destination node set in buildShortestPaths*
     *
     * @param source
     * @return
     * @throws NodeNotFoundException
     * @throws NoPathFoundException
     */
    public LinkedList<Position> getPath(Position source) throws NodeNotFoundException, NoPathFoundException {
        LinkedList<Position> path = new LinkedList<>();
        Position step = null;
        for (Position position1 : settledNodes) {
            if (position1.equals(source)) {
                step = position1;
                break;
            }
        }
        if (step == null) {
            throw new NodeNotFoundException();
        }
        if (predecessors.get(step) == null) {
            throw new NoPathFoundException();
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
