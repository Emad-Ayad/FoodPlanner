package com.example.foodplanner.view.details.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.foodplanner.data.model.IngredientItem;
import com.example.foodplanner.R;
import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private Context context;
    private List<IngredientItem> ingredients = new ArrayList<>();

    public IngredientsAdapter(Context context) {
        this.context = context;
    }

    public void setIngredients(List<IngredientItem> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.ingreadans_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientItem item = ingredients.get(position);

        holder.ingredientText.setText(item.getMeasure() + " " + item.getName());

        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.placeholder_food)
                .into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ingredientImage;
        TextView ingredientText;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientImage = itemView.findViewById(R.id.ingredientImage);
            ingredientText = itemView.findViewById(R.id.ingredientText);
        }
    }
}