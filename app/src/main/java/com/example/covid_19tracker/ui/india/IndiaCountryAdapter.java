package com.example.covid_19tracker.ui.india;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19tracker.R;

import java.util.ArrayList;
import java.util.List;

public class IndiaCountryAdapter extends RecyclerView.Adapter<IndiaCountryAdapter.ViewHolder>{

    private List<IndiaCountry> indiaCountries;
    private List<IndiaCountry> indiaCountriesFull;

    private Context context;

    public IndiaCountryAdapter(List<IndiaCountry> indiaCountries, Context context) {
        this.indiaCountries = indiaCountries;
        this.context = context;
        indiaCountriesFull = new ArrayList<>(indiaCountries);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_india_country, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndiaCountryAdapter.ViewHolder holder, int position) {
        IndiaCountry indiaCountry = indiaCountries.get(position);
        holder.tvConfirmed.setText(indiaCountry.getmConfirmed());
        holder.tvState.setText(indiaCountry.getmState());

    }

    @Override
    public int getItemCount() {
        return indiaCountries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvConfirmed, tvState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvConfirmed = itemView.findViewById(R.id.tvConfirmed);
            tvState = itemView.findViewById(R.id.tvState);
        }
    }


}
