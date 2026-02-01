package com.example.foodplanner.details.view;

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

import com.example.foodplanner.details.presenter.DetailsPresenter;
import com.example.foodplanner.details.presenter.DetailsPresenterImp;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.model.MealDetail;
import com.example.foodplanner.data.model.Meal;
import com.example.foodplanner.firebase.AuthManger;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailsFragment extends Fragment implements DetailsView {

    private DetailsPresenter presenter;
    private ImageView mealImage;
    private TextView mealName, mealArea, mealCategory, instructionsText;

    private YouTubePlayerView youtubePlayerView;
    private IngredientsAdapter ingredientsAdapter;
    private RecyclerView rvIngredients;
    private ImageView addToFavBtn;
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

}