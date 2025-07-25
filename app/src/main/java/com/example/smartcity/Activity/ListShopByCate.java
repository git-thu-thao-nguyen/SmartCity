package com.example.smartcity.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class ListShopByCate extends AppCompatActivity {

    ListView listViewShop;
    List<String> listNameShop = new ArrayList<>();
    List<String> listDescShop = new ArrayList<>();
    int fav = R.drawable.ic_favorite_border;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shop_by_cate);

        listViewShop = findViewById(R.id.listviewShop);

        final String nameCate = SharedPrefManager.getInstance(this).getCategory();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Urls.URL_GET_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("GetCateResponse", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("listNameShop");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("listDescShop");

                            for(int i=0; i< jsonArray1.length(); i++) {
                                String s = (String) jsonArray1.get(i);
                                listNameShop.add(s);
                            }

                            for(int i=0; i< jsonArray2.length(); i++) {
                                String s = (String) jsonArray2.get(i);
                                listDescShop.add(s);
                            }
                            Log.d("listttt", String.valueOf(listNameShop));
                            Log.d("listttt", String.valueOf(listDescShop));
                            Log.d("listttt", String.valueOf(getApplicationContext()));

                            if(!listNameShop.isEmpty()){
                                final MyAdapterShop myAdapterShop = new MyAdapterShop(getApplicationContext(), listNameShop,listDescShop,fav);
                                listViewShop.setAdapter(myAdapterShop);

                            }else {
                                Toast.makeText(ListShopByCate.this,"Not found a shop with the category",Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListShopByCate.this,"Error 1!" + e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListShopByCate.this,"Error 1!" + error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nameCate", String.valueOf(nameCate));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    class MyAdapterShop extends ArrayAdapter<String> {

        Context context;
        List<String> rlistNameShop;
        List<String> rlistDescShop;
        int rfav;
        boolean b=false;

        MyAdapterShop(Context c, List<String> rlistNameShop, List<String> rlistDescShop, int img){
            super((Context) c, R.layout.item_shop, listNameShop);
            this.context = (Context) c;
            this.rlistNameShop = listNameShop;
            this.rlistDescShop = listDescShop;
            this.rfav = img;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View item_cate = (View) layoutInflater.inflate(R.layout.item_shop, parent,false);
            TextView myName = item_cate.findViewById(R.id.nameShop);
            TextView myDesc = item_cate.findViewById(R.id.descShop);
            final ImageView favo = item_cate.findViewById(R.id.fav);

            //now set our resources on views
            myName.setText(rlistNameShop.get(position));
            myDesc.setText(rlistDescShop.get(position));
            favo.setImageResource(fav);

            item_cate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yesFavorite(item_cate,favo);
                }
            });
            return item_cate;
        }

        //Favorite

        public void yesFavorite(View v, final ImageView img){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    img.setImageResource(R.drawable.ic_favorite_black);
                    Toast.makeText(ListShopByCate.this, "This shop is added to favorite", Toast.LENGTH_SHORT).show();
                    noFavorite(v,img);
                }
            });
        }

        public void noFavorite(View v,final ImageView img){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    img.setImageResource(R.drawable.ic_favorite_border);
                    Toast.makeText(ListShopByCate.this, "This shop is erased from favorite", Toast.LENGTH_SHORT).show();
                    yesFavorite(v,img);
                }
            });
        }
    }
}
