package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

/**
 * Created by iasatan on 2018.01.22..
 */

public class Edge {
    private Integer id;
    private Position node1;
    private Position node2;
    private Double distance;

    public Edge() {
    }

    public Edge(Integer id, Position node1, Position node2, Double distance) {
        this.id = id;
        this.node1 = node1;
        this.node2 = node2;
        this.distance = distance;
    }

    public Edge(Integer id, Position node1, Position node2) {
        this.id = id;
        this.node1 = node1;
        this.node2 = node2;
        distance = node1.getDistance(node2);
    }

    public Position getNode1() {
        return node1;
    }

    public void setNode1(Position node1) {
        this.node1 = node1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Position getNode2() {
        return node2;
    }

    public void setNode2(Position node2) {
        this.node2 = node2;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
