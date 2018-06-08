package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import com.example.android.test.R;

import java.util.LinkedList;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NoPathFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.exception.NodeNotFoundException;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.util.DijkstraAlgorithm;

/**
 * Created by iasatan on 2018.03.23..
 */

public class Navigation {
    private int picture;
    private Double distance;
    private String nextPositionText;
    private Double totalDistance;
    private DijkstraAlgorithm dijkstraAlgorithm;
    private Room destination;
    private float correctionDegree;

    public Navigation(DijkstraAlgorithm dijkstraAlgorithm, Room destination) {
        this.dijkstraAlgorithm = dijkstraAlgorithm;
        this.destination = destination;
        distance = 1000.0;
    }

    public int getPicture() {
        return picture;
    }

    public Double getDistance() {
        return distance;
    }

    public String getNextPositionText() {
        return nextPositionText;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public float getCorrectionDegree() {
        return correctionDegree;
    }


    public void navigateDirectionBased(Position position) throws NodeNotFoundException, NoPathFoundException {
        if (position.equals(destination.getPosition()) || distance < 1) {
            arrivedAtDestination();
            return;
        }


        LinkedList<Position> path = dijkstraAlgorithm.getPath(position);
        Position nextPosition = path.get(path.size() - 2);

        if (path != null) {
            totalDistance = dijkstraAlgorithm.getTotalDistance(position);
            nextPositionText = nextPosition.toString();
            if (nextPosition.getY() == position.getY()) {
                if (nextPosition.getX() > position.getX()) {
                    correctionDegree = -145;
                } else {
                    correctionDegree = 35;
                }
                getDistanceSameY(position, path);

            } else if (nextPosition.getX() == position.getX()) {
                if (nextPosition.getY() > position.getY()) {
                    correctionDegree = -55;
                } else {
                    correctionDegree = 125;
                }
                getDistanceSameX(position, path);

            }

        }
    }

    private void arrivedAtDestination() {
        nextPositionText = Integer.toString(R.string.arrived);
        picture = R.drawable.arrived;
        distance = 0.0;
        totalDistance = 0.0;
    }


    public void navigateImageBased(Position position) throws NodeNotFoundException, NoPathFoundException {
        if (position.equals(destination.getPosition()) || distance < 1) {
            arrivedAtDestination();
            return;

        }

        LinkedList<Position> path = dijkstraAlgorithm.getPath(position);
        Position nextPosition = path.get(path.size() - 2);
        if (path != null) {
            totalDistance = dijkstraAlgorithm.getTotalDistance(position);
            nextPositionText = nextPosition.toString();
            if (position.getX() == nextPosition.getX()) {
                if (position.getY() < nextPosition.getY()) {
                    picture = dijkstraAlgorithm.getClosestPositionInPathWithPicture(nextPosition, Alignment.LEFT).getLeftId();
                } else {
                    picture = dijkstraAlgorithm.getClosestPositionInPathWithPicture(nextPosition, Alignment.RIGHT).getRightId();
                }

                getDistanceSameX(position, path);

            } else if (position.getY() == nextPosition.getY()) {
                if (position.getX() < nextPosition.getX()) {
                    picture = dijkstraAlgorithm.getClosestPositionInPathWithPicture(nextPosition, Alignment.BEHIND).getBehindId();
                } else {
                    picture = dijkstraAlgorithm.getClosestPositionInPathWithPicture(nextPosition, Alignment.FRONT).getFrontId();
                }

                getDistanceSameY(position, path);

            }
        }


    }


    private void getDistanceSameX(Position position, LinkedList<Position> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).getX() == position.getX()) {
                distance = Math.abs(path.get(i).getY() - position.getY());
                break;
            }
        }
    }

    private void getDistanceSameY(Position position, LinkedList<Position> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).getY() == position.getY()) {
                distance = Math.abs(path.get(i).getX() - position.getX());
                break;
            }
        }
    }
}
