package com.example.foodplanner.firebase;

import com.google.firebase.auth.FirebaseAuth;


public class AuthManger { //TODO sign with Google and Facebook
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    String webId="867975195220-qnhje0freg6ka2ars5t1haarl62jphnu.apps.googleusercontent.com";



    public void login(String email, String password, AuthResponse callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(request -> {
                    if (request.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(request.getException().getMessage());
                    }
                });
    }


    public void register(String email, String password, AuthResponse callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(request -> {
                    if (request.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(request.getException().getMessage());
                    }
                });
    }

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public void logout() {
        auth.signOut();
    }


}
