package com.example.projectwiki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projectwiki.Admin.DaftarPengguna;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailPengguna extends AppCompatActivity {

    ImageView backbtn, ppuser , copynis, copynohp;
    TextView nama_user, angka_kembali, angka_pinjam, addnis, addnohp, addkls, addjk, addalamat, addemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengguna);

        copynohp = findViewById(R.id.copynohp);
        backbtn = findViewById(R.id.backbtn);
        ppuser = findViewById(R.id.ppuser);
        copynis = findViewById(R.id.copynis);
        copynohp = findViewById(R.id.copynohp);

        nama_user = findViewById(R.id.nama_user);
        angka_kembali = findViewById(R.id.angka_kembali);
        angka_pinjam = findViewById(R.id.angka_pinjam);
        addnohp = findViewById(R.id.addnohp);
        addnis = findViewById(R.id.addnis);
        addemail = findViewById(R.id.addemail);
        addjk = findViewById(R.id.addjk);
        addkls = findViewById(R.id.addkls);
        addalamat = findViewById(R.id.addalamat);

        Intent intent = getIntent();
        String nama = intent.getStringExtra(DaftarPengguna.EXTRA_DU_NAMA);
        String nis = intent.getStringExtra(DaftarPengguna.EXTRA_DU_NIS);
        String uid = intent.getStringExtra(DaftarPengguna.EXTRA_DU_UID);
        String kls = intent.getStringExtra(DaftarPengguna.EXTRA_DU_KELAS);
        String alamat = intent.getStringExtra(DaftarPengguna.EXTRA_DU_ALAMAT);
        String img = intent.getStringExtra(DaftarPengguna.EXTRA_DU_IMG);
        String nohp = intent.getStringExtra(DaftarPengguna.EXTRA_DU_NOHP);
        String email = intent.getStringExtra(DaftarPengguna.EXTRA_DU_EMAIL);
        String jk = intent.getStringExtra(DaftarPengguna.EXTRA_DU_JK);

        nama_user.setText(nama);
        addnohp.setText(nohp);
        addnis.setText(nis);
        addalamat.setText(alamat);
        addjk.setText(jk);
        addkls.setText(kls);
        addemail.setText(email);

        Glide.with(ppuser.getContext()).load(img).centerCrop().into(ppuser);

        getData(uid);
        getData2(uid);
        
        copynis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("cek", addnis.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DetailPengguna.this, "NISN Telah Disalin", Toast.LENGTH_SHORT).show();
            }
        });
        copynohp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("cek", addnohp.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DetailPengguna.this, "No.HP Telah Disalin", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getData(String uid) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AllDb.SERVER_COUNT_PINJAM_BARANG_URL2+uid, null,
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

    private void getData2(String uid) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AllDb.SERVER_COUNT_PINJAM_BARANG_URL+uid, null,
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