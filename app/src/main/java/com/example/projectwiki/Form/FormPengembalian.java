package com.example.projectwiki.Form;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.projectwiki.Admin.DetailPinjamanUser;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.Dashboard;
import com.example.projectwiki.Detail.DetailBarangPinjaman;
import com.example.projectwiki.DetailPengembalian;
import com.example.projectwiki.R;
import com.example.projectwiki.RequestHandler;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class FormPengembalian extends AppCompatActivity {


    ImageView btn_back,img_detail;
    TextView addkd, addtglp, addname, addnis, addnamab, addjml, addjt, addket, button_pengembalian;

    ProgressDialog dialog;

    String encodeImg;

    //<<<< ini untuk Gambar
    Context context;
    Uri capturedImageURL;
    private String file=null;

    private Uri mImageUri = null;

    private static final  int GALLERY_REQUEST =1;
    private static final int CAMERA_REQUEST_CODE=1;
    Uri uricam;

    int PERMISSION_DATA = 20;
    int ACCESS_DATA = 40;

    Bitmap mImageUri1;
    Bitmap bitmap;
    //sampe sini >>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengembalian);
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
        button_pengembalian = (TextView) findViewById(R.id.button_pengembalian);


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
        String bpid = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_BPID);
        String uid = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_UID);
        String pid = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_PID);
        String nama = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_NAME);
        String namaB = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_NAMEB);
        String tgl = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_TGL);
        String req = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_REQ);
        String due = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_DUE);
        String ket = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_KET);
        String total = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_TOTAL);
        String status = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_STATUS);
        String nis = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_NIS);

        addkd.setText(pid);
        addtglp.setText(tgl);
        addname.setText(nama);
        addnamab.setText(namaB);
        addjml.setText(total);
        addjt.setText(due);
        addnis.setText(nis);
        addket.setText(ket);

        img_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UploadImage();
                Dexter.withActivity(FormPengembalian.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

//                                Intent intent = new Intent(Intent.ACTION_PICK);
//                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent, "Browse Image"),1);

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                                }

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(FormPengembalian.this, "Izin Ditolak", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

                //bedaaa
//                boolean pick = true;
//                if(pick==true){
//                    if(!checkCameraPermission()){
//                        requestCameraPermission();
//                    } else PickImage();
//                } else {
//                    if (!checkStoragePermisiion()){
//                        requestStoragePermission();
//                    } else PickImage();
//                }

                //baruu

//


                //coba

            }
        });

        button_pengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
//                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Intent intent = getIntent();
                String bpid = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_BPID);
                String uid = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_UID);
                String pid = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_PID);
                String nama = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_NAME);
                String namaB = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_NAMEB);
                String tgl = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_TGL);
                String req = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_REQ);
                String due = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_DUE);
                String ket = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_KET);
                String total = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_TOTAL);
                String status = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_STATUS);
                String nis = intent.getStringExtra(DetailBarangPinjaman.EXTRA_DBP_NIS);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_FORM_PENGEMBALIAN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

//                  addjml.setText(test);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
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
                        params.put("jml_barang", total);
                        params.put("status", "Menunggu");
                        params.put("img_pengembalian", encodeImg);
                        params.put("due_date", due);
                        return params;
                    }
                };
                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_BPINJAM_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                Toast.makeText(FormPengembalian.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(FormPengembalian.this, Dashboard.class);
                                startActivity(intent1);
                                finish();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(FormPengembalian.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("pid", pid);
                        params.put("status", "peringatan");
                        return params;
                    }
                };
                RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
                RequestHandler.getInstance(context).addToRequestQueue(stringRequest2);
            }
        });

    }

//    private void PickImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(Intent.createChooser(intent, "Browse Image"),100);
//    }
//
//    private void requestStoragePermission() {
//        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
//    }
//
//    private void requestCameraPermission() {
//        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
//    }
//
//    private boolean checkStoragePermisiion() {
//        boolean res2= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
//
//        return res2;
//    }
//
//    private boolean checkCameraPermission() {
//        boolean res1= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
//        boolean res2= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
//
//        return res1 && res2;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_DATA && requestCode == Activity.RESULT_OK) {
            ActivityCompat.requestPermissions((Activity)context, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, PERMISSION_DATA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            mImageUri = data.getData();
//            mSelectImage.setImageURI(mImageUri);
//            CropImage.activity(mImageUri)
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1,1)
//                    .start(this);
            mImageUri1 = (Bitmap) data.getExtras().get("data");
//            imgvontoh.setImageBitmap(mImageUri1);
//            img_detail.setImageBitmap(mImageUri1);
            Glide.with(context).load(mImageUri1).centerCrop().into(img_detail);
            encodeBitmapImage(mImageUri1);


        }

    }

    private void encodeBitmapImage(Bitmap mImageUri1) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mImageUri1.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imageBytes = stream.toByteArray();

        encodeImg = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);


    }


}