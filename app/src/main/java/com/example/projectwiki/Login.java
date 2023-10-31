//package com.example.projectwiki;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.text.method.HideReturnsTransformationMethod;
//import android.text.method.PasswordTransformationMethod;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Login extends AppCompatActivity {
//
//    List<DataUserModel> dataModels;
//
//    EditText addemail,addpassword;
//    TextView btnlogin, btndaftar, txtemail2;
//    ProgressDialog dialog;
//    boolean addpassVisible;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        addemail = (EditText) findViewById(R.id.addemail);
//        addpassword = (EditText) findViewById(R.id.addpass);
//        btnlogin = (TextView) findViewById(R.id.tbl_login);
//        btndaftar = (TextView) findViewById(R.id.txt_daft);
//        txtemail2 = (TextView) findViewById(R.id.txtemail2);
//
//        dialog = new ProgressDialog(this);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setMessage("Tolong tunggu.....");
//        dialog.setCancelable(false);
//        dialog.setTitle("Data sedang di upload");
//        dialog.setCanceledOnTouchOutside(false);
//
//        dataModels = new ArrayList<>();
//
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validasiData();
//            }
//        });
//
//        btndaftar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Login.this, Register.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        addemail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString()!=null)
//                {
//                    getData4(s.toString());
//                }
//                else
//                {
//                    getData4("");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        addpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        addpassword.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int Right=2;
//                if(event.getAction() == MotionEvent.ACTION_UP){
//                    if(event.getRawX()>=addpassword.getRight()-addpassword.getCompoundDrawables()[Right].getBounds().width())
//                    {
//                        int selection=addpassword.getSelectionEnd();
//                        if(addpassVisible)
//                        {
//                            //set drawable image here
//                            addpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_eyes_open, 0);
//                            //for hide password
//                            addpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                            addpassVisible = false;
//                        } else {
//                            //set drawable image here
//                            addpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_eyes_close, 0);
//                            //for show password
//                            addpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                            addpassVisible = true;
//                        }
//                        addpassword.setSelection(selection);
//                        return  true;
//                    }
//                }
//
//                return false;
//            }
//        });
//
//    }
//
//    private void getData4(String data) {
//        String email = addemail.getText().toString();
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER+email, null,
//                new Response.Listener<JSONArray>(){
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        for (int i = 0; i <= response.length();i++){
//                            try {
////                        String user = firebaseAuth.getUid();
////                        JSONObject jsonObject = response.getJSONObject(i);
////                        dataModels.add(new DataModel(
////                                jsonObject.getString("namahewan"),
////                                jsonObject.getString("jk"),
////                                jsonObject.getString("statushewan"),
////                                jsonObject.getString("id"),
////                                jsonObject.getString("imaghewan"),
////                                jsonObject.getString("nokandang"),
////                                jsonObject.getString("statuskesehatan")
////                        ));
//                                JSONObject dataObject = response.getJSONObject(i);
//
//                                DataUserModel dataUserModel = new DataUserModel();
//                                dataUserModel.setUid(dataObject.getString("uid").toString());
//                                dataUserModel.setNis(dataObject.getString("nis").toString());
//                                dataUserModel.setEmail(dataObject.getString("email").toString());
//                                dataUserModel.setPassword(dataObject.getString("password").toString());
//                                dataUserModel.setNama(dataObject.getString("nama").toString());
//                                dataUserModel.setKelas(dataObject.getString("kelas").toString());
//                                dataUserModel.setNo_hp(dataObject.getString("no_hp").toString());
//                                dataUserModel.setJk(dataObject.getString("jk").toString());
//                                dataUserModel.setAlamat(dataObject.getString("alamat").toString());
//                                dataUserModel.setType_user(dataObject.getString("type_user").toString());
//                                dataModels.add(dataUserModel);
//
//                                txtemail2.setText(dataObject.getString("type_user").toString());
//
//                            }catch (JSONException e){
//                                e.printStackTrace();
//                            }
//                        }
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        requestQueue.add(jsonArrayRequest);
//    }
//
//    private void validasiData() {
//        String email = addemail.getText().toString();
//        String pass = addpassword.getText().toString();
//        String type_user = txtemail2.getText().toString();
//
//        if(TextUtils.isEmpty(email)){
//            addemail.setError("Tolong Diisi");
//            addemail.requestFocus();
//        } else if (TextUtils.isEmpty(pass)){
//            addpassword.setError("Tolong Diisi");
//            addpassword.requestFocus();
//        } else {
//            getdataUser(email,pass,type_user);
//        }
//    }
//
//    private void getdataUser(final String email, final String pass, final String type_user) {
//        dialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,AllDb.SERVER_LOGIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String resp = jsonObject.getString("server_response");
//                            if (resp.equals("[{\"status\":\"OK\"}]")){
//                                dialog.dismiss();
//                                Toast.makeText(Login.this,"Login Berhasil", Toast.LENGTH_SHORT).show();
//                            } else  {
//                                dialog.dismiss();
//                                Toast.makeText(Login.this, resp, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String,String>();
//                params.put("email", email);
//                params.put("password", pass);
//                params.put("type_user", type_user);
//                return  params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(stringRequest);
//    }
//
//
//}
//
//



package com.example.projectwiki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Admin.HomeAdmin;
import com.example.projectwiki.Admin.LoginAdmin;
import com.example.projectwiki.Fragmen.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {

    List<DataUserModel> dataModels;

    EditText addemail,addpassword;
    TextView btnlogin, btndaftar, txtemail2;
    ProgressDialog dialog;
    boolean addpassVisible;
    SharedPreferences sesi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addemail = (EditText) findViewById(R.id.addemail);
        addpassword = (EditText) findViewById(R.id.addpass);
        btnlogin = (TextView) findViewById(R.id.tbl_login);
        btndaftar = (TextView) findViewById(R.id.txt_daft);
        txtemail2 = (TextView) findViewById(R.id.txtemail2);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Data sedang di upload");
        dialog.setCanceledOnTouchOutside(false);

        dataModels = new ArrayList<>();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiData();
            }
        });

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        addemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString()!=null)
                {
                    getData4(s.toString());
                }
                else
                {
                    getData4("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        addpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX()>=addpassword.getRight()-addpassword.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection=addpassword.getSelectionEnd();
                        if(addpassVisible)
                        {
                            //set drawable image here
                            addpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_eyes_open, 0);
                            //for hide password
                            addpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            addpassVisible = false;
                        } else {
                            //set drawable image here
                            addpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_eyes_close, 0);
                            //for show password
                            addpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            addpassVisible = true;
                        }
                        addpassword.setSelection(selection);
                        return  true;
                    }
                }

                return false;
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("sesi_login", MODE_PRIVATE);
        if(sharedPreferences.contains("email")){
//            startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
//            finish();
            String type = sharedPreferences.getString("typeuser",null);
            if(type.equals("admin")){
                startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                finish();
            }
        }
        String email = sharedPreferences.getString("email",null);
        String type = sharedPreferences.getString("typeuser",null);


//        if(textw.equals("admin")){
//            if(sharedPreferences.contains("email")){
//            startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
//            finish();
//        }
//        } else {
//            if(sharedPreferences.contains("email")){
//                startActivity(new Intent(getApplicationContext(), Dashboard.class));
//                finish();
//            }
//        }

//        if(sharedPreferences.contains("email")){
//            startActivity(new Intent(getApplicationContext(), Dashboard.class));
//            finish();
//        }

//        SharedPreferences sesiadmin = getSharedPreferences("sesiadmin_login", MODE_PRIVATE);
//        if(sesiadmin.contains("email")){
//            startActivity(new Intent(getApplicationContext(), Dashboard.class));
//            finish();
//        }

//        if(sesi.getString("type_user", null) != null){
//            txtemail2.setText(sesi.getString("type_user", null));
//        }
//
//        String type = txtemail2.getText().toString();
//        if(type.equals("user")){
//            SharedPreferences sesi = getSharedPreferences("sesi_login", MODE_PRIVATE);
//            if(sesi.contains("email")){
//                startActivity(new Intent(getApplicationContext(), Dashboard.class));
//
//            }
//        } else if (type.equals("admin")){
//            SharedPreferences sesi = getSharedPreferences("sesi_login", MODE_PRIVATE);
//            if(sesi.contains("email")){
//                startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
//            }
//        }

    }

    private void getData4(String data) {
        String email = addemail.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER+email, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                DataUserModel dataUserModel = new DataUserModel();
                                dataUserModel.setUid(dataObject.getString("uid").toString());
                                dataUserModel.setNis(dataObject.getString("nis").toString());
                                dataUserModel.setEmail(dataObject.getString("email").toString());
                                dataUserModel.setPassword(dataObject.getString("password").toString());
                                dataUserModel.setNama(dataObject.getString("nama").toString());
                                dataUserModel.setKelas(dataObject.getString("kelas").toString());
                                dataUserModel.setNo_hp(dataObject.getString("no_hp").toString());
                                dataUserModel.setJk(dataObject.getString("jk").toString());
                                dataUserModel.setAlamat(dataObject.getString("alamat").toString());
                                dataUserModel.setType_user(dataObject.getString("type_user").toString());
                                dataModels.add(dataUserModel);

                                txtemail2.setText(dataObject.getString("type_user").toString());

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void validasiData() {
        String email = addemail.getText().toString();
        String pass = addpassword.getText().toString();
        String type_user = txtemail2.getText().toString();


        if(TextUtils.isEmpty(email)){
            addemail.setError("Tolong Diisi");
            addemail.requestFocus();
        } else if (TextUtils.isEmpty(pass)){
            addpassword.setError("Tolong Diisi");
            addpassword.requestFocus();
        } else{
            getdataUser(email,pass,type_user);
        }
//        else if (type_user.equals("admin")){
//            getdataUser2(email,pass,type_user);
//        }
    }

//    private void getdataUser2(final String email, final String pass, final String type_user) {
//        dialog.show();
//
//        final SharedPreferences.Editor sesiadmin = getSharedPreferences("sesiadmin_login", MODE_PRIVATE).edit();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,AllDb.SERVER_LOGIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String resp = jsonObject.getString("server_response");
//                            if (resp.equals("[{\"status\":\"OK\"}]")){
//                                dialog.dismiss();
//                                sesiadmin.putString("email", email);
//                                sesiadmin.putString("password", pass);
//                                sesiadmin.putString("typeuser", type_user);
//                                sesiadmin.commit();
//
////                                Toast.makeText(Login.this,"Login Berhasil", Toast.LENGTH_SHORT).show();
//
//                                Intent intent = new Intent(getApplicationContext(), HomeAdmin.class);
//                                startActivity(intent);
////                                if (type_user.equals("admin")){
////                                    Intent intent = new Intent(getApplicationContext(), HomeAdmin.class);
////                                    startActivity(intent);
////                                } else {
////                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
////                                    startActivity(intent);
////                                }
//                            } else  {
//                                dialog.dismiss();
//                                Toast.makeText(Login.this, resp, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String,String>();
//                params.put("email", email);
//                params.put("password", pass);
//                params.put("type_user", type_user);
//                return  params;
//            }
//        };
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//    }

    private void getdataUser(final String email, final String pass, final String type_user) {
        dialog.show();

        final SharedPreferences.Editor sesi = getSharedPreferences("sesi_login", MODE_PRIVATE).edit();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,AllDb.SERVER_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String resp = jsonObject.getString("server_response");
                            if (resp.equals("[{\"status\":\"OK\"}]")){
                                dialog.dismiss();
                                sesi.putString("email", email);
                                sesi.putString("password", pass);
                                sesi.putString("typeuser", type_user);
                                sesi.commit();

//                                Toast.makeText(Login.this,"Login Berhasil", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//                                startActivity(intent);

                                if (type_user.equals("admin")){
                                    Intent in = new Intent(getApplicationContext(), HomeAdmin.class);
                                    startActivity(in);
                                } else if (type_user.equals("user")){
                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(intent);
                                }

                            } else  {
                                dialog.dismiss();
                                Toast.makeText(Login.this, resp, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("email", email);
                params.put("password", pass);
                params.put("type_user", type_user);
                return  params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


}


