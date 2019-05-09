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

import com.example.smartcity.Activity.ListShopByCate;
import com.example.smartcity.Model.WeatherModel.Main;
import com.example.smartcity.R;
import com.example.smartcity.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class SearchCategorie extends AppCompatActivity {

    private Spinner spinnerCategory;
    private String nameCategory = "debut";
    private Button buttonSearch, buttonBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_categorie);

        spinnerCategory = findViewById(R.id.category);
        buttonSearch = findViewById(R.id.ButtonSearchCate);
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
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nameCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                nameCategory = null;
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPrefManager.getInstance(getApplicationContext()).set_category(nameCategory);
                startActivity(new Intent(getApplicationContext(), ListShopByCate.class));
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchCategorie.this, MainActivity.class));
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
    }

}
