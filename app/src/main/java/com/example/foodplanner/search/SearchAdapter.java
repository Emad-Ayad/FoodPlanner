package com.example.foodplanner.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.mealsbycategory.view.MealsAdapter;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Meal> meals = new ArrayList<>();
    private Context context;

    public SearchAdapter(Context context){
        this.context=context;
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

        holder.itemView.setOnClickListener(v->{ // TODO nav  ^_^ ربنا يوفقك
            Meal meal = meals.get(position);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView mealImage;
        private TextView mealName;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
        }
    }


}
