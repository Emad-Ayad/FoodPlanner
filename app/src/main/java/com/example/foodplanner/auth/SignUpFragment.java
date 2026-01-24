package com.example.foodplanner.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.firebase.*;
import com.example.foodplanner.R;

public class SignUpFragment extends Fragment { //TODO signUp with Google and Facebook
    EditText username, email, password, confirmPassword;
    Button signUpButton,googleBtn,facebookBtn;
    TextView login;
    AuthManger authManger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.nameInput);
        email = view.findViewById(R.id.emailInput);
        password = view.findViewById(R.id.passwordInput);
        confirmPassword = view.findViewById(R.id.confirmPasswordInput);
        signUpButton = view.findViewById(R.id.signUpButton);
        googleBtn = view.findViewById(R.id.googleLogin);
        facebookBtn = view.findViewById(R.id.facebookLogin);
        login = view.findViewById(R.id.toLogin);
        authManger = new AuthManger();

        signUpButton.setOnClickListener(v->{
            String usernameText = username.getText().toString();
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            String confirmPasswordText = confirmPassword.getText().toString();

            if(emailText.isEmpty() || passwordText.isEmpty()){
                email.setError("Please enter email");
                password.setError("Please enter password");
            }
            if(!passwordText.equals(confirmPasswordText)){
                password.setError("Passwords do not match");
            }

            authManger.register(emailText, passwordText, new AuthResponse() {
                @Override
                public void onSuccess() { //TODO Navigate to Home
                    Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
        });



        login.setOnClickListener(v->{
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_signUpFragment_to_loginFragment);
        });







    }
}