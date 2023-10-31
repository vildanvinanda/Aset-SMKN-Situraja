package com.example.projectwiki.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.projectwiki.Dashboard;
import com.example.projectwiki.Form.FormPengajuan;
import com.example.projectwiki.R;
import com.example.projectwiki.RequestHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;

import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_BPID;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_DUE;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_KET;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_NAME;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_NAMEB;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_NIS;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_PID;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_REQ;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_STATUS;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_TGL;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_TOTAL;
import static com.example.projectwiki.Admin.DaftarPengajuan.EXTRA_DP2_UID;


public class DetailPinjamanUser extends AppCompatActivity {
    TextView txtwaktu,kode_pesanan, tglpinjam, addname, addnis, addnamab, addjml, addjt, button_pinjam, btn_terima,btn_tolak;
    RelativeLayout tamplate_btn,tamplatetxt5;

    public static final String EXTRA_DPU_BPID = "bpid";
    public static final String EXTRA_DPU_UID = "uid";
    public static final String EXTRA_DPU_PID = "pid";
    public static final String EXTRA_DPU_NAME = "nama";
    public static final String EXTRA_DPU_NAMEB = "nama_barang";
    public static final String EXTRA_DPU_TGL = "tgl_pinjam";
    public static final String EXTRA_DPU_REQ = "req_pinjam";
    public static final String EXTRA_DPU_DUE = "due_date";
    public static final String EXTRA_DPU_KET = "keterangan";
    public static final String EXTRA_DPU_TOTAL = "total_bpinjam";
    public static final String EXTRA_DPU_STATUS = "status";
    public static final String EXTRA_DPU_NIS = "nis";
    public static final String EXTRA_DPU_EMAIL = "email";

    ImageView btn_back;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjaman_user);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        txtwaktu = (TextView) findViewById(R.id.txtwaktu);
        btn_tolak = (TextView) findViewById(R.id.btn_tolak);
        btn_terima = (TextView) findViewById(R.id.btn_terima);
        kode_pesanan = (TextView) findViewById(R.id.kode_pesanan);
        tglpinjam = (TextView) findViewById(R.id.tglpinjam);
        addname = (TextView) findViewById(R.id.addname);
        addnis = (TextView) findViewById(R.id.addnis);
        button_pinjam = (TextView) findViewById(R.id.button_pinjam);
        addnamab = (TextView) findViewById(R.id.addnamab);
        addjml = (TextView) findViewById(R.id.addjml);
        addjt = (TextView) findViewById(R.id.addjt);
        tamplate_btn = (RelativeLayout) findViewById(R.id.tamplate_btn);
        tamplatetxt5 = (RelativeLayout) findViewById(R.id.tamplatetxt5);

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


        Intent intent = getIntent();
        String bpid = intent.getStringExtra(EXTRA_DP2_BPID);
        String uid = intent.getStringExtra(EXTRA_DP2_UID);
        String pinid = intent.getStringExtra(EXTRA_DP2_PID);
        String nama = intent.getStringExtra(EXTRA_DP2_NAME);
        String namaB = intent.getStringExtra(EXTRA_DP2_NAMEB);
        String tgl = intent.getStringExtra(EXTRA_DP2_TGL);
        String req = intent.getStringExtra(EXTRA_DP2_REQ);
        String due = intent.getStringExtra(EXTRA_DP2_DUE);
        String ket = intent.getStringExtra(EXTRA_DP2_KET);
        String total = intent.getStringExtra(EXTRA_DP2_TOTAL);
        String status = intent.getStringExtra(EXTRA_DP2_STATUS);
        String nis = intent.getStringExtra(EXTRA_DP2_NIS);

        addnamab.setText(namaB);
        addname.setText(nama);
        addnis.setText(nis);
        addjml.setText(total);
        addjt.setText(due);
        kode_pesanan.setText(pinid);
        tglpinjam.setText(tgl);

        btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPinjamanUser.this);
                builder.setTitle("Ubah Data");
                builder.setMessage("Anda yakin untuk memperbarui data ini?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updatedata();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPinjamanUser.this);
                builder.setTitle("Hapus Data");
                builder.setMessage("Anda yakin untuk menghapus data ini?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        String pinid = intent.getStringExtra(EXTRA_DP2_PID);
                        hapusData(pinid);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        if(due.isEmpty()){
            tamplatetxt5.setVisibility(View.GONE);
        } else {
            tamplatetxt5.setVisibility(View.VISIBLE);
        }

        if(status.equals("menunggu")){
            tamplate_btn.setVisibility(View.VISIBLE);
        } else {
            tamplate_btn.setVisibility(View.GONE);
        }

    }


    private void updatedata() {
        dialog.show();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Intent intent = getIntent();
        String bpid = intent.getStringExtra(EXTRA_DP2_BPID);
        String uid = intent.getStringExtra(EXTRA_DP2_UID);
        String pinid = intent.getStringExtra(EXTRA_DP2_PID);
        String nama = intent.getStringExtra(EXTRA_DP2_NAME);
        String namaB = intent.getStringExtra(EXTRA_DP2_NAMEB);
        String tgl = intent.getStringExtra(EXTRA_DP2_TGL);
        String req = intent.getStringExtra(EXTRA_DP2_REQ);
        String due = intent.getStringExtra(EXTRA_DP2_DUE);
        String ket = intent.getStringExtra(EXTRA_DP2_KET);
        String total = intent.getStringExtra(EXTRA_DP2_TOTAL);
        String status = intent.getStringExtra(EXTRA_DP2_STATUS);
        String nis = intent.getStringExtra(EXTRA_DP2_NIS);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_FORM_PENGAJUAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(DetailPinjamanUser.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailPinjamanUser.this, DaftarPengajuan.class);
                        startActivity(intent);
                        finish();
//                  addjml.setText(test);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
                Toast.makeText(DetailPinjamanUser.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("bpid", bpid);
                params.put("uid", uid);
                params.put("nama", nama);
                params.put("nama_barang", namaB);
                params.put("tgl_pinjam", currentDate);
                params.put("req_pinjam", req);
                params.put("keterangan", ket);
                params.put("total_bpinjam", total);
//                  params.put("due_date", "112");
                params.put("status", "aktif");
                params.put("peminjaman", "kembali");
                return params;
            }
        };

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_PENGAJUAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
//                          addjml.setText(test);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pinid", pinid);
                params.put("status_pengajuan", "diterima");
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest2);

    }
    private void hapusData(String pinid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_DELETE_DATA_PENGAJUAN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DetailPinjamanUser.this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DetailPinjamanUser.this, DaftarPengajuan.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                Toast.makeText(DetailPinjamanUser.this, "gagal", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pinid", pinid);
//                            params.put("namapemilik", namapemilik);
//                            params.put("jk", jk);
//                            params.put("statushewan", statushewan);
//                            params.put("kategori", kategori);
//                            params.put("kj", kj);
//                            params.put("tanggallahir", tanggallahir);
//                            params.put("tanggalbeli", tanggalbeli);
//                            params.put("umur", umur);
//                            params.put("hargabeli", hargabeli);
//                            params.put("belidari", belidari);
//                            params.put("peristiwa", peristiwa);
//                            params.put("nomor", nomor);
//                            params.put("imaghewan", encodedImages);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(DetailPinjamanUser.this);
        queue.add(stringRequest);
    }
}