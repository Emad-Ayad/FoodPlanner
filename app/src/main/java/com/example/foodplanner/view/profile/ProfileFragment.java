package com.example.foodplanner.view.profile;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.foodplanner.data.firebase.AuthManger;
import com.example.foodplanner.view.auth.AuthActivity;

import com.example.foodplanner.R;


public class ProfileFragment extends Fragment {

    TextView nameTv, emailTv;
    Button logoutBtn;
    AuthManger authManger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTv = view.findViewById(R.id.profileName);
        emailTv = view.findViewById(R.id.profileEmail);
        logoutBtn = view.findViewById(R.id.exit);

        authManger = new AuthManger();

        if (authManger.isGuest(requireContext())) {
            nameTv.setText("Guest User");
            emailTv.setText("Limited access");
            logoutBtn.setText("Login");
        } else {
            nameTv.setText(authManger.getUserName());
            emailTv.setText(authManger.getUserEmail());
            logoutBtn.setText("Logout");
        }

        logoutBtn.setOnClickListener(v->{
            authManger.logout();
            authManger.clearGuest(getContext());
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}