  package com.example.projectwiki.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.projectwiki.Detail.DetailBarang;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.Dashboard;
import com.example.projectwiki.Detail.DetailBarang;
import com.example.projectwiki.Fragmen.DaftarPinjaman;
import com.example.projectwiki.Fragmen.Home;
import com.example.projectwiki.R;
import com.example.projectwiki.RequestHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//import static com.example.projectwiki.Adapter.AdapterRecItemDashboard.EXTRA_1DES;
//import static com.example.projectwiki.Adapter.AdapterRecItemDashboard.EXTRA_1NAME;
//import static com.example.projectwiki.Adapter.AdapterRecItemDashboard.EXTRA_1TGL;
//import static com.example.projectwiki.Detail.DetailBarang.EXTRA_2NAME;
//import static com.example.projectwiki.Detail.DetailBarang.EXTRA_2DES;
//import static com.example.projectwiki.Detail.DetailBarang.EXTRA_2JML;
//import static com.example.projectwiki.Detail.DetailBarang.EXTRA_2NAME;

  public class FormPengajuan extends AppCompatActivity {

    ImageView btn_back, img_detail;
    TextView jdl_detaiIMG, txt_des, txt_jml, tbl_ajukan, txt_uid,txt_nama,txt_bpid, txt_nis;
    EditText addwaktu, addjml, addketerangan;
      ProgressDialog dialog;
      private int mDate,mMonth,mYear;

//    private String namabarangq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengajuan);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        img_detail = (ImageView) findViewById(R.id.img_detail);
        jdl_detaiIMG = (TextView) findViewById(R.id.jdl_detaiIMG);
        txt_des = (TextView) findViewById(R.id.txt_des);
        tbl_ajukan = (TextView) findViewById(R.id.tbl_ajukan);
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        txt_bpid = (TextView) findViewById(R.id.txt_bpid);
        txt_uid = (TextView) findViewById(R.id.txt_uid);
        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_jml = (TextView) findViewById(R.id.txt_jml);
        addwaktu = (EditText) findViewById(R.id.addwaktu);
        addjml = (EditText) findViewById(R.id.addjml);
        addketerangan = (EditText) findViewById(R.id.addketerangan);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Dalam proses pendaftaran");
        dialog.setCanceledOnTouchOutside(false);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra(DetailBarang.EXTRA_2NAME);
        String total = intent.getStringExtra(DetailBarang.EXTRA_2JML);
        String des = intent.getStringExtra(DetailBarang.EXTRA_2DES);
        String uuid = intent.getStringExtra(DetailBarang.EXTRA_UUID);
        String nuser = intent.getStringExtra(DetailBarang.EXTRA_NUSER);
        String bpid = intent.getStringExtra(DetailBarang.EXTRA_BPID);
        String nis = intent.getStringExtra(DetailBarang.EXTRA_NIS);

        String img = intent.getStringExtra(DetailBarang.EXTRA_DTB_1IMG);
        String img2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_2IMG);
        String img3 = intent.getStringExtra(DetailBarang.EXTRA_DTB_3IMG);


        String name2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_2NAME);
        String total2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_2JML);
        String des2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_2DES);
        String uuid2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_UUID);
        String nuser2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_NUSER);
        String bpid2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_BPID);
        String nis2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_NIS);
        String for_uid = intent.getStringExtra(DetailBarang.EXTRA_DTB_TYPEUID);

//        txt_uid.setText(uuid);
        txt_uid.setText(for_uid);

        String uidnih = txt_uid.getText().toString();

        if(uidnih.equals("uidhome")){
            txt_uid.setText(uuid);
        } else {
            txt_uid.setText(uuid2);
        }
//        tbl_ajukan.setText(uidnih);

        txt_nama.setText(nuser);
        txt_nama.setText(nuser2);

        txt_bpid.setText(bpid);
        txt_bpid.setText(bpid2);

        txt_nis.setText(nis);
        txt_nis.setText(nis2);

        jdl_detaiIMG.setText(name);
        txt_des.setText(des);
        txt_jml.setText(total);

        jdl_detaiIMG.setText(name2);
        txt_des.setText(des2);
        txt_jml.setText(total2);

        String jml = addjml.getText().toString();
        String indek = txt_jml.getText().toString();


        Glide.with(img_detail.getContext())
                .load(img)
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(img_detail);
        Glide.with(img_detail.getContext())
                .load(img2)
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(img_detail);
        Glide.with(img_detail.getContext())
                .load(img3)
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(img_detail);

        tbl_ajukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateData();
            }
        });

    }

      private void ValidateData() {

        String waktu = addwaktu.getText().toString();
        String jml = addjml.getText().toString();
        String ket = addketerangan.getText().toString();
          double a,b,c;
        a = Double.parseDouble(txt_jml.getText().toString());
        b = Double.parseDouble(addjml.getText().toString());
        c = a-b;
//          String test = Double.toString(c);

        if(TextUtils.isEmpty(waktu)){
            addwaktu.setError("Wajib Diisi !!");
            addwaktu.requestFocus();
        } else if (waktu.equals("0")){
            addwaktu.setError("Tidak Boleh 0 !!");
            addwaktu.requestFocus();
        }else if (TextUtils.isEmpty(jml)){
            addjml.setError("Wajib Diisi !!");
            addjml.requestFocus();
        }  else if (c < 0){
            addjml.setError("Stock Barang Kurang !");
            addjml.requestFocus();
        }  else if (TextUtils.isEmpty(ket)){
            addketerangan.setError("Wajib Diisi");
            addketerangan.requestFocus();
        } else{
            InsertData();
//            Toast.makeText(this, "Mantap", Toast.LENGTH_SHORT).show();
        }
      }

      private void InsertData() {
          dialog.show();
          String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
          String waktu = addwaktu.getText().toString();
          String jml = addjml.getText().toString();
          String total = txt_jml.getText().toString();
          String ket = addketerangan.getText().toString();
          String nbarang = jdl_detaiIMG.getText().toString();

          String uidtxt = txt_uid.getText().toString();
          String nametxt = txt_nama.getText().toString();

          String bpidtxt = txt_bpid.getText().toString();
          String nis = txt_nis.getText().toString();

          Intent intent = getIntent();
          String uuid = intent.getStringExtra(DetailBarang.EXTRA_UUID);

          //ini


          String nuser = intent.getStringExtra(DetailBarang.EXTRA_NUSER);
          String bpid = intent.getStringExtra(DetailBarang.EXTRA_BPID);
          String uuid2 = intent.getStringExtra(DetailBarang.EXTRA_DTB_UUID);

          int a,b,c;
          a = Integer.parseInt(txt_jml.getText().toString());
          b = Integer.parseInt(addjml.getText().toString());
          c = a-b;
          String test = Integer.toString(c);

          StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_PEMINJAMAN_URL,
                  new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  dialog.dismiss();
                  Toast.makeText(FormPengajuan.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(FormPengajuan.this, Dashboard.class);
                  startActivity(intent);
                  finish();
//                  addjml.setText(test);
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                  dialog.dismiss();
                  Toast.makeText(FormPengajuan.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
              }
          }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> params = new HashMap<String, String>();
                  params.put("bpid", bpidtxt);
                  params.put("uid", uidtxt);
                  params.put("nama", nametxt);
                  params.put("nama", nbarang);
                  params.put("nis", nis);
                  params.put("nama_barang", nbarang);
                  params.put("tgl_pinjam", currentDate);
                  params.put("req_pinjam", waktu);
                  params.put("keterangan", ket);
                  params.put("total_bpinjam", jml);
//                  params.put("due_date", "112");
                  params.put("status_pengajuan", "menunggu");
                  return params;
              }
          };

          StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_BARANG_URL,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          dialog.dismiss();
//                          addjml.setText(test);
                      }
                  }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                  dialog.dismiss();
              }
          }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> params = new HashMap<String, String>();
                  params.put("bpid", bpid);
                  params.put("jml_barang", test);
                  return params;
              }
          };
          RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
          RequestHandler.getInstance(this).addToRequestQueue(stringRequest2);
      }
  }