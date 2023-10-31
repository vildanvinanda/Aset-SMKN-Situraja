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
import com.example.projectwiki.Adapter.AdapterDaftarPengajuan;
import com.example.projectwiki.Adapter.AdapterPengembalianBarang;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.DetailPengembalian;
import com.example.projectwiki.Model.ModelPengembalian;
import com.example.projectwiki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListPengembalian extends AppCompatActivity implements AdapterPengembalianBarang.OnItemClickListener{

    List<ModelPengembalian> modelPengembalians;
    List<DataUserModel> dataUserModels;
    private AdapterPengembalianBarang adapterPengembalianBarang;

    RelativeLayout btnsemua, btnfilter1, btnfilter2;
    EditText kolomsearch;
    RecyclerView recdafpin,recdafpin2, recdafpin3, recdafpin4;
    TextView txtall,txtpinjam,txtkembali,txt_uid,test,txt_nis,txt_email;
    ImageView btn_back;

    public static final String EXTRA_LP_BPID = "bpid";
    public static final String EXTRA_LP_UID = "uid";
    public static final String EXTRA_LP_PID = "pid";
    public static final String EXTRA_LP_NAME = "nama";
    public static final String EXTRA_LP_NAMEB = "nama_barang";
    public static final String EXTRA_LP_TGL = "tgl_pinjam";
    public static final String EXTRA_LP_REQ = "req_pinjam";
    public static final String EXTRA_LP_IMG = "img_pengembalian";
    public static final String EXTRA_LP_KET = "keterangan";
    public static final String EXTRA_LP_TOTAL = "total_bpinjam";
    public static final String EXTRA_LP_STATUS = "status";
    public static final String EXTRA_LP_NIS = "nis";
    public static final String EXTRA_LP_IDPB = "idpb";
    public static final String EXTRA_LP_DUE = "due_date";
    public static final String EXTRA_LP_TGL_TERIMA = "tgl_penerimaan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pengembalian);

        modelPengembalians = new ArrayList<>();

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
                Intent intent = new Intent(ListPengembalian.this, HomeAdmin.class);
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

            modelPengembalians.clear();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_DATA_PENGEMBALIAN_URL+text, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i <= response.length();i++){
                                try {

                                    JSONObject dataObject = response.getJSONObject(i);

                                    String uuid = txt_uid.getText().toString();
                                    ModelPengembalian modelPengembalian = new ModelPengembalian();
                                    modelPengembalian.setIdpb(dataObject.getString("idpb").toString());
                                    modelPengembalian.setPid(dataObject.getString("pid").toString());
                                    modelPengembalian.setBpid(dataObject.getString("bpid").toString());
                                    modelPengembalian.setUid(dataObject.getString("uid").toString());
                                    modelPengembalian.setNama(dataObject.getString("nama").toString());
                                    modelPengembalian.setNis(dataObject.getString("nis").toString());
                                    modelPengembalian.setNama_barang(dataObject.getString("nama_barang").toString());
                                    modelPengembalian.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                    modelPengembalian.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                    modelPengembalian.setKeterangan(dataObject.getString("keterangan").toString());
                                    modelPengembalian.setJml_barang(dataObject.getString("jml_barang").toString());
                                    modelPengembalian.setStatus(dataObject.getString("status").toString());
                                    modelPengembalian.setImg_pengembalian(dataObject.getString("img_pengembalian").toString());
                                    modelPengembalians.add(modelPengembalian);



                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                            adapterPengembalianBarang = new AdapterPengembalianBarang(getApplicationContext(), modelPengembalians);
                            recdafpin4.setAdapter(adapterPengembalianBarang);
                            adapterPengembalianBarang.setOnItemClickListener(ListPengembalian.this::onItemClick);
                            adapterPengembalianBarang.notifyDataSetChanged();

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

        modelPengembalians.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_PENGEMBALIAN_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                ModelPengembalian modelPengembalian = new ModelPengembalian();
                                modelPengembalian.setIdpb(dataObject.getString("idpb").toString());
                                modelPengembalian.setPid(dataObject.getString("pid").toString());
                                modelPengembalian.setBpid(dataObject.getString("bpid").toString());
                                modelPengembalian.setUid(dataObject.getString("uid").toString());
                                modelPengembalian.setNama(dataObject.getString("nama").toString());
                                modelPengembalian.setNis(dataObject.getString("nis").toString());
                                modelPengembalian.setNama_barang(dataObject.getString("nama_barang").toString());
                                modelPengembalian.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                modelPengembalian.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                modelPengembalian.setKeterangan(dataObject.getString("keterangan").toString());
                                modelPengembalian.setJml_barang(dataObject.getString("jml_barang").toString());
                                modelPengembalian.setStatus(dataObject.getString("status").toString());
                                modelPengembalian.setImg_pengembalian(dataObject.getString("img_pengembalian").toString());
                                modelPengembalians.add(modelPengembalian);


                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        adapterPengembalianBarang = new AdapterPengembalianBarang(getApplicationContext(), modelPengembalians);
                        recdafpin.setAdapter(adapterPengembalianBarang);
                        adapterPengembalianBarang.setOnItemClickListener(ListPengembalian.this::onItemClick);
                        adapterPengembalianBarang.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void getData2() {
        modelPengembalians.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_PENGEMBALIAN_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                String cekstatus = "Menunggu";
                                if(dataObject.getString("status").equals(cekstatus)){
                                    ModelPengembalian modelPengembalian = new ModelPengembalian();
                                    modelPengembalian.setIdpb(dataObject.getString("idpb").toString());
                                    modelPengembalian.setPid(dataObject.getString("pid").toString());
                                    modelPengembalian.setBpid(dataObject.getString("bpid").toString());
                                    modelPengembalian.setUid(dataObject.getString("uid").toString());
                                    modelPengembalian.setNama(dataObject.getString("nama").toString());
                                    modelPengembalian.setNis(dataObject.getString("nis").toString());
                                    modelPengembalian.setNama_barang(dataObject.getString("nama_barang").toString());
                                    modelPengembalian.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                    modelPengembalian.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                    modelPengembalian.setKeterangan(dataObject.getString("keterangan").toString());
                                    modelPengembalian.setJml_barang(dataObject.getString("jml_barang").toString());
                                    modelPengembalian.setStatus(dataObject.getString("status").toString());
                                    modelPengembalian.setImg_pengembalian(dataObject.getString("img_pengembalian").toString());
                                    modelPengembalians.add(modelPengembalian);
                                }
//                                else if (dataObject.getString("status_pengajuan").equals("diterima")){
//                                    modelPengembalian modelPengembalian = new modelPengembalian();
//                                    modelPengembalian.setPinid(dataObject.getString("pinid").toString());
//                                    modelPengembalian.setBpid(dataObject.getString("bpid").toString());
//                                    modelPengembalian.setUid(dataObject.getString("uid").toString());
//                                    modelPengembalian.setNama(dataObject.getString("nama").toString());
//                                    modelPengembalian.setNama_barang(dataObject.getString("nama_barang").toString());
//                                    modelPengembalian.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
//                                    modelPengembalian.setReq_pinjam(dataObject.getString("req_pinjam").toString());
//                                    modelPengembalian.setKeterangan(dataObject.getString("keterangan").toString());
//                                    modelPengembalian.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
//                                    modelPengembalian.setDue_date(dataObject.getString("due_date").toString());
//                                    modelPengembalian.setStatus_pengajuan(dataObject.getString("status_pengajuan").toString());
//                                    modelPengembalians.add(modelPengembalian);
//                                }


                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        adapterPengembalianBarang = new AdapterPengembalianBarang(getApplicationContext(), modelPengembalians);
                        recdafpin2.setAdapter(adapterPengembalianBarang);
                        adapterPengembalianBarang.setOnItemClickListener(ListPengembalian.this::onItemClick);
                        adapterPengembalianBarang.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void getData3() {
        modelPengembalians.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_PENGEMBALIAN_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                String cekstatus = "Diterima";
                                if(dataObject.getString("status").equals(cekstatus)){
                                    ModelPengembalian modelPengembalian = new ModelPengembalian();
                                    modelPengembalian.setIdpb(dataObject.getString("idpb").toString());
                                    modelPengembalian.setPid(dataObject.getString("pid").toString());
                                    modelPengembalian.setBpid(dataObject.getString("bpid").toString());
                                    modelPengembalian.setUid(dataObject.getString("uid").toString());
                                    modelPengembalian.setNama(dataObject.getString("nama").toString());
                                    modelPengembalian.setNis(dataObject.getString("nis").toString());
                                    modelPengembalian.setNama_barang(dataObject.getString("nama_barang").toString());
                                    modelPengembalian.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                    modelPengembalian.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                    modelPengembalian.setKeterangan(dataObject.getString("keterangan").toString());
                                    modelPengembalian.setJml_barang(dataObject.getString("jml_barang").toString());
                                    modelPengembalian.setStatus(dataObject.getString("status").toString());
                                    modelPengembalian.setImg_pengembalian(dataObject.getString("img_pengembalian").toString());
                                    modelPengembalians.add(modelPengembalian);
                                }


                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        adapterPengembalianBarang = new AdapterPengembalianBarang(getApplicationContext(), modelPengembalians);
                        recdafpin3.setAdapter(adapterPengembalianBarang);
                        adapterPengembalianBarang.setOnItemClickListener(ListPengembalian.this::onItemClick);
                        adapterPengembalianBarang.notifyDataSetChanged();

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
        Intent intent = new Intent(ListPengembalian.this, DetailPengembalian.class);
        ModelPengembalian click = modelPengembalians.get(position);
        intent.putExtra(EXTRA_LP_IDPB, click.getIdpb());
        intent.putExtra(EXTRA_LP_BPID, click.getBpid());
        intent.putExtra(EXTRA_LP_UID, click.getUid());
        intent.putExtra(EXTRA_LP_PID, click.getPid());
        intent.putExtra(EXTRA_LP_NAMEB, click.getNama_barang());
        intent.putExtra(EXTRA_LP_NAME, click.getNama());
        intent.putExtra(EXTRA_LP_TGL, click.getTgl_pinjam());
        intent.putExtra(EXTRA_LP_REQ, click.getReq_pinjam());
        intent.putExtra(EXTRA_LP_KET, click.getKeterangan());
        intent.putExtra(EXTRA_LP_TOTAL, click.getJml_barang());
        intent.putExtra(EXTRA_LP_STATUS, click.getStatus());
        intent.putExtra(EXTRA_LP_NIS, click.getNis());
        intent.putExtra(EXTRA_LP_IMG, click.getImg_pengembalian());
        intent.putExtra(EXTRA_LP_DUE, click.getDue_date());
        intent.putExtra(EXTRA_LP_TGL_TERIMA, click.getTgl_penerimaan());
        startActivity(intent);
    }
}