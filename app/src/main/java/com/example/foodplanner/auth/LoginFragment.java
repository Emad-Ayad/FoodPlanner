package com.example.foodplanner.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.MainActivity;
import com.example.foodplanner.firebase.*;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class LoginFragment extends Fragment {//TODO sign with Google and Facebook

    EditText email, password;
    Button loginBtn,googleBtn,guestBtn;
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
                    public void onSuccess() {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        googleBtn.setOnClickListener(v -> signInWithGoogle());

        signUp.setOnClickListener(v->{
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_loginFragment_to_signUpFragment);
        });
    }
    private void signInWithGoogle() {

        Executor executor = Executors.newSingleThreadExecutor();

        GetGoogleIdOption googleIdOption =
                new GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(authManger.webId)
                        .build();

        GetCredentialRequest request =
                new GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build();

        CredentialManager credentialManager = CredentialManager.create(requireContext());

        credentialManager.getCredentialAsync(requireActivity(), request, null, executor,
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {

                    @Override
                    public void onResult(GetCredentialResponse result) {

                        if (result.getCredential() instanceof GoogleIdTokenCredential) {

                            GoogleIdTokenCredential googleCredential =
                                    GoogleIdTokenCredential.createFrom(
                                            result.getCredential().getData()
                                    );

                            String idToken = googleCredential.getIdToken();

                            authManger.firebaseAuthWithGoogle(idToken, new AuthResponse() {
                                @Override
                                public void onSuccess() {
                                    requireActivity().runOnUiThread(() -> {
                                        Toast.makeText(getContext(),
                                                "Google Login Success",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    });
                                }

                                @Override
                                public void onFailure(String error) {
                                    requireActivity().runOnUiThread(() ->
                                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show()
                                    );
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
                    }
                }
        );
    }

}