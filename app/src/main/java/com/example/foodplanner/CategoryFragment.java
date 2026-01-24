package com.example.foodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.datasource.remote.CategoriesNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.model.Category;

import java.util.List;

public class CategoryFragment extends Fragment implements CategoriesNetworkResponse {

    private static final String TAG = "CategoryFragment";
    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupRecyclerView();
        loadCategories();
    }

    private void initViews(View view) {
        rvCategories = view.findViewById(R.id.rvCategories);
    }

    private void setupRecyclerView() {
        categoryAdapter = new CategoryAdapter(category -> {
            // Navigate to MealsByCategoryFragment
            Bundle args = new Bundle();
            args.putString(MealsByCategoryFragment.ARG_CATEGORY_NAME, category.getName());
            androidx.navigation.Navigation.findNavController(getView())
                    .navigate(R.id.action_categoriesFragment_to_mealsByCategoryFragment, args);
        }, getContext());

        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(categoryAdapter);
    }

    private void loadCategories() {
        MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSource();
        remoteDataSource.getCategories(this);
    }

    @Override
    public void onSuccess(List<Category> categories) {
        if (categories != null && !categories.isEmpty()) {
            categoryAdapter.setCategories(categories);
        }
    }

    @Override
    public void onError(String message) {
        Log.e(TAG, "Error loading categories: " + message);
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInternetError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}