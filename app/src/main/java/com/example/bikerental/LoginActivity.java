package com.example.bikerental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etPhone;
    TextView tvError, tvSignup;
    Button btnLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = findViewById(R.id.etPhone);
        tvError = findViewById(R.id.tvError);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        db = new DatabaseHelper(this);

        String signupText = "You don't have an account ? <font color='#00BCD4'>Create an Account</font>";
        tvSignup.setText(Html.fromHtml(signupText, Html.FROM_HTML_MODE_LEGACY));

        etPhone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String phone = s.toString();
                if (phone.length() == 10 && phone.matches("^[6-9]\\d{9}$")) {
                    tvError.setVisibility(View.INVISIBLE);
                    btnLogin.setAlpha(1.0f);
                    btnLogin.setEnabled(true);
                    btnLogin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00BCD4")));
                } else {
                    if(phone.isEmpty()){
                        tvError.setText("This field is required");
                    } else {
                        tvError.setText("Mobile number should be 10 digits.");
                    }
                    tvError.setVisibility(View.VISIBLE);
                    btnLogin.setAlpha(0.5f);
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundTintList(null);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        btnLogin.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().trim();
            if (db.checkUser(phone)) {

                String fullName = db.getUserName(phone);
                String email = db.getUserEmail(phone);


                String fName = "";
                String lName = "";
                if (fullName != null && fullName.contains(" ")) {
                    String[] parts = fullName.split(" ", 2);
                    fName = parts[0];
                    lName = parts[1];
                } else {
                    fName = (fullName != null) ? fullName : "User";
                }


                SharedPreferences sp = getSharedPreferences("UserSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putBoolean("isLoggedIn", true);


                editor.putString("userName", fullName);
                editor.putString("etFirstName", fName);
                editor.putString("etLastName", lName);
                editor.putString("etPhone", phone);
                editor.putString("etEmail", (email != null) ? email : "Not provided");

                editor.apply();

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                tvError.setText("User not found. Please register.");
                tvError.setVisibility(View.VISIBLE);
            }
        });

        tvSignup.setOnClickListener(v -> startActivity(new Intent(this, SignupActivity.class)));
    }
}