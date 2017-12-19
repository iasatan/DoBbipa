package hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.test.BR;
import com.example.android.test.R;
import com.example.android.test.databinding.SearchRoomItemBinding;

import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.activity.NavigationActivity;
import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Room;

/**
 * Created by iasatan on 2017.12.08..
 */

public class SearchRoomRecycleViewAdapter extends RecyclerView.Adapter<SearchRoomRecycleViewAdapter.RoomsViewHolder> {
    private final List<Room> rooms;

    public SearchRoomRecycleViewAdapter(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public RoomsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_room_item, parent, false);
        return new RoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomsViewHolder holder, int position) {
        final Room room = rooms.get(position);
        holder.getBinding().setVariable(BR.room, room);
        holder.getBinding().executePendingBindings();
        holder.getBinding().startNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NavigationActivity.class);
                intent.putExtra("room", room.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class RoomsViewHolder extends RecyclerView.ViewHolder {
        private final SearchRoomItemBinding searchRoomItemBinding;

        public RoomsViewHolder(View itemView) {
            super(itemView);
            searchRoomItemBinding = DataBindingUtil.bind(itemView);
        }

        public SearchRoomItemBinding getBinding() {
            return searchRoomItemBinding;
        }
    }

}
