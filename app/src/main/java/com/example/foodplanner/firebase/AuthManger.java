package com.example.foodplanner.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class AuthManger { // TODO sign with Google and Facebook
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

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public void logout() {
        auth.signOut();
    }

    public void signInWithGoogleCredential(String idToken, AuthResponse callback) {
        com.google.firebase.auth.AuthCredential credential = com.google.firebase.auth.GoogleAuthProvider
                .getCredential(idToken, null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage()
                                : "Google Sign-In failed";
                        callback.onFailure(error);
                    }
                });
    }

    public com.example.foodplanner.data.model.User getCurrentUser() {
        com.google.firebase.auth.FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            return null;
        }

        String uid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        String displayName = firebaseUser.getDisplayName();
        String photoUrl = firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null;

        return new com.example.foodplanner.data.model.User(uid, email, displayName, photoUrl);
    }

}
