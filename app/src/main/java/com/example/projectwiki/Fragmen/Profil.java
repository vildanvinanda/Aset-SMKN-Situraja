package com.example.projectwiki.Fragmen;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.projectwiki.Form.FormPengembalian;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class Profil extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public Profil() {
        // Required empty public constructor
    }
    public static Profil newInstance(String param1, String param2) {
        Profil fragment = new Profil();
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

    Bitmap bitmap;
    String encodedImages;
    List<DataUserModel> dataModels;
    TextView txt_user, txt_nis, txt_email, txt_kls, txt_nomor, txt_jk, txt_alamat, txtuid;
    ImageView ppuser,settingbtn;
    FloatingActionButton float_editpp;
    ProgressDialog dialog;
    Uri capturedImgURL;
    int PERMISSION_DATA = 20;
    int ACCESS_DATA = 40;

    public static final String EXTRA_P_NAMA = "nama";
    public static final String EXTRA_P_EMAIL = "email";
    public static final String EXTRA_P_NIS = "nis";
    public static final String EXTRA_P_UID = "uid";
    public static final String EXTRA_P_PASSWORD = "password";
    public static final String EXTRA_P_HP = "no_hp";
    public static final String EXTRA_P_KLS = "kelas";
    public static final String EXTRA_P_ALAMAT = "alamat";
    public static final String EXTRA_P_JK = "jk";
    public static final String EXTRA_P_TYPE = "user";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        txt_user = (TextView) view.findViewById(R.id.txt_user);
        txt_nis = (TextView) view.findViewById(R.id.txt_nis);
        txt_email = (TextView) view.findViewById(R.id.txt_email);
        txtuid = (TextView) view.findViewById(R.id.txtuid);
        txt_kls = (TextView) view.findViewById(R.id.txt_kls);
        txt_nomor = (TextView) view.findViewById(R.id.txt_nomor);
        txt_jk = (TextView) view.findViewById(R.id.txt_jk);
        txt_alamat = (TextView) view.findViewById(R.id.txt_alamat);
        float_editpp = (FloatingActionButton) view.findViewById(R.id.float_editpp);
        ppuser = (ImageView) view.findViewById(R.id.ppuser);
        settingbtn = (ImageView) view.findViewById(R.id.settingbtn);

        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Tolong tunggu.....");
        dialog.setCancelable(false);
        dialog.setTitle("Data sedang di upload");
        dialog.setCanceledOnTouchOutside(false);

        dataModels = new ArrayList<>();

        String cek = getArguments().getString("contoh");
        getData4(cek);


        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDataUser();
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent data = result.getData();
                            Uri uri =data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);
                                ppuser.setImageBitmap(bitmap);
                                sendImage(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


        float_editpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);


            }
        });
        return view;

    }

    private void SendDataUser() {
        String cek = getArguments().getString("contoh");

        String nama = txt_user.getText().toString();
        String nis = txt_nis.getText().toString();
        String email = txt_email.getText().toString();
        String alamat = txt_alamat.getText().toString();
        String uid = txtuid.getText().toString();
        String kls = txt_kls.getText().toString();
        String nomor = txt_nomor.getText().toString();
        String jk = txt_jk.getText().toString();

        Intent intent = new Intent(getActivity(), ProfilSetting.class);
        intent.putExtra(EXTRA_P_NAMA,nama);
        intent.putExtra(EXTRA_P_EMAIL,email);
        intent.putExtra(EXTRA_P_NIS,nis);
        intent.putExtra(EXTRA_P_UID,uid);
        intent.putExtra(EXTRA_P_PASSWORD,nama);
        intent.putExtra(EXTRA_P_HP,nomor);
        intent.putExtra(EXTRA_P_KLS,kls);
        intent.putExtra(EXTRA_P_JK,jk);
        intent.putExtra(EXTRA_P_ALAMAT,alamat);
        intent.putExtra(EXTRA_P_TYPE,"user");
        startActivity(intent);
    }

    private void sendImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
        if(bitmap!=null){
            dialog.show();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String base64Image =  android.util.Base64.encodeToString(bytes, Base64.DEFAULT);

//            RequestQueue queue = Volley.newRequestQueue(getContext());
            String uid = txtuid.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_IMG_URL,
                   new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Data Berhasil Diinput", Toast.LENGTH_LONG).show();

                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uid", uid);
                        params.put("img_user", base64Image);
                        return params;
                    }
                };
            RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

        }
    }

//        @Override
//        public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
//
//            if (requestCode == ACCESS_DATA && resultCode == RESULT_OK) {
//                if (data != null) {
//                    capturedImgURL = data.getData();
//                }
//
//                try {
//                    Uri uri = data.getData();
//                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), capturedImgURL);
//                    ppuser.setImageBitmap(bitmap);
//                    sendImage(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }
//
//        private void sendImage(Bitmap bitmap) {
//        dialog.show();
//            String cek = getArguments().getString("contoh");
//            final String nameImage = "IMG_"+ String.valueOf(System.currentTimeMillis())+".jpg";
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//
//            byte[] imageBytes = stream.toByteArray();
//
//            encodedImages = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//
//            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_SEARCH_USER + cek, null,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//
//                            for (int i = 0; i <= response.length(); i++) {
//                                try {
//                                    JSONObject dataObject = response.getJSONObject(i);
//
//                                    DataUserModel dataUserModel = new DataUserModel();
//                                    dataUserModel.setUid(dataObject.getString("uid").toString());
//                                    dataUserModel.setNis(dataObject.getString("nis").toString());
//                                    dataUserModel.setEmail(dataObject.getString("email").toString());
//                                    dataUserModel.setPassword(dataObject.getString("password").toString());
//                                    dataUserModel.setNama(dataObject.getString("nama").toString());
//                                    dataUserModel.setKelas(dataObject.getString("kelas").toString());
//                                    dataUserModel.setNo_hp(dataObject.getString("no_hp").toString());
//                                    dataUserModel.setJk(dataObject.getString("jk").toString());
//                                    dataUserModel.setAlamat(dataObject.getString("alamat").toString());
//                                    dataUserModel.setType_user(dataObject.getString("type_user").toString());
//                                    dataUserModel.setType_user(dataObject.getString("img_user").toString());
//                                    dataModels.add(dataUserModel);
//
//
//                                    txt_user.setText(dataObject.getString("nama").toString());
//                                    txtuid.setText(dataObject.getString("uid").toString());
//                                    txt_alamat.setText(dataObject.getString("alamat").toString());
//                                    txt_email.setText(dataObject.getString("email").toString());
//                                    txt_kls.setText(dataObject.getString("kelas").toString());
//                                    txt_nomor.setText(dataObject.getString("no_hp").toString());
//
//                                    String uid = txtuid.getText().toString();
//                                    Glide.with(ppuser.getContext()).load(dataObject.getString("img_user").toString()).centerCrop().placeholder(R.drawable.ic_prof_user).into(ppuser);
//
//                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_DATA_USER_IMG_URL,
//                                            new Response.Listener<String>() {
//                                                @Override
//                                                public void onResponse(String response) {
//                                                    dialog.dismiss();
//                                                    Toast.makeText(getActivity(), "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
//
//                                                }
//                                            }, new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            dialog.dismiss();
//                                            Toast.makeText(getActivity(), "Data Gagal Diinput", Toast.LENGTH_LONG).show();
//                                        }
//                                    }) {
//                                        @Override
//                                        protected Map<String, String> getParams() throws AuthFailureError {
//                                            Map<String, String> params = new HashMap<String, String>();
//                                            params.put("uid", uid);
//                                            params.put("img_user", encodedImages);
//                                            return params;
//                                        }
//                                    };
//                                    RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//
//            requestQueue.add(jsonArrayRequest);
//
//
//        }


        @Override
        public void onStart () {
            super.onStart();
            String cek = getArguments().getString("contoh");
            getData4(cek);
        }
    @Override
    public void onPause () {
        super.onPause();
        String cek = getArguments().getString("contoh");
        getData4(cek);
    }
        @Override
        public void onResume () {
            super.onResume();
            String cek = getArguments().getString("contoh");
            getData4(cek);
        }

        private void getData4 (String cek){
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                                    txt_kls.setText(dataObject.getString("kelas").toString());
                                    txt_nomor.setText(dataObject.getString("no_hp").toString());
                                    txt_nis.setText(dataObject.getString("nis").toString());
                                    txt_jk.setText(dataObject.getString("jk").toString());

                                    String img = dataObject.getString("img_user").toString();

                                    Glide.with(ppuser.getContext())
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
