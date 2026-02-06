package com.example.foodplanner.view.planer;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.view.planer.presenter.PlannerPresenter;
import com.example.foodplanner.view.planer.presenter.PlannerPresenterImp;
import com.example.foodplanner.view.planer.view.PlannerAdapter;
import com.example.foodplanner.view.planer.view.PlannerView;
import com.example.foodplanner.data.model.MealPlan;
import com.example.foodplanner.data.firebase.AuthManger;

import java.util.List;

public class PlannerFragment extends Fragment implements PlannerView {

    private RecyclerView rvPlan;
    private TextView guestMessage;
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
        guestMessage = view.findViewById(R.id.guestMessage);

        AuthManger authManger = new AuthManger();
        boolean isGuest = authManger.isGuest(getContext());

        if (isGuest) {
            if (guestMessage != null) {
                guestMessage.setVisibility(View.VISIBLE);
                guestMessage.setText("Login to plan your meals");
            }
            rvPlan.setVisibility(View.GONE);
        } else {
            if (guestMessage != null) {
                guestMessage.setVisibility(View.GONE);
            }
            rvPlan.setVisibility(View.VISIBLE);

            adapter = new PlannerAdapter(getContext(), plan -> {
                showDeleteConfirmation(plan);
            });
            rvPlan.setAdapter(adapter);

            presenter = new PlannerPresenterImp(this, getContext());
            presenter.getMealPlan();
        }
    }

    @Override
    public void showPlan(List<MealPlan> plans) {
        adapter.setPlans(plans);
    }

    @Override
    public void showError(String message) {
        android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_SHORT).show();
    }

    private void showDeleteConfirmation(MealPlan plan){
        new AlertDialog.Builder(requireContext())
                .setTitle("Remove meal?")
                .setMessage("Are you sure you want to remove this meal from your plan?")
                .setPositiveButton("Remove", (dialog, which) -> {
                    presenter.removeFromPlan(plan);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}