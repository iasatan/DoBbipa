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
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter.ResidentsRecycleViewAdapter;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.SearchBindingHelper;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding activitySearchBinding;
    String searchTerm;
    SearchBindingHelper searchBindingHelper;
    DatabaseHandler db;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(getApplicationContext(), "dobbipa34", 1);
        searchBindingHelper = new SearchBindingHelper();
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        activitySearchBinding.seachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTerm = activitySearchBinding.searchText.getText().toString();
                activitySearchBinding.residentsRecyclerView.setHasFixedSize(true);
                recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                activitySearchBinding.residentsRecyclerView.setLayoutManager(recyclerViewLayoutManager);
                if (!NumberUtils.isCreatable(searchTerm)) {
                    List<Person> people = db.getAllPeople();
                    List<Person> searchPerson = new ArrayList<>();
                    for (Person person : people) {
                        if (StringUtils.containsIgnoreCase(person.getName(), searchTerm)) {
                            searchPerson.add(person);
                        }
                    }
                    recyclerViewAdapter = new ResidentsRecycleViewAdapter(searchPerson);

                } else {
                    Integer number = Integer.valueOf(searchTerm);
                    Room room = db.getRoomByNumber(number);
                    recyclerViewAdapter = new ResidentsRecycleViewAdapter(room.getPeople());
                }
                activitySearchBinding.residentsRecyclerView.setAdapter(recyclerViewAdapter);

            }
        });
    }

}
