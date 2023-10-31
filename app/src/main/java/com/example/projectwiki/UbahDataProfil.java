package com.example.projectwiki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projectwiki.Adapter.AdapterPengembalianBarang;
import com.example.projectwiki.Admin.ListPengembalian;
import com.example.projectwiki.Fragmen.Profil;
import com.example.projectwiki.Model.ModelDafPin;
import com.example.projectwiki.Model.ModelPengajuan;
import com.example.projectwiki.Model.ModelPengembalian;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UbahDataProfil extends AppCompatActivity {
    ImageView rowright, rowright2, rowright3, rowright4, rowright5, btn_back;
    ImageView rowright_admin, rowright2_admin, rowright3_admin;
    RelativeLayout admin_domain, user_domain;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_profil);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        rowright = (ImageView) findViewById(R.id.rowright);
        rowright_admin = (ImageView) findViewById(R.id.rowright_admin);
        rowright2_admin = (ImageView) findViewById(R.id.rowright2_admin);
        rowright3_admin = (ImageView) findViewById(R.id.rowright3_admin);
        rowright2 = (ImageView) findViewById(R.id.rowright2);
        rowright3 = (ImageView) findViewById(R.id.rowright3);
        rowright4 = (ImageView) findViewById(R.id.rowright4);
        rowright5 = (ImageView) findViewById(R.id.rowright5);
        admin_domain = (RelativeLayout) findViewById(R.id.admin_domain);
        user_domain = (RelativeLayout) findViewById(R.id.user_domain);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Data sedang di update");
        dialog.setCanceledOnTouchOutside(false);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String alamat = intent.getStringExtra(ProfilSetting.EXTRA_PS_ALAMAT);
        String type = intent.getStringExtra(ProfilSetting.EXTRA_PS_TYPE);

        if (type.equals("user")) {
            user_domain.setVisibility(View.VISIBLE);
            admin_domain.setVisibility(View.GONE);
        } else {
            user_domain.setVisibility(View.GONE);
            admin_domain.setVisibility(View.VISIBLE);
        }

        rowright_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UbahDataProfil.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_ubahuser, (RelativeLayout) findViewById(R.id.bottomubahusername)
                        );
                EditText edduser = (EditText) bottomSheetView.findViewById(R.id.edduser);
//                ImageView btnclosefilter = bottomSheetView.findViewById(R.id.btnclosefilter);
                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.btnubahusername).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String addname = edduser.getText().toString().trim();

                        if (TextUtils.isEmpty(addname)) {
                            edduser.requestFocus();
                            edduser.setError("Tolong diisi");
                        } else {
                            updateName(addname);
                        }


                    }

                    private void updateName(String addname) {
                        dialog.show();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);

                        dialog.show();
                        List<DataUserModel> dataUserModels = new ArrayList<>();

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
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
                                                dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                dataUserModels.add(dataUserModel);

                                                String nama = dataObject.getString("nama").toString();
                                                String nohp = dataObject.getString("no_hp").toString();
                                                String jk = dataObject.getString("jk").toString();
                                                String alamat = dataObject.getString("alamat").toString();
                                                String kls = dataObject.getString("kelas").toString();
                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", addname);
                                                        params.put("kelas", kls);
                                                        params.put("no_hp", nohp);
                                                        params.put("jk", jk);
                                                        params.put("alamat", alamat);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } catch (JSONException e) {
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


                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright2_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UbahDataProfil.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_ubahnomor, (RelativeLayout) findViewById(R.id.bottomubahnohp)
                        );

//                ImageView btnclosefilter = bottomSheetView.findViewById(R.id.btnclosefilter);
                EditText eddnomor = (EditText) bottomSheetView.findViewById(R.id.eddnomerhp);
                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btnubahnohp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String addnomor = eddnomor.getText().toString().trim();

                        if (TextUtils.isEmpty(addnomor)) {
                            eddnomor.requestFocus();
                            eddnomor.setError("Tolong diisi");
                        } else {
                            updateNomor();
                        }


                    }

                    private void updateNomor() {
                        dialog.show();
                        List<DataUserModel> dataUserModels = new ArrayList<>();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);
                        String addnomor = eddnomor.getText().toString().trim();

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
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
                                                dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                dataUserModels.add(dataUserModel);

                                                String nama = dataObject.getString("nama").toString();
                                                String nohp = dataObject.getString("no_hp").toString();
                                                String jk = dataObject.getString("jk").toString();
                                                String alamat = dataObject.getString("alamat").toString();
                                                String kls = dataObject.getString("kelas").toString();
                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", nama);
                                                        params.put("kelas", kls);
                                                        params.put("no_hp", addnomor);
                                                        params.put("jk", jk);
                                                        params.put("alamat", alamat);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } catch (JSONException e) {
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

                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright3_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UbahDataProfil.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_alamat, (RelativeLayout) findViewById(R.id.modalalamat)
                        );
                EditText eddalamat = (EditText) bottomSheetView.findViewById(R.id.eddalamat);
//                ImageView btnclosefilter = bottomSheetView.findViewById(R.id.btnclosefilter);
                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.btnupdatealamat).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String alamat = eddalamat.getText().toString();
                        if (TextUtils.isEmpty(alamat)) {
                            eddalamat.requestFocus();
                            eddalamat.setError("Tolong diisi");
                        } else {
                            updateAlamat(alamat);
                        }

                    }

                    private void updateAlamat(String alamat) {
                        dialog.show();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);

                        dialog.show();
                        List<DataUserModel> dataUserModels = new ArrayList<>();

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
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
                                                dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                dataUserModels.add(dataUserModel);

                                                String nama = dataObject.getString("nama").toString();
                                                String nohp = dataObject.getString("no_hp").toString();
                                                String jk = dataObject.getString("jk").toString();
//                                                String alamat = dataObject.getString("alamat").toString();
                                                String kls = dataObject.getString("kelas").toString();
                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", nama);
                                                        params.put("kelas", kls);
                                                        params.put("no_hp", nohp);
                                                        params.put("jk", jk);
                                                        params.put("alamat", alamat);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } catch (JSONException e) {
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
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UbahDataProfil.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_ubahuser, (RelativeLayout) findViewById(R.id.bottomubahusername)
                        );
                EditText edduser = (EditText) bottomSheetView.findViewById(R.id.edduser);
//                ImageView btnclosefilter = bottomSheetView.findViewById(R.id.btnclosefilter);
                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.btnubahusername).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String addname = edduser.getText().toString().trim();

                        if (TextUtils.isEmpty(addname)) {
                            edduser.requestFocus();
                            edduser.setError("Tolong diisi");
                        } else {
                            updateName(addname);
                            updateName2(addname);
                            updateName3(addname);
                        }


                    }

                    private void updateName3(String addname) {
                        //in ubah nama form penjam
                        RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());
                        List<ModelPengajuan> modelPengajuans = new ArrayList<>();
                        JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_PENGAJUAN_URL, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
                                            try {

                                                JSONObject dataObject = response.getJSONObject(i);

                                                ModelPengajuan modelPengajuan = new ModelPengajuan();
                                                modelPengajuan.setUid(dataObject.getString("uid").toString());
                                                modelPengajuan.setNama(dataObject.getString("nama").toString());
                                                modelPengajuans.add(modelPengajuan);

                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest3 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_NAMA_FORM_PINJAM_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
//                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
//                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", addname);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest3);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        requestQueue3 .add(jsonArrayRequest3);
                    }

                    private void updateName2(String addname) {
                        //in ubah nama penhembalian
                        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
                        List<ModelPengembalian> modelPengembalians = new ArrayList<>();
                        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_PENGEMBALIAN_URL, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
                                            try {

                                                JSONObject dataObject = response.getJSONObject(i);

                                                ModelPengembalian modelPengembalian = new ModelPengembalian();
                                                modelPengembalian.setUid(dataObject.getString("uid").toString());
                                                modelPengembalian.setNama(dataObject.getString("nama").toString());
                                                modelPengembalians.add(modelPengembalian);

                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_NAMA_PENGEMBALIAN_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
//                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
//                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", addname);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        requestQueue2.add(jsonArrayRequest2);
                    }

                    private void updateName(String addname) {
                        dialog.show();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);

                        dialog.show();
                        List<DataUserModel> dataUserModels = new ArrayList<>();

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
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
                                                dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                dataUserModels.add(dataUserModel);

                                                String nama = dataObject.getString("nama").toString();
                                                String nohp = dataObject.getString("no_hp").toString();
                                                String jk = dataObject.getString("jk").toString();
                                                String alamat = dataObject.getString("alamat").toString();
                                                String kls = dataObject.getString("kelas").toString();
                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
//                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
//                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", addname);
                                                        params.put("kelas", kls);
                                                        params.put("no_hp", nohp);
                                                        params.put("jk", jk);
                                                        params.put("alamat", alamat);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } catch (JSONException e) {
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


                        //in ubah nama pinjaman
                        RequestQueue requestQueue4 = Volley.newRequestQueue(getApplicationContext());
                        List<ModelDafPin> modelDafPins = new ArrayList<>();
                        JsonArrayRequest jsonArrayRequest4 = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_BPINJAM_URL, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
                                            try {

                                                JSONObject dataObject = response.getJSONObject(i);

                                                ModelDafPin modelDafPin = new ModelDafPin();
                                                modelDafPin.setUid(dataObject.getString("uid").toString());
                                                modelDafPin.setNama(dataObject.getString("nama").toString());
                                                modelDafPins.add(modelDafPin);

                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest3 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_NAMA_PINJAMAN_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", addname);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest3);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        requestQueue4 .add(jsonArrayRequest4);


                    }


                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UbahDataProfil.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_ubahnomor, (RelativeLayout) findViewById(R.id.bottomubahnohp)
                        );

//                ImageView btnclosefilter = bottomSheetView.findViewById(R.id.btnclosefilter);
                EditText eddnomor = (EditText) bottomSheetView.findViewById(R.id.eddnomerhp);
                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btnubahnohp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String addnomor = eddnomor.getText().toString().trim();

                        if (TextUtils.isEmpty(addnomor)) {
                            eddnomor.requestFocus();
                            eddnomor.setError("Tolong diisi");
                        } else {
                            updateNomor();
                        }


                    }

                    private void updateNomor() {
                        dialog.show();
                        List<DataUserModel> dataUserModels = new ArrayList<>();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);
                        String addnomor = eddnomor.getText().toString().trim();

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
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
                                                dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                dataUserModels.add(dataUserModel);

                                                String nama = dataObject.getString("nama").toString();
                                                String nohp = dataObject.getString("no_hp").toString();
                                                String jk = dataObject.getString("jk").toString();
                                                String alamat = dataObject.getString("alamat").toString();
                                                String kls = dataObject.getString("kelas").toString();
                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", nama);
                                                        params.put("kelas", kls);
                                                        params.put("no_hp", addnomor);
                                                        params.put("jk", jk);
                                                        params.put("alamat", alamat);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } catch (JSONException e) {
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

                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UbahDataProfil.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_ubahgender, (RelativeLayout) findViewById(R.id.bottomupdateJK)
                        );

                RadioGroup radiongrup = (RadioGroup) bottomSheetView.findViewById(R.id.radiongrup);


//                ImageView btnclosefilter = bottomSheetView.findViewById(R.id.btnclosefilter);
                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btnupdatejk).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        Toast.makeText(UbahDataProfil.this, "Janis kelamin telah diubah", Toast.LENGTH_SHORT).show();
                        radiongrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                RadioButton radio_laki = (RadioButton) bottomSheetView.findViewById(R.id.radio_laki);
                                RadioButton radio_cewe = (RadioButton) bottomSheetView.findViewById(R.id.radio_cewe);

                                String g1 = radio_laki.getText().toString();
                                String g2 = radio_cewe.getText().toString();

                                Button btnupdateJK = (Button) bottomSheetView.findViewById(R.id.btnupdatejk);
                                btnupdateJK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (radio_laki.isChecked()) {
                                            updateJKL(g1);
                                        } else {
                                            updateJKP(g2);
                                        }
                                    }
                                });
                            }

                            private void updateJKP(String g2) {
                                dialog.show();
                                Intent intent = getIntent();
                                String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);

                                dialog.show();
                                List<DataUserModel> dataUserModels = new ArrayList<>();

                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {

                                                for (int i = 0; i <= response.length(); i++) {
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
                                                        dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                        dataUserModels.add(dataUserModel);

                                                        String nama = dataObject.getString("nama").toString();
                                                        String nohp = dataObject.getString("no_hp").toString();
                                                        String jk = dataObject.getString("jk").toString();
//                                                String alamat = dataObject.getString("alamat").toString();
                                                        String kls = dataObject.getString("kelas").toString();
                                                        String uid = dataObject.getString("uid").toString();

                                                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        dialog.dismiss();
                                                                        Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                                    }
                                                                }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                            }
                                                        }) {
                                                            @Override
                                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                                Map<String, String> params = new HashMap<String, String>();
                                                                params.put("uid", uid);
                                                                params.put("nama", nama);
                                                                params.put("kelas", kls);
                                                                params.put("no_hp", nohp);
                                                                params.put("jk", g2);
                                                                params.put("alamat", alamat);
                                                                return params;
                                                            }
                                                        };
                                                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                                    } catch (JSONException e) {
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

                            private void updateJKL(String g1) {
                                dialog.show();
                                Intent intent = getIntent();
                                String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);

                                dialog.show();
                                List<DataUserModel> dataUserModels = new ArrayList<>();

                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {

                                                for (int i = 0; i <= response.length(); i++) {
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
                                                        dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                        dataUserModels.add(dataUserModel);

                                                        String nama = dataObject.getString("nama").toString();
                                                        String nohp = dataObject.getString("no_hp").toString();
                                                        String jk = dataObject.getString("jk").toString();
//                                                String alamat = dataObject.getString("alamat").toString();
                                                        String kls = dataObject.getString("kelas").toString();
                                                        String uid = dataObject.getString("uid").toString();

                                                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        dialog.dismiss();
                                                                        Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                                    }
                                                                }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                            }
                                                        }) {
                                                            @Override
                                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                                Map<String, String> params = new HashMap<String, String>();
                                                                params.put("uid", uid);
                                                                params.put("nama", nama);
                                                                params.put("kelas", kls);
                                                                params.put("no_hp", nohp);
                                                                params.put("jk", g1);
                                                                params.put("alamat", alamat);
                                                                return params;
                                                            }
                                                        };
                                                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                                    } catch (JSONException e) {
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
//                    private void updateJKL(String g1) {
//                        Intent intent = getIntent();
//                        String nama = intent.getStringExtra(ProfilSetting.EXTRA_PS_NAMA);
//                        String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);
//                        String nis = intent.getStringExtra(ProfilSetting.EXTRA_PS_NIS);
//                        String uid = intent.getStringExtra(ProfilSetting.EXTRA_PS_UID);
//                        String pass = intent.getStringExtra(ProfilSetting.EXTRA_PS_PASSWORD);
//                        String hp = intent.getStringExtra(ProfilSetting.EXTRA_PS_HP);
//                        String kls = intent.getStringExtra(ProfilSetting.EXTRA_PS_KLS);
//                        String jk = intent.getStringExtra(ProfilSetting.EXTRA_PS_JK);
//                        String alamat = intent.getStringExtra(ProfilSetting.EXTRA_PS_ALAMAT);
//
//
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        }) {
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String, String> params = new HashMap<String, String>();
//                                params.put("uid", uid);
//                                params.put("nama", nama);
//                                params.put("kelas", kls);
//                                params.put("no_hp", hp);
//                                params.put("jk", g1);
//                                params.put("alamat", alamat);
//                                return params;
//                            }
//                        };
//                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//                    }
                        });
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> spinnerList;
                ArrayList<String> spinnerList2;
                ArrayAdapter<String> adapter, adapter2;
                String[] kelas = {"Pilih Kelas", "X", "XI", "XII"};
                String[] jurusan = {"Pilih Jurusan", "RPL", "TKRO", "TBSM", "TPTL", "TKJ", "OTOTRONIK"};

                spinnerList = new ArrayList<>();
                spinnerList2 = new ArrayList<>();

                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, kelas);
                adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, jurusan);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                adapter2.setDropDownViewResource(R.layout.spinner_item);

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UbahDataProfil.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_ubahkelas, (RelativeLayout) findViewById(R.id.bottomubahkelas)
                        );

                Spinner addkelas = (Spinner) bottomSheetView.findViewById(R.id.addkelas);
                Spinner addjurusan = (Spinner) bottomSheetView.findViewById(R.id.addjurusan);
                addkelas.setAdapter(adapter);
                addjurusan.setAdapter(adapter2);

                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btnubahkls).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String jurusan = addjurusan.getSelectedItem().toString();
                        String kelas = addkelas.getSelectedItem().toString();

                        if (kelas.equals("Pilih Kelas")) {
                            addkelas.requestFocus();
                        } else if (jurusan.equals("Pilih Jurusan")) {
                            addjurusan.requestFocus();
                        } else {
                            updateKelas();
                        }

                    }

                    private void updateKelas() {
                        dialog.show();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);
                        String kelas2 = addkelas.getSelectedItem().toString();
                        String jurusan2 = addjurusan.getSelectedItem().toString();
                        String compliteclass = kelas2 + "-" + jurusan2;
                        List<DataUserModel> dataUserModels = new ArrayList<>();

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
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
                                                dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                dataUserModels.add(dataUserModel);

                                                String nama = dataObject.getString("nama").toString();
                                                String nohp = dataObject.getString("no_hp").toString();
                                                String jk = dataObject.getString("jk").toString();
//                                                String alamat = dataObject.getString("alamat").toString();
                                                String kls = dataObject.getString("kelas").toString();
                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", nama);
                                                        params.put("kelas", compliteclass);
                                                        params.put("no_hp", nohp);
                                                        params.put("jk", jk);
                                                        params.put("alamat", alamat);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } catch (JSONException e) {
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

                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UbahDataProfil.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_alamat, (RelativeLayout) findViewById(R.id.modalalamat)
                        );
                EditText eddalamat = (EditText) bottomSheetView.findViewById(R.id.eddalamat);
//                ImageView btnclosefilter = bottomSheetView.findViewById(R.id.btnclosefilter);
                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.btnupdatealamat).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String alamat = eddalamat.getText().toString();
                        if (TextUtils.isEmpty(alamat)) {
                            eddalamat.requestFocus();
                            eddalamat.setError("Tolong diisi");
                        } else {
                            updateAlamat(alamat);
                        }

                    }

                    private void updateAlamat(String alamat) {
                        dialog.show();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(ProfilSetting.EXTRA_PS_EMAIL);

                        dialog.show();
                        List<DataUserModel> dataUserModels = new ArrayList<>();

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + email, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for (int i = 0; i <= response.length(); i++) {
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
                                                dataUserModel.setImg_user(dataObject.getString("img_user").toString());
                                                dataUserModels.add(dataUserModel);

                                                String nama = dataObject.getString("nama").toString();
                                                String nohp = dataObject.getString("no_hp").toString();
                                                String jk = dataObject.getString("jk").toString();
//                                                String alamat = dataObject.getString("alamat").toString();
                                                String kls = dataObject.getString("kelas").toString();
                                                String uid = dataObject.getString("uid").toString();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(UbahDataProfil.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UbahDataProfil.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("nama", nama);
                                                        params.put("kelas", kls);
                                                        params.put("no_hp", nohp);
                                                        params.put("jk", jk);
                                                        params.put("alamat", alamat);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } catch (JSONException e) {
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
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

    }

}