package com.example.foodplanner.view.favorite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private List<Meal> meals;
    private OnRemoveClickListener listener;

    public interface OnRemoveClickListener {
        void onRemoveClick(Meal meal);
    }

    public FavoriteAdapter(Context context, OnRemoveClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.meals = new ArrayList<>();
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.tvName.setText(meal.getName());
        Glide.with(context).load(meal.getImageUrl()).placeholder(R.drawable.ic_launcher_background).into(holder.ivMeal);

        holder.itemView.setOnClickListener(v->{
            NavDirections action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(meal.getId());
            Navigation.findNavController(v).navigate(action);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemoveClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMeal;
        TextView tvName;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.ivMeal);
            tvName = itemView.findViewById(R.id.tvName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
