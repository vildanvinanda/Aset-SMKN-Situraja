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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Adapter.AdapterBarangPinjaman;
import com.example.projectwiki.Adapter.AdapterDaftarPengajuan;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Detail.DetailBarangPinjaman;
import com.example.projectwiki.Fragmen.DaftarPinjaman;
import com.example.projectwiki.Model.ModelDafPin;
import com.example.projectwiki.Model.ModelPengajuan;
import com.example.projectwiki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaftarPengajuan extends AppCompatActivity implements AdapterDaftarPengajuan.OnItemClickListener{

    List<ModelPengajuan>modelPengajuans;
    List<DataUserModel> dataUserModels;
    private AdapterDaftarPengajuan adapterDaftarPengajuan;

    RelativeLayout btnsemua, btnfilter1, btnfilter2;
    EditText kolomsearch;
    RecyclerView recdafpin,recdafpin2, recdafpin3, recdafpin4;
    TextView txtall,txtpinjam,txtkembali,txt_uid,test,txt_nis,txt_email;
    ImageView btn_back;

    public static final String EXTRA_DP2_BPID = "bpid";
    public static final String EXTRA_DP2_UID = "uid";
    public static final String EXTRA_DP2_PID = "pid";
    public static final String EXTRA_DP2_NAME = "nama";
    public static final String EXTRA_DP2_NAMEB = "nama_barang";
    public static final String EXTRA_DP2_TGL = "tgl_pinjam";
    public static final String EXTRA_DP2_REQ = "req_pinjam";
    public static final String EXTRA_DP2_DUE = "due_date";
    public static final String EXTRA_DP2_KET = "keterangan";
    public static final String EXTRA_DP2_TOTAL = "total_bpinjam";
    public static final String EXTRA_DP2_STATUS = "status";
    public static final String EXTRA_DP2_NIS = "nis";
    public static final String EXTRA_DP2_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pengajuan);

        modelPengajuans = new ArrayList<>();

        btnsemua = (RelativeLayout) findViewById(R.id.btnsemua);
        btnfilter1 = (RelativeLayout) findViewById(R.id.btnfilter1);
        btnfilter2 = (RelativeLayout) findViewById(R.id.btnfilter2);
        kolomsearch = (EditText) findViewById(R.id.kolomsearch);

        recdafpin = (RecyclerView) findViewById(R.id.recdafpin);
        recdafpin2 = (RecyclerView) findViewById(R.id.recdafpin2);
        recdafpin3 = (RecyclerView) findViewById(R.id.recdafpin3);
        recdafpin4 = (RecyclerView) findViewById(R.id.recdafpin4);

        txtall = (TextView) findViewById(R.id.txtall);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_uid = (TextView) findViewById(R.id.txt_uid);
        txtpinjam = (TextView) findViewById(R.id.txtpinjam);
        txtkembali = (TextView) findViewById(R.id.txtkembali);
        test = (TextView) findViewById(R.id.test);
        btn_back = (ImageView) findViewById(R.id.btn_back);

        recdafpin.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
        recdafpin2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
        recdafpin3.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
        recdafpin4.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));

        dataUserModels = new ArrayList<>();
        getData();

        btnsemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnsemua.setBackground(getResources().getDrawable(R.drawable.bgtombol));
                btnfilter1.setBackground(getResources().getDrawable(R.drawable.button_rounded_daftar));
                btnfilter2.setBackground(getResources().getDrawable(R.drawable.button_rounded_daftar));
                txtall.setTextColor(getResources().getColor(R.color.white));
                txtpinjam.setTextColor(getResources().getColor(R.color.primer));
                txtkembali.setTextColor(getResources().getColor(R.color.primer));
                getData();
                recdafpin.setVisibility(View.VISIBLE);
                recdafpin2.setVisibility(View.GONE);
                recdafpin3.setVisibility(View.GONE);
            }
        });

        btnfilter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnsemua.setBackground(getResources().getDrawable(R.drawable.button_rounded_daftar));
                btnfilter1.setBackground(getResources().getDrawable(R.drawable.bgtombol));
                btnfilter2.setBackground(getResources().getDrawable(R.drawable.button_rounded_daftar));
                txtall.setTextColor(getResources().getColor(R.color.primer));
                txtpinjam.setTextColor(getResources().getColor(R.color.white));
                txtkembali.setTextColor(getResources().getColor(R.color.primer));
                getData2();
                recdafpin.setVisibility(View.GONE);
                recdafpin2.setVisibility(View.VISIBLE);
                recdafpin3.setVisibility(View.GONE);
            }
        });

        btnfilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnsemua.setBackground(getResources().getDrawable(R.drawable.button_rounded_daftar));
                btnfilter1.setBackground(getResources().getDrawable(R.drawable.button_rounded_daftar));
                btnfilter2.setBackground(getResources().getDrawable(R.drawable.bgtombol));
                txtall.setTextColor(getResources().getColor(R.color.primer));
                txtpinjam.setTextColor(getResources().getColor(R.color.primer));
                txtkembali.setTextColor(getResources().getColor(R.color.white));
                getData3();
                recdafpin.setVisibility(View.GONE);
                recdafpin2.setVisibility(View.GONE);
                recdafpin3.setVisibility(View.VISIBLE);
            }
        });

        kolomsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString()!=null)
                {
                    getData5(s.toString());
                }
                else
                {
                    getData5("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarPengajuan.this, HomeAdmin.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getData5(String text) {
        if (text.isEmpty()) {
            recdafpin.setVisibility(View.VISIBLE);
            recdafpin2.setVisibility(View.GONE);
            recdafpin3.setVisibility(View.GONE);
            recdafpin4.setVisibility(View.GONE);
            getData();

        } else {
            recdafpin.setVisibility(View.GONE);
            recdafpin2.setVisibility(View.GONE);
            recdafpin3.setVisibility(View.GONE);
            recdafpin4.setVisibility(View.VISIBLE);

            modelPengajuans.clear();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_BARANG_PENGAJUAN_URL+text, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i <= response.length();i++){
                                try {

                                    JSONObject dataObject = response.getJSONObject(i);

                                    String uuid = txt_uid.getText().toString();
                                        ModelPengajuan modelPengajuan = new ModelPengajuan();
                                        modelPengajuan.setPinid(dataObject.getString("pinid").toString());
                                        modelPengajuan.setBpid(dataObject.getString("bpid").toString());
                                        modelPengajuan.setUid(dataObject.getString("uid").toString());
                                        modelPengajuan.setNama(dataObject.getString("nama").toString());
                                        modelPengajuan.setNama_barang(dataObject.getString("nama_barang").toString());
                                        modelPengajuan.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                        modelPengajuan.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                        modelPengajuan.setKeterangan(dataObject.getString("keterangan").toString());
                                        modelPengajuan.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                                        modelPengajuan.setDue_date(dataObject.getString("due_date").toString());
                                        modelPengajuan.setStatus_pengajuan(dataObject.getString("status_pengajuan").toString());
                                        modelPengajuans.add(modelPengajuan);



                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                            adapterDaftarPengajuan = new AdapterDaftarPengajuan(getApplicationContext(), modelPengajuans);
                            recdafpin4.setAdapter(adapterDaftarPengajuan);
                            adapterDaftarPengajuan.setOnItemClickListener(DaftarPengajuan.this::onItemClick);
                            adapterDaftarPengajuan.notifyDataSetChanged();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonArrayRequest);
        }
    }

    private void getData() {

        modelPengajuans.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_PENGAJUAN_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                ModelPengajuan modelPengajuan = new ModelPengajuan();
                                modelPengajuan.setPinid(dataObject.getString("pinid").toString());
                                modelPengajuan.setBpid(dataObject.getString("bpid").toString());
                                modelPengajuan.setUid(dataObject.getString("uid").toString());
                                modelPengajuan.setNama(dataObject.getString("nama").toString());
                                modelPengajuan.setNama_barang(dataObject.getString("nama_barang").toString());
                                modelPengajuan.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                modelPengajuan.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                modelPengajuan.setKeterangan(dataObject.getString("keterangan").toString());
                                modelPengajuan.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                                modelPengajuan.setDue_date(dataObject.getString("due_date").toString());
                                modelPengajuan.setStatus_pengajuan(dataObject.getString("status_pengajuan").toString());
                                modelPengajuans.add(modelPengajuan);


                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        adapterDaftarPengajuan = new AdapterDaftarPengajuan(getApplicationContext(), modelPengajuans);
                        recdafpin.setAdapter(adapterDaftarPengajuan);
                        adapterDaftarPengajuan.setOnItemClickListener(DaftarPengajuan.this::onItemClick);
                        adapterDaftarPengajuan.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void getData2() {
        modelPengajuans.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_PENGAJUAN_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                String cekstatus = "menunggu";
                                if(dataObject.getString("status_pengajuan").equals(cekstatus)){
                                    ModelPengajuan modelPengajuan = new ModelPengajuan();
                                    modelPengajuan.setPinid(dataObject.getString("pinid").toString());
                                    modelPengajuan.setBpid(dataObject.getString("bpid").toString());
                                    modelPengajuan.setUid(dataObject.getString("uid").toString());
                                    modelPengajuan.setNama(dataObject.getString("nama").toString());
                                    modelPengajuan.setNama_barang(dataObject.getString("nama_barang").toString());
                                    modelPengajuan.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                    modelPengajuan.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                    modelPengajuan.setKeterangan(dataObject.getString("keterangan").toString());
                                    modelPengajuan.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                                    modelPengajuan.setDue_date(dataObject.getString("due_date").toString());
                                    modelPengajuan.setStatus_pengajuan(dataObject.getString("status_pengajuan").toString());
                                    modelPengajuans.add(modelPengajuan);
                                }
//                                else if (dataObject.getString("status_pengajuan").equals("diterima")){
//                                    ModelPengajuan modelPengajuan = new ModelPengajuan();
//                                    modelPengajuan.setPinid(dataObject.getString("pinid").toString());
//                                    modelPengajuan.setBpid(dataObject.getString("bpid").toString());
//                                    modelPengajuan.setUid(dataObject.getString("uid").toString());
//                                    modelPengajuan.setNama(dataObject.getString("nama").toString());
//                                    modelPengajuan.setNama_barang(dataObject.getString("nama_barang").toString());
//                                    modelPengajuan.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
//                                    modelPengajuan.setReq_pinjam(dataObject.getString("req_pinjam").toString());
//                                    modelPengajuan.setKeterangan(dataObject.getString("keterangan").toString());
//                                    modelPengajuan.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
//                                    modelPengajuan.setDue_date(dataObject.getString("due_date").toString());
//                                    modelPengajuan.setStatus_pengajuan(dataObject.getString("status_pengajuan").toString());
//                                    modelPengajuans.add(modelPengajuan);
//                                }


                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        adapterDaftarPengajuan = new AdapterDaftarPengajuan(getApplicationContext(), modelPengajuans);
                        recdafpin2.setAdapter(adapterDaftarPengajuan);
                        adapterDaftarPengajuan.setOnItemClickListener(DaftarPengajuan.this::onItemClick);
                        adapterDaftarPengajuan.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void getData3() {
        modelPengajuans.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_PENGAJUAN_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                String cekstatus = "diterima";
                                if(dataObject.getString("status_pengajuan").equals(cekstatus)){
                                    ModelPengajuan modelPengajuan = new ModelPengajuan();
                                    modelPengajuan.setPinid(dataObject.getString("pinid").toString());
                                    modelPengajuan.setBpid(dataObject.getString("bpid").toString());
                                    modelPengajuan.setUid(dataObject.getString("uid").toString());
                                    modelPengajuan.setNama(dataObject.getString("nama").toString());
                                    modelPengajuan.setNama_barang(dataObject.getString("nama_barang").toString());
                                    modelPengajuan.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                    modelPengajuan.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                    modelPengajuan.setKeterangan(dataObject.getString("keterangan").toString());
                                    modelPengajuan.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                                    modelPengajuan.setDue_date(dataObject.getString("due_date").toString());
                                    modelPengajuan.setStatus_pengajuan(dataObject.getString("status_pengajuan").toString());
                                    modelPengajuans.add(modelPengajuan);
                                }


                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        adapterDaftarPengajuan = new AdapterDaftarPengajuan(getApplicationContext(), modelPengajuans);
                        recdafpin3.setAdapter(adapterDaftarPengajuan);
                        adapterDaftarPengajuan.setOnItemClickListener(DaftarPengajuan.this::onItemClick);
                        adapterDaftarPengajuan.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(int position) {

//
        Intent intent = new Intent(DaftarPengajuan.this, DetailPinjamanUser.class);
        ModelPengajuan click = modelPengajuans.get(position);
        intent.putExtra(EXTRA_DP2_BPID, click.getBpid());
        intent.putExtra(EXTRA_DP2_UID, click.getUid());
        intent.putExtra(EXTRA_DP2_PID, click.getPinid());
        intent.putExtra(EXTRA_DP2_NAMEB, click.getNama_barang());
        intent.putExtra(EXTRA_DP2_NAME, click.getNama());
        intent.putExtra(EXTRA_DP2_TGL, click.getTgl_pinjam());
        intent.putExtra(EXTRA_DP2_REQ, click.getReq_pinjam());
        intent.putExtra(EXTRA_DP2_DUE, click.getDue_date());
        intent.putExtra(EXTRA_DP2_KET, click.getKeterangan());
        intent.putExtra(EXTRA_DP2_TOTAL, click.getTotal_bpinjam());
        intent.putExtra(EXTRA_DP2_STATUS, click.getStatus_pengajuan());
        intent.putExtra(EXTRA_DP2_NIS, click.getNis());
        startActivity(intent);
    }
}