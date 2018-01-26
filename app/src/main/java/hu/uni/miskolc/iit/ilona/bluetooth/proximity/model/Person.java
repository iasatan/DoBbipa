package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.android.test.R;

/**
 * Created by iasatan on 2017.10.17..
 */

public class Person {
    //region variables
    private final Context context;
    private final Integer id;
    private final String name;
    private final Integer roomId;
    private final String title;
    private final Drawable image;
    private final Integer imageId;

    //endregion
    //region constructors
    public Person(Integer id, String name, Integer roomId, Integer image, String title, Context context) {
        this.id = id;
        this.context = context;
        this.name = name;
        this.roomId = roomId;
        this.image = context.getDrawable(image);
        this.title = title;
        imageId = image;
    }

    public Person(Integer id, String name, Integer roomId, Integer image, Context context) {
        this.id = id;
        this.context = context;
        this.name = name;
        this.roomId = roomId;
        this.image = context.getDrawable(image);
        title = "";
        imageId = image;
    }

    public Person(Integer id, String name, Integer roomId, Context context) {
        this.id = id;
        this.context = context;
        this.name = name;
        this.roomId = roomId;
        image = context.getDrawable(R.drawable.nf404);
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

    public Drawable getImage() {
        return image;
    }

    //endregion
    @Override
    public String toString() {
        return name;
    }
}
