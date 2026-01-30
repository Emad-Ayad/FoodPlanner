package com.example.foodplanner.home.view;

import android.app.AlertDialog;
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
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;
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

        adapter = new HomeAdapter(meal -> {
            showDayPickerDialog(meal);
        }, meal -> {
            presenter.addToFav(meal);
            Toast.makeText(getContext(), "Added to Fav " , Toast.LENGTH_SHORT).show();

        });
        recyclerView.setAdapter(adapter);
        presenter = new HomePresenterImp(this,getContext());

        mealImage.setOnClickListener(v->{
            presenter.mealOnClickLinstener();
        });


        presenter.getMealOfTheDay();
        presenter.getQuickMeals();


    }

    private void showDayPickerDialog(Meal meal) {
        String[] week = { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        new AlertDialog.Builder(getContext())
                .setTitle("Choose a day")
                .setItems(week, (dialog, day) -> {
                    MealPlan plan = new MealPlan(
                            meal.getId(),
                            week[day],
                            meal.getName(),
                            meal.getImageUrl(),
                            meal.getArea(),
                            "category" //TODO mybe adjust the model how did i forget ?!
                            );
                    presenter.addToPlan(plan);
                    Toast.makeText(getContext(), "Added to plan " , Toast.LENGTH_SHORT).show();
                }).show();
    }

    @Override
    public void showMealOfTheDay(Meal meal) { //TODO if i had time move this fun from here
        mealTitle.setText(meal.getName());
        mealCountry.setText(meal.getArea());
        Glide.with(mealImage)
                .load(meal.getImageUrl())
                .centerCrop()
                .into(mealImage);

    }

    @Override
    public void showQuickMeals(List<Meal> meals) {
        adapter.updateMeals(meals);
    }

    @Override
    public void navToMealDetails(String mealId) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(mealId);
        NavHostFragment.findNavController(this).navigate(action);
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