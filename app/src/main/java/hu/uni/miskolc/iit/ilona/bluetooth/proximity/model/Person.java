package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

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
    private final Drawable image;
    private final Integer imageId;
    private final Integer buildingId;

    //endregion
    //region constructors
    public Person(Integer id, String name, Integer roomId, Integer image, String title, Context context, Integer buildingId) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.image = context.getDrawable(image);
        this.title = title;
        imageId = image;
        this.buildingId = buildingId;
    }

    public Person(Integer id, String name, Integer roomId, Integer image, Context context, Integer buildingId) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.image = context.getDrawable(image);
        title = "";
        imageId = image;
        this.buildingId = buildingId;
    }

    public Person(Integer id, String name, Integer roomId, Context context, Integer buildingId) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        image = context.getDrawable(R.drawable.nf404);
        title = "";
        imageId = R.drawable.nf404;
        this.buildingId = buildingId;
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

    public Drawable getImage() {
        return image;
    }

    public Integer getBuildingId() {
        return buildingId;
    }


    //endregion
    @Override
    public String toString() {
        return name;
    }
}
