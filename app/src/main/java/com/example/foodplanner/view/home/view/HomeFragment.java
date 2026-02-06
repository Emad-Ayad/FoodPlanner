package com.example.foodplanner.view.home.view;

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
import com.example.foodplanner.data.firebase.AuthManger;
import com.example.foodplanner.view.home.presenter.HomePresenter;
import com.example.foodplanner.view.home.presenter.HomePresenterImp;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {
    private ImageView mealImage , addToFav;
    private TextView mealTitle, mealCountry;
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private HomePresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
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
        addToFav = view.findViewById(R.id.addToFavBtn);


        AuthManger authManger = new AuthManger();
        boolean isGuest = authManger.isGuest(getContext());

        adapter = new HomeAdapter(meal -> {
            if (meal.isFav()) {
                presenter.addToFav(meal);
            } else {
                presenter.removeFromFav(meal);
            }
            Snackbar.make(view, "Added to Fav", Snackbar.LENGTH_SHORT).show();

        }, isGuest);
        recyclerView.setAdapter(adapter);
        presenter = new HomePresenterImp(this, getContext());

        mealImage.setOnClickListener(v -> {
            presenter.mealOnClickLinstener();
        });

        addToFav.setOnClickListener(v -> {
            presenter.onMealOfTheDayFavClicked();
        });

        presenter.getMealOfTheDay();
        presenter.getQuickMeals();

    }

    @Override
    public void showMealOfTheDay(Meal meal) { // TODO if i had time move this fun from here
        mealTitle.setText(meal.getName());
        mealCountry.setText(meal.getArea());
        Glide.with(mealImage)
                .load(meal.getImageUrl())
                .centerCrop()
                .into(mealImage);

        updateFavIcon(meal.isFav());

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
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showInternetError(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void updateFavIcon(boolean isFav) {
        if (isFav) {
            addToFav.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            addToFav.setImageResource(R.drawable.baseline_favorite_border_24);
        }
    }
}