package com.example.projectwiki.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.Dashboard;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Fragmen.Profil;
import com.example.projectwiki.Login;
import com.example.projectwiki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeAdmin extends AppCompatActivity {

    List<DataUserModel> dataModels;
    TextView txtuser,txtuidvisible,angka_kembali, angka_pinjam,jdl2;

    RelativeLayout btn_listbar,btn_users, btn_tambbarang,btn_faq,btn_pengajuan,btn_profil;

    public static final String EXTRA_HA_EMAIL = "email";
    public static final String EXTRA_HA_TYPE = "type_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        txtuser = (TextView) findViewById(R.id.txtuser);
        angka_pinjam = (TextView) findViewById(R.id.angka_pinjam);
        txtuidvisible = (TextView) findViewById(R.id.txtuidvisible);
        angka_kembali = (TextView) findViewById(R.id.angka_kembali);

        btn_listbar = (RelativeLayout) findViewById(R.id.btn_listbar);
        btn_profil = (RelativeLayout) findViewById(R.id.btn_profil);
        btn_users = (RelativeLayout) findViewById(R.id.btn_users);
        btn_pengajuan = (RelativeLayout) findViewById(R.id.btn_pengajuan);
        btn_faq = (RelativeLayout) findViewById(R.id.btn_faq);
        btn_tambbarang = (RelativeLayout) findViewById(R.id.btn_tambbarang);


        btn_listbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, ListPengembalian.class);
                startActivity(intent);
            }
        });

        jdl2 = (TextView) findViewById(R.id.jdl2);
        dataModels = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("sesi_login", MODE_PRIVATE);
//        SharedPreferences sesi = getSharedPreferences("sesi_login", MODE_PRIVATE);
//
//        if(sesi.contains("email")){
//            Intent intent = new Intent(HomeAdmin.this, Login.class);
//            startActivity(intent);
//            finish();
//            return;
//        }


        String textw = sharedPreferences.getString("email", null).toString();

        btn_tambbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, DaftarBarang.class);
                intent.putExtra(EXTRA_HA_TYPE, "admin");
                startActivity(intent);
            }
        });
        btn_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, DaftarPengguna.class);
                startActivity(intent);
            }
        });
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, DaftarFaq.class);
                startActivity(intent);
            }
        });
        btn_pengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, DaftarPengajuan.class);
                startActivity(intent);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textw = sharedPreferences.getString("email", null).toString();
                Intent intent = new Intent(HomeAdmin.this, ProfilAdmin.class);
                intent.putExtra(EXTRA_HA_EMAIL, textw);
                startActivity(intent);
            }
        });

        getData7();
        getData6();
        getData4(textw);

    }
    private void getData4(String textw) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER+textw, null,
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

                                txtuser.setText(dataObject.getString("nama").toString());
                                txtuidvisible.setText(dataObject.getString("uid").toString());

                                String cel = dataObject.getString("uid").toString();
                                String uuid = txtuidvisible.getText().toString();


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

    private void getData7() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AllDb.SERVER_COUNT_PB_ADMIN_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String angka = response.getString("test");
                            angka_kembali.setText(angka);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                angka_pinjam.setText(response.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                angka_pinjam.setText("error");
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);


    }
    private void getData6() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AllDb.SERVER_COUNT_PB_ADMIN_URL2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String angka = response.getString("test");
                            angka_pinjam.setText(angka);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                angka_pinjam.setText(response.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                angka_pinjam.setText("error");
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);


    }

}