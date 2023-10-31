package com.example.projectwiki.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.projectwiki.Dashboard;
import com.example.projectwiki.Form.FormPengajuan;
import com.example.projectwiki.R;
import com.example.projectwiki.RequestHandler;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FormTambahBarang extends AppCompatActivity {

    ImageView btn_back, img_detail;
    TextView tbl_ajukan, txt_uid, btn_kirim, txt_nama,txt_bpid, txt_nis;
    EditText addnamab, addjml, addketerangan;
    ProgressDialog dialog;
    Bitmap bitmap;
    String encodedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tambah_barang);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_kirim = (TextView) findViewById(R.id.btn_kirim);
        img_detail = (ImageView) findViewById(R.id.img_detail);
        addnamab = (EditText) findViewById(R.id.addnamab);
        addjml = (EditText) findViewById(R.id.addjml);
        addketerangan = (EditText) findViewById(R.id.addketerangan);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Dalam proses perubahan");
        dialog.setCanceledOnTouchOutside(false);

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1&&resultCode==RESULT_OK){
//            Uri ImageUris = data.getData();
//            try {
//
//                InputStream inputStream = getContentResolver().openInputStream(ImageUris);
//                bitmap = BitmapFactory.decodeStream(inputStream);
////                img_detail.setImageBitmap(bitmap);
//                Glide.with(getApplicationContext())
//                        .load(bitmap)
//                        .centerCrop()
//                        .into(img_detail);
//                encodeBitmapImage(bitmap);
//
//            }catch (Exception ex)
//            {
//
//            }
//        }
//
//    }

//    private void encodeBitmapImage(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//
//        byte[] imageBytes = stream.toByteArray();
//
//        encodedImages = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//
//    }

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
            insertData(namaB, jmlB,desB);
        }
    }

    private void insertData(String namaB, String jmlB, String desB) {
        dialog.show();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //img
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_INSERT_DATA_BARANG_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            Toast.makeText(FormTambahBarang.this, "Barang Berhasil Disimpan", Toast.LENGTH_LONG).show();
                            addnamab.setText("");
                            addjml.setText("");
                            addketerangan.setText("");

//                  addjml.setText(test);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                    dialog.dismiss();
                    Toast.makeText(FormTambahBarang.this, "Barang Gagal Diinput", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nama_barang", namaB);
                    params.put("jml_barang", jmlB);
                    params.put("deskripsi", desB);
                    params.put("tgl_upload", currentDate);
                    params.put("img_barang", base64Image);
                    return params;
                }

            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(stringRequest);

    }


}