package com.example.foodplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.model.Meal;

import java.util.List;


public class HomeFragment extends Fragment {
    private ImageView mealImage;
    private TextView mealTitle, mealCountry;
    private MealsRemoteDataSource remoteDataSource;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealImage = view.findViewById(R.id.ivMealImage);
        mealTitle = view.findViewById(R.id.tvMealTitle);
        mealCountry = view.findViewById(R.id.tvMealCountry);

        remoteDataSource = new MealsRemoteDataSource();
        loadMealOfTheDay();
    }

    private void loadMealOfTheDay() {
        remoteDataSource.getMealOfTheDay(new MealsNetworkResponse() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (meals == null || meals.isEmpty()) return;

                Meal meal = meals.get(0);
                mealTitle.setText(meal.getName());
                mealCountry.setText(meal.getArea());

                Glide.with(requireContext())
                        .load(meal.getImageUrl())
                        .centerCrop()
                        .into(mealImage);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInternetError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }



}