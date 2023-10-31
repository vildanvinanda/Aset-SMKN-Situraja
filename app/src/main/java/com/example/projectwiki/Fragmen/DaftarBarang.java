package com.example.projectwiki.Fragmen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Adapter.AdapterBarangPinjaman;
import com.example.projectwiki.Adapter.AdapterDaftarBarang;
import com.example.projectwiki.Adapter.AdapterRecItemDashboard;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Detail.DetailBarang;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.Model.ModelDafPin;
import com.example.projectwiki.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DaftarBarang extends Fragment implements AdapterDaftarBarang.OnItemClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public DaftarBarang() {

    }
    public static DaftarBarang newInstance(String param1, String param2) {
        DaftarBarang fragment = new DaftarBarang();
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


    private AdapterDaftarBarang adapterDaftarBarang;
    List<DataUserModel> dataModels;
    List<ModelBarang> modelBarangs;

    EditText  kolomsearch;
    RecyclerView rec_barang,rec_barang2;
    TextView txt_uid,txt_nis,txt_nama;

    public static final String EXTRA_DB_UID = "uid";
    public static final String EXTRA_DB_NAME = "nama";
    public static final String EXTRA_DB_NIS = "nis";

    public static final String EXTRA_DB_BPID = "bpid";
    public static final String EXTRA_DB_NAMEB = "nama_barang";
    public static final String EXTRA_DB_TGL = "tgl_upload";
    public static final String EXTRA_DB_IMG = "img_barang";
    public static final String EXTRA_DB_DES = "deskripsi";
    public static final String EXTRA_DB_JML = "jml_barang";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daftar_barang, container, false);


        modelBarangs = new ArrayList<>();
        dataModels = new ArrayList<>();

        kolomsearch = (EditText) view.findViewById(R.id.kolomsearch);
        rec_barang = (RecyclerView) view.findViewById(R.id.rec_barang);
        rec_barang2 = (RecyclerView) view.findViewById(R.id.rec_barang2);
        txt_uid = (TextView) view.findViewById(R.id.txt_uid);
        txt_nis = (TextView) view.findViewById(R.id.txt_nis);
        txt_nama = (TextView) view.findViewById(R.id.txt_nama);

        rec_barang.setLayoutManager(new GridLayoutManager(getContext(),2));

        rec_barang2.setLayoutManager(new GridLayoutManager(getContext(),2));

        getData();

        String cek = getArguments().getString("contoh");
        getData4(cek);

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

        return view;
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
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                            adapterDaftarBarang = new AdapterDaftarBarang(getContext(), modelBarangs);
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
        String cek = getArguments().getString("contoh");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        adapterDaftarBarang = new AdapterDaftarBarang(getContext(),modelBarangs);
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

    private void getData4(String cek) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER+cek, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {
//                        String user = firebaseAuth.getUid();
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        dataModels.add(new DataModel(
//                                jsonObject.getString("namahewan"),
//                                jsonObject.getString("jk"),
//                                jsonObject.getString("statushewan"),
//                                jsonObject.getString("id"),
//                                jsonObject.getString("imaghewan"),
//                                jsonObject.getString("nokandang"),
//                                jsonObject.getString("statuskesehatan")
//                        ));
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
                                dataModels.add(dataUserModel);

                                txt_nis.setText(dataObject.getString("nis").toString());
                                txt_uid.setText(dataObject.getString("uid").toString());
                                txt_nama.setText(dataObject.getString("nama").toString());


                                String cel = dataObject.getString("uid").toString();


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

        String nama = txt_nama.getText().toString();
        String uuid = txt_uid.getText().toString();
        String nis = txt_nis.getText().toString();

        Intent intent = new Intent(getActivity(), DetailBarang.class);
        ModelBarang clickitm = modelBarangs.get(position);
        intent.putExtra(EXTRA_DB_NAMEB, clickitm.getNama_barang());
        intent.putExtra(EXTRA_DB_JML, clickitm.getJml_barang());
        intent.putExtra(EXTRA_DB_DES, clickitm.getDeskripsi());
        intent.putExtra(EXTRA_DB_TGL, clickitm.getTgl_upload());
        intent.putExtra(EXTRA_DB_IMG, clickitm.getImg_barang());
        intent.putExtra(EXTRA_DB_BPID, clickitm.getBpid());
        intent.putExtra(EXTRA_DB_NAME, nama);
        intent.putExtra(EXTRA_DB_UID, uuid);
        intent.putExtra(EXTRA_DB_NIS, nis);

        startActivity(intent);

    }
}