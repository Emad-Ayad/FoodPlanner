package com.example.foodplanner.planer.view;

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
import com.example.foodplanner.data.model.MealPlan;

import java.util.ArrayList;
import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.ViewHolder> {

    private List<MealPlan> plans = new ArrayList<>();
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(MealPlan plan);
    }

    public PlannerAdapter(Context context, OnDeleteClickListener listener) {
        this.context = context;
        this.onDeleteClickListener = listener;
    }

    public void setPlans(List<MealPlan> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealPlan plan = plans.get(position);
        holder.tvDay.setText(plan.getDay());
        holder.tvName.setText(plan.getName());
        Glide.with(context).load(plan.getImageUrl()).into(holder.ivMeal);

        holder.btnDelete.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(plan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMeal, btnDelete;
        TextView tvDay, tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.ivMeal);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
