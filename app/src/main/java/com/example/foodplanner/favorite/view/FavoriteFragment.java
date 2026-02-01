package com.example.foodplanner.favorite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.favorite.presenter.FavoritePresenter;
import com.example.foodplanner.favorite.presenter.FavoritePresenterImp;
import com.example.foodplanner.firebase.AuthManger;

import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteView {

    private RecyclerView rvFavorites;
    private TextView guestMessage;
    private FavoriteAdapter adapter;
    private FavoritePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        guestMessage = view.findViewById(R.id.guestMessage);

        AuthManger authManger = new AuthManger();
        boolean isGuest = authManger.isGuest(getContext());

        if (isGuest) {
            if (guestMessage != null) {
                guestMessage.setVisibility(View.VISIBLE);
                guestMessage.setText("Login to save your favorite meals");
            }
            rvFavorites.setVisibility(View.GONE);
        } else {
            if (guestMessage != null) {
                guestMessage.setVisibility(View.GONE);
            }
            rvFavorites.setVisibility(View.VISIBLE);

            adapter = new FavoriteAdapter(getContext(), meal -> {
                presenter.removeFromFavorites(meal);
                Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
            });
            rvFavorites.setAdapter(adapter);

            presenter = new FavoritePresenterImp(this, getContext());

            presenter.getFavorites();
        }
    }

    @Override
    public void showFavorites(List<Meal> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
