package com.example.smartcity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class AddMessage extends AppCompatActivity {

    private EditText editTextMessage;
    private Button boutonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        editTextMessage = findViewById(R.id.postmessage);
        boutonSend = findViewById(R.id.buttonSend);

        boutonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addMessage();
            }
        });
    }

    private void addMessage() {

        final int idAuthor = SharedPrefManager.getInstance(this).getUserId();
        final int idAdressee = SharedPrefManager.getInstance(this).getNetworkId();
        final String content = editTextMessage.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_ADD_MESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AddMessResponse", response.toString());

                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), SocialNetwork.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idAuthor", String.valueOf(idAuthor));
                params.put("idAdressee", String.valueOf(idAdressee));
                params.put("content", content);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
