package com.example.foodplanner.home.view;

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
import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.home.presenter.*;

import java.util.List;


public class HomeFragment extends Fragment implements HomeView{
    private ImageView mealImage;
    private TextView mealTitle, mealCountry;
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private HomePresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvMeals);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        mealImage = view.findViewById(R.id.mealImage);
        mealTitle = view.findViewById(R.id.mealTitle);
        mealCountry = view.findViewById(R.id.mealCountry);

        adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);
        presenter = new HomePresenterImp(this);

        presenter.getMealOfTheDay();
        presenter.getQuickMeals();

    }

    @Override
    public void showMealOfTheDay(Meal meal) {
        mealTitle.setText(meal.getName());
        mealCountry.setText(meal.getArea());
        Glide.with(this)
                .load(meal.getImageUrl())
                .centerCrop()
                .into(mealImage);
    }

    @Override
    public void showQuickMeals(List<Meal> meals) {
        adapter.updateMeals(meals);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInternetError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}