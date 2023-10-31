package com.example.projectwiki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projectwiki.Form.FormPengajuan;

import java.util.HashMap;
import java.util.Map;

public class Regis extends AppCompatActivity {

    TextView tbl_regis;
    EditText adduser_first, adduser_end;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        tbl_regis = findViewById(R.id.tbl_regis);
        adduser_first = findViewById(R.id.adduser_first);
        adduser_end = findViewById(R.id.adduser_end);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Dalam proses pendaftaran");
        dialog.setCanceledOnTouchOutside(false);

        tbl_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Creat();
            }
        });

    }

    private void Creat() {
        dialog.show();
        String nama = adduser_end.getText().toString();
        String nis = adduser_first.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(Regis.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(Regis.this, Dashboard.class);
//                        startActivity(intent);
//                        finish();
//                  addjml.setText(test);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
                Toast.makeText(Regis.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", "098776");
                params.put("email", "sas");
                params.put("password", "sssss");
                params.put("nama", "tes");
                params.put("kelas", "compli");
                params.put("no_hp", "082122212");
                params.put("jk", "jk");
                params.put("alamat", "alamat");
                params.put("type_user", "user");
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}