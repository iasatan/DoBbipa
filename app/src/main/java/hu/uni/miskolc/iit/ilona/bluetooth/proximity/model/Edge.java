package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

/**
 * Created by iasatan on 2018.01.22..
 */

public class Edge {
    //region variables
    private final Integer id;
    private final Position node1;
    private final Position node2;
    private final Double distance;

    //endregion
    //region constructors

    public Edge(Integer id, Position node1, Position node2) {
        this.id = id;
        this.node1 = node1;
        this.node2 = node2;
        distance = node1.getDistance(node2);
    }

    //endregion
    //region getters & setters
    public Position getNode1() {
        return node1;
    }

    public Integer getId() {
        return id;
    }

    public Position getNode2() {
        return node2;
    }

    public Double getDistance() {
        return distance;
    }

    //endregion


    @Override
    public String toString() {
        return "Edge{" +
                "node1=" + node1 +
                ", node2=" + node2 +
                ", distance=" + distance +
                '}';
    }
}
