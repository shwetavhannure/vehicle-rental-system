package com.example.bikerental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private EditText etFirst, etLast, etPhone, etEmail;
    private TextView errFirst, errLast, errPhone, errEmail, errGender, tvLogin;
    private RadioGroup rgGender;
    private Button btnSignup;
    private DatabaseHelper db;

    private final Pattern namePattern = Pattern.compile("^[A-Za-z]{2,}$");
    private final Pattern phonePattern = Pattern.compile("^[6-9][0-9]{9}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFirst = findViewById(R.id.etFirstName);
        etLast = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);

        errFirst = findViewById(R.id.errFirst);
        errLast = findViewById(R.id.errLast);
        errPhone = findViewById(R.id.errPhone);
        errEmail = findViewById(R.id.errEmail);
        errGender = findViewById(R.id.errGender);
        rgGender = findViewById(R.id.rgGender);
        btnSignup = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLogin);
        db = new DatabaseHelper(this);

        String loginText = "You have an account? <font color='#00BCD4'>Login</font>";
        tvLogin.setText(Html.fromHtml(loginText, Html.FROM_HTML_MODE_LEGACY));

        setupLiveValidation(etFirst, errFirst, "name");
        setupLiveValidation(etLast, errLast, "name");
        setupLiveValidation(etPhone, errPhone, "phone");
        setupLiveValidation(etEmail, errEmail, "email");

        rgGender.setOnCheckedChangeListener((group, checkedId) -> {
            errGender.setVisibility(View.INVISIBLE);
            checkAllFields();
        });

        btnSignup.setOnClickListener(v -> {
            String firstName = etFirst.getText().toString().trim();
            String lastName = etLast.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            String fullName = firstName + " " + lastName;

            if (db.insertUser(fullName, phone, email)) {

                SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putBoolean("isLoggedIn", true);
                editor.putString("userName", fullName);


                editor.putString("etFirstName", firstName);
                editor.putString("etLastName", lastName);
                editor.putString("etPhone", phone);
                editor.putString("etEmail", email);

                editor.apply();

                Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
            } else {
                Toast.makeText(this, "User Already Exist With the same phone number", Toast.LENGTH_LONG).show();
            }
        });

        tvLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private void setupLiveValidation(EditText et, TextView err, String type) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().trim();
                boolean valid = true;
                if (input.isEmpty()) { err.setText("This field is required."); valid = false; }
                else if (type.equals("name") && !namePattern.matcher(input).matches()) { err.setText("Name is not valid."); valid = false; }
                else if (type.equals("phone") && !phonePattern.matcher(input).matches()) { err.setText("Mobile number should be 10 digits."); valid = false; }
                else if (type.equals("email") && !Patterns.EMAIL_ADDRESS.matcher(input).matches()) { err.setText("Email is not valid."); valid = false; }

                err.setVisibility(valid ? View.INVISIBLE : View.VISIBLE);
                checkAllFields();
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void checkAllFields() {
        boolean valid = namePattern.matcher(etFirst.getText().toString().trim()).matches() &&
                namePattern.matcher(etLast.getText().toString().trim()).matches() &&
                phonePattern.matcher(etPhone.getText().toString().trim()).matches() &&
                Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches() &&
                rgGender.getCheckedRadioButtonId() != -1;

        btnSignup.setEnabled(valid);
        btnSignup.setAlpha(valid ? 1.0f : 0.5f);
        btnSignup.setBackgroundTintList(valid ? ColorStateList.valueOf(Color.parseColor("#00BCD4")) : null);
    }
}