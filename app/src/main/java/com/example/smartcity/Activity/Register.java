package com.example.smartcity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smartcity.R;
import com.example.smartcity.RequestHandler;
import com.example.smartcity.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText editTextPseudo, editTextFirstName, editTextLastName, editTextEmail, editTextAge, editTextCity, editTextMDP;
    private Button buttonRegist, buttonSignIn;
    private ProgressBar loading;
    private static String URL_REGIST = Urls.URL_REGIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        loading = findViewById(R.id.loading);
        editTextPseudo = findViewById(R.id.pseudo);
        editTextFirstName = findViewById(R.id.firstname);
        editTextLastName = findViewById(R.id.lastname);
        editTextEmail = findViewById(R.id.email);
        editTextAge = findViewById(R.id.age);
        editTextCity = findViewById(R.id.city);
        editTextMDP = findViewById(R.id.MDP);

        buttonRegist = findViewById(R.id.regist);
        buttonSignIn = findViewById(R.id.signin);

        buttonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String pseudo = editTextPseudo.getText().toString().trim();
                final String firstname = editTextFirstName.getText().toString().trim();
                final String lastname = editTextLastName.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                final String age = editTextAge.getText().toString().trim();
                final String city = editTextCity.getText().toString().trim();
                final String mdp = editTextMDP.getText().toString().trim();

                if(pseudo.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || age.isEmpty() || city.isEmpty() || mdp.isEmpty()){
                    editTextPseudo.setError("Please insert Pseudo");
                    editTextFirstName.setError("Please insert First Name");
                    editTextLastName.setError("Please insert Last Name");
                    editTextEmail.setError("Please insert Email");
                    editTextAge.setError("Please insert Age");
                    editTextCity.setError("Please insert City");
                    editTextMDP.setError("Please insert Password");
                } else {
                    Regist();
                }
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent IntentLogin = new Intent(Register.this, Login.class);
                startActivity(IntentLogin);
            }
        });
    }

    private void Regist(){

        loading.setVisibility(View.VISIBLE);
        buttonRegist.setVisibility(View.GONE);

        final String pseudo = this.editTextPseudo.getText().toString().trim();
        final String firstname = this.editTextFirstName.getText().toString().trim();
        final String lastname = this.editTextLastName.getText().toString().trim();
        final String email = this.editTextEmail.getText().toString().trim();
        final String age = this.editTextAge.getText().toString().trim();
        final String city = editTextCity.getText().toString().trim();
        final String mdp = this.editTextMDP.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RegisterResponse", response.toString());
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(Register.this,"Register Success!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Register.this,Login.class);
                                startActivity(intent);

                                loading.setVisibility(View.GONE);
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Register.this,"Register Error 1! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            buttonRegist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this,"Register Error 2! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        buttonRegist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pseudo",pseudo);
                params.put("firstname",firstname);
                params.put("lastname",lastname);
                params.put("email",email);
                params.put("age",age);
                params.put("city",city);
                params.put("mdp",mdp);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
