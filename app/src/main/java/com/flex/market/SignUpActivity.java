package com.flex.market;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout tilFirstName, tilLastName, tilEmail, tilPassword;
    private EditText etFirstName, etLastName, etEmail, etPassword;
    static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_24dp);
        }

        tilFirstName = findViewById(R.id.text_input_layout_first_name);
        tilLastName = findViewById(R.id.text_input_layout_last_name);
        tilEmail = findViewById(R.id.text_input_layout_email);
        tilPassword = findViewById(R.id.text_input_layout_password);

        etFirstName = findViewById(R.id.editText_first_name);
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tilFirstName.setError(getString(R.string.error_field_required));
                } else {
                    tilFirstName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etLastName = findViewById(R.id.editText_last_name);
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tilLastName.setError(getString(R.string.error_field_required));
                } else {
                    tilLastName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etEmail = findViewById(R.id.editText_email);
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

        etPassword = findViewById(R.id.editText_password);
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

        progressBar = findViewById(R.id.sign_up_progress);
        Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(
                getResources().getColor(R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.SRC_IN
        );
        progressBar.setProgressDrawable(progressDrawable);

        Button btnSignUp = findViewById(R.id.buttonSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tilFirstName.setErrorEnabled(false);
                tilLastName.setErrorEnabled(false);
                tilEmail.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);

                // Store values at the time of the sign up attempt.
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(password)) {
                    tilPassword.setError(getString(R.string.error_field_required));
                    focusView = tilPassword;
                    cancel = true;
                } else if (!Helper.isPasswordValid(password)) {
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

                if (TextUtils.isEmpty(lastName)) {
                    tilLastName.setError(getString(R.string.error_field_required));
                    focusView = tilLastName;
                    cancel = true;
                }

                if (TextUtils.isEmpty(firstName)) {
                    tilFirstName.setError(getString(R.string.error_field_required));
                    focusView = tilFirstName;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    Helper.showProgress(true, progressBar);
                    MarketAPI.SignUp(getBaseContext(), firstName, lastName, email, password);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
