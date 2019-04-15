package com.colorhaake.traveler.view.home;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colorhaake.traveler.R;
import com.colorhaake.traveler.plain_object.ActivityGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by josephcheng on 2017/3/25.
 */
public class ActivityGroupAdapter extends
        RecyclerView.Adapter<ActivityGroupAdapter.ActivityGroupViewHolder> {

    public static final String TAG = ActivityGroupAdapter.class.getName();
    private List<ActivityGroup> list;

    public ActivityGroupAdapter(List<ActivityGroup> list) {
        this.list = list;
    }

    @Override
    public ActivityGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActivityGroupViewHolder(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_event_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ActivityGroupViewHolder holder, int position) {
        final ActivityGroup item = list.get(position);
        holder.nameView.setText(item.class_name);

        final ActivityEventAdapter adapter = new ActivityEventAdapter(
                item.items
        );
        holder.activityEventView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(list.get(position).id);
    }

    public void setActivityGroups(List<ActivityGroup> list) {
        List<ActivityGroup> oldList = this.list;
        this.list = list;
        DiffUtil.calculateDiff(new ActivityGroupDiffCallback(oldList, list), false)
                .dispatchUpdatesTo(this);
    }

    static class ActivityGroupViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.activity_event_list_name) TextView nameView;
        @BindView(R.id.activity_event_list) RecyclerView activityEventView;

        public ActivityGroupViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            // for scrolling animation snap effect
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(activityEventView);
        }
    }
}
