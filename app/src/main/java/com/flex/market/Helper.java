package com.flex.market;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.ProgressBar;

import java.util.regex.Pattern;

final class Helper {
    static boolean isEmailValid(String email) {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email.toLowerCase()).matches();
    }

    static boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[0-9])(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[а-я])(?=.*[А-Я])))(?=\\S+$).{6,}$";

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
