package com.flex.market;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.ProgressBar;

import java.util.regex.Pattern;

final class Helper {
    static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email).matches();
    }

    static boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

        Pattern pat = Pattern.compile(passwordRegex);

        return pat.matcher(password).matches();
    }

    static void showProgress(final boolean show, final ProgressBar pb) {
        int animTime = 400;

        pb.setVisibility(show ? View.VISIBLE : View.GONE);
        pb.animate().setDuration(animTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pb.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
