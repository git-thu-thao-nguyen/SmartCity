package com.example.smartcity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.smartcity.R;
import com.example.smartcity.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class SearchOffres extends AppCompatActivity {

    private Spinner spinnerHobby;
    private String hobby = "debut";
    private Button buttonSearch, buttonBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_offres);

        spinnerHobby = findViewById(R.id.hobby);
        buttonSearch = findViewById(R.id.ButtonSearchHobby);
        buttonBack = findViewById(R.id.Back);

        List listCategory = new ArrayList();
        listCategory.add("Car");
        listCategory.add("Cinema");
        listCategory.add("Informatique");
        listCategory.add("Game");
        listCategory.add("Book");
        listCategory.add("Fashion");
        listCategory.add("Technology");
        listCategory.add("Shopping");
        listCategory.add("Sport");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHobby.setAdapter(adapter);
        spinnerHobby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hobby = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hobby = null;
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPrefManager.getInstance(getApplicationContext()).set_hobby(hobby);
                startActivity(new Intent(getApplicationContext(), ListOffres.class));
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchOffres.this, MainActivity.class));
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
    }
}
