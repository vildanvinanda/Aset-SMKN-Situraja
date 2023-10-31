package com.example.projectwiki.Fragmen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Adapter.AdapterBarangPinjaman;
import com.example.projectwiki.Adapter.AdapterDaftarBarang;
import com.example.projectwiki.Adapter.AdapterNotif;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Detail.DetailBarang;
import com.example.projectwiki.Detail.DetailNotifikasi;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.Model.ModelNotif;
import com.example.projectwiki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notivication extends Fragment implements AdapterNotif.OnItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Notivication() {
    }
    public static Notivication newInstance(String param1, String param2) {
        Notivication fragment = new Notivication();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    List<ModelNotif> modelNotifs;
    List<DataUserModel> dataUserModels;
    private AdapterNotif adapterNotif;

    ImageView backbtnnotif;
    RecyclerView recviewnotif;

    public static final String EXTRA_N_BPID = "bpid";
    public static final String EXTRA_N_EMAIL = "email";
    public static final String EXTRA_N_TGL = "tgl_kirim";
    public static final String EXTRA_N_JDL = "judul";
    public static final String EXTRA_N_KET = "keterangan";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notovication, container, false);

        backbtnnotif = (ImageView) view.findViewById(R.id.backbtnnotif);
        recviewnotif = (RecyclerView) view.findViewById(R.id.recviewnotif);

        modelNotifs = new ArrayList<>();
        dataUserModels = new ArrayList<>();

        recviewnotif.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        String cek = getArguments().getString("contoh");

        getData4(cek);
        return view;
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

                                String uid = dataObject.getString("uid").toString();
                                String email = dataObject.getString("email").toString();

                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_NOTIF_URL+email, null,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {

                                                for (int i = 0; i <= response.length();i++){
                                                    try {

                                                        JSONObject dataObject = response.getJSONObject(i);

                                                        if(dataObject.getString("email").equals(email)){
                                                            ModelNotif modelNotif = new ModelNotif();
                                                            modelNotif.setNid(dataObject.getString("nid").toString());
                                                            modelNotif.setEmail(dataObject.getString("email").toString());
                                                            modelNotif.setBpid(dataObject.getString("bpid").toString());
                                                            modelNotif.setTgl_kirim(dataObject.getString("tgl_kirim").toString());
                                                            modelNotif.setJudul(dataObject.getString("judul").toString());
                                                            modelNotif.setKeterangan(dataObject.getString("keterangan").toString());
                                                            modelNotifs.add(modelNotif);
                                                        } else{

                                                        }


                                                    }catch (JSONException e){
                                                        e.printStackTrace();
                                                    }

                                                }
                                                adapterNotif = new AdapterNotif(getContext(), modelNotifs);
                                                recviewnotif.setAdapter(adapterNotif);
                                                adapterNotif.setOnItemClickListener(Notivication.this::onItemClick);
                                                adapterNotif.notifyDataSetChanged();

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });

                                requestQueue.add(jsonArrayRequest);

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

        Intent intent = new Intent(getActivity(), DetailNotifikasi.class);
        ModelNotif clickitm = modelNotifs.get(position);
        intent.putExtra(EXTRA_N_BPID, clickitm.getBpid());
        intent.putExtra(EXTRA_N_JDL, clickitm.getJudul());
        intent.putExtra(EXTRA_N_EMAIL, clickitm.getEmail());
        intent.putExtra(EXTRA_N_TGL, clickitm.getTgl_kirim());
        intent.putExtra(EXTRA_N_KET, clickitm.getKeterangan());
        startActivity(intent);

    }
}