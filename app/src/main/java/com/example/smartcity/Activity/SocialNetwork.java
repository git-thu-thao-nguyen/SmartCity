package com.example.smartcity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.smartcity.Model.WeatherModel.Main;
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

public class SocialNetwork extends AppCompatActivity {

    private TextView textViewNomR, textViewTypeNetwork, textViewFollowers;
    private Button boutonMessage, boutonFollow, boutonListMess ,boutonBack;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_network);

        if(!SharedPrefManager.getInstance(this).isLoggin()){
            Intent i = new Intent(this, Login.class);
            finish();
            startActivity(i);
        }

        textViewNomR = findViewById(R.id.TextViewNomR);
        textViewTypeNetwork = findViewById(R.id.TextViewTypeNetwork);
        textViewFollowers = findViewById(R.id.TextViewFollowers);

        textViewNomR.setText(SharedPrefManager.getInstance(this).getNetworkName());
        textViewTypeNetwork.setText(SharedPrefManager.getInstance(this).getNetworkType());

        boutonFollow = findViewById(R.id.BoutonFollow);
        boutonMessage = findViewById(R.id.BoutonMessage);
        boutonListMess = findViewById(R.id.Bouton_list_mess);
        boutonBack = findViewById(R.id.Bouton_back);

        setButtonFollow();

        boutonMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String isfollowed = SharedPrefManager.getInstance(getApplicationContext()).getIsFollowed();
                if(SharedPrefManager.getInstance(getApplicationContext()).getStatusNetwork() == 1 || isfollowed =="1") {
                    startActivity(new Intent(SocialNetwork.this, AddMessage.class));
                    //finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Our social networking is private, please follow", Toast.LENGTH_LONG).show();
                }
            }
        });

        boutonListMess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String isfollowed = SharedPrefManager.getInstance(getApplicationContext()).getIsFollowed();
                if(SharedPrefManager.getInstance(getApplicationContext()).getStatusNetwork() == 1 || isfollowed =="1") {
                    startActivity(new Intent(SocialNetwork.this, ListMessage.class));
                    //finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Our social networking is private, please follow", Toast.LENGTH_LONG).show();
                }
            }
        });

        boutonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SocialNetwork.this, MainActivity.class));
                finish();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Following...");

    }


    private void setButtonFollow(){

        final int idUser = SharedPrefManager.getInstance(this).getUserId();
        final int idNet = SharedPrefManager.getInstance(this).getNetworkId();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_IS_FOLLOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("isFollowResponse", response.toString());
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            textViewFollowers.setText(obj.getString("countFollower"));
                            if (obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).set_isFollowed("1");
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                boutonFollow.setText("Unfollow");
                                boutonFollow.setBackgroundResource(R.drawable.unfollow);
                                boutonFollow.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Unfollow();
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                boutonFollow.setText("Follow");
                                boutonFollow.setBackgroundResource(R.drawable.follow);
                                boutonFollow.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Follow();
                                    }
                                });
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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUser", String.valueOf(idUser));
                params.put("idNet", String.valueOf(idNet));
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void Follow() {

        final int idUser = SharedPrefManager.getInstance(this).getUserId();
        final int idNet = SharedPrefManager.getInstance(this).getNetworkId();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_FOLLOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("FollowResponse", response.toString());
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            textViewFollowers.setText(obj.getString("countFollower"));
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).set_isFollowed("1");
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                boutonFollow.setText("Unfollow");
                                boutonFollow.setBackgroundResource(R.drawable.unfollow);
                                boutonFollow.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Unfollow();
                                    }
                                });
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
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUser", String.valueOf(idUser));
                params.put("idNet", String.valueOf(idNet));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void Unfollow() {

        final int idUser = SharedPrefManager.getInstance(this).getUserId();
        final int idNet = SharedPrefManager.getInstance(this).getNetworkId();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_UNFOLLOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("UnfollowResponse", response.toString());
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            textViewFollowers.setText(obj.getString("countFollower"));
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).set_isFollowed("0");
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                boutonFollow.setText("Follow");
                                boutonFollow.setBackgroundResource(R.drawable.follow);
                                boutonFollow.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Follow();
                                    }
                                });
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
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUser", String.valueOf(idUser));
                params.put("idNet", String.valueOf(idNet));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
