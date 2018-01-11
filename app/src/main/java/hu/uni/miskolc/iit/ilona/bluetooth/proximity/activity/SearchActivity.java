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
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter.SearchRecycleViewAdapter;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.SearchResult;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding activitySearchBinding;
    private String searchTerm;
    private DatabaseHandler db;
    private RecyclerView.Adapter searchRecyclerViewAdapter;
    private RecyclerView.LayoutManager searchRecyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(getApplicationContext());//, getString(R.string.databaseName), 1);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        activitySearchBinding.seachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTerm = activitySearchBinding.searchText.getText().toString();
                activitySearchBinding.searchRecyclerView.setHasFixedSize(true);
                activitySearchBinding.searchRecyclerView.setHasFixedSize(true);
                searchRecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                activitySearchBinding.searchRecyclerView.setLayoutManager(searchRecyclerViewLayoutManager);
                if (!NumberUtils.isCreatable(searchTerm)) {
                    List<Person> people = db.getAllPeople();
                    List<Room> rooms = db.getAllRoom();
                    List<SearchResult> results = new ArrayList<>();
                    for (Person person : people) {
                        if (StringUtils.containsIgnoreCase(person.getName(), searchTerm)) {
                            results.add(new SearchResult(person.getImage(), person.getName(), person.getTitle(), person.getRoomId()));
                        }
                    }
                    for (Room room : rooms) {
                        if (StringUtils.containsIgnoreCase(room.getTitle(), searchTerm)) {
                            results.add(new SearchResult(getApplicationContext().getDrawable(R.drawable.nf404), room.getTitle(), room.getNumber().toString(), room.getId()));
                        }
                    }
                    if (results.size() == 0) {
                        results.add(new SearchResult(getApplicationContext().getDrawable(R.drawable.nf404), getString(R.string.noResult), "", 0));
                    }
                    searchRecyclerViewAdapter = new SearchRecycleViewAdapter(results);


                } else {
                    Integer number = Integer.valueOf(searchTerm);
                    Room room = db.getRoomByNumber(number);
                    searchRecyclerViewAdapter = new SearchPersonRecycleViewAdapter(room.getPeople());
                }
                activitySearchBinding.searchRecyclerView.setAdapter(searchRecyclerViewAdapter);

            }
        });
    }

}
