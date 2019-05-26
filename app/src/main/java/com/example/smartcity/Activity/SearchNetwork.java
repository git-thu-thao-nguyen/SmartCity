package com.example.smartcity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smartcity.R;
import com.example.smartcity.RequestHandler;
import com.example.smartcity.SharedPrefManager;
import com.example.smartcity.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchNetwork extends AppCompatActivity {

    private EditText editTextName;
    private Button boutonSearch, boutonBack;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_network);

        editTextName = findViewById(R.id.NameSearch);
        boutonSearch = findViewById(R.id.ButtonSearch);
        boutonBack = findViewById(R.id.Back);


        boutonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!editTextName.getText().toString().isEmpty()){
                    searchNetwork();
                }
            }
        });

        boutonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchNetwork.this, MainActivity.class));
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
    }

    private void searchNetwork() {

        final String nameN = editTextName.getText().toString().trim();

        progressDialog.setMessage("Searching ... ");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_SEARCH_NETWORK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("SearchReponse", response.toString());
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager
                                        .getInstance(getApplicationContext())
                                        .set_network(obj.getInt("idN"),obj.getString("admin"),obj.getString("nameN"),obj.getString("type"),obj.getInt("status"));
                                startActivity(new Intent(getApplicationContext(), SocialNetwork.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nameN", nameN);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
