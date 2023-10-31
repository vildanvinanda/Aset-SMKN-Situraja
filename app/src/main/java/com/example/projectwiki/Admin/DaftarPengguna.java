package com.example.projectwiki.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Adapter.AdapterDaftarPengajuan;
import com.example.projectwiki.Adapter.AdapterDaftarUser;
import com.example.projectwiki.Adapter.AdapterFaq;
import com.example.projectwiki.Adapter.AdapterPengembalianBarang;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DetailPengguna;
import com.example.projectwiki.FaqModel;
import com.example.projectwiki.Model.ModelPengajuan;
import com.example.projectwiki.Model.ModelUser;
import com.example.projectwiki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaftarPengguna extends AppCompatActivity implements AdapterDaftarUser.OnItemClickListener{


    EditText kolomsearch;
    ImageView btn_back;
    RecyclerView recdafuser, recdafuser2;
    TextView addjmlorang;

    List<ModelUser> modelUsers;
    private AdapterDaftarUser adapterDaftarUser;


    public static final String EXTRA_DU_NAMA = "nama";
    public static final String EXTRA_DU_UID = "uid";
    public static final String EXTRA_DU_EMAIL = "email";
    public static final String EXTRA_DU_NIS = "nis";
    public static final String EXTRA_DU_KELAS = "kls";
    public static final String EXTRA_DU_NOHP = "nohp";
    public static final String EXTRA_DU_JK = "jk";
    public static final String EXTRA_DU_ALAMAT = "address";
    public static final String EXTRA_DU_IMG = "img";
    public static final String EXTRA_DP2_TOTAL = "total_bpinjam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pengguna);

        kolomsearch = findViewById(R.id.kolomsearch);
        btn_back = findViewById(R.id.btn_back);
        recdafuser = findViewById(R.id.recdafuser);
        addjmlorang = findViewById(R.id.addjmlorang);
        recdafuser2 = findViewById(R.id.recdafuser2);

        modelUsers = new ArrayList<>();
        recdafuser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recdafuser2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
        getDataUser();
        kolomsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString()!=null)
                {
                    getData2(s.toString());
                }
                else
                {
                    getData2("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarPengguna.this, HomeAdmin.class);
                startActivity(intent);
                finish();
            }
        });

        getDataJMLUSER();

    }

    private void getDataJMLUSER() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AllDb.SERVER_COUNT_USERS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String angka = response.getString("test");
                            addjmlorang.setText(angka);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                angka_pinjam.setText(response.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addjmlorang.setText("error");
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void getDataUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_AllUSER_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);
                                ModelUser modelUser = new ModelUser();
                                modelUser.setKelas(dataObject.getString("kelas").toString());
                                modelUser.setNama(dataObject.getString("nama").toString());
                                modelUser.setUid(dataObject.getString("uid").toString());
                                modelUser.setNis(dataObject.getString("nis").toString());
                                modelUser.setEmail(dataObject.getString("email").toString());
                                modelUser.setNo_hp(dataObject.getString("no_hp").toString());
                                modelUser.setJk(dataObject.getString("jk").toString());
                                modelUser.setAlamat(dataObject.getString("alamat").toString());
                                modelUser.setImg_user(dataObject.getString("img_user").toString());
                                modelUsers.add(modelUser);

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapterDaftarUser = new AdapterDaftarUser(getApplicationContext(),modelUsers);
                        recdafuser.setAdapter(adapterDaftarUser);
                        adapterDaftarUser.setOnItemClickListener(DaftarPengguna.this::onItemClick);
                        adapterDaftarUser.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }
    private void getData2(String text) {
        if (text.isEmpty()) {
            recdafuser.setVisibility(View.VISIBLE);
            recdafuser2.setVisibility(View.GONE);
           getDataUser();

        } else {
            recdafuser.setVisibility(View.GONE);
            recdafuser2.setVisibility(View.VISIBLE);


            modelUsers.clear();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_USER_NIS_URL+text, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i <= response.length();i++){
                                try {

                                    JSONObject dataObject = response.getJSONObject(i);

                                    ModelUser modelUser = new ModelUser();
                                    modelUser.setKelas(dataObject.getString("kelas").toString());
                                    modelUser.setNama(dataObject.getString("nama").toString());
                                    modelUser.setUid(dataObject.getString("uid").toString());
                                    modelUser.setNis(dataObject.getString("nis").toString());
                                    modelUser.setEmail(dataObject.getString("email").toString());
                                    modelUser.setNo_hp(dataObject.getString("no_hp").toString());
                                    modelUser.setJk(dataObject.getString("jk").toString());
                                    modelUser.setAlamat(dataObject.getString("alamat").toString());
                                    modelUser.setImg_user(dataObject.getString("img_user").toString());
                                    modelUsers.add(modelUser);



                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                            adapterDaftarUser = new AdapterDaftarUser(getApplicationContext(), modelUsers);
                            recdafuser2.setAdapter(adapterDaftarUser);
                            adapterDaftarUser.setOnItemClickListener(DaftarPengguna.this::onItemClick);
                            adapterDaftarUser.notifyDataSetChanged();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonArrayRequest);
        }
    }
    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(DaftarPengguna.this, DetailPengguna.class);
        ModelUser click = modelUsers.get(position);
        intent.putExtra(EXTRA_DU_NAMA, click.getNama());
        intent.putExtra(EXTRA_DU_UID, click.getUid());
        intent.putExtra(EXTRA_DU_EMAIL, click.getEmail());
        intent.putExtra(EXTRA_DU_NIS, click.getNis());
        intent.putExtra(EXTRA_DU_KELAS, click.getKelas());
        intent.putExtra(EXTRA_DU_NOHP, click.getNo_hp());
        intent.putExtra(EXTRA_DU_JK, click.getJk());
        intent.putExtra(EXTRA_DU_ALAMAT, click.getAlamat());
        intent.putExtra(EXTRA_DU_IMG, click.getImg_user());
        startActivity(intent);

    }
}