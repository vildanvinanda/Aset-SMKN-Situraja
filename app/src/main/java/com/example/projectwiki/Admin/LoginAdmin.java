//package com.example.projectwiki.Admin;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
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
//import com.example.projectwiki.AllDb;
//import com.example.projectwiki.Dashboard;
//import com.example.projectwiki.DataUserModel;
//import com.example.projectwiki.Login;
//import com.example.projectwiki.R;
//import com.example.projectwiki.RequestHandler;
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
//public class LoginAdmin extends AppCompatActivity {
//
//    List<DataUserModel> dataModels;
//
//    EditText addemail,addpassword;
//    TextView btnlogin, btnkembali, txtemail2;
//    ProgressDialog dialog;
//    boolean addpassVisible;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_admin);
//
//        addemail = (EditText) findViewById(R.id.addemail);
//        addpassword = (EditText) findViewById(R.id.addpass);
//        btnlogin = (TextView) findViewById(R.id.tbl_login);
//        btnkembali = (TextView) findViewById(R.id.txt_daft);
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
//        btnkembali.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginAdmin.this, Login.class);
//                startActivity(intent);
//            }
//        });
//
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validasiData();
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
//        SharedPreferences sesi1 = getSharedPreferences("sesiadmin_login", MODE_PRIVATE);
//        if(sesi1.contains("email")){
//            startActivity(new Intent(getApplicationContext(), Dashboard.class));
//            finish();
//        }
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
//
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
//
//        if(TextUtils.isEmpty(email)){
//            addemail.setError("Tolong Diisi");
//            addemail.requestFocus();
//        } else if (TextUtils.isEmpty(pass)){
//            addpassword.setError("Tolong Diisi");
//            addpassword.requestFocus();
//        } else if(type_user.equals("user")) {
//            Toast.makeText(this, "Anda bukan admin", Toast.LENGTH_SHORT).show();
//        } else{
//            getdataUser(email,pass,type_user);
//        }
////        else if (type_user.equals("admin")){
////            getdataUser2(email,pass,type_user);
////        }
//    }
//
//    private void getdataUser(final String email, final String pass, final String type_user) {
//        dialog.show();
//
//        final SharedPreferences.Editor sesi = getSharedPreferences("sesiadmin_login", MODE_PRIVATE).edit();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,AllDb.SERVER_LOGIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String resp = jsonObject.getString("server_response");
//                            if (resp.equals("[{\"status\":\"OK\"}]")){
//                                dialog.dismiss();
//                                sesi.putString("email", email);
//                                sesi.putString("password", pass);
//                                sesi.putString("typeuser", type_user);
//                                sesi.commit();
//
////                                Toast.makeText(Login.this,"Login Berhasil", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), HomeAdmin.class);
//                                startActivity(intent);
//
////                                if (type_user.equals("admin")){
////                                    Intent in = new Intent(getApplicationContext(), HomeAdmin.class);
////                                    startActivity(in);
////                                } else if (type_user.equals("user")){
////                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
////                                    startActivity(intent);
////                                }
//                            } else  {
//                                dialog.dismiss();
//                                Toast.makeText(LoginAdmin.this, resp, Toast.LENGTH_SHORT).show();
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
//}



//INI BARU
package com.example.projectwiki.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
import com.example.projectwiki.AllDb;
import com.example.projectwiki.Dashboard;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Login;
import com.example.projectwiki.R;
import com.example.projectwiki.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginAdmin extends AppCompatActivity {


    List<DataUserModel> dataModels;

    EditText addemail,addpassword;
    TextView btnlogin, btnkembali, txtemail2;
    ProgressDialog dialog;
    boolean addpassVisible;

    int success;
    ConnectivityManager conMgr;


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "user_id";
    public final static String TAG_USERTYPE = "user_type";


    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String user_id, username,user_type;

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    final String MESSAGE_NO_INTERNET_ACCESS = "No Internet Connection";
    final String MESSAGE_CANNOT_BE_EMPTY = "Kolom Tidak Boleh Kosong";
    final String MESSAGE_LOGIN = "Logging in ...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        addemail = (EditText) findViewById(R.id.addemail);
        addpassword = (EditText) findViewById(R.id.addpass);
        btnlogin = (TextView) findViewById(R.id.tbl_login);
        btnkembali = (TextView) findViewById(R.id.txt_daft);
        txtemail2 = (TextView) findViewById(R.id.txtemail2);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Data sedang di upload");
        dialog.setCanceledOnTouchOutside(false);

        dataModels = new ArrayList<>();

        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAdmin.this, Login.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiData();
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

        SharedPreferences sesi1 = getSharedPreferences("sesiadmin_login", MODE_PRIVATE);
        if(sesi1.contains("email")){
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }
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
        } else if(type_user.equals("user")) {
            Toast.makeText(this, "Anda bukan admin", Toast.LENGTH_SHORT).show();
        } else{
            getdataUser(email,pass,type_user);
        }
//        else if (type_user.equals("admin")){
//            getdataUser2(email,pass,type_user);
//        }
    }

    private void getdataUser(final String email, final String pass, final String type_user) {
        dialog.show();

        final SharedPreferences.Editor sesi = getSharedPreferences("sesiadmin_login", MODE_PRIVATE).edit();
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
                                Intent intent = new Intent(getApplicationContext(), HomeAdmin.class);
                                startActivity(intent);

//                                if (type_user.equals("admin")){
//                                    Intent in = new Intent(getApplicationContext(), HomeAdmin.class);
//                                    startActivity(in);
//                                } else if (type_user.equals("user")){
//                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//                                    startActivity(intent);
//                                }
                            } else  {
                                dialog.dismiss();
                                Toast.makeText(LoginAdmin.this, resp, Toast.LENGTH_SHORT).show();
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