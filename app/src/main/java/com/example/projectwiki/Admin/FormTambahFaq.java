package com.example.projectwiki.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FormTambahFaq extends AppCompatActivity {

    ImageView btn_back;
    EditText addpertanyaan, addjawaban;
    TextView btn_kirim;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tambah_faq);

        btn_back = findViewById(R.id.btn_back);
        btn_kirim = findViewById(R.id.btn_kirim);
        addpertanyaan = findViewById(R.id.addpertanyaan);
        addjawaban = findViewById(R.id.addjawaban);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Dalam proses pendaftaran");
        dialog.setCanceledOnTouchOutside(false);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiData();
            }
        });



    }

    private void validasiData() {
        String jwb = addjawaban.getText().toString();
        String pertanyaan = addpertanyaan.getText().toString();

        if(TextUtils.isEmpty(jwb)){
            addjawaban.setError("Wajib Diisi!");
            addjawaban.requestFocus();
        } else if(TextUtils.isEmpty(pertanyaan)){
            addpertanyaan.setError("Wajib Diisi!!");
            addpertanyaan.requestFocus();
        } else {
            insertData(pertanyaan,jwb);
        }

    }

    private void insertData(String pertanyaan, String jwb) {
//        SERVER_INSERT_DATA_FAQ_URL
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_FAQ_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(FormTambahFaq.this, "Barang Berhasil Disimpan", Toast.LENGTH_LONG).show();
                        addjawaban.setText("");
                        addpertanyaan.setText("");
                        Intent intent = new Intent(FormTambahFaq.this, DaftarFaq.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
                Toast.makeText(FormTambahFaq.this, "Barang Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pertanyaan", pertanyaan);
                params.put("jawaban", jwb);
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);



    }
}