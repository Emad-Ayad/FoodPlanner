package com.example.foodplanner.search.view;

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
import com.example.foodplanner.home.view.HomeAdapter;
import com.example.foodplanner.mealsbycategory.view.MealsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Meal> meals = new ArrayList<>();
    private Context context;

    private OnPlanClickListener planListener;
    private OnFavClickListener favListener;


    public interface OnPlanClickListener {
        void onPlanClick(Meal meal);
    }
    public interface OnFavClickListener {
        void onFavClick(Meal meal);
    }

    public SearchAdapter(Context context,OnPlanClickListener planListener,OnFavClickListener favListener){
        this.context=context;
        this.planListener = planListener;
        this.favListener = favListener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_card, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.mealName.setText(meals.get(position).getName());
        Glide.with(context).load(meals.get(position).getImageUrl()).into(holder.mealImage);

        holder.itemView.setOnClickListener(v->{
            Meal meal = meals.get(position);
            NavDirections action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meal.getId());

            Navigation.findNavController(v).navigate(action);
        });

        holder.addToPlanBtn.setOnClickListener(v -> {
            if (planListener != null) {
                planListener.onPlanClick(meals.get(position));
            }
        });

        holder.addToFavBtn.setOnClickListener(v->{
            if (favListener != null) {
                favListener.onFavClick(meals.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView mealImage;
        private TextView mealName;
        private ImageView addToPlanBtn;
        private ImageView addToFavBtn;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            addToPlanBtn = itemView.findViewById(R.id.addToPlanBtn);
            addToFavBtn = itemView.findViewById(R.id.addToFavBtn);
        }
    }


}
