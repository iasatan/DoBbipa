package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.android.test.R;

/**
 * Created by iasatan on 2017.10.17..
 */

public class Person {
    Context context;
    private Integer id;
    private String name;
    private Integer roomId;
    private Drawable image;
    private Integer imageId;
    private String title;

    public Person(Context context) {
        this.context = context;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = context.getDrawable(image);
    }

    @Override
    public String toString() {
        return name;
    }
}
