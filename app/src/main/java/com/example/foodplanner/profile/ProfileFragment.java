package com.example.foodplanner.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.auth.AuthActivity;
import com.example.foodplanner.data.model.User;
import com.example.foodplanner.firebase.AuthManger;
import com.example.foodplanner.utils.SharedPreferencesManager;

public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private TextView userName, userEmail;
    private Button signOutBtn;
    private SharedPreferencesManager prefsManager;
    private AuthManger authManger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = view.findViewById(R.id.profileImage);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        signOutBtn = view.findViewById(R.id.signOutButton);

        prefsManager = new SharedPreferencesManager(requireContext());
        authManger = new AuthManger();

        loadUserData();

        signOutBtn.setOnClickListener(v -> signOut());
    }

    private void loadUserData() {
        User user = prefsManager.getUserSession();
        if (user != null) {
            if (user.getDisplayName() != null && !user.getDisplayName().isEmpty()) {
                userName.setText(user.getDisplayName());
            } else {
                userName.setText("User");
            }

            if (user.getEmail() != null) {
                userEmail.setText(user.getEmail());
            }

            if (user.getPhotoUrl() != null && !user.getPhotoUrl().isEmpty()) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .circleCrop()
                        .placeholder(R.drawable.placeholder_food)
                        .into(profileImage);
            }
        }
    }

    private void signOut() {
        authManger.logout();
        prefsManager.clearUserSession();

        Toast.makeText(getContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(requireActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
