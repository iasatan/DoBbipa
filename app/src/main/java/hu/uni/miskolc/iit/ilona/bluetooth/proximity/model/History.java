package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

/**
 * Created by iasatan on 2018.01.30..
 */

public class History {
    private final Integer id;
    private final Integer groupId;
    private final String name;
    private final Long date;
    private final Integer position;
    private final Float direction;

    public History(Integer id, Integer groupId, String name, Integer position, Float direction, Long date) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.date = date;
        this.position = position;
        this.direction = direction;
    }

    public History(Integer id, String name, Integer position, Float direction) {
        this.id = id;
        groupId = 0;
        this.name = name;
        date = Long.valueOf(0);
        this.position = position;
        this.direction = direction;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public Long getDate() {
        return date;
    }

    public Integer getPosition() {
        return position;
    }

    public Float getDirection() {
        return direction;
    }
}
