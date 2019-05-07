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

import com.example.smartcity.R;
import com.example.smartcity.RequestHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smartcity.SharedPrefManager;
import com.example.smartcity.Urls;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMessage extends AppCompatActivity {

    ListView listView;
    List<String> listIdAuthor = new ArrayList<>();
    List<String> listContent = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        listView = findViewById(R.id.listview);

        final int idAdressee = SharedPrefManager.getInstance(this).getNetworkId();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Urls.URL_GET_MESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("GetMessResponse", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("listIdA");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("listContent");


                            for(int i=0; i< jsonArray1.length(); i++) {
                                String s = (String) jsonArray1.get(i);
                                listIdAuthor.add(s);
                            }

                            for(int i=0; i< jsonArray2.length(); i++) {
                                String s = (String) jsonArray2.get(i);
                                listContent.add(s);
                            }

                            MyAdapter myAdapter = new MyAdapter(getApplicationContext(), listIdAuthor,listContent);
                            listView.setAdapter(myAdapter);

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(ListMessage.this,"Error 1!" + e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListMessage.this,"Error 1!" + error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idAdressee", String.valueOf(idAdressee));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rlistIdAuthor;
        List<String> rlistContent;

        MyAdapter(Context c, List<String> listIdAuthor, List<String> listContent){
            super((Context) c, R.layout.item_message, listIdAuthor);
            this.context = (Context) c;
            this.rlistIdAuthor = listIdAuthor;
            this.rlistContent = listContent;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item_mess = (View) layoutInflater.inflate(R.layout.item_message, parent,false);
            TextView myId = item_mess.findViewById(R.id.idAuthor);
            TextView myContent = item_mess.findViewById(R.id.content);

            //now set our resources on views
            myId.setText(rlistIdAuthor.get(position));
            myContent.setText(rlistContent.get(position));

            return item_mess;
        }
    }




    private void mess(){

        final int idAdressee = SharedPrefManager.getInstance(this).getNetworkId();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Urls.URL_GET_MESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("GetMessResponse", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("listIdA");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("listContent");

                            List<String> listIdAuthor = new ArrayList();
                            List<String> listContent = new ArrayList();

                            for(int i=0; i< jsonArray1.length(); i++) {
                                String s = (String) jsonArray1.get(i);
                                listIdAuthor.add(s);
                            }

                            for(int i=0; i< jsonArray2.length(); i++) {
                                String s = (String) jsonArray2.get(i);
                                listContent.add(s);
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(ListMessage.this,"Error 1!" + e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListMessage.this,"Error 1!" + error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idAdressee", String.valueOf(idAdressee));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



}
