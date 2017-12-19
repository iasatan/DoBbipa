package hu.uni.miskolc.iit.ilona.bluetooth.proximity.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.test.R;
import com.example.android.test.databinding.ActivitySearchBinding;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.DatabaseHandler;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter.SearchPersonRecycleViewAdapter;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter.SearchRoomRecycleViewAdapter;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding activitySearchBinding;
    private String searchTerm;
    private DatabaseHandler db;
    private RecyclerView.Adapter peopleRecyclerViewAdapter;
    private RecyclerView.Adapter roomRecyclerViewAdapter;
    private RecyclerView.LayoutManager peopleRecyclerViewLayoutManager;
    private RecyclerView.LayoutManager roomRecyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(getApplicationContext());//, getString(R.string.databaseName), 1);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        activitySearchBinding.seachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTerm = activitySearchBinding.searchText.getText().toString();
                activitySearchBinding.searchPeopleRecyclerView.setHasFixedSize(true);
                activitySearchBinding.searchRoomRecyclerView.setHasFixedSize(true);
                peopleRecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                roomRecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                activitySearchBinding.searchPeopleRecyclerView.setLayoutManager(peopleRecyclerViewLayoutManager);
                activitySearchBinding.searchRoomRecyclerView.setLayoutManager(roomRecyclerViewLayoutManager);
                if (!NumberUtils.isCreatable(searchTerm)) {
                    List<Person> people = db.getAllPeople();
                    List<Person> searchPeople = new ArrayList<>();
                    List<Room> rooms = db.getAllRoom();
                    List<Room> searchRooms = new ArrayList<>();
                    for (Person person : people) {
                        if (StringUtils.containsIgnoreCase(person.getName(), searchTerm)) {
                            searchPeople.add(person);
                        }
                    }
                    for (Room room : rooms) {
                        if (StringUtils.containsIgnoreCase(room.getTitle(), searchTerm)) {
                            searchRooms.add(room);
                        }
                    }
                    if (searchPeople.size() == 0 && searchRooms.size() == 0) {
                        searchPeople.add(new Person(0, getString(R.string.nothingFound), 0, R.drawable.nf404, "", getApplicationContext()));
                    }
                    peopleRecyclerViewAdapter = new SearchPersonRecycleViewAdapter(searchPeople);
                    if (searchRooms.size() != 0) {
                        roomRecyclerViewAdapter = new SearchRoomRecycleViewAdapter(searchRooms);
                        activitySearchBinding.searchRoomRecyclerView.setAdapter(roomRecyclerViewAdapter);
                    }

                } else {
                    Integer number = Integer.valueOf(searchTerm);
                    Room room = db.getRoomByNumber(number);
                    peopleRecyclerViewAdapter = new SearchPersonRecycleViewAdapter(room.getPeople());
                }
                activitySearchBinding.searchPeopleRecyclerView.setAdapter(peopleRecyclerViewAdapter);

            }
        });
    }

}
