package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.graphics.drawable.Drawable;

/**
 * Created by iasatan on 2018.01.11..
 * Helper class for the search bar. Serializes the results, therefore only one RecycleViewAdapter is enough
 */

public class SearchResult {
    //region variables
    private final Drawable image;
    private final String name;
    private final String title;
    private final Integer roomId;

    //endregion
    public SearchResult(Drawable image, String name, String title, Integer roomId) {
        this.image = image;
        this.name = name;
        this.title = title;
        this.roomId = roomId;
    }

    //region getters & setters
    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Integer getRoomId() {
        return roomId;
    }

    //endregion
}
