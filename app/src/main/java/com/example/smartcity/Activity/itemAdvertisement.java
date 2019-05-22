package com.example.smartcity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class itemAdvertisement extends AppCompatActivity {

    private List<String> listImg = new ArrayList<>();
    private ImageView imageView;
    private Button next, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_advertisement);

        imageView = findViewById(R.id.img_ad);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);

        final String hobby = SharedPrefManager.getInstance(this).getHobby();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Urls.URL_GET_AD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("AdvertisementResponse", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("listImg");

                            for(int i=0; i< jsonArray.length(); i++) {
                                String s = (String) jsonArray.get(i);
                                listImg.add(s);
                            }

                            if(!listImg.isEmpty()){
                                //TODO

                                Picasso.get()
                                        .load(listImg.get(0).toString())
                                        .into(imageView);

                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Random random = new Random();
                                        int randomNumber = random.nextInt(4);
                                        //Load image
                                        Picasso.get()
                                                .load(listImg.get(randomNumber).toString())
                                                .into(imageView);
                                    }
                                });
                            }else {
                                Toast.makeText(itemAdvertisement.this,"Not Advertisement ",Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(itemAdvertisement.this,"Error 1!" + e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(itemAdvertisement.this,"Error 1!" + error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hobby", String.valueOf(hobby));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(itemAdvertisement.this, MainActivity.class));
                finish();
            }
        });
    }
}
