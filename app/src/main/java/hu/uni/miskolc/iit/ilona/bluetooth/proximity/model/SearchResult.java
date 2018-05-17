package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.android.test.R;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.DatabaseHandler;

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

    public static List<SearchResult> search(String searchTerm, DatabaseHandler db, Context context) {
        List<SearchResult> results = new ArrayList<>();
        if (!NumberUtils.isCreatable(searchTerm)) {
            List<Person> people = db.getAllPeople();
            List<Room> rooms = db.getAllRoom();
            for (Person person : people) {
                if (StringUtils.containsIgnoreCase(person.getName(), searchTerm)) {
                    results.add(new SearchResult(context.getDrawable(person.getImageId()), person.getName(), person.getTitle(), person.getRoomId()));
                }
            }
            for (Room room : rooms) {
                if (StringUtils.containsIgnoreCase(room.getTitle(), searchTerm)) {
                    results.add(new SearchResult(context.getDrawable(R.drawable.nf404), room.getTitle(), room.getNumber().toString(), room.getId()));
                }
            }
            if (results.size() == 0) {
                results.add(new SearchResult(context.getDrawable(R.drawable.nf404), context.getString(R.string.noResult), "", 0));
            }


        } else {
            Integer number = Integer.valueOf(searchTerm);
            Room room = db.getRoomByNumber(number);
            results.add(new SearchResult(context.getDrawable(R.drawable.nf404), room.getTitle(), room.getNumber().toString(), room.getId()));
            for (Person person : room.getPeople()) {
                results.add(new SearchResult(context.getDrawable(person.getImageId()), person.getName(), person.getTitle(), person.getRoomId()));
            }

            if (results.size() == 0) {
                results.add(new SearchResult(context.getDrawable(R.drawable.nf404), room.getNumber().toString(), room.getTitle(), 0));
            }
        }
        return results;
    }

    public static List<SearchResult> searchResultListFromPersonList(List<Person> people, Context context, Room room) {
        List<SearchResult> results = new ArrayList<>();
        for (Person person : people) {
            results.add(new SearchResult(context.getDrawable(person.getImageId()), person.getName(), person.getTitle(), room.getId()));
        }
        return results;
    }

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


}
