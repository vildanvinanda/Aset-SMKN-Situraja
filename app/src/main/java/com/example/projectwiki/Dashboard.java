package com.example.projectwiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Admin.HomeAdmin;
import com.example.projectwiki.Detail.DetailBarang;
import com.example.projectwiki.Form.FormPengajuan;
import com.example.projectwiki.Fragmen.DaftarBarang;
import com.example.projectwiki.Fragmen.DaftarPinjaman;
import com.example.projectwiki.Fragmen.Home;
import com.example.projectwiki.Fragmen.Notivication;
import com.example.projectwiki.Fragmen.Profil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class Dashboard extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

//    TextView contoh,w;
    List<DataUserModel> dataModels;

    FloatingActionButton market;



    private Home homeFragment = new Home();
    private DaftarPinjaman daftarPinjaman = new DaftarPinjaman();
    private Notivication notivication = new Notivication();
    private DaftarBarang daftarBarang = new DaftarBarang();
    private Profil profil = new Profil();

    private FormPengajuan formPengajuan = new FormPengajuan();

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        market = (FloatingActionButton) findViewById(R.id.market);

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                market.setImageResource(R.drawable.ic_bag_active);
                bottomNavigationView.setSelectedItemId(R.id.minull);
//                Intent intent = new Intent(Dashboard.this, MainActivity2.class);
//                startActivity(intent);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
//        w = findViewById(R.id.w);
//        contoh = findViewById(R.id.contoh);
        dataModels = new ArrayList<>();



        final SharedPreferences sharedPreferences = getSharedPreferences("sesi_login", MODE_PRIVATE);
        SharedPreferences sesi = getSharedPreferences("sessi_login", MODE_PRIVATE);

        if(sesi.contains("email")){
            Intent intent = new Intent(Dashboard.this, Login.class);
            startActivity(intent);
            return;
        }



//        getData4(textw);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnItemSelectedListener(this);


        String textw = sharedPreferences.getString("email", null).toString();
        Bundle bundle = new Bundle();
        bundle.putString("contoh", textw);
        homeFragment.setArguments(bundle);
        notivication.setArguments(bundle);
        daftarPinjaman.setArguments(bundle);
        daftarBarang.setArguments(bundle);
        profil.setArguments(bundle);





    }



    private void enableBottomBar(){
        Boolean enable = true;
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).setEnabled(enable);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        final SharedPreferences sharedPreferences = getSharedPreferences("sesi_login", MODE_PRIVATE);
        SharedPreferences sesi = getSharedPreferences("sessi_login", MODE_PRIVATE);


        switch (item.getItemId()){
            case R.id.miHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
                market.setImageResource(R.drawable.ic_bag);
                return true;
            case R.id.miDafPin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, daftarPinjaman).commit();
                market.setImageResource(R.drawable.ic_bag);
                return true;
            case R.id.minull:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, daftarBarang).commit();
                market.setImageResource(R.drawable.ic_bag_active);
                return true;
            case R.id.miNotif:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, notivication).commit();
                market.setImageResource(R.drawable.ic_bag);
                return true;
            case R.id.miProfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, profil).commit();
                market.setImageResource(R.drawable.ic_bag);
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    private void getData4(String text) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER+text, null,
//                new Response.Listener<JSONArray>(){
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        for (int i = 0; i <= response.length();i++){
//                            try {
////                        String user = firebaseAuth.getUid();
////                        JSONObject jsonObject = response.getJSONObject(i);
////                        dataModels.add(new DataModel(
////                                jsonObject.getString("namahewan"),
////                                jsonObject.getString("jk"),
////                                jsonObject.getString("statushewan"),
////                                jsonObject.getString("id"),
////                                jsonObject.getString("imaghewan"),
////                                jsonObject.getString("nokandang"),
////                                jsonObject.getString("statuskesehatan")
////                        ));
//                                JSONObject dataObject = response.getJSONObject(i);
//
//                                DataUserModel dataUserModel = new DataUserModel();
//                                dataUserModel.setUid(dataObject.getString("uid").toString());
//                                dataUserModel.setNis(dataObject.getString("nis").toString());
//                                dataUserModel.setEmail(dataObject.getString("email").toString());
//                                dataUserModel.setPassword(dataObject.getString("password").toString());
//                                dataUserModel.setNama(dataObject.getString("nama").toString());
//                                dataUserModel.setKelas(dataObject.getString("kelas").toString());
//                                dataUserModel.setNo_hp(dataObject.getString("no_hp").toString());
//                                dataUserModel.setJk(dataObject.getString("jk").toString());
//                                dataUserModel.setAlamat(dataObject.getString("alamat").toString());
//                                dataUserModel.setType_user(dataObject.getString("type_user").toString());
//                                dataModels.add(dataUserModel);
//
//                                String cek = ;
//                                Bundle bundle = new Bundle();
//                                bundle.putString("contohh", textw);
//                                homeFragment.setArguments(bundle);
//
//                            }catch (JSONException e){
//                                e.printStackTrace();
//                            }
//                        }
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        requestQueue.add(jsonArrayRequest);
//    }
}