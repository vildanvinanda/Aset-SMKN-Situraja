package com.example.projectwiki.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Adapter.AdapterDaftarBarang;
import com.example.projectwiki.Adapter.AdapterDaftarBarangAdmin;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Detail.DetailBarang;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaftarBarang extends AppCompatActivity implements AdapterDaftarBarangAdmin.OnItemClickListener{

    private AdapterDaftarBarangAdmin adapterDaftarBarang;
    List<DataUserModel> dataModels;
    List<ModelBarang> modelBarangs;
    ImageView btn_back;
    EditText kolomsearch;
    RecyclerView rec_barang,rec_barang2;
    TextView txt_uid,txt_nis,txt_nama;
    FloatingActionButton btn_tambahBarang;

    public static final String EXTRA_DB2_BPID = "bpid";
    public static final String EXTRA_DB2_NAMEB = "nama_barang";
    public static final String EXTRA_DB2_TGL = "tgl_upload";
    public static final String EXTRA_DB2_IMG = "img_barang";
    public static final String EXTRA_DB2_DES = "deskripsi";
    public static final String EXTRA_DB2_JML = "jml_barang";
    public static final String EXTRA_DB2_TYPE = "type_user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_barang);

        modelBarangs = new ArrayList<>();
        dataModels = new ArrayList<>();

        kolomsearch = (EditText) findViewById(R.id.kolomsearch);
        rec_barang = (RecyclerView) findViewById(R.id.rec_barang);
        rec_barang2 = (RecyclerView) findViewById(R.id.rec_barang2);
        btn_tambahBarang = (FloatingActionButton) findViewById(R.id.btn_tambahBarang);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        txt_uid = (TextView) findViewById(R.id.txt_uid);
        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_nama = (TextView) findViewById(R.id.txt_nama);

        rec_barang.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        rec_barang2.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_tambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarBarang.this, FormTambahBarang.class);
                startActivity(intent);
            }
        });
        getData();

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

    }
    private void getData2(String cek) {
        if (cek.isEmpty()) {
            rec_barang.setVisibility(View.VISIBLE);
            rec_barang2.setVisibility(View.GONE);
            getData();

        } else {
            rec_barang.setVisibility(View.GONE);
            rec_barang2.setVisibility(View.VISIBLE);

            modelBarangs.clear();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_BARANG_URL+cek, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i <= response.length();i++){
                                try {

                                    JSONObject dataObject = response.getJSONObject(i);

                                    String uuid = txt_uid.getText().toString();

                                    ModelBarang modelBarangn = new ModelBarang();
                                    modelBarangn.setBpid(dataObject.getString("bpid").toString());
                                    modelBarangn.setNama_barang(dataObject.getString("nama_barang").toString());
                                    modelBarangn.setJml_barang(dataObject.getString("jml_barang").toString());
                                    modelBarangn.setDeskripsi(dataObject.getString("deskripsi").toString());
                                    modelBarangn.setTgl_upload(dataObject.getString("tgl_upload").toString());
                                    modelBarangn.setImg_barang(dataObject.getString("img_barang").toString());
                                    modelBarangs.add(modelBarangn);



                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                            adapterDaftarBarang = new AdapterDaftarBarangAdmin(getApplicationContext(), modelBarangs);
                            rec_barang2.setAdapter(adapterDaftarBarang);
                            adapterDaftarBarang.setOnItemClickListener(DaftarBarang.this::onItemClick);
                            adapterDaftarBarang.notifyDataSetChanged();

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

        modelBarangs.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_BARANG_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);
                                ModelBarang modelBarang = new ModelBarang();
                                modelBarang.setBpid(dataObject.getString("bpid").toString());
                                modelBarang.setNama_barang(dataObject.getString("nama_barang").toString());
                                modelBarang.setDeskripsi(dataObject.getString("deskripsi").toString());
                                modelBarang.setImg_barang(dataObject.getString("img_barang").toString());
                                modelBarang.setJml_barang(dataObject.getString("jml_barang").toString());
                                modelBarang.setTgl_upload(dataObject.getString("tgl_upload").toString());
                                modelBarangs.add(modelBarang);

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapterDaftarBarang = new AdapterDaftarBarangAdmin(getApplicationContext(),modelBarangs);
                        rec_barang.setAdapter(adapterDaftarBarang);
                        adapterDaftarBarang.setOnItemClickListener(DaftarBarang.this::onItemClick);
                        adapterDaftarBarang.notifyDataSetChanged();

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

        String nama = txt_nama.getText().toString();
        String uuid = txt_uid.getText().toString();
        String nis = txt_nis.getText().toString();

        Intent in = getIntent();
        String cek = in.getStringExtra(HomeAdmin.EXTRA_HA_TYPE);

        Intent intent = new Intent(DaftarBarang.this, DetailBarang.class);
        ModelBarang clickitm = modelBarangs.get(position);
        intent.putExtra(EXTRA_DB2_NAMEB, clickitm.getNama_barang());
        intent.putExtra(EXTRA_DB2_JML, clickitm.getJml_barang());
        intent.putExtra(EXTRA_DB2_DES, clickitm.getDeskripsi());
        intent.putExtra(EXTRA_DB2_TGL, clickitm.getTgl_upload());
        intent.putExtra(EXTRA_DB2_IMG, clickitm.getImg_barang());
        intent.putExtra(EXTRA_DB2_BPID, clickitm.getBpid());
        intent.putExtra(EXTRA_DB2_TYPE, "admin");

        startActivity(intent);

    }
}