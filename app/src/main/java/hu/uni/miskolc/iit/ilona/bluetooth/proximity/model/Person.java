package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import com.example.android.test.R;

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
    //region constructors
    public Person(Integer id, String name, Integer roomId, Integer image, String title) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.title = title;
        imageId = image;
    }

    public Person(Integer id, String name, Integer roomId, Integer image) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        title = "";
        imageId = image;
    }

    public Person(Integer id, String name, Integer roomId) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        title = "";
        imageId = R.drawable.nf404;
    }


    //endregion
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
