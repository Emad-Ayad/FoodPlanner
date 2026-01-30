package com.example.foodplanner.home.view;

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
import com.example.foodplanner.mealsbycategory.view.MealsAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MealViewHolder> {

    private List<Meal> meals = new ArrayList<>();

    private OnPlanClickListener planListener;

    public interface OnPlanClickListener {
        void onPlanClick(Meal meal);
    }

    HomeAdapter(OnPlanClickListener listener) {
        this.planListener = listener;
    }



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

        holder.itemView.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(meal.getId());

            Navigation.findNavController(v).navigate(action);

        });

        holder.addToPlanBtn.setOnClickListener(v -> {
            if (planListener != null) {
                planListener.onPlanClick(meals.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        ImageView addToPlanBtn;


        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(com.example.foodplanner.R.id.mealTitle);
        }

        public void bind(Meal meal) {
            mealName.setText(meal.getName());
            addToPlanBtn = itemView.findViewById(R.id.addToPlanBtn);


            Glide.with(mealImage.getContext())
                    .load(meal.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_food)
                    .into(mealImage);



        }
    }
}