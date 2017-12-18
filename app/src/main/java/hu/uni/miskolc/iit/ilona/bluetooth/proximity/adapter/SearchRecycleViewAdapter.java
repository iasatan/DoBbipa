package hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.test.BR;
import com.example.android.test.R;
import com.example.android.test.databinding.SearchItemBinding;

import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.activity.NavigationActivity;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;

/**
 * Created by iasatan on 2017.12.08..
 */

public class SearchRecycleViewAdapter extends RecyclerView.Adapter<SearchRecycleViewAdapter.ResidentViewHolder> {
    private List<Person> people;

    public SearchRecycleViewAdapter(List<Person> people) {
        this.people = people;
    }

    @Override
    public ResidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        ResidentViewHolder holder = new ResidentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ResidentViewHolder holder, int position) {
        final Person person = people.get(position);
        holder.getBinding().setVariable(BR.person, person);
        holder.getBinding().executePendingBindings();
        holder.getBinding().startNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NavigationActivity.class);
                intent.putExtra("room", person.getRoomId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public static class ResidentViewHolder extends RecyclerView.ViewHolder {
        private SearchItemBinding searchItemBinding;

        public ResidentViewHolder(View itemView) {
            super(itemView);
            searchItemBinding = DataBindingUtil.bind(itemView);
        }

        public SearchItemBinding getBinding() {
            return searchItemBinding;
        }
    }

}
