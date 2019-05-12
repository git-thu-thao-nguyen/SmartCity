package com.example.smartcity.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOffres extends AppCompatActivity {

    ListView listViewOffres;
    List<String> listNameShop = new ArrayList<>();
    List<String> listOffres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offres);

        listViewOffres = findViewById(R.id.listviewOffre);

        final String hobby = SharedPrefManager.getInstance(this).getHobby();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Urls.URL_GET_OFFRE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("GetOffreResponse", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("listNameShop");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("listOffres");


                            for(int i=0; i< jsonArray1.length(); i++) {
                                String s = (String) jsonArray1.get(i);
                                listNameShop.add(s);
                            }

                            for(int i=0; i< jsonArray2.length(); i++) {
                                String s = (String) jsonArray2.get(i);
                                listOffres.add(s);
                            }

                            if(!listNameShop.isEmpty()){
                                ListOffres.MyAdapterOffre myAdapterOffre = new ListOffres.MyAdapterOffre(getApplicationContext(), listNameShop,listOffres);
                                listViewOffres.setAdapter(myAdapterOffre);
                            }else {
                                Toast.makeText(ListOffres.this,"Not found the offres ",Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListOffres.this,"Error 1!" + e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListOffres.this,"Error 1!" + error.toString(),Toast.LENGTH_SHORT).show();

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
    }

    class MyAdapterOffre extends ArrayAdapter<String> {

        Context context;
        List<String> rlistNameShop;
        List<String> rlistOffres;

        MyAdapterOffre(Context c, List<String> rlistNameShop, List<String> rlistOffres){
            super((Context) c, R.layout.item_offres, listNameShop);
            this.context = (Context) c;
            this.rlistNameShop = listNameShop;
            this.rlistOffres = listOffres;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item_offre = (View) layoutInflater.inflate(R.layout.item_offres, parent,false);
            TextView myName = item_offre.findViewById(R.id.nameShop);
            TextView myDesc = item_offre.findViewById(R.id.offres);

            //now set our resources on views
            myName.setText(rlistNameShop.get(position));
            myDesc.setText(rlistOffres.get(position));

            return item_offre;
        }
    }
}
