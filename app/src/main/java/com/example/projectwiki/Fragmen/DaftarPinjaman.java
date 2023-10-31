package com.example.projectwiki.Fragmen;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Adapter.AdapterBarangPinjaman;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Detail.DetailBarangPinjaman;
import com.example.projectwiki.Model.ModelDafPin;
import com.example.projectwiki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DaftarPinjaman extends Fragment implements AdapterBarangPinjaman.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<ModelDafPin> modelDafPins;
    List<DataUserModel> dataUserModels;

    private AdapterBarangPinjaman adapterBarangPinjaman;
    RelativeLayout btnsemua, btnfilter1, btnfilter2;
    EditText kolomsearch;
    RecyclerView recdafpin,recdafpin2, recdafpin3, recdafpin4;
    TextView txtall,txtpinjam,txtkembali,txt_uid,test,txt_nis,txt_email;

    public static final String EXTRA_DP_BPID = "bpid";
    public static final String EXTRA_DP_UID = "uid";
    public static final String EXTRA_DP_PID = "pid";
    public static final String EXTRA_DP_NAME = "nama";
    public static final String EXTRA_DP_NAMEB = "nama_barang";
    public static final String EXTRA_DP_TGL = "tgl_pinjam";
    public static final String EXTRA_DP_REQ = "req_pinjam";
    public static final String EXTRA_DP_DUE = "due_date";
    public static final String EXTRA_DP_KET = "keterangan";
    public static final String EXTRA_DP_TOTAL = "total_bpinjam";
    public static final String EXTRA_DP_STATUS = "status";
    public static final String EXTRA_DP_NIS = "nis";
    public static final String EXTRA_DP_EMAIL = "email";

    private String mParam1;
    private String mParam2;

    public static DaftarPinjaman newInstance(String param1, String param2) {
        DaftarPinjaman fragment = new DaftarPinjaman();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DaftarPinjaman() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_daftar_pinjaman, container, false);

       modelDafPins = new ArrayList<>();


        btnsemua = (RelativeLayout) view.findViewById(R.id.btnsemua);
        btnfilter1 = (RelativeLayout) view.findViewById(R.id.btnfilter1);
        btnfilter2 = (RelativeLayout) view.findViewById(R.id.btnfilter2);
        kolomsearch = (EditText) view.findViewById(R.id.kolomsearch);

        recdafpin = (RecyclerView) view.findViewById(R.id.recdafpin);
        recdafpin2 = (RecyclerView) view.findViewById(R.id.recdafpin2);
        recdafpin3 = (RecyclerView) view.findViewById(R.id.recdafpin3);
        recdafpin4 = (RecyclerView) view.findViewById(R.id.recdafpin4);

        txtall = (TextView) view.findViewById(R.id.txtall);
        txt_email = (TextView) view.findViewById(R.id.txt_email);
        txt_nis = (TextView) view.findViewById(R.id.txt_nis);
        txt_uid = (TextView) view.findViewById(R.id.txt_uid);
        txtpinjam = (TextView) view.findViewById(R.id.txtpinjam);
        txtkembali = (TextView) view.findViewById(R.id.txtkembali);
        test = (TextView) view.findViewById(R.id.test);


        recdafpin.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recdafpin2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recdafpin3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recdafpin4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));

        dataUserModels = new ArrayList<>();
        String cek = getArguments().getString("contoh");
        String uuid = test.getText().toString();


//        test.setText(uuid);
        getData();
        getData4(cek);

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

       return view;
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

            modelDafPins.clear();
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_URL+text, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i <= response.length();i++){
                                try {

                                    JSONObject dataObject = response.getJSONObject(i);

                                    String uuid = txt_uid.getText().toString();
                                    if(dataObject.getString("uid").equals(uuid)){
                                        ModelDafPin modelDafPin = new ModelDafPin();
                                        modelDafPin.setPid(dataObject.getString("pid").toString());
                                        modelDafPin.setBpid(dataObject.getString("bpid").toString());
                                        modelDafPin.setUid(dataObject.getString("uid").toString());
                                        modelDafPin.setNama(dataObject.getString("nama").toString());
                                        modelDafPin.setNama_barang(dataObject.getString("nama_barang").toString());
                                        modelDafPin.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                        modelDafPin.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                        modelDafPin.setKeterangan(dataObject.getString("keterangan").toString());
                                        modelDafPin.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                                        modelDafPin.setDue_date(dataObject.getString("due_date").toString());
                                        modelDafPin.setStatus(dataObject.getString("status").toString());
                                        modelDafPins.add(modelDafPin);
                                    } else{

                                    }


                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                            adapterBarangPinjaman = new AdapterBarangPinjaman(getContext(), modelDafPins);
                            recdafpin4.setAdapter(adapterBarangPinjaman);
                            adapterBarangPinjaman.setOnItemClickListener(DaftarPinjaman.this::onItemClick);
                            adapterBarangPinjaman.notifyDataSetChanged();

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

        modelDafPins.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_BPINJAM_URL, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i <= response.length();i++){
                    try {

                        JSONObject dataObject = response.getJSONObject(i);

                        String uuid = txt_uid.getText().toString();
                        if(dataObject.getString("uid").equals(uuid)){
                            ModelDafPin modelDafPin = new ModelDafPin();
                            modelDafPin.setPid(dataObject.getString("pid").toString());
                            modelDafPin.setBpid(dataObject.getString("bpid").toString());
                            modelDafPin.setUid(dataObject.getString("uid").toString());
                            modelDafPin.setNama(dataObject.getString("nama").toString());
                            modelDafPin.setNama_barang(dataObject.getString("nama_barang").toString());
                            modelDafPin.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                            modelDafPin.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                            modelDafPin.setKeterangan(dataObject.getString("keterangan").toString());
                            modelDafPin.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                            modelDafPin.setDue_date(dataObject.getString("due_date").toString());
                            modelDafPin.setStatus(dataObject.getString("status").toString());
                            modelDafPins.add(modelDafPin);
                        } else{

                        }


                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
                adapterBarangPinjaman = new AdapterBarangPinjaman(getContext(), modelDafPins);
                recdafpin.setAdapter(adapterBarangPinjaman);
                adapterBarangPinjaman.setOnItemClickListener(DaftarPinjaman.this::onItemClick);
                adapterBarangPinjaman.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void getData2() {
        modelDafPins.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_BPINJAM_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                String uuid = txt_uid.getText().toString();
                                if(dataObject.getString("uid").equals(uuid)){
                                    if(dataObject.getString("status").equals("aktif")){
                                        ModelDafPin modelDafPin = new ModelDafPin();
                                        modelDafPin.setPid(dataObject.getString("pid").toString());
                                        modelDafPin.setBpid(dataObject.getString("bpid").toString());
                                        modelDafPin.setUid(dataObject.getString("uid").toString());
                                        modelDafPin.setNama(dataObject.getString("nama").toString());
                                        modelDafPin.setNama_barang(dataObject.getString("nama_barang").toString());
                                        modelDafPin.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                        modelDafPin.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                        modelDafPin.setKeterangan(dataObject.getString("keterangan").toString());
                                        modelDafPin.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                                        modelDafPin.setDue_date(dataObject.getString("due_date").toString());
                                        modelDafPin.setStatus(dataObject.getString("status").toString());
                                        modelDafPins.add(modelDafPin);
                                    } else if (dataObject.getString("status").equals("peringatan")){
                                        ModelDafPin modelDafPin = new ModelDafPin();
                                        modelDafPin.setPid(dataObject.getString("pid").toString());
                                        modelDafPin.setBpid(dataObject.getString("bpid").toString());
                                        modelDafPin.setUid(dataObject.getString("uid").toString());
                                        modelDafPin.setNama(dataObject.getString("nama").toString());
                                        modelDafPin.setNama_barang(dataObject.getString("nama_barang").toString());
                                        modelDafPin.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                        modelDafPin.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                        modelDafPin.setKeterangan(dataObject.getString("keterangan").toString());
                                        modelDafPin.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                                        modelDafPin.setDue_date(dataObject.getString("due_date").toString());
                                        modelDafPin.setStatus(dataObject.getString("status").toString());
                                        modelDafPins.add(modelDafPin);
                                    }
                                } else{

                                }


                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        adapterBarangPinjaman = new AdapterBarangPinjaman(getContext(), modelDafPins);
                        recdafpin2.setAdapter(adapterBarangPinjaman);
                        adapterBarangPinjaman.setOnItemClickListener(DaftarPinjaman.this::onItemClick);
                        adapterBarangPinjaman.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void getData3() {
        modelDafPins.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_BPINJAM_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);

                                String uuid = txt_uid.getText().toString();
                                if(dataObject.getString("uid").equals(uuid)){
                                    if(dataObject.getString("status").equals("tidak aktif")){
                                        ModelDafPin modelDafPin = new ModelDafPin();
                                        modelDafPin.setPid(dataObject.getString("pid").toString());
                                        modelDafPin.setBpid(dataObject.getString("bpid").toString());
                                        modelDafPin.setUid(dataObject.getString("uid").toString());
                                        modelDafPin.setNama(dataObject.getString("nama").toString());
                                        modelDafPin.setNama_barang(dataObject.getString("nama_barang").toString());
                                        modelDafPin.setTgl_pinjam(dataObject.getString("tgl_pinjam").toString());
                                        modelDafPin.setReq_pinjam(dataObject.getString("req_pinjam").toString());
                                        modelDafPin.setKeterangan(dataObject.getString("keterangan").toString());
                                        modelDafPin.setTotal_bpinjam(dataObject.getString("total_bpinjam").toString());
                                        modelDafPin.setDue_date(dataObject.getString("due_date").toString());
                                        modelDafPin.setStatus(dataObject.getString("status").toString());
                                        modelDafPins.add(modelDafPin);
                                    }
                                } else{

                                }


                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        adapterBarangPinjaman = new AdapterBarangPinjaman(getContext(), modelDafPins);
                        recdafpin3.setAdapter(adapterBarangPinjaman);
                        adapterBarangPinjaman.setOnItemClickListener(DaftarPinjaman.this::onItemClick);
                        adapterBarangPinjaman.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void getData4(String cek) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER+cek, null,
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
                                dataUserModels.add(dataUserModel);

//                                txtuser.setText(dataObject.getString("nama").toString());
                                txt_uid.setText(dataObject.getString("uid").toString());
                                txt_nis.setText(dataObject.getString("nis").toString());
                                txt_email.setText(dataObject.getString("email").toString());
//                                test.setText(dataObject.getString("uid").toString());

//                                String cel = dataObject.getString("uid").toString();


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

    @Override
    public void onItemClick(int position) {

        String nis = txt_nis.getText().toString();
        String email = txt_email.getText().toString();
//
        Intent intent = new Intent(getActivity(), DetailBarangPinjaman.class);
        ModelDafPin click = modelDafPins.get(position);
        intent.putExtra(EXTRA_DP_BPID, click.getBpid());
        intent.putExtra(EXTRA_DP_UID, click.getUid());
        intent.putExtra(EXTRA_DP_PID, click.getPid());
        intent.putExtra(EXTRA_DP_NAMEB, click.getNama_barang());
        intent.putExtra(EXTRA_DP_NAME, click.getNama());
        intent.putExtra(EXTRA_DP_TGL, click.getTgl_pinjam());
        intent.putExtra(EXTRA_DP_REQ, click.getReq_pinjam());
        intent.putExtra(EXTRA_DP_DUE, click.getDue_date());
        intent.putExtra(EXTRA_DP_KET, click.getKeterangan());
        intent.putExtra(EXTRA_DP_TOTAL, click.getTotal_bpinjam());
        intent.putExtra(EXTRA_DP_STATUS, click.getStatus());
        intent.putExtra(EXTRA_DP_EMAIL, email);
        intent.putExtra(EXTRA_DP_NIS, nis);
        startActivity(intent);

    }
}