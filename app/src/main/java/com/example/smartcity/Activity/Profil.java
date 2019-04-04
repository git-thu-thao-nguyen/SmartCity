package com.example.smartcity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.smartcity.R;
import com.example.smartcity.SharedPrefManager;

public class Profil extends AppCompatActivity {

    private TextView textViewPseudo, textViewName, textViewEmail, textViewAge, textViewCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

            textViewPseudo = findViewById(R.id.pseudo);
            textViewName = findViewById(R.id.name);
            textViewEmail = findViewById(R.id.email);
            textViewAge = findViewById(R.id.age);
            textViewCity = findViewById(R.id.city);

            textViewPseudo.setText(SharedPrefManager.getInstance(this).getUserPseudo());
            textViewName.setText(SharedPrefManager.getInstance(this).getUserFirstName() + " " + SharedPrefManager.getInstance(this).getUserLastName());
            textViewEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
            textViewAge.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserAge()));
            textViewCity.setText(SharedPrefManager.getInstance(this).getUserCity());

    }
}
