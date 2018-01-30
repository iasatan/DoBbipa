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
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.SearchResult;

/**
 * Created by iasatan on 2017.12.08..
 */

public class SearchRecycleViewAdapter extends RecyclerView.Adapter<SearchRecycleViewAdapter.RoomsViewHolder> {
    private final List<SearchResult> results;

    public SearchRecycleViewAdapter(List<SearchResult> results) {
        this.results = results;
    }

    @Override
    public RoomsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new RoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomsViewHolder holder, int position) {
        final SearchResult result = results.get(position);
        holder.getBinding().setVariable(BR.result, result);
        holder.getBinding().executePendingBindings();
        if (result.getRoomId() != 0) {

            holder.getBinding().startNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), NavigationActivity.class);
                    intent.putExtra("room", result.getRoomId());
                    view.getContext().startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class RoomsViewHolder extends RecyclerView.ViewHolder {
        private final SearchItemBinding searchItemBinding;

        public RoomsViewHolder(View itemView) {
            super(itemView);
            searchItemBinding = DataBindingUtil.bind(itemView);
        }

        public SearchItemBinding getBinding() {
            return searchItemBinding;
        }
    }

}
