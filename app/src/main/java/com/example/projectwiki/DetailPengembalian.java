package com.example.projectwiki;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.projectwiki.Adapter.AdapterRecItemDashboard;
import com.example.projectwiki.Admin.ListPengembalian;
import com.example.projectwiki.Detail.DetailBarang;
import com.example.projectwiki.Form.FormPengembalian;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.Model.ModelPengembalian;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.projectwiki.Admin.DaftarBarang.EXTRA_DB2_BPID;

public class DetailPengembalian extends AppCompatActivity{


    ImageView btn_back,img_detail;
    TextView addkd, addtgl_terima,addstatus, txt_email, addtglp, addname, addnis, addnamab, addjml, addjt, addket, btn_tolak,btn_terima,btn_hapus;
    RelativeLayout tamplatetxt8, sblm_diterima;
    ProgressDialog dialog;

    List<DataUserModel> dataUserModels;

    Context context;

    public static final String EXTRA_NAME = "nama_barang";
    public static final String EXTRA_BARANG = "jml_barang";
    public static final String EXTRA_DES = "deskripsi";
    public static final String EXTRA_TGL = "tgl_upload";
    public static final String EXTRA_IMG = "img_barang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dataUserModels = new ArrayList<>();

        context = this;
        img_detail = (ImageView) findViewById(R.id.img_detail);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        addkd = (TextView) findViewById(R.id.addkd);
        addtglp = (TextView) findViewById(R.id.addtglp);
        addname = (TextView) findViewById(R.id.addname);
        addnamab = (TextView) findViewById(R.id.addnamab);
        addjml = (TextView) findViewById(R.id.addjml);
        addjt = (TextView) findViewById(R.id.addjt);
        addnis = (TextView) findViewById(R.id.addnis);
        addket = (TextView) findViewById(R.id.addket);
        txt_email = (TextView) findViewById(R.id.txt_email);
        btn_hapus = (TextView) findViewById(R.id.btn_hapus);
        btn_terima = (TextView) findViewById(R.id.btn_terima);
        btn_tolak = (TextView) findViewById(R.id.btn_tolak);
        btn_tolak = (TextView) findViewById(R.id.btn_tolak);
        addtgl_terima = (TextView) findViewById(R.id.addtgl_terima);
        addstatus = (TextView) findViewById(R.id.addstatus);
        tamplatetxt8 = (RelativeLayout) findViewById(R.id.tamplatetxt8);
        sblm_diterima = (RelativeLayout) findViewById(R.id.sblm_diterima);


        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Data sedang di upload");
        dialog.setCanceledOnTouchOutside(false);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String pid = intent.getStringExtra(ListPengembalian.EXTRA_LP_PID);
        String uid = intent.getStringExtra(ListPengembalian.EXTRA_LP_UID);
        String nama = intent.getStringExtra(ListPengembalian.EXTRA_LP_NAME);
        String namaB = intent.getStringExtra(ListPengembalian.EXTRA_LP_NAMEB);
        String tgl = intent.getStringExtra(ListPengembalian.EXTRA_LP_TGL);
        String due = intent.getStringExtra(ListPengembalian.EXTRA_LP_DUE);
        String ket = intent.getStringExtra(ListPengembalian.EXTRA_LP_KET);
        String img = intent.getStringExtra(ListPengembalian.EXTRA_LP_IMG);
        String total = intent.getStringExtra(ListPengembalian.EXTRA_LP_TOTAL);
        String status = intent.getStringExtra(ListPengembalian.EXTRA_LP_STATUS);
        String nis = intent.getStringExtra(ListPengembalian.EXTRA_LP_NIS);
        String tgl_terima = intent.getStringExtra(ListPengembalian.EXTRA_LP_TGL_TERIMA);

        addkd.setText(pid);
        addtglp.setText(tgl);
        addname.setText(nama);
        addnamab.setText(namaB);
        addjml.setText(total);
        addjt.setText(due);
        addnis.setText(nis);
        addket.setText(ket);
        addstatus.setText(status);
        addtgl_terima.setText(tgl_terima);

        Glide.with(img_detail.getContext())
                .load(img)
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(img_detail);

        String cekStatus = addstatus.getText().toString();
        if(cekStatus.equals("Menunggu")){
            addstatus.setTextColor(context.getResources().getColor(R.color.KuningTua));
            addstatus.setBackground(context.getResources().getDrawable(R.color.KuningMuda));
            btn_hapus.setVisibility(View.GONE);
            sblm_diterima.setVisibility(View.VISIBLE);
            tamplatetxt8.setVisibility(View.GONE);
        } else {
            addstatus.setTextColor(context.getResources().getColor(R.color.Hijautua));
            addstatus.setBackground(context.getResources().getDrawable(R.color.Hijaumuda));
            btn_hapus.setVisibility(View.VISIBLE);
            sblm_diterima.setVisibility(View.GONE);
            tamplatetxt8.setVisibility(View.VISIBLE);
        }



        btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPengembalian.this);
                builder.setTitle("Hapus Data");
                builder.setMessage("Anda yakin untuk menghapus data ini?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tolakPengembalian();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPengembalian.this);
                builder.setTitle("Hapus Data");
                builder.setMessage("Anda yakin untuk menghapus data ini?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusData();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPengembalian.this);
                builder.setTitle("Barang Diterima?");
                builder.setMessage("Anda yakin untuk menerima barang ini?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateData();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void getData4(String uid) {
        Intent intent = getIntent();
        String bpid = intent.getStringExtra(ListPengembalian.EXTRA_LP_BPID);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_USER_UID_URL+uid, null,
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

                                txt_email.setText(dataObject.getString("email").toString());


                                String email = txt_email.getText().toString();

                                insertNotif(email, bpid);

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

    private void insertNotif(String email,String bpid) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_NOTIF_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(DetailPengembalian.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
//                  addjml.setText(test);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
                Toast.makeText(DetailPengembalian.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("bpid", bpid);
                params.put("tgl_kirim", currentDate);
                params.put("judul", "Pengembalian Anda Ditolak");
                params.put("keterangan", "Mohon perhatiannya, pengembalian anda ditolak oleh admin, tolong untuk periksa lagi dan coba untuk mengembalikannya lain waktu dan tolong untuk segera hubungi admin \n \n Sekian dan terimakasih \n \n Dikirim Oleh : \n Admin");
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void updateData() {
        dialog.show();
        Intent intent = getIntent();
        String uid = intent.getStringExtra(ListPengembalian.EXTRA_LP_UID);
        String idpb = intent.getStringExtra(ListPengembalian.EXTRA_LP_IDPB);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_PENGEMBALIAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Intent intent1 = new Intent(DetailPengembalian.this, ListPengembalian.class);
                        startActivity(intent1);
                        finish();
                        Toast.makeText(DetailPengembalian.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
//                  addjml.setText(test);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
                Toast.makeText(DetailPengembalian.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idpb", idpb);
                params.put("status", "Diterima");
                params.put("tgl_penerimaan", currentDate);

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

        getData5(uid);
    }

    private void hapusData() {
        dialog.show();
        Intent intent = getIntent();
        String idpb = intent.getStringExtra(ListPengembalian.EXTRA_LP_IDPB);
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_DELETE_DATA_PENGEMBALIAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(DetailPengembalian.this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(DetailPengembalian.this, "Data Gagal Dihapus", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idpb", idpb);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest2);
    }

    private void tolakPengembalian() {
        dialog.show();
        Intent intent = getIntent();
        String idpb = intent.getStringExtra(ListPengembalian.EXTRA_LP_IDPB);
        String bpid = intent.getStringExtra(ListPengembalian.EXTRA_LP_BPID);
        String uid = intent.getStringExtra(ListPengembalian.EXTRA_LP_UID);
        String pid = intent.getStringExtra(ListPengembalian.EXTRA_LP_PID);
        String nama = intent.getStringExtra(ListPengembalian.EXTRA_LP_NAME);
        String namaB = intent.getStringExtra(ListPengembalian.EXTRA_LP_NAMEB);
        String tgl = intent.getStringExtra(ListPengembalian.EXTRA_LP_TGL);
        String req = intent.getStringExtra(ListPengembalian.EXTRA_LP_REQ);
        String due = intent.getStringExtra(ListPengembalian.EXTRA_LP_DUE);
        String ket = intent.getStringExtra(ListPengembalian.EXTRA_LP_KET);
        String img = intent.getStringExtra(ListPengembalian.EXTRA_LP_IMG);
        String total = intent.getStringExtra(ListPengembalian.EXTRA_LP_TOTAL);
        String status = intent.getStringExtra(ListPengembalian.EXTRA_LP_STATUS);
        String nis = intent.getStringExtra(ListPengembalian.EXTRA_LP_NIS);
        String tgl_terima = intent.getStringExtra(ListPengembalian.EXTRA_LP_TGL_TERIMA);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_FORM_PENGAJUAN_ADMIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(DetailPengembalian.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
//                  addjml.setText(test);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
                Toast.makeText(DetailPengembalian.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pid", pid);
                params.put("bpid", bpid);
                params.put("uid", uid);
                params.put("nama", nama);
                params.put("nis", nis);
                params.put("nama_barang", namaB);
                params.put("tgl_pinjam", tgl);
                params.put("req_pinjam", req);
                params.put("keterangan", ket);
                params.put("total_bpinjam", total);
                params.put("due_date", due);
                params.put("status", "peringatan");
                params.put("peminjaman", "belum kembali");

                return params;
            }
        };
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_DELETE_DATA_PENGEMBALIAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idpb", idpb);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest2);

        getData4(uid);

    }

    private void getData5(String uid) {
        Intent intent = getIntent();
        String bpid = intent.getStringExtra(ListPengembalian.EXTRA_LP_BPID);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_USER_UID_URL+uid, null,
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

                                txt_email.setText(dataObject.getString("email").toString());


                                String email = txt_email.getText().toString();

                                insertNotif2(email, bpid);

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

    private void insertNotif2(String email,String bpid) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_NOTIF_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(DetailPengembalian.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
//                  addjml.setText(test);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                dialog.dismiss();
                Toast.makeText(DetailPengembalian.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("bpid", bpid);
                params.put("tgl_kirim", currentDate);
                params.put("judul", "Pengembalian Anda Ditolak");
                params.put("keterangan", "Mohon perhatiannya, \n pengembalian anda sudah diterima terimakasih atas kerjasamanya \n \n Sekian dan terimakasih \n \n Dikirim Oleh : \n Admin");
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

}


//INI UNTUK LIST ALL USER

//package com.example.projectwiki;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.projectwiki.Adapter.AdapterAllUser;
//import com.example.projectwiki.Adapter.AdapterRecItemDashboard;
//import com.example.projectwiki.Model.ModelBarang;
//import com.example.projectwiki.Model.ModelUser;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;

//public class MainActivity2 extends AppCompatActivity implements AdapterAllUser.OnItemClickListener  {
//
//    RecyclerView recrec;
//    ArrayList<ModelUser> modelUsers;
//    private AdapterAllUser adapterAllUser;
//
//    public static final String EXTRA_NAME = "nama";
//    public static final String EXTRA_NIS = "nis";
//    public static final String EXTRA_EMAIL = "email";
//    public static final String EXTRA_KELAS = "kelas";
//    public static final String EXTRA_NO_HP = "no_hp";
//    public static final String EXTRA_JK = "jk";
//    public static final String EXTRA_ALAMAT = "alamat";
//    public static final String EXTRA_TYPE_USER = "type_user";
//
//    Context context;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//        recrec = (RecyclerView) findViewById(R.id.recrec);
//
//        modelUsers = new ArrayList<>();
//
//        recrec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
//
//        getData();
//    }
//
//    private void getData() {
//
//        String user = "baim@gnail.com";
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_AllUSER_URL, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                for (int i = 0; i <= response.length();i++){
//                    try {
////
//                        JSONObject dataObject = response.getJSONObject(i);
//                        ModelUser modelUser = new ModelUser();
//                        modelUser.setNama(dataObject.getString("nama").toString());
//                        modelUser.setNis(dataObject.getString("nis").toString());
//                        modelUser.setEmail(dataObject.getString("email").toString());
//                        modelUser.setKelas(dataObject.getString("kelas").toString());
//                        modelUser.setNo_hp(dataObject.getString("no_hp").toString());
//                        modelUser.setJk(dataObject.getString("jk").toString());
//                        modelUser.setAlamat(dataObject.getString("alamat").toString());
//                        modelUser.setType_user(dataObject.getString("type_user").toString());
//                        modelUsers.add(modelUser);
//
//
//
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    }
//
//                }
//                adapterAllUser = new AdapterAllUser(getApplicationContext(),modelUsers);
//                recrec.setAdapter(adapterAllUser);
//                adapterAllUser.setOnItemClickListener(MainActivity2.this);
//                adapterAllUser.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        requestQueue.add(jsonArrayRequest);
//    }
//
//
//    @Override
//    public void onItemClick(int position) {
//        Intent intent = new Intent(this, MainActivity3.class);
//        ModelUser clickitm = modelUsers.get(position);
//        intent.putExtra(EXTRA_NAME, clickitm.getNama());
//        intent.putExtra(EXTRA_NIS, clickitm.getNis());
//        intent.putExtra(EXTRA_EMAIL, clickitm.getEmail());
//        intent.putExtra(EXTRA_KELAS, clickitm.getKelas());
//        intent.putExtra(EXTRA_NO_HP, clickitm.getNo_hp());
//        intent.putExtra(EXTRA_JK, clickitm.getJk());
//        intent.putExtra(EXTRA_ALAMAT, clickitm.getAlamat());
//        intent.putExtra(EXTRA_TYPE_USER, clickitm.getType_user());
//        startActivity(intent);
//    }
//}