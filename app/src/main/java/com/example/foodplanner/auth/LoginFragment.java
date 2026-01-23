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

import com.example.foodplanner.R;
import com.example.foodplanner.firebase.*;


public class LoginFragment extends Fragment {

    EditText email, password;
    Button loginBtn,googleBtn,facebookBtn,guestBtn;
    TextView signUp;
    AuthManger authManger;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.emailInput);
        password = view.findViewById(R.id.passwordInput);
        loginBtn = view.findViewById(R.id.loginButton);
        googleBtn = view.findViewById(R.id.googleLogin);
        facebookBtn = view.findViewById(R.id.facebookLogin);
        guestBtn = view.findViewById(R.id.guestButton);
        signUp = view.findViewById(R.id.toSignUp);
        authManger = new AuthManger();

        loginBtn.setOnClickListener(v->{
            String emailValue= email.getText().toString();
            String passwordValue=password.getText().toString();
            if(emailValue.isEmpty() || passwordValue.isEmpty()){
                email.setError("Please enter email");
                password.setError("Please enter password");
            }
            else{
                authManger.login(emailValue, passwordValue, new AuthResponse() {
                    @Override
                    public void onSuccess() { // TODO Navigate to home fragment
                        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signUp.setOnClickListener(v->{
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_loginFragment_to_signUpFragment);
        });


    }
}