package com.example.projectwiki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Form.FormPengembalian;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText adduser_first, adduser_end, addemail, addnis, addalamat, addpass, addverifpass, addnomor;
    TextView tbl_regis, txt_login;
    Spinner addkelas, addjurusan;
    RadioGroup radiongrup_gender;

    ArrayList<String> spinnerList, spinnerList2;
    ArrayAdapter<String> adapter, adapter2;

    String item;
    String[] kelas = {"Pilih Kelas", "X", "XI", "XII"};

    String item2;
    String[] jurusan = {"Pilih Jurusan", "RPL", "TKRO", "TBSM", "TPTL", "TKJ", "OTOTRONIK"};

    RadioButton radioButton;

    ProgressDialog dialog;

    boolean addpassVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addemail = (EditText) findViewById(R.id.addemail);
        addpass = (EditText) findViewById(R.id.addpass);
        addalamat = (EditText) findViewById(R.id.addalamat);
        addnis = (EditText) findViewById(R.id.addnis);
        adduser_end = (EditText) findViewById(R.id.adduser_end);
        adduser_first = (EditText) findViewById(R.id.adduser_first);
        addverifpass = (EditText) findViewById(R.id.addverifpass);
        addnomor = (EditText) findViewById(R.id.addnomor);
        tbl_regis = (TextView) findViewById(R.id.tbl_regis);
        txt_login = (TextView) findViewById(R.id.txt_login);

        addkelas = (Spinner) findViewById(R.id.addkelas);
        addjurusan = (Spinner) findViewById(R.id.addjurusan);
        spinnerList = new ArrayList<>();
        spinnerList2 = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,kelas);
        adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item,jurusan);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Dalam proses pendaftaran");
        dialog.setCanceledOnTouchOutside(false);

        addkelas.setAdapter(adapter);
        addjurusan.setAdapter(adapter2);

        radiongrup_gender = (RadioGroup) findViewById(R.id.radiongrup_gender);



        tbl_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiData();
            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        addpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        addverifpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        addpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX()>=addpass.getRight()-addpass.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection=addpass.getSelectionEnd();
                        if(addpassVisible)
                        {
                            //set drawable image here
                            addpass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_eyes_open, 0);
                            //for hide password
                            addpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            addpassVisible = false;
                        } else {
                            //set drawable image here
                            addpass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_eyes_close, 0);
                            //for show password
                            addpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            addpassVisible = true;
                        }
                        addpass.setSelection(selection);
                        return  true;
                    }
                }

                return false;
            }
        });
        addverifpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX()>=addverifpass.getRight()-addverifpass.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection=addpass.getSelectionEnd();
                        if(addpassVisible)
                        {
                            //set drawable image here
                            addverifpass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_eyes_open, 0);
                            //for hide password
                            addverifpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            addpassVisible = false;
                        } else {
                            //set drawable image here
                            addverifpass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_eyes_close, 0);
                            //for show password
                            addverifpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            addpassVisible = true;
                        }
                        addverifpass.setSelection(selection);
                        return  true;
                    }
                }

                return false;
            }
        });

    }

    private void validasiData() {

        String userF = adduser_first.getText().toString();
        String userE = adduser_end.getText().toString();
        String nis = addnis.getText().toString();
        String email = addemail.getText().toString();
        String pass = addpass.getText().toString();
        String verif = addverifpass.getText().toString();
        String alamat = addalamat.getText().toString();
        String no_hp = addnomor.getText().toString();
        String jurusan = addjurusan.getSelectedItem().toString();
        String kelas = addkelas.getSelectedItem().toString();
        String verifpassword = addverifpass.getText().toString().trim();

        if(TextUtils.isEmpty(userF)){
            adduser_first.setError("Tolong Diisi");
            adduser_first.requestFocus();
        } else if (TextUtils.isEmpty(nis)){
            addnis.setError("Tolong Diisi");
            addnis.requestFocus();
        }else if (nis.length() < 10){
            addnis.setError("Wajib 10");
            addnis.requestFocus();
        }else if (TextUtils.isEmpty(email)){
            addemail.setError("Tolong Diisi");
            addemail.requestFocus();
        }else if (radiongrup_gender.getCheckedRadioButtonId() == -1){
            radiongrup_gender.requestFocus();
        }else if (kelas.equals("Pilih Kelas")){
            addkelas.requestFocus();
        }else if (jurusan.equals("Pilih Jurusan")){
            addjurusan.requestFocus();
        } else if (TextUtils.isEmpty(no_hp)){
            addnomor.setError("Tolong Diisi");
            addnomor.requestFocus();
        } else if (no_hp.length() < 12){
            addnomor.setError("Wajib 12");
            addnomor.requestFocus();
        } else if (TextUtils.isEmpty(alamat)){
            addalamat.setError("Tolong Diisi");
            addalamat.requestFocus();
        } else if (TextUtils.isEmpty(pass)){
            addpass.setError("Tolong Diisi");
            addpass.requestFocus();
        } else if (pass.length() < 8){
            addpass.setError("Wajib 8 karakter");
            addpass.requestFocus();
        } else if (!pass.equals(verifpassword)){
            addverifpass.setError("Password tidak sesuai");
            addverifpass.requestFocus();
        } else if (TextUtils.isEmpty(verifpassword)){
            addverifpass.setError("Tolong Diisi");
            addverifpass.requestFocus();
        } else {
            CreateToServer();
        }

    }

//    public void CreateToServer(){
//        String nis = addnis.getText().toString();
//        String userF = adduser_first.getText().toString();
//        String userE = adduser_end.getText().toString();
//        String name = userF+" "+userE;
//        String pass = addpass.getText().toString();
//        String email = addemail.getText().toString();
//        String no = addnomor.getText().toString();
//        String address = addalamat.getText().toString();
//        String kelas = addkelas.getSelectedItem().toString();
//        String jurusan = addjurusan.getSelectedItem().toString();
//        String kls =kelas +"-"+ jurusan;
//
//
//        //radiobuttonH
//        int idTerpilihH = radiongrup_gender.getCheckedRadioButtonId();
//        radioButton = (RadioButton) findViewById(idTerpilihH);
//        String jk = radioButton.getText().toString();
//        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//        dialog.show();
//        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, AllDb.SERVER_REGISTER_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        dialog.dismiss();
//
////                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_REGISTER_URL,
////                                new Response.Listener<String>() {
////                                    @Override
////                                    public void onResponse(String response) {
////                                        dialog.dismiss();
////                                        Toast.makeText(Register.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
////                                        finish();
////                                    }
////                                }, new Response.ErrorListener() {
////                            @Override
////                            public void onErrorResponse(VolleyError error) {
////                                dialog.dismiss();
////                            }
////                        }) {
////                            @Override
////                            protected Map<String, String> getParams() throws AuthFailureError {
////                                Map<String, String> params = new HashMap<String, String>();
////                                params.put("nis", nis);
////                                params.put("email", email);
////                                params.put("password", pass);
////                                params.put("nama", name);
////                                params.put("kelas","kls");
////                                params.put("no_hp","12902999");
////                                params.put("jk", "aasasd");
////                                params.put("alamat", address);
////                                params.put("type_user", "user");
////                                return  params;
////                            }
////                        };
////                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);
//
//                        Toast.makeText(Register.this, "Berhasil", Toast.LENGTH_LONG).show();
////                        Intent intent = new Intent (Register.this, Login.class);
////                        startActivity(intent);
////                        finish();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//                Toast.makeText(Register.this, "gagal", Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("nis", nis);
//                params.put("email", email);
//                params.put("password", pass);
//                params.put("nama", name);
//                params.put("kelas", kls);
//                params.put("no_hp",no);
//                params.put("jk", jk);
//                params.put("alamat", address);
//                return  params;
//            }
//        };
////        RequestHandler.getInstance(this).addToRequestQueue(stringRequest3);
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(stringRequest3);
//    }


    //create baru
    public void CreateToServer(){

        String nis = addnis.getText().toString();
        String userF = adduser_first.getText().toString();
        String userE = adduser_end.getText().toString();
        String name = userF+" "+userE;
        String pass = addpass.getText().toString();
        String email = addemail.getText().toString();
        String no = addnomor.getText().toString();
        String address = addalamat.getText().toString();
        String kelas = addkelas.getSelectedItem().toString();
        String jurusan = addjurusan.getSelectedItem().toString();
        String kls =kelas +"-"+ jurusan;


        //radiobuttonH
        int idTerpilihH = radiongrup_gender.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(idTerpilihH);
        String jk = radioButton.getText().toString();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_REGISTER_URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_NOTIF_URL,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                dialog.dismiss();
//                                Toast.makeText(Register.this, "Registrasi Berhasil", Toast.LENGTH_LONG).show();
//                                finish();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        dialog.dismiss();
//                        Toast.makeText(Register.this, "Registrasi Gagal", Toast.LENGTH_LONG).show();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("email", email);
//                        params.put("bpid", "");
//                        params.put("tgl_kirim", currentDate);
//                        params.put("judul", "Selamat Datang di Aplikasi Aset SMKN Situraja");
//                        params.put("keterangan", "Selamat anda telah bergabung dengan aplikasi Aset SMKN Situraja, dengan ini anda dapat melakukan peminjaman aset-aset di SMN Situraja");
//                        return params;
//                    }
//                };
//                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                Toast.makeText(Register.this, "Berhasil", Toast.LENGTH_LONG).show();
                Intent intent = new Intent (Register.this, Login.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(Register.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                params.put("uid", "");
                params.put("nis", nis);
                params.put("email", email);
                params.put("password", pass);
                params.put("nama", name);
                params.put("kelas", kls);
                params.put("no_hp", no);
                params.put("jk", jk);
                params.put("alamat", address);
                params.put("type_user", "user");
                params.put("img_user", "");
                return  params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
