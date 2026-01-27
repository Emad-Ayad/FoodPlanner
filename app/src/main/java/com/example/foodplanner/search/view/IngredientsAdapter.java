package com.example.foodplanner.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onIngredientClick(Ingredient ingredient);
    }

    public IngredientsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingreadans_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.tvIngredient.setText(ingredient.getName());
        holder.itemView.setOnClickListener(v -> listener.onIngredientClick(ingredient));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredient = (TextView) itemView;
        }
    }
}
