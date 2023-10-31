package com.example.projectwiki.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projectwiki.AllDb;
import com.example.projectwiki.DataUserModel;
import com.example.projectwiki.Fragmen.Profil;
import com.example.projectwiki.ProfilSetting;
import com.example.projectwiki.R;
import com.example.projectwiki.RequestHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilAdmin extends AppCompatActivity {

    Bitmap bitmap;
    String encodedImages;
    List<DataUserModel> dataModels;
    TextView txt_user, txt_nis, txt_email, txt_kls,txt_type, txt_nomor, txt_jk, txt_alamat, txtuid;
    ImageView ppuser,settingbtn,backbtn;
    FloatingActionButton float_editpp;
    ProgressDialog dialog;
    Uri capturedImgURL;
    int PERMISSION_DATA = 20;
    int ACCESS_DATA = 40;

    private final int PICK_IMAGE_REQUEST = 71;
    private String selectedPicture = "";

    public static final String EXTRA_PA_NAMA = "nama";
    public static final String EXTRA_PA_EMAIL = "email";
    public static final String EXTRA_PA_NIS = "nis";
    public static final String EXTRA_PA_UID = "uid";
    public static final String EXTRA_PA_PASSWORD = "password";
    public static final String EXTRA_PA_HP = "no_hp";
    public static final String EXTRA_PA_ALAMAT = "alamat";
    public static final String EXTRA_PA_JK = "jk";
    public static final String EXTRA_PA_TYPE = "txt_type";

    String EXTRA_PA_IMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_admin);
        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txtuid = (TextView) findViewById(R.id.txtuid);
        txt_kls = (TextView) findViewById(R.id.txt_kls);
        txt_nomor = (TextView) findViewById(R.id.txt_nomor);
        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_jk = (TextView) findViewById(R.id.txt_jk);
        txt_alamat = (TextView) findViewById(R.id.txt_alamat);
        float_editpp = (FloatingActionButton) findViewById(R.id.float_editpp);
        ppuser = (ImageView) findViewById(R.id.ppuser);
        backbtn = (ImageView) findViewById(R.id.backbtn);
        settingbtn = (ImageView) findViewById(R.id.settingbtn);

        dialog = new ProgressDialog(getApplicationContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Data sedang di upload");
        dialog.setCanceledOnTouchOutside(false);

        dataModels = new ArrayList<>();

       Intent intent = getIntent();
       String cek = intent.getStringExtra(HomeAdmin.EXTRA_HA_EMAIL);
        getData4(cek);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilAdmin.this, HomeAdmin.class);
                startActivity(intent);
            }
        });

        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDataUser();
            }
        });


//        ActivityResultLauncher<Intent> activityResultLauncher =
//                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if(result.getResultCode() == RESULT_OK){
//                            Intent data = result.getData();
//                            Uri uri =data.getData();
//                            try {
//                                bitmap = MediaStore.Images.Media.getBitmap();
//
//                                sendImage(bitmap);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });


        float_editpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                activityResultLauncher.launch(intent);

//                Dexter.withActivity(ProfilAdmin.this)
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

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

    }


    @Override
    public void onStart () {
        super.onStart();
//        Intent intent = getIntent();
//        String cek = intent.getStringExtra(HomeAdmin.EXTRA_HA_EMAIL);
//        getData4(cek);
//        String cek = intent.getStringExtra(ProfilAdmin.EXTRA_PA_IMG);
        Glide.with(getApplicationContext())
                .load(EXTRA_PA_IMG)
                .centerCrop()
                .into(ppuser);
    }
    @Override
    public void onPause () {
        super.onPause();
        Intent intent = getIntent();
        String cek = intent.getStringExtra(HomeAdmin.EXTRA_HA_EMAIL);
        getData4(cek);
//        Glide.with(getApplicationContext())
//                .load(EXTRA_PA_IMG)
//                .centerCrop()
//                .into(ppuser);
    }
    @Override
    public void onResume () {
        super.onResume();
//        Intent intent = getIntent();
//        String cek = intent.getStringExtra(HomeAdmin.EXTRA_HA_EMAIL);
//        getData4(cek);
//        String cek = intent.getStringExtra(ProfilAdmin.EXTRA_PA_IMG);
        Glide.with(getApplicationContext())
                .load(EXTRA_PA_IMG)
                .centerCrop()
                .into(ppuser);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Intent intent = getIntent();
//        String cek = intent.getStringExtra(HomeAdmin.EXTRA_HA_EMAIL);
//        getData4(cek);
//        String cek = intent.getStringExtra(ProfilAdmin.EXTRA_PA_IMG);
        Glide.with(getApplicationContext())
                .load(EXTRA_PA_IMG)
                .centerCrop()
                .into(ppuser);
    }

    private void SendDataUser() {

        String nama = txt_user.getText().toString();
        String nis = txt_nis.getText().toString();
        String email = txt_email.getText().toString();
        String alamat = txt_alamat.getText().toString();
        String uid = txtuid.getText().toString();
        String nomor = txt_nomor.getText().toString();
        String type = txt_type.getText().toString();

        Intent intent = new Intent(ProfilAdmin.this, ProfilSetting.class);
        intent.putExtra(EXTRA_PA_NAMA,nama);
        intent.putExtra(EXTRA_PA_EMAIL,email);
        intent.putExtra(EXTRA_PA_NIS,nis);
        intent.putExtra(EXTRA_PA_UID,uid);
        intent.putExtra(EXTRA_PA_PASSWORD,nama);
        intent.putExtra(EXTRA_PA_HP,nomor);
        intent.putExtra(EXTRA_PA_ALAMAT,alamat);
        intent.putExtra(EXTRA_PA_TYPE,"admin");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                selectedPicture = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                String uid = txtuid.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_IMG_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                Toast.makeText(ProfilAdmin.this, "Data Berhasil Diinput", Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(ProfilAdmin.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uid", uid);
                        params.put("img_user", selectedPicture);
                        return params;
                    }
                };
                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }



        }
    }

//    private void encodeBitmapImage(Bitmap bitmap) {
//        dialog.show();
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//
//            byte[] imageBytes = stream.toByteArray();
//
//            encodedImages = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//            String uid = txtuid.getText().toString();
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_IMG_URL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            dialog.dismiss();
//                            Toast.makeText(ProfilAdmin.this, "Data Berhasil Diinput", Toast.LENGTH_LONG).show();
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    dialog.dismiss();
//                    Toast.makeText(ProfilAdmin.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("uid", uid);
//                    params.put("img_user", encodedImages);
//                    return params;
//                }
//            };
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(stringRequest);
//
//    }
    private void getData4 (String cek){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + cek, null,
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
                                dataModels.add(dataUserModel);


                                txt_user.setText(dataObject.getString("nama").toString());
                                txtuid.setText(dataObject.getString("uid").toString());
                                txt_alamat.setText(dataObject.getString("alamat").toString());
                                txt_email.setText(dataObject.getString("email").toString());
                                txt_nomor.setText(dataObject.getString("no_hp").toString());
                                txt_nis.setText(dataObject.getString("nis").toString());
                                txt_type.setText(dataObject.getString("type_user").toString());

                                String img = dataObject.getString("img_user").toString();
                                EXTRA_PA_IMG = img;

                                Glide.with(getApplicationContext())
                                        .load(img)
                                        .centerCrop()
                                        .into(ppuser);
//                                    Glide.with(ppuser.getContext()).load().centerCrop().placeholder(R.drawable.ic_prof_user).into(ppuser);

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

}