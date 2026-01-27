package com.example.foodplanner.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.foodplanner.R;
import com.example.foodplanner.model.*;
import com.example.foodplanner.search.presenter.SearchPresenter;
import com.example.foodplanner.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchedView,
        AreasAdapter.OnItemClickListener,
        IngredientsAdapter.OnItemClickListener,
        SearchCategoryAdapter.OnItemClickListener {

    private RecyclerView rvMeals;
    private ProgressBar progressBar;
    private EditText searchText;
    private TextInputLayout searchLayout;
    private ChipGroup chipGroup;
    private Chip chipName, chipCategory, chipArea, chipIngredient;
    private SearchPresenter presenter;
    private SearchAdapter adapter;
    private AreasAdapter areasAdapter;
    private IngredientsAdapter ingredientsAdapter;
    private SearchCategoryAdapter categoryAdapter;
    private SearchMode currentMode = SearchMode.NAME;

    private List<Country> countriesList = new ArrayList<>();
    private List<Category> categoriesList = new ArrayList<>();
    private List<Ingredient> ingredientsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMeals = view.findViewById(R.id.rvMeals);
        progressBar = view.findViewById(R.id.progressBar);
        searchText = view.findViewById(R.id.searchInput);
        searchLayout = view.findViewById(R.id.searchLayout);
        chipGroup = view.findViewById(R.id.chipGroupSearch);
        chipName = view.findViewById(R.id.chipName);
        chipCategory = view.findViewById(R.id.chipCategory);
        chipArea = view.findViewById(R.id.chipArea);
        chipIngredient = view.findViewById(R.id.chipIngredient);


        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new SearchAdapter(this.getContext());
        areasAdapter = new AreasAdapter(this);
        ingredientsAdapter = new IngredientsAdapter(this);
        categoryAdapter = new SearchCategoryAdapter(this.getContext(), this);

        rvMeals.setAdapter(adapter);

        presenter = new SearchPresenterImp(this);
        progressBar.setVisibility(View.GONE);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (currentMode == SearchMode.NAME) {
                    if (text.isEmpty() || text.equals(" ")) {
                        progressBar.setVisibility(View.GONE);
                        adapter.setMeals(new ArrayList<>());
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    presenter.getSearchedMeals(text);

                } else if (currentMode == SearchMode.CATEGORY) {
                    if (text.isEmpty()) {
                        categoryAdapter.setCategories(categoriesList);
                        return;
                    }
                    List<Category> filtered = new ArrayList<>();
                    for (Category category : categoriesList) {
                        if (category.getName().toLowerCase().contains(text)){
                            filtered.add(category);
                        }
                    }
                    categoryAdapter.setCategories(filtered);
                } else if (currentMode == SearchMode.AREA) {
                    if (text.isEmpty()) {
                        areasAdapter.setCountries(countriesList);
                        return;
                    }
                    List<Country> filtered = new ArrayList<>();
                    for (Country country : countriesList) {
                        if (country.getName().toLowerCase().contains(text)){
                            filtered.add(country);
                        }
                    }
                    areasAdapter.setCountries(filtered);

                }else if (currentMode == SearchMode.INGREDIENT) {
                    if (text.isEmpty()) {
                        ingredientsAdapter.setIngredients(ingredientsList);
                        return;
                    }
                    List<Ingredient> filtered = new ArrayList<>();
                    for (Ingredient ingredient : ingredientsList) {
                        if (ingredient.getName().toLowerCase().contains(text)){
                            filtered.add(ingredient);
                        }
                    }
                    ingredientsAdapter.setIngredients(filtered);

                }
            }
        });

        setupChipListeners();
    }

    private void setupChipListeners() {
        chipName.setOnClickListener(v -> switchMode(SearchMode.NAME));
        chipCategory.setOnClickListener(v -> switchMode(SearchMode.CATEGORY));
        chipArea.setOnClickListener(v -> switchMode(SearchMode.AREA));
        chipIngredient.setOnClickListener(v -> switchMode(SearchMode.INGREDIENT));
    }

    private void switchMode(SearchMode mode) {
        currentMode = mode;
        progressBar.setVisibility(View.VISIBLE);

        switch (mode) {
            case NAME:
                rvMeals.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                break;

            case CATEGORY:
                rvMeals.setAdapter(categoryAdapter);
                presenter.getCategories();
                break;

            case AREA:
                rvMeals.setAdapter(areasAdapter);
                presenter.getAreas();
                break;

            case INGREDIENT:
                rvMeals.setAdapter(ingredientsAdapter);
                presenter.getIngredients();
                break;
        }
    }

    @Override
    public void showMeals(List<Meal> meals) {
        progressBar.setVisibility(View.GONE);
        rvMeals.setAdapter(adapter);
        adapter.setMeals(meals);
    }

    @Override
    public void showCategories(List<Category> categories) {
        progressBar.setVisibility(View.GONE);
        categoriesList=categories;
        categoryAdapter.setCategories(categoriesList);
    }

    @Override
    public void showAreas(List<Country> countries) {
        progressBar.setVisibility(View.GONE);
        countriesList=countries;
        areasAdapter.setCountries(countriesList);
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        progressBar.setVisibility(View.GONE);
        ingredientsList=ingredients;
        ingredientsAdapter.setIngredients(ingredientsList);
    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInternetError(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAreaClick(Country country) {
        progressBar.setVisibility(View.VISIBLE);
        presenter.getMealsByArea(country.getName());
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {
        progressBar.setVisibility(View.VISIBLE);
        presenter.getMealsByIngredient(ingredient.getName());
    }

    @Override
    public void onCategoryClick(Category category) {
        progressBar.setVisibility(View.VISIBLE);
        presenter.getMealsByCategory(category.getName());
    }
}