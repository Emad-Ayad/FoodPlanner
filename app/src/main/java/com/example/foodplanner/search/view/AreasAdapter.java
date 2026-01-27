package com.example.foodplanner.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Country;

import java.util.ArrayList;
import java.util.List;

public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.AreaViewHolder> {

    private List<Country> countries = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAreaClick(Country country);
    }

    public AreasAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingreadans_item, parent, false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.tvArea.setText(country.getName());
        holder.itemView.setOnClickListener(v -> listener.onAreaClick(country));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView tvArea;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArea = (TextView) itemView; // ingreadans_item is just a TextView
        }
    }
}
