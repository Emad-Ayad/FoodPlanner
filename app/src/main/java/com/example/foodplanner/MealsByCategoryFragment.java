package com.example.foodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.datasource.remote.MealsNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.model.Meal;

import java.util.List;

public class MealsByCategoryFragment extends Fragment implements MealsNetworkResponse {

    private static final String TAG = "MealsByCategoryFragment";
    public static final String ARG_CATEGORY_NAME = "category_name";

    private RecyclerView rvMeals;
    private ProgressBar progressBar;
    private TextView categoryTitle;
    private MealsAdapter mealsAdapter;
    private String categoryName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString(ARG_CATEGORY_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals_by_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupToolbar(view);
        setupRecyclerView();
        loadMealsByCategory();
    }

    private void initViews(View view) {
        rvMeals = view.findViewById(R.id.rvMeals);
        progressBar = view.findViewById(R.id.progressBar);
        categoryTitle = view.findViewById(R.id.tvCategoryTitle);

        if (categoryName != null) {
            categoryTitle.setText(categoryName);
        }
    }

    private void setupToolbar(View view) {
        com.google.android.material.appbar.MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(view).popBackStack());
    }

    private void setupRecyclerView() {
        mealsAdapter = new MealsAdapter(meal -> {
            // TODO: Navigate to meal details
            Toast.makeText(getContext(), "Clicked: " + meal.getName(), Toast.LENGTH_SHORT).show();
        });

        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvMeals.setAdapter(mealsAdapter);
    }

    private void loadMealsByCategory() {
        if (categoryName == null) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSource();
        remoteDataSource.getMealsByCategory(categoryName, this);
    }

    @Override
    public void onSuccess(List<Meal> meals) {
        progressBar.setVisibility(View.GONE);
        if (meals != null && !meals.isEmpty()) {
            mealsAdapter.setMeals(meals);
        } else {
            Toast.makeText(getContext(), "No meals found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.GONE);
        Log.e(TAG, "Error: " + message);
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInternetError(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
