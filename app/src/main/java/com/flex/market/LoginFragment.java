package com.flex.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginFragment extends Fragment {
    private TextInputLayout tilEmail, tilPassword;
    private EditText etEmail, etPassword;
    public static ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tilEmail = view.findViewById(R.id.text_input_layout_email);
        tilPassword = view.findViewById(R.id.text_input_layout_password);

        etEmail = view.findViewById(R.id.editText_email);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Helper.isEmailValid(s.toString())) {
                    tilEmail.setErrorEnabled(false);
                } else {
                    tilEmail.setError(getString(R.string.error_invalid_email));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etPassword = view.findViewById(R.id.editText_password);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Helper.isPasswordValid(s.toString())) {
                    tilPassword.setErrorEnabled(false);
                } else {
                    tilPassword.setError(getString(R.string.error_invalid_password));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        progressBar = view.findViewById(R.id.login_progress);

        Button btnLogin = view.findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilEmail.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);

                // Store values at the time of the login attempt.
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(password)) {
                    tilPassword.setError(getString(R.string.error_field_required));
                    focusView = tilPassword;
                    cancel = true;
                } else if (!Helper.isPasswordValid(password)){
                    tilPassword.setError(getString(R.string.error_invalid_password));
                    focusView = tilPassword;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(email)) {
                    tilEmail.setError(getString(R.string.error_field_required));
                    focusView = tilEmail;
                    cancel = true;
                } else if (!Helper.isEmailValid(email)) {
                    tilEmail.setError(getString(R.string.error_invalid_email));
                    focusView = tilEmail;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    Helper.showProgress(true, progressBar);
                    MarketAPI.GetToken(getContext(), email, password);
                }
            }
        });

        TextView tvSignUp = view.findViewById(R.id.textViewSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
