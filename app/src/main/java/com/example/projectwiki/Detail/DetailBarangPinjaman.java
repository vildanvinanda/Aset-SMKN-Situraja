package com.example.projectwiki.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.Form.FormPengembalian;
import com.example.projectwiki.R;
import com.example.projectwiki.Register;
import com.example.projectwiki.RequestHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;

import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_BPID;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_DUE;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_EMAIL;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_KET;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_NAME;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_NAMEB;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_NIS;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_PID;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_REQ;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_STATUS;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_TGL;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_TOTAL;
import static com.example.projectwiki.Fragmen.DaftarPinjaman.EXTRA_DP_UID;

public class DetailBarangPinjaman extends AppCompatActivity {

    TextView txtwaktu,kode_pesanan, tglpinjam, addname, addnis, addnamab, addjml, addjt, button_pinjam,addket;
    RelativeLayout tamplatetxt5;
    CountdownView countdownView;
    ImageView btn_back;
    ProgressDialog dialog;


    public static final String EXTRA_DBP_BPID = "bpid";
    public static final String EXTRA_DBP_UID = "uid";
    public static final String EXTRA_DBP_PID = "pid";
    public static final String EXTRA_DBP_NAME = "nama";
    public static final String EXTRA_DBP_NAMEB = "nama_barang";
    public static final String EXTRA_DBP_TGL = "tgl_pinjam";
    public static final String EXTRA_DBP_REQ = "req_pinjam";
    public static final String EXTRA_DBP_DUE = "due_date";
    public static final String EXTRA_DBP_KET = "keterangan";
    public static final String EXTRA_DBP_TOTAL = "total_bpinjam";
    public static final String EXTRA_DBP_STATUS = "status";
    public static final String EXTRA_DBP_NIS = "nis";
    public static final String EXTRA_DBP_EMAIL = "email";


    public static final String EXTRA_DBP_BPID2 = "bpid";
    public static final String EXTRA_DBP_UID2 = "uid";
    public static final String EXTRA_DBP_PID2 = "pid";
    public static final String EXTRA_DBP_NAME2 = "nama";
    public static final String EXTRA_DBP_NAMEB2 = "nama_barang";
    public static final String EXTRA_DBP_TGL2 = "tgl_pinjam";
    public static final String EXTRA_DBP_REQ2 = "req_pinjam";
    public static final String EXTRA_DBP_DUE2 = "due_date";
    public static final String EXTRA_DBP_KET2 = "keterangan";
    public static final String EXTRA_DBP_TOTAL2 = "total_bpinjam";
    public static final String EXTRA_DBP_STATUS2 = "status";
    public static final String EXTRA_DBP_NIS2 = "nis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang_pinjaman);

        countdownView = (CountdownView) findViewById(R.id.countdown);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        txtwaktu = (TextView) findViewById(R.id.txtwaktu);
        kode_pesanan = (TextView) findViewById(R.id.kode_pesanan);
        tglpinjam = (TextView) findViewById(R.id.tglpinjam);
        addname = (TextView) findViewById(R.id.addname);
        addnis = (TextView) findViewById(R.id.addnis);
        addket = (TextView) findViewById(R.id.addket);
        button_pinjam = (TextView) findViewById(R.id.button_pinjam);
        addnamab = (TextView) findViewById(R.id.addnamab);
        addjml = (TextView) findViewById(R.id.addjml);
        addjt = (TextView) findViewById(R.id.addjt);
        tamplatetxt5 = (RelativeLayout) findViewById(R.id.tamplatetxt5);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Dalam proses pendaftaran");
        dialog.setCanceledOnTouchOutside(false);

        Intent intent = getIntent();
        String bpid = intent.getStringExtra(EXTRA_DP_BPID);
        String uid = intent.getStringExtra(EXTRA_DP_UID);
        String pid = intent.getStringExtra(EXTRA_DP_PID);
        String nama = intent.getStringExtra(EXTRA_DP_NAME);
        String namaB = intent.getStringExtra(EXTRA_DP_NAMEB);
        String tgl = intent.getStringExtra(EXTRA_DP_TGL);
        String req = intent.getStringExtra(EXTRA_DP_REQ);
        String due = intent.getStringExtra(EXTRA_DP_DUE);
        String ket = intent.getStringExtra(EXTRA_DP_KET);
        String total = intent.getStringExtra(EXTRA_DP_TOTAL);
        String status = intent.getStringExtra(EXTRA_DP_STATUS);
        String nis = intent.getStringExtra(EXTRA_DP_NIS);
        String email = intent.getStringExtra(EXTRA_DP_EMAIL);


        addnamab.setText(namaB);
        addname.setText(nama);
        addnis.setText(nis);
        addjml.setText(total);
        addjt.setText(due);
        kode_pesanan.setText(pid);
        addket.setText(ket);
        tglpinjam.setText(tgl);

        String cekk= addjt.getText().toString();

        if(cekk.isEmpty())
        {
            tamplatetxt5.setVisibility(View.GONE);
        } else {
            tamplatetxt5.setVisibility(View.VISIBLE);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String countDate = "23-03-2023";
        String gettgl = addjt.getText().toString();
        Date now = new Date();

        try {
            Date date = simpleDateFormat.parse(gettgl);
            long currentTime= now.getTime();
            long Day= date.getTime();
            long countDownDay = Day -currentTime;

            countdownView.start(countDownDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                countdownView.setVisibility(View.GONE);
                txtwaktu.setVisibility(View.VISIBLE);

                UpdateData();

            }
        });

        button_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        if(status.equals("peringatan")){
            countdownView.setVisibility(View.GONE);
            txtwaktu.setVisibility(View.VISIBLE);
        }
    }

    private void InsertData() {
        Intent in = getIntent();
        String bpid = in.getStringExtra(EXTRA_DP_BPID);
        String uid = in.getStringExtra(EXTRA_DP_UID);
        String pid = in.getStringExtra(EXTRA_DP_PID);
        String nama = in.getStringExtra(EXTRA_DP_NAME);
        String namaB = in.getStringExtra(EXTRA_DP_NAMEB);
        String tgl = in.getStringExtra(EXTRA_DP_TGL);
        String req = in.getStringExtra(EXTRA_DP_REQ);
        String due = in.getStringExtra(EXTRA_DP_DUE);
        String ket = in.getStringExtra(EXTRA_DP_KET);
        String total = in.getStringExtra(EXTRA_DP_TOTAL);
        String status = in.getStringExtra(EXTRA_DP_STATUS);
        String nis = in.getStringExtra(EXTRA_DP_NIS);
        String email = in.getStringExtra(EXTRA_DP_EMAIL);

        Intent intent = new Intent(DetailBarangPinjaman.this, FormPengembalian.class);
        intent.putExtra(EXTRA_DBP_BPID, bpid);
        intent.putExtra(EXTRA_DBP_UID, uid);
        intent.putExtra(EXTRA_DBP_PID, pid);
        intent.putExtra(EXTRA_DBP_NAME, nama);
        intent.putExtra(EXTRA_DBP_NAMEB, namaB);
        intent.putExtra(EXTRA_DBP_TGL, tgl);
        intent.putExtra(EXTRA_DBP_REQ, req);
        intent.putExtra(EXTRA_DBP_DUE, due);
        intent.putExtra(EXTRA_DBP_KET, ket);
        intent.putExtra(EXTRA_DBP_TOTAL, total);
        intent.putExtra(EXTRA_DBP_STATUS, status);
        intent.putExtra(EXTRA_DBP_NIS, nis);
        intent.putExtra(EXTRA_DBP_EMAIL, email);
        startActivity(intent);
//        finish();
    }

    private void UpdateData() {
        dialog.show();
        Intent intent = getIntent();
        String bpid = intent.getStringExtra(EXTRA_DP_BPID);
        String uid = intent.getStringExtra(EXTRA_DP_UID);
        String pid = intent.getStringExtra(EXTRA_DP_PID);
        String nama = intent.getStringExtra(EXTRA_DP_NAME);
        String namaB = intent.getStringExtra(EXTRA_DP_NAMEB);
        String tgl = intent.getStringExtra(EXTRA_DP_TGL);
        String req = intent.getStringExtra(EXTRA_DP_REQ);
        String due = intent.getStringExtra(EXTRA_DP_DUE);
        String ket = intent.getStringExtra(EXTRA_DP_KET);
        String total = intent.getStringExtra(EXTRA_DP_TOTAL);
        String status = intent.getStringExtra(EXTRA_DP_STATUS);
        String nis = intent.getStringExtra(EXTRA_DP_NIS);
        String email = intent.getStringExtra(EXTRA_DP_EMAIL);


        //insert data to admin

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_POST_DATA_PENGAJUAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(DetailBarangPinjaman.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
                        finish();
//                  addjml.setText(test);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
                Toast.makeText(DetailBarangPinjaman.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("bpid", bpid);
                params.put("uid", uid);
                params.put("nama", nama);
                params.put("nama_barang", namaB);
                params.put("tgl_pinjam", tgl);
                params.put("req_pinjam", req);
                params.put("keterangan", ket);
                params.put("total_bpinjam", total);
                params.put("due_date", due);
                params.put("status", "");
                params.put("status_pengajuan", "Menunggu");
                return params;
            }
        };

        //ini update data
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_BPINJAM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(DetailBarangPinjaman.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(DetailBarangPinjaman.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pid", pid);
                params.put("status", "peringatan");
                params.put("peminjaman", "belum kembali");
                return params;
            }
        };

        //ini create notif
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_NOTIF_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("bpid", bpid);
                params.put("tgl_kirim", currentDate);
                params.put("judul", "Waktu Peminjaman Anda Telah Habis");
                params.put("keterangan", "Tolong untuk  segera mengembalikan barang ini sesuai dengan tanggal yang telah ditentukan");
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest3);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest2);
    }
}