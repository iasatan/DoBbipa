package hu.uni.miskolc.iit.ilona.bluetooth.proximity.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.test.BR;
import com.example.android.test.R;
import com.example.android.test.databinding.ResidentsItemBinding;

import java.util.List;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;

/**
 * Created by iasatan on 2017.12.08..
 */

public class ResidentsRecycleViewAdapter extends RecyclerView.Adapter<ResidentsRecycleViewAdapter.ResidentViewHolder> {
    private List<Person> people;

    public ResidentsRecycleViewAdapter(List<Person> people) {
        this.people = people;
    }

    @Override
    public ResidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.residents_item, parent, false);
        ResidentViewHolder holder = new ResidentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ResidentViewHolder holder, int position) {
        final Person person = people.get(position);
        holder.getBinding().setVariable(BR.person, person);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public static class ResidentViewHolder extends RecyclerView.ViewHolder {
        private ResidentsItemBinding residentsItemBinding;

        public ResidentViewHolder(View itemView) {
            super(itemView);
            residentsItemBinding = DataBindingUtil.bind(itemView);
        }

        public ResidentsItemBinding getBinding() {
            return residentsItemBinding;
        }
    }

}
