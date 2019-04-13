package com.example.smartcity.Activity;

import android.app.ProgressDialog;
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

public class EditProfil extends AppCompatActivity {

    private EditText editTextPseudo, editTextFirstName, editTextLastName, editTextEmail, editTextAge, editTextCity, editTextMDP;
    private Button buttonSaveEdit, buttonCancelEdit;
    private ProgressBar loading;

    private static String URL_EDIT = Urls.URL_EDIT_PROFIL;
    int getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        if(!SharedPrefManager.getInstance(this).isLoggin()){
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
        }

        editTextPseudo = findViewById(R.id.pseudo);
        editTextFirstName = findViewById(R.id.firstname);
        editTextLastName = findViewById(R.id.lastname);
        editTextEmail = findViewById(R.id.email);
        editTextAge = findViewById(R.id.age);
        editTextCity = findViewById(R.id.city);
        editTextMDP = findViewById(R.id.MDP);
        buttonSaveEdit = findViewById(R.id.edit);
        buttonCancelEdit = findViewById(R.id.cancel_edit);
        loading = findViewById(R.id.loading);

        getID = SharedPrefManager.getInstance(this).getUserId();
        Log.d("getID", String.valueOf(getID));

        //Set informations default of users
        editTextPseudo.setText(SharedPrefManager.getInstance(this).getUserPseudo());
        editTextFirstName.setText(SharedPrefManager.getInstance(this).getUserFirstName());
        editTextLastName.setText(SharedPrefManager.getInstance(this).getUserLastName());
        editTextEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        editTextAge.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserAge()));
        editTextCity.setText(SharedPrefManager.getInstance(this).getUserCity());


        buttonSaveEdit.setOnClickListener(new View.OnClickListener() {
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
                    editProfil();
                }
            }
        });


        buttonCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfil.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    /*
    private void getUserDetail() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("EditProfilResponse", response.toString());

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if(success.equals("1")){
                                for(int i=0; i< jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String pseudo = object.getString("pseudo").trim();
                                    String lastname = object.getString("lastname").trim();
                                    String firstname = object.getString("firstname").trim();
                                    String email = object.getString("email").trim();
                                    int age = object.getInt("age");
                                    String city = object.getString("city").trim();
                                    String mdp = object.getString("mdp").trim();


                                    Log.d("DataUser", pseudo + "," + lastname + "," + firstname + "," + email + "," + age + "," + city);

                                    editTextPseudo.setText(pseudo);
                                    editTextLastName.setText(lastname);
                                    editTextFirstName.setText(firstname);
                                    editTextEmail.setText(email);
                                    editTextAge.setText(age);
                                    editTextCity.setText(city);
                                    editTextMDP.setText(mdp);
                                }

                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(EditProfil.this,"Error Reading Detail 1!" + e.toString(),Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfil.this,"Error Reading Detail 2!" + error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(getID));
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }
    */

    private void editProfil(){

        loading.setVisibility(View.VISIBLE);
        buttonSaveEdit.setVisibility(View.GONE);

        final int id = getID;
        final String pseudo = this.editTextPseudo.getText().toString().trim();
        final String firstname = this.editTextFirstName.getText().toString().trim();
        final String lastname = this.editTextLastName.getText().toString().trim();
        final String email = this.editTextEmail.getText().toString().trim();
        final String age = this.editTextAge.getText().toString().trim();
        final String city = editTextCity.getText().toString().trim();
        final String mdp = this.editTextMDP.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("EditResponse", response.toString());
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("edit");


                            if(success.equals("1")){

                                Toast.makeText(EditProfil.this,"Save Edit Success!", Toast.LENGTH_SHORT).show();


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

                                    Intent intent = new Intent(EditProfil.this,MainActivity.class);
                                    startActivity(intent);

                                    loading.setVisibility(View.GONE);
                                }
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(EditProfil.this,"Edit Error 1! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            buttonSaveEdit.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfil.this,"Edit Error 2! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        buttonSaveEdit.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(id));
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
