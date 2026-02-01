package com.example.foodplanner.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.foodplanner.firebase.AuthManger;

import com.example.foodplanner.R;


public class ProfileFragment extends Fragment {

    TextView nameTv, emailTv;
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

        authManger = new AuthManger();

        nameTv.setText(authManger.getUserName());
        emailTv.setText(authManger.getUserEmail());
    }
}