package com.example.projectwiki.Fragmen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Adapter.AdapterRecItemDashboard;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Detail.DetailBarang;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.Model.ModelUser;
import com.example.projectwiki.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements AdapterRecItemDashboard.OnItemClickListener{

    public static final String EXTRA_UID_USER = "uiduser";
    public static final String EXTRA_NAME_USER = "namauser";
    public static final String EXTRA_NIS_USER = "nis";

    public static final String EXTRA_1NAME = "nama_barang";
    public static final String EXTRA_1BARANG = "jml_barang";
    public static final String EXTRA_1DES = "deskripsi";
    public static final String EXTRA_1TGL = "tgl_upload";
    public static final String EXTRA_1IMG = "img_barang";
    public static final String EXTRA_1BPID = "bpid";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
    public static final String SERVER_COUNT_USER_BARANG_URL = "http://192.168.100.14/projectwiki/countuser.php";

    List<DataUserModel> dataModels;
//    CountdownView countdownView;
    TextView txtuser,txtuidvisible, angka_pinjam,jdl2, txtnisvisible;
    List<ModelBarang> modelBarangs;

    RelativeLayout txtlihat2;
    private AdapterRecItemDashboard adapterRecItemDashboard;
    RecyclerView recview;
    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        countdownView = (CountdownView) view.findViewById(R.id.coundownid);
        txtuser = (TextView) view.findViewById(R.id.txtuser);
        angka_pinjam = (TextView) view.findViewById(R.id.angka_pinjam);
        txtuidvisible = (TextView) view.findViewById(R.id.txtuidvisible);
        txtnisvisible = (TextView) view.findViewById(R.id.txtnisvisible);
        jdl2 = (TextView) view.findViewById(R.id.jdl2);


//        txtuser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity2.class);
//                startActivity(intent);
//            }
//        });

        modelBarangs = new ArrayList<>();
        recview = (RecyclerView) view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        getData();

        dataModels = new ArrayList<>();

        String cek = getArguments().getString("contoh");
        getData4(cek);

        String test = "peringatan";


        return view;
    }

    private void getData7(String uuid) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AllDb.SERVER_COUNT_PINJAM_BARANG_URL+uuid, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String angka = response.getString("test");
                    angka_pinjam.setText(angka);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                angka_pinjam.setText(response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                angka_pinjam.setText("error");
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);


    }
    private void getData6(String uuid) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AllDb.SERVER_COUNT_PINJAM_BARANG_URL2+uuid, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String angka = response.getString("test");
                    angka_pinjam.setText(angka);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                angka_pinjam.setText(response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                angka_pinjam.setText("error");
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);


    }


    private void getData() {
        String cek = getArguments().getString("contoh");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_HIGHLIGHT_BARANG_URL, null,
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
                adapterRecItemDashboard = new AdapterRecItemDashboard(getContext(),modelBarangs);
                adapterRecItemDashboard.getItemCount();
                recview.setAdapter(adapterRecItemDashboard);
                adapterRecItemDashboard.setOnItemClickListener(Home.this::onItemClick);
                adapterRecItemDashboard.notifyDataSetChanged();

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
                                dataModels.add(dataUserModel);

                                txtuser.setText(dataObject.getString("nama").toString());
                                txtuidvisible.setText(dataObject.getString("uid").toString());
                                txtnisvisible.setText(dataObject.getString("nis").toString());

                                String cel = dataObject.getString("uid").toString();
                                String uuid = txtuidvisible.getText().toString();
                                getData7(uuid);
                                getData6(uuid);

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
        String cek = getArguments().getString("contoh");
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
                                dataUserModel.setNama(dataObject.getString("nama").toString());
                                dataModels.add(dataUserModel);

                                txtuser.setText(dataObject.getString("nama").toString());
                                txtuidvisible.setText(dataObject.getString("uid").toString());
                                txtnisvisible.setText(dataObject.getString("nis").toString());

                                String uname = txtuser.getText().toString();
                                String uuid = txtuidvisible.getText().toString();
                                String nis = txtnisvisible.getText().toString();

                                Intent intent = new Intent(getActivity(), DetailBarang.class);
                                ModelBarang clickitm = modelBarangs.get(position);

                                intent.putExtra(EXTRA_1NAME, clickitm.getNama_barang());
                                intent.putExtra(EXTRA_1BARANG, clickitm.getJml_barang());
                                intent.putExtra(EXTRA_1DES, clickitm.getDeskripsi());
                                intent.putExtra(EXTRA_1TGL, clickitm.getTgl_upload());
                                intent.putExtra(EXTRA_1IMG, clickitm.getImg_barang());
                                intent.putExtra(EXTRA_1BPID, clickitm.getBpid());
                                intent.putExtra(EXTRA_NAME_USER, uname);
                                intent.putExtra(EXTRA_UID_USER, uuid);
                                intent.putExtra(EXTRA_NIS_USER, nis);

                                startActivity(intent);

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


}