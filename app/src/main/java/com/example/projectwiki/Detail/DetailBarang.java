package com.example.projectwiki.Detail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projectwiki.Admin.DaftarBarang;
import com.example.projectwiki.Admin.FormUpdateBarang;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.Form.FormPengajuan;
import com.example.projectwiki.Model.ModelBarang;
import com.example.projectwiki.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.projectwiki.Admin.DaftarBarang.EXTRA_DB2_BPID;
import static com.example.projectwiki.Admin.DaftarBarang.EXTRA_DB2_DES;
import static com.example.projectwiki.Admin.DaftarBarang.EXTRA_DB2_IMG;
import static com.example.projectwiki.Admin.DaftarBarang.EXTRA_DB2_JML;
import static com.example.projectwiki.Admin.DaftarBarang.EXTRA_DB2_NAMEB;
import static com.example.projectwiki.Admin.DaftarBarang.EXTRA_DB2_TGL;
import static com.example.projectwiki.Admin.DaftarBarang.EXTRA_DB2_TYPE;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_BPID;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_DES;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_IMG;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_JML;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_NAME;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_NAMEB;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_NIS;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_TGL;
import static com.example.projectwiki.Fragmen.DaftarBarang.EXTRA_DB_UID;
import static com.example.projectwiki.Fragmen.Home.EXTRA_1BARANG;
import static com.example.projectwiki.Fragmen.Home.EXTRA_1DES;
import static com.example.projectwiki.Fragmen.Home.EXTRA_1IMG;
import static com.example.projectwiki.Fragmen.Home.EXTRA_1NAME;
import static com.example.projectwiki.Fragmen.Home.EXTRA_1BPID;
import static com.example.projectwiki.Fragmen.Home.EXTRA_1TGL;
import static com.example.projectwiki.Fragmen.Home.EXTRA_NAME_USER;
import static com.example.projectwiki.Fragmen.Home.EXTRA_NIS_USER;
import static com.example.projectwiki.Fragmen.Home.EXTRA_UID_USER;

//import static com.example.projectwiki.Adapter.AdapterRecItemDashboard.EXTRA_1BARANG;
//import static com.example.projectwiki.Adapter.AdapterRecItemDashboard.EXTRA_1DES;
//import static com.example.projectwiki.Adapter.AdapterRecItemDashboard.EXTRA_1IMG;
//import static com.example.projectwiki.Adapter.AdapterRecItemDashboard.EXTRA_1NAME;
//import static com.example.projectwiki.Adapter.AdapterRecItemDashboard.EXTRA_1TGL;

public class DetailBarang extends AppCompatActivity {

    private String namabarangq;
    ImageView btn_back, img_detail;
    TextView jdl_detaiIMG,btn_hapus, txt_jml,txt_type, txt_bpid, btn_updateB, txt_tgl, text_des, button_pinjam, tex, connntoj;

    ArrayList<ModelBarang> modelBarangs;

    public static final String EXTRA_2NAME = "namabarang";
    public static final String EXTRA_2JML = "jmlbarang";
    public static final String EXTRA_2TOTAL = "jmlbarang";
    public static final String EXTRA_2DES = "desbarang";
    public static final String EXTRA_UUID = "uuiduser";
    public static final String EXTRA_NUSER = "nuser";
    public static final String EXTRA_BPID = "bpidd";
    public static final String EXTRA_NIS = "nis";
    public static final String EXTRA_DTB_TYPEUID = "uuidhome";

    public static final String EXTRA_DTB_2NAME = "namabarang";
    public static final String EXTRA_DTB_2JML = "jmlbarang";
    public static final String EXTRA_DTB_2TOTAL = "jmlbarang";
    public static final String EXTRA_DTB_2DES = "desbarang";
    public static final String EXTRA_DTB_UUID = "uuid";
    public static final String EXTRA_DTB_NUSER = "nuser";
    public static final String EXTRA_DTB_BPID = "bpidd";
    public static final String EXTRA_DTB_NIS = "nis";

    public static final String EXTRA_DTB_3NAME = "namabarang";
    public static final String EXTRA_DTB_3JML = "jmlbarang";
    public static final String EXTRA_DTB_3IMG = "img";
    public static final String EXTRA_DTB_3DES = "desbarang";
    public static final String EXTRA_DTB_3BPID = "bpid";
    public static final String EXTRA_DTB_3TYPE = "type";

    public static final String EXTRA_DTB_1IMG = "img1";
    public static final String EXTRA_DTB_2IMG = "img2";
    public static final String EXTRA_DTB_3IMGE = "img3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        modelBarangs = new ArrayList<>();

        btn_back = (ImageView) findViewById(R.id.btn_back);
        img_detail = (ImageView) findViewById(R.id.img_detail);
        jdl_detaiIMG = (TextView) findViewById(R.id.jdl_detaiIMG);
        btn_updateB = (TextView) findViewById(R.id.btn_updateB);
        txt_bpid = (TextView) findViewById(R.id.txt_bpid);
        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_jml = (TextView) findViewById(R.id.txt_jml);
        btn_hapus = (TextView) findViewById(R.id.btn_hapus);
        tex = (TextView) findViewById(R.id.tex);
        connntoj = (TextView) findViewById(R.id.connntoj);
        txt_tgl = (TextView) findViewById(R.id.txt_tgl);
        text_des = (TextView) findViewById(R.id.text_des);
        button_pinjam = (TextView) findViewById(R.id.button_pinjam);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String nama_barang = intent.getStringExtra(EXTRA_1NAME);
        String jml_barang = intent.getStringExtra(EXTRA_1BARANG);
        String deskripsi = intent.getStringExtra(EXTRA_1DES);
        String tgl_upload = intent.getStringExtra(EXTRA_1TGL);
        String img_barang = intent.getStringExtra(EXTRA_1IMG);
        String bpid = intent.getStringExtra(EXTRA_1BPID);
        String uname = intent.getStringExtra(EXTRA_NAME_USER);
        String uuid = intent.getStringExtra(EXTRA_UID_USER);

        Intent intent2 = getIntent();
        String nama_barang2 = intent2.getStringExtra(EXTRA_DB_NAMEB);
        String jml_barang2 = intent2.getStringExtra(EXTRA_DB_JML);
        String deskripsi2 = intent2.getStringExtra(EXTRA_DB_DES);
        String tgl_upload2 = intent2.getStringExtra(EXTRA_DB_TGL);
        String img_barang2 = intent2.getStringExtra(EXTRA_DB_IMG);
        String bpid2 = intent2.getStringExtra(EXTRA_DB_BPID);
        String uname2 = intent2.getStringExtra(EXTRA_DB_NAME);
        String uuid2 = intent2.getStringExtra(EXTRA_DB_UID);

        Intent intent3 = getIntent();
        String nama_barang3 = intent3.getStringExtra(EXTRA_DB2_NAMEB);
        String jml_barang3 = intent3.getStringExtra(EXTRA_DB2_JML);
        String deskripsi3 = intent3.getStringExtra(EXTRA_DB2_DES);
        String tgl_upload3 = intent3.getStringExtra(EXTRA_DB2_TGL);
        String img_barang3 = intent3.getStringExtra(EXTRA_DB2_IMG);
        String bpid3 = intent3.getStringExtra(EXTRA_DB2_BPID);
        String type3 = intent3.getStringExtra(EXTRA_DB2_TYPE);



//        connntoj.setText(bpid);


        namabarangq = getIntent().getExtras().get("nama_barang").toString();

        Glide.with(img_detail.getContext())
                .load(img_barang)
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(img_detail);
        Glide.with(img_detail.getContext())
                .load(img_barang2)
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(img_detail);
        Glide.with(img_detail.getContext())
                .load(img_barang3)
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(img_detail);


        jdl_detaiIMG.setText(namabarangq);
        txt_jml.setText(jml_barang);
        text_des.setText(deskripsi);
        txt_tgl.setText(tgl_upload);

        jdl_detaiIMG.setText(namabarangq);
        txt_jml.setText(jml_barang3);
        text_des.setText(deskripsi2);
        txt_tgl.setText(tgl_upload3);

        jdl_detaiIMG.setText(namabarangq);
        txt_jml.setText(jml_barang2);
        text_des.setText(deskripsi3);
        txt_tgl.setText(tgl_upload2);

        txt_type.setText(type3);

        txt_bpid.setText(bpid3);

//        connntoj.setText(uname);


        String cekType = txt_type.getText().toString();

        if(cekType.equals("admin")){
            button_pinjam.setVisibility(View.GONE);
            btn_updateB.setVisibility(View.VISIBLE);
            btn_hapus.setVisibility(View.VISIBLE);
        } else{
            button_pinjam.setVisibility(View.VISIBLE);
            btn_updateB.setVisibility(View.GONE);
            btn_hapus.setVisibility(View.GONE);
        }

        button_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                W = ;
//                String E = txt_jml.getText().toString();
//                String R = text_des.getText().toString();
////
                insertData();
            }
        });
        btn_updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = getIntent();
                String img_barang3 = intent3.getStringExtra(EXTRA_DB2_IMG);
                String bpid3 = intent3.getStringExtra(EXTRA_DB2_BPID);

                String des = text_des.getText().toString();
                String nama = jdl_detaiIMG.getText().toString();
                String jml = txt_jml.getText().toString();

                Intent intent1 = new Intent(DetailBarang.this, FormUpdateBarang.class);
                intent1.putExtra(EXTRA_DTB_3NAME,nama);
                intent1.putExtra(EXTRA_DTB_3JML,jml);
                intent1.putExtra(EXTRA_DTB_3DES,des);
                intent1.putExtra(EXTRA_DTB_3IMG, img_barang3);
                intent1.putExtra(EXTRA_DTB_3BPID, bpid3);
                intent1.putExtra(EXTRA_DTB_3TYPE, "activityint");
                startActivity(intent1);
            }
        });
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailBarang.this);
                builder.setTitle("Hapus Data");
                builder.setMessage("Anda yakin untuk menghapus data ini?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent3 = getIntent();
                        String bpid = intent3.getStringExtra(EXTRA_DB2_BPID);
                        hapusData(bpid);
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

    private void hapusData(String bpid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_DELETE_DATA_BARANG_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DetailBarang.this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DetailBarang.this, DaftarBarang.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                Toast.makeText(DetailBarang.this, "gagal", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("bpid", bpid);
//                            params.put("namapemilik", namapemilik);
//                            params.put("jk", jk);
//                            params.put("statushewan", statushewan);
//                            params.put("kategori", kategori);
//                            params.put("kj", kj);
//                            params.put("tanggallahir", tanggallahir);
//                            params.put("tanggalbeli", tanggalbeli);
//                            params.put("umur", umur);
//                            params.put("hargabeli", hargabeli);
//                            params.put("belidari", belidari);
//                            params.put("peristiwa", peristiwa);
//                            params.put("nomor", nomor);
//                            params.put("imaghewan", encodedImages);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(DetailBarang.this);
        queue.add(stringRequest);

    }

    private void insertData() {

        Intent in = getIntent();
        String uname = in.getStringExtra(EXTRA_NAME_USER);
        String uuid = in.getStringExtra(EXTRA_UID_USER);

        String bpid = in.getStringExtra(EXTRA_1BPID);
        String img_barang = in.getStringExtra(EXTRA_1IMG);
        String img_barang3 = in.getStringExtra(EXTRA_DB2_IMG);
        String img_barang2 = in.getStringExtra(EXTRA_DB_IMG);

        String bpid2 = in.getStringExtra(EXTRA_DB_BPID);
        String uname2 = in.getStringExtra(EXTRA_DB_NAME);
        String uuid2 = in.getStringExtra(EXTRA_DB_UID);

        String nis = in.getStringExtra(EXTRA_NIS_USER);
        String nis2 = in.getStringExtra(EXTRA_DB_NIS);


        String des = text_des.getText().toString();
        String nama = jdl_detaiIMG.getText().toString();
        String jml = txt_jml.getText().toString();


        Intent intent = new Intent(DetailBarang.this, FormPengajuan.class);
        intent.putExtra(EXTRA_2NAME, nama);
        intent.putExtra(EXTRA_2JML, jml);
        intent.putExtra(EXTRA_2DES, des);
        intent.putExtra(EXTRA_UUID, uuid);
        intent.putExtra(EXTRA_NUSER, uname);
        intent.putExtra(EXTRA_BPID, bpid);
        intent.putExtra(EXTRA_NIS, nis);

        intent.putExtra(EXTRA_DTB_2NAME, nama);
        intent.putExtra(EXTRA_DTB_2JML, jml);
        intent.putExtra(EXTRA_DTB_2DES, des);
        intent.putExtra(EXTRA_DTB_UUID, uuid2);
        intent.putExtra(EXTRA_DTB_NUSER, uname2);
        intent.putExtra(EXTRA_DTB_BPID, bpid2);
        intent.putExtra(EXTRA_DTB_NIS, nis2);

        intent.putExtra(EXTRA_DTB_1IMG, img_barang);
        intent.putExtra(EXTRA_DTB_2IMG, img_barang2);
        intent.putExtra(EXTRA_DTB_3IMG, img_barang3);

        intent.putExtra(EXTRA_DTB_TYPEUID, "uidhome");
        startActivity(intent);
    }

}