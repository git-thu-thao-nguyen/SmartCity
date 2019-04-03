package com.example.smartcity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.smartcity.R;

public class Profil extends AppCompatActivity {

    private TextView textViewNom, textViewPseudo, textViewEmail, textViewAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
    }
}
