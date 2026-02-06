package com.example.foodplanner.view.details.view;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.view.details.presenter.DetailsPresenter;
import com.example.foodplanner.view.details.presenter.DetailsPresenterImp;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.model.MealDetail;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.data.model.MealPlan;
import com.example.foodplanner.data.firebase.AuthManger;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailsFragment extends Fragment implements DetailsView {

    private DetailsPresenter presenter;
    private ImageView mealImage,addToFavBtn;
    private TextView mealName, mealArea, mealCategory, instructionsText;

    private YouTubePlayerView youtubePlayerView;
    private IngredientsAdapter ingredientsAdapter;
    private RecyclerView rvIngredients;
    private Button addToPlan;
    private boolean isFav = false;
    private Meal currentMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        presenter = new DetailsPresenterImp(this, getContext());

        String mealId = null;
        if (getArguments() != null) {
            mealId = DetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        }

        if (mealId != null) {
            presenter.getMealDetails(mealId);
        } else {
            showErrorMessage("Invalid Meal ID");
        }
    }

    private void initViews(View view) {
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealName);
        mealArea = view.findViewById(R.id.mealArea);
        mealCategory = view.findViewById(R.id.mealCategory);
        instructionsText = view.findViewById(R.id.instructionsText);
        youtubePlayerView = view.findViewById(R.id.youtubePlayer);
        rvIngredients = view.findViewById(R.id.rvIngredients);
        addToFavBtn = view.findViewById(R.id.addToFavBtn);
        addToPlan = view.findViewById(R.id.btnAddToPlan);
        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        ingredientsAdapter = new IngredientsAdapter(getContext());
        rvIngredients.setAdapter(ingredientsAdapter);
        getLifecycle().addObserver(youtubePlayerView);
    }

    @Override
    public void showMealDetails(MealDetail meal) {
        mealName.setText(meal.getName());
        mealArea.setText(meal.getCountry());
        mealCategory.setText(meal.getCategory());
        instructionsText.setText(meal.getInstructions());

        Glide.with(this).load(meal.getImageUrl()).into(mealImage);

        loadVideo(meal.getYoutubeUrl());
        ingredientsAdapter.setIngredients(meal.getIngredients());

        currentMeal = new Meal(
                meal.getId(),
                meal.getName(),
                meal.getCountry(),
                meal.getImageUrl());

        AuthManger authManger = new AuthManger();
        boolean isGuest = authManger.isGuest(getContext());

        if (isGuest) {
            addToFavBtn.setVisibility(View.GONE);
        } else {
            addToFavBtn.setVisibility(View.VISIBLE);
            addToFavBtn.setOnClickListener(v -> {
                if (isFav) {
                    presenter.removeFromFav(currentMeal);
                } else {
                    presenter.addToFav(currentMeal);
                }
            });
        }

        if (isGuest) {
            addToPlan.setVisibility(View.GONE);
        } else {
            addToPlan.setVisibility(View.VISIBLE);
            addToPlan.setOnClickListener(v -> {
                showWeekDatePicker(currentMeal);
            });
        }
    }

    @Override
    public void showFavAdded() {
        isFav = true;
        addToFavBtn.setImageResource(R.drawable.baseline_favorite_24);
    }

    @Override
    public void showFavRemoved() {
        isFav = false;
        addToFavBtn.setImageResource(R.drawable.baseline_favorite_border_24);
    }

    @Override
    public void updateFavState(boolean isFav) {
        this.isFav = isFav;
        addToFavBtn.setImageResource(
                isFav ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
    }

    private void loadVideo(String youtubeUrl) {
        if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
            String videoId = extractVideoId(youtubeUrl);
            if (videoId != null) {
                youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0);
                    }
                });
            }
        } else {
            youtubePlayerView.setVisibility(View.GONE);
        }
    }

    private String extractVideoId(String youtubeUrl) {
        String videoId = null;
        String regex = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(youtubeUrl);
        if (matcher.find()) {
            videoId = matcher.group();
        }
        return videoId;
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void showWeekDatePicker(Meal meal) {
        Calendar today = Calendar.getInstance();

        long minDate = today.getTimeInMillis();

        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.add(Calendar.DAY_OF_YEAR, 6);
        long maxDate = maxCalendar.getTimeInMillis();

        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {

                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth);

                    SimpleDateFormat sdf =
                            new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault());

                    String formattedDate = sdf.format(selected.getTime());

                    MealPlan plan = new MealPlan(
                            meal.getId(),
                            formattedDate,
                            meal.getName(),
                            meal.getImageUrl(),
                            meal.getArea(),
                            ""
                    );

                    presenter.addToPlan(plan);
                    Toast.makeText(getContext(), "Added to plan", Toast.LENGTH_SHORT).show();
                },
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );

        dialog.getDatePicker().setMinDate(minDate);
        dialog.getDatePicker().setMaxDate(maxDate);
        dialog.show();
    }

}