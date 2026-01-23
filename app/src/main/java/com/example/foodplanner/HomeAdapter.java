package com.example.foodplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.model.*;
import com.example.foodplanner.R;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MealViewHolder> {

    private List<Meal> meals = new ArrayList<>();

    public void updateMeals(List<Meal> newMeals) {
        this.meals.clear();
        this.meals.addAll(newMeals);
        notifyDataSetChanged();
    }

    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.example.foodplanner.R.layout.home_meals_card,parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);

        /*holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMealClick(meal);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(com.example.foodplanner.R.id.mealTitle);
        }

        public void bind(Meal meal) {
            mealName.setText(meal.getName());

            Glide.with(mealImage.getContext())
                    .load(meal.getImageUrl())  // strMealThumb
                    .centerCrop()
                    .placeholder(com.example.foodplanner.R.drawable.placeholder_food)
                    .into(mealImage);
        }
    }
}