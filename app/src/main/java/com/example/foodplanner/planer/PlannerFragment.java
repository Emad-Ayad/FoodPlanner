package com.example.foodplanner.planer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.planer.presenter.PlannerPresenter;
import com.example.foodplanner.planer.presenter.PlannerPresenterImp;
import com.example.foodplanner.planer.view.PlannerAdapter;
import com.example.foodplanner.planer.view.PlannerView;
import com.example.foodplanner.data.model.MealPlan;

import java.util.List;

public class PlannerFragment extends Fragment implements PlannerView {

    private RecyclerView rvPlan;
    private PlannerAdapter adapter;
    private PlannerPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPlan = view.findViewById(R.id.rvPlan);
        rvPlan.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PlannerAdapter(getContext(), plan -> {
            presenter.removeFromPlan(plan);
        });
        rvPlan.setAdapter(adapter);

        presenter = new PlannerPresenterImp(this, getContext());
        presenter.getMealPlan();
    }

    @Override
    public void showPlan(List<MealPlan> plans) {
        adapter.setPlans(plans);
    }

    @Override
    public void showError(String message) {
        android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_SHORT).show();
    }
}