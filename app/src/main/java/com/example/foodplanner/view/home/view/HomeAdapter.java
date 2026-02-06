package com.example.foodplanner.view.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MealViewHolder> {

    private List<Meal> meals = new ArrayList<>();
    private boolean isGuest = false;

    private OnFavClickListener favListener;


    public interface OnFavClickListener {
        void onFavClick(Meal meal);
    }

    HomeAdapter(OnFavClickListener favListener, boolean isGuest) {
        this.favListener = favListener;
        this.isGuest = isGuest;
    }

    public void updateMeals(List<Meal> newMeals) {
        this.meals.clear();
        this.meals.addAll(newMeals);
        notifyDataSetChanged();
    }

    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.example.foodplanner.R.layout.home_meals_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);

        holder.itemView.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(meal.getId());

            Navigation.findNavController(v).navigate(action);

        });

        if (isGuest) {
            holder.addToFavBtn.setVisibility(View.GONE);
        } else {
            holder.addToFavBtn.setVisibility(View.VISIBLE);

            holder.addToFavBtn.setOnClickListener(v -> {

                if (meal.isFav()) {
                    meal.setFav(false);
                    favListener.onFavClick(meal);
                } else {
                    meal.setFav(true);
                    favListener.onFavClick(meal);
                }

                notifyItemChanged(position);
            });
        }

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        ImageView addToFavBtn;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(com.example.foodplanner.R.id.mealTitle);
        }

        public void bind(Meal meal) {
            mealName.setText(meal.getName());
            addToFavBtn = itemView.findViewById(R.id.addToFavBtn);

            Glide.with(mealImage.getContext())
                    .load(meal.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_food)
                    .into(mealImage);

            addToFavBtn.setImageResource(
                    meal.isFav()
                            ? R.drawable.baseline_favorite_24
                            : R.drawable.baseline_favorite_border_24
            );

        }
    }
}