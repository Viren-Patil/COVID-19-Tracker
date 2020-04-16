package com.example.covid_19tracker.ui.timeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19tracker.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IndiaTimelineAdapter extends RecyclerView.Adapter<IndiaTimelineAdapter.ViewHolder> {

    ArrayList<IndiaTimeline> indiaTimelines;

    public IndiaTimelineAdapter(ArrayList<IndiaTimeline> indiaTimelines){
        this.indiaTimelines = indiaTimelines;
    }

    @NonNull
    @Override
    public IndiaTimelineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_timeline, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndiaTimelineAdapter.ViewHolder holder, int position) {
        IndiaTimeline indiaTimeline = indiaTimelines.get(position);
        holder.tvDate.setText(indiaTimeline.getmDate());
        holder.tvTotalTimelineConfirm.setText(indiaTimeline.getmTotalTimelineConfirm());
        holder.tvTotalTimelineDeaths.setText(indiaTimeline.getmTotalTimelineDeaths());
    }

    @Override
    public int getItemCount() {
        return indiaTimelines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTotalTimelineConfirm, tvTotalTimelineDeaths;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotalTimelineConfirm = itemView.findViewById(R.id.tvTotalTimelineConfirm);
            tvTotalTimelineDeaths = itemView.findViewById(R.id.tvTotalTimelineDeaths);
        }
    }
}
