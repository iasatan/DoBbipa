package hu.uni.miskolc.iit.ilona.bluetooth.proximity.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.test.R;
import com.example.android.test.databinding.ActivitySearchBinding;

import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.DatabaseHandler;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter.SearchRecycleViewAdapter;
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
                searchRecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                activitySearchBinding.searchRecyclerView.setLayoutManager(searchRecyclerViewLayoutManager);
                List<SearchResult> results = SearchResult.search(searchTerm, db, getApplicationContext());
                searchRecyclerViewAdapter = new SearchRecycleViewAdapter(results);
                activitySearchBinding.searchRecyclerView.setAdapter(searchRecyclerViewAdapter);

            }
        });
    }

}
