package com.example.foodplanner.view.mealsbycategory.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
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
import com.example.foodplanner.R;
import com.example.foodplanner.view.mealsbycategory.presenter.MealsByCategoryPresenter;
import com.example.foodplanner.view.mealsbycategory.presenter.MealsByCategoryPresenterImp;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;
import com.example.foodplanner.data.firebase.AuthManger;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List;
import java.util.Locale;

public class MealsByCategoryFragment extends Fragment implements MealsByCategoryView {

    private RecyclerView rvMeals;
    private ProgressBar progressBar;
    private TextView categoryTitle;
    private MealsAdapter mealsAdapter;
    private String categoryName;
    private MealsByCategoryPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString("category_name");
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

        rvMeals = view.findViewById(R.id.rvMeals);
        progressBar = view.findViewById(R.id.progressBar);
        categoryTitle = view.findViewById(R.id.categoryTitle);
        categoryTitle.setText(categoryName);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar); // TODO i just wanted to try something new (Future Me feel free to change)
        toolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (getContext() != null) {
            AuthManger authManger = new AuthManger();
            boolean isGuest = authManger.isGuest(getContext());

            mealsAdapter = new MealsAdapter(this.getContext(), meal -> {
                if (meal.isFav()) {
                    presenter.addToFav(meal);
                } else {
                    presenter.removeFromFav(meal);
                }
                Toast.makeText(getContext(), "Added to Fav ", Toast.LENGTH_SHORT).show();

            }, isGuest);
        }
        rvMeals.setAdapter(mealsAdapter);

        presenter = new MealsByCategoryPresenterImp(this, getContext());
        progressBar.setVisibility(View.VISIBLE);
        presenter.getMealsByCategory(categoryName);
    }


    @Override
    public void showMeals(List<Meal> meals) {
        progressBar.setVisibility(View.GONE);
        if (meals != null && !meals.isEmpty()) {
            mealsAdapter.setMeals(meals);
        } else {
            Toast.makeText(getContext(), "No meals found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        Log.e("MealsByCategoryFragment", "Error: " + message);
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInternetError(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
