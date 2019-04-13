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
import com.example.smartcity.SharedPrefManager;
import com.example.smartcity.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText pseudo, mdp;
    private Button buttonLogin;
    private Button linkRegist;
    private ProgressBar loading;
    private static String URL_LOGIN = Urls.URL_LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading = findViewById(R.id.login_loading);
        pseudo = findViewById(R.id.login_pseudo);
        mdp = findViewById(R.id.login_MDP);
        buttonLogin = findViewById(R.id.login);
        linkRegist = (Button)findViewById(R.id.link_regist);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pseudo1 = pseudo.getText().toString().trim();
                String mdp1 = mdp.getText().toString().trim();
                Log.d("LoginPseudo",pseudo1);
                Log.d("LoginMdp",mdp1);
                if(pseudo1.isEmpty() || mdp1.isEmpty()){
                    pseudo.setError("Please insert Pseudo");
                    mdp.setError("Please insert Password");
                } else {
                    Login(pseudo1,mdp1);
                }
            }
        });

        linkRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegist = new Intent(Login.this, Register.class);
                startActivity(intentRegist);
            }
        });
    }

    private void Login(final String pseudo, final String mdp) {

        loading.setVisibility(View.VISIBLE);
        buttonLogin.setVisibility(View.GONE);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("LoginResponse", "Login Response: " + response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if(success.equals("1")){

                                for(int i=0; i< jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int id = object.getInt("id");
                                    String pseudo = object.getString("pseudo").trim();
                                    String lastname = object.getString("lastname").trim();
                                    String firstname = object.getString("firstname").trim();
                                    String email = object.getString("email").trim();
                                    int age = object.getInt("age");
                                    String city = object.getString("city").trim();

                                    Log.d("DataUser", pseudo + "," + lastname + "," + firstname + "," + email + "," + age + "," + city);


                                    SharedPrefManager.getInstance(getApplicationContext()).set_DataUser(id,pseudo,firstname,lastname,email,age,city);

                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    //intent.putExtra("pseudo",p);
                                    startActivity(intent);

                                    loading.setVisibility(View.GONE);
                                }
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            buttonLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this,"Error Login 1!" + e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        buttonLogin.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this,"Error Login 2!" + error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pseudo", pseudo);
                params.put("mdp",mdp);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
