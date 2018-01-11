package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.graphics.drawable.Drawable;

/**
 * Created by iasatan on 2018.01.11..
 */

public class SearchResult {

    private Drawable image;
    private String name;
    private String title;
    private Integer roomId;

    public SearchResult(Drawable image, String name, String title, Integer roomId) {
        this.image = image;
        this.name = name;
        this.title = title;
        this.roomId = roomId;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
