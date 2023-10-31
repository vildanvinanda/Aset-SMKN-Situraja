package com.example.projectwiki.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
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
import com.example.projectwiki.AllDb;
import com.example.projectwiki.Detail.DetailBarang;
import com.example.projectwiki.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.projectwiki.Adapter.AdapterDaftarBarangAdmin.EXTRA_DB4_BPID;
import static com.example.projectwiki.Adapter.AdapterDaftarBarangAdmin.EXTRA_DB4_DES;
import static com.example.projectwiki.Adapter.AdapterDaftarBarangAdmin.EXTRA_DB4_IMG;
import static com.example.projectwiki.Adapter.AdapterDaftarBarangAdmin.EXTRA_DB4_JML;
import static com.example.projectwiki.Adapter.AdapterDaftarBarangAdmin.EXTRA_DB4_NAMEB;
import static com.example.projectwiki.Adapter.AdapterDaftarBarangAdmin.EXTRA_DB4_TGL;
import static com.example.projectwiki.Adapter.AdapterDaftarBarangAdmin.EXTRA_DB4_TYPE;
import static com.example.projectwiki.Detail.DetailBarang.EXTRA_DTB_3BPID;
import static com.example.projectwiki.Detail.DetailBarang.EXTRA_DTB_3DES;
import static com.example.projectwiki.Detail.DetailBarang.EXTRA_DTB_3IMG;
import static com.example.projectwiki.Detail.DetailBarang.EXTRA_DTB_3JML;
import static com.example.projectwiki.Detail.DetailBarang.EXTRA_DTB_3NAME;
import static com.example.projectwiki.Detail.DetailBarang.EXTRA_DTB_3TYPE;

public class FormUpdateBarang extends AppCompatActivity {

    ImageView btn_back, img_detail;
    TextView tbl_ajukan, txt_uid, txt_type, btn_kirim, connntoj,txt_type2, txt_nama,txt_bpid, txt_nis;
    EditText addnamab, addjml, addketerangan;
    ProgressDialog dialog;
    Bitmap bitmap;
    String encodedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_update_barang);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_kirim = (TextView) findViewById(R.id.btn_kirim);
        connntoj = (TextView) findViewById(R.id.connntoj);
        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_type2 = (TextView) findViewById(R.id.txt_type2);
        img_detail = (ImageView) findViewById(R.id.img_detail);
        addnamab = (EditText) findViewById(R.id.addnamab);
        addjml = (EditText) findViewById(R.id.addjml);
        addketerangan = (EditText) findViewById(R.id.addketerangan);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(true);
        dialog.setTitle("Dalam proses pendaftaran");
        dialog.setCanceledOnTouchOutside(false);

        Intent intent = getIntent();
        String jdl = intent.getStringExtra(EXTRA_DTB_3NAME);
        String bpid = intent.getStringExtra(EXTRA_DTB_3BPID);
        String jml = intent.getStringExtra(EXTRA_DTB_3JML);
        String des = intent.getStringExtra(EXTRA_DTB_3DES);
        String img = intent.getStringExtra(EXTRA_DTB_3IMG);
        String type = intent.getStringExtra(EXTRA_DTB_3TYPE);

        String nama_barang4 = intent.getStringExtra(EXTRA_DB4_NAMEB);
        String jml_barang4 = intent.getStringExtra(EXTRA_DB4_JML);
        String deskripsi4 = intent.getStringExtra(EXTRA_DB4_DES);
        String tgl_upload4 = intent.getStringExtra(EXTRA_DB4_TGL);
        String img_barang4 = intent.getStringExtra(EXTRA_DB4_IMG);
        String bpid4 = intent.getStringExtra(EXTRA_DB4_BPID);
        String type4= intent.getStringExtra(EXTRA_DB4_TYPE);

        txt_type.setText(type);
        txt_type2.setText(type4);

        String input = txt_type.getText().toString();
        String input2 = txt_type2.getText().toString();

        if (input.equals("activityint")){
            Glide.with(img_detail.getContext())
                    .load(img)
                    .centerCrop()
                    .placeholder(R.drawable.gambar)
                    .into(img_detail);

            addnamab.setText(jdl);
            addjml.setText(jml);
            addketerangan.setText(des);
        }

        if(input2.equals("adapterintent")){
            Glide.with(img_detail.getContext())
                    .load(img_barang4)
                    .centerCrop()
                    .placeholder(R.drawable.gambar)
                    .into(img_detail);

            addnamab.setText(nama_barang4);
            addjml.setText(jml_barang4);
            addketerangan.setText(deskripsi4);
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Uri uri =data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                Glide.with(getApplicationContext())
                                        .load(bitmap).centerCrop()
                                        .into(img_detail);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        img_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Dexter.withActivity(FormTambahBarang.this)
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//
//                                Intent intent = new Intent(Intent.ACTION_PICK);
//                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent, "Browse Image"),1);
//
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                                permissionToken.continuePermissionRequest();
//                            }
//                        }).check();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiData();
            }
        });

    }
    private void validasiData() {
        String namaB = addnamab.getText().toString();
        String jmlB = addjml.getText().toString();
        String desB = addketerangan.getText().toString();

        if(TextUtils.isEmpty(namaB)){
            addnamab.setError("Wajib Diisi!!");
            addnamab.requestFocus();
        }else if(TextUtils.isEmpty(jmlB)){
            addjml.setError("Wajib Diisi!!");
            addjml.requestFocus();
        } else if(TextUtils.isEmpty(desB)){
            addketerangan.setError("Wajib Diisi!!");
            addketerangan.requestFocus();
        } else {
            insertData();
        }
    }

    private void insertData() {
        dialog.show();
        String namaB = addnamab.getText().toString();

        String jmlB = addjml.getText().toString();
        String desB = addketerangan.getText().toString();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Intent intent = getIntent();
        String bpid = intent.getStringExtra(EXTRA_DTB_3BPID);
        //img
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();

        if(bitmap != null){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_BARANG_ADMIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            Toast.makeText(FormUpdateBarang.this, "Barang Berhasil Disimpan", Toast.LENGTH_LONG).show();
                            addnamab.setText("");
                            addjml.setText("");
                            addketerangan.setText("");
                            img_detail.setImageResource(0);

                            Intent intent = new Intent(FormUpdateBarang.this, DaftarBarang.class);
                            startActivity(intent);
//                  addjml.setText(test);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                    dialog.dismiss();
                    Toast.makeText(FormUpdateBarang.this, "Barang Gagal Diinput", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("bpid", bpid);
                    params.put("nama_barang", namaB);
                    params.put("jml_barang", jmlB);
                    params.put("deskripsi", desB);
                    params.put("tgl_upload", currentDate);
                    params.put("img_barang", base64Image);
                    return params;
                }

            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        } else Toast.makeText(getApplicationContext(), "Tolong perbarui pohot barang!", Toast.LENGTH_SHORT).show();



    }
}