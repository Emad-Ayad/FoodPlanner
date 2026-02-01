package com.example.foodplanner.firebase;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthManger {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public String webId = "867975195220-qnhje0freg6ka2ars5t1haarl62jphnu.apps.googleusercontent.com";

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

    public void firebaseAuthWithGoogle(String idToken, AuthResponse callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public String getUserEmail() {
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : "";
    }

    public String getUserName() {
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getDisplayName() : "";
    }

    public boolean isGuest(Activity activity) {
        return activity.getSharedPreferences("app_prefs", activity.MODE_PRIVATE)
                .getBoolean("isGuest", false);
    }

    public boolean isGuest(Context context) {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getBoolean("isGuest", false);
    }

    public void clearGuest(Context context) {
        context.getSharedPreferences("app_prefs", context.MODE_PRIVATE)
                .edit()
                .remove("isGuest")
                .apply();
    }

    public void logout() {
        auth.signOut();
    }

}
