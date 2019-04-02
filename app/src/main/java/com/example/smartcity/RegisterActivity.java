package com.example.smartcity;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextPseudo, editTextEmail, editTextAge, editTextMDP;
    private Button buttonRegist;
    private ProgressBar loading;
    private static String URL_REGIST = "http://192.168.1.17/HTML/android_register_login/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        loading = findViewById(R.id.loading);
        editTextPseudo = findViewById(R.id.pseudo);
        editTextEmail = findViewById(R.id.email);
        editTextAge = findViewById(R.id.age);
        editTextMDP = findViewById(R.id.MDP);
        buttonRegist = findViewById(R.id.regist);

        buttonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();
            }
        });
    }

    private void Regist(){
        loading.setVisibility(View.VISIBLE);
        buttonRegist.setVisibility(View.GONE);

        final String pseudo = this.editTextPseudo.getText().toString().trim();
        final String email = this.editTextEmail.getText().toString().trim();
        final String age = this.editTextAge.getText().toString().trim();
        final String mdp = this.editTextMDP.getText().toString().trim();
        Log.d("Hihi",pseudo);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("haha", "Register Response: " + response.toString());
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(RegisterActivity.this,"Register Success!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this,"Register Error 1! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            buttonRegist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,"Register Error 2! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        buttonRegist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pseudo",pseudo);
                params.put("email",email);
                params.put("age",age);
                params.put("mdp",mdp);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
