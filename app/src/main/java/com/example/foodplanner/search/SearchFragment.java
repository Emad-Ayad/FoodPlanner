package com.example.foodplanner.search;

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

import com.example.foodplanner.R;
import com.example.foodplanner.mealsbycategory.presenter.MealsByCategoryPresenterImp;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SearchedView {
    private RecyclerView rvMeals;
    private ProgressBar progressBar;
    private EditText searchText;
    private SearchPresenter presenter;
    private SearchAdapter adapter;

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
        rvMeals=view.findViewById(R.id.rvMeals);
        progressBar = view.findViewById(R.id.progressBar);
        searchText= view.findViewById(R.id.searchInput);
        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter= new SearchAdapter(this.getContext());
        rvMeals.setAdapter(adapter);


        presenter = new SearchPresenterImp(this);
        progressBar.setVisibility(View.GONE);

        searchText.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (text.isEmpty() || text.equals(" ")) {
                    progressBar.setVisibility(View.GONE);
                    adapter.setMeals(new ArrayList<>());
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                presenter.getSearchedMeals(text);

            }
        });
    }

    @Override
    public void showMeals(List<Meal> meals) {
        progressBar.setVisibility(View.GONE);
        adapter.setMeals(meals);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showInternetError(String message) {

    }
}