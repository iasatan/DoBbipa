package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

/**
 * Created by iasatan on 2017.10.17..
 */

public class Person {
    //region variables
    private final Integer id;
    private final String name;
    private final Integer roomId;
    private final String title;
    private final Integer imageId;

    //endregion
    public Person(Integer id, String name, Integer roomId, Integer image, String title) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.title = title;
        imageId = image;
    }

    //region getters & setters
    public String getTitle() {
        return title;
    }

    public Integer getImageId() {
        return imageId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRoomId() {
        return roomId;
    }


    //endregion
    @Override
    public String toString() {
        return name;
    }
}
