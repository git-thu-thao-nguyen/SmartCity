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
import android.widget.Switch;
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

public class CreateNetwork extends AppCompatActivity {

    private int idUser = SharedPrefManager.getInstance(this).getUserId();
    private EditText editTextNam, editTextType;
    private Switch switchStatus;
    private int networkStatus ;
    private Button boutonCreate, buttonBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_network);

        editTextNam = findViewById(R.id.name_network);
        editTextType = findViewById(R.id.type_network);
        switchStatus = findViewById(R.id.status);
        boutonCreate = findViewById(R.id.button_create);
        buttonBack = findViewById(R.id.button_back);
        progressDialog = new ProgressDialog(this);

        boutonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNetwork();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNetwork.this, MainActivity.class));
                finish();
            }
        });
    }

    private void createNetwork() {

        final String nameN = editTextNam.getText().toString().trim();
        final String type = editTextType.getText().toString().trim();
        final int status = networkStatus;
        final int idU = idUser;

        progressDialog.setMessage("Creating a social network ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_CREATE_NETWORK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            displayMessage(obj.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        displayMessage("erreur");
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("admin", String.valueOf(idU));
                params.put("nameN", nameN);
                params.put("type", type);
                params.put("status", String.valueOf(status));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void displayMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void onChecked(View view) {
        view = this.getWindow().getDecorView();

        if(switchStatus.isChecked()){
            networkStatus = 0;
        }else{
            networkStatus = 1;
        }
    }
}
