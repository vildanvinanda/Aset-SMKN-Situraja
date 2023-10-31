package com.example.projectwiki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projectwiki.Admin.ProfilAdmin;
import com.example.projectwiki.Fragmen.Profil;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilSetting extends AppCompatActivity {

    public static final String EXTRA_PS_NAMA = "nama";
    public static final String EXTRA_PS_EMAIL = "email";
    public static final String EXTRA_PS_NIS = "nis";
    public static final String EXTRA_PS_UID = "uid";
    public static final String EXTRA_PS_PASSWORD = "password";
    public static final String EXTRA_PS_HP = "no_hp";
    public static final String EXTRA_PS_KLS = "kelas";
    public static final String EXTRA_PS_ALAMAT = "alamat";
    public static final String EXTRA_PS_JK = "jk";
    public static final String EXTRA_PS_TYPE = "type_user";

    ImageView rowright, rowright2, rowright3, rowright4,rowright5, btn_back;
    ImageView rowright1_admin, rowright2_admin, rowright3_admin;
    TextView txt_type;
    private ProgressDialog dialog;
    LinearLayout admin_domain,user_domain;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_setting);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        rowright = (ImageView) findViewById(R.id.rowright);
        rowright2 = (ImageView) findViewById(R.id.rowright2);
        rowright3 = (ImageView) findViewById(R.id.rowright3);
        rowright4 = (ImageView) findViewById(R.id.rowright4);
        rowright5 = (ImageView) findViewById(R.id.rowright5);

        rowright1_admin = (ImageView) findViewById(R.id.rowright1_admin);
        rowright2_admin = (ImageView) findViewById(R.id.rowright2_admin);
        rowright3_admin = (ImageView) findViewById(R.id.rowright3_admin);
        txt_type = (TextView) findViewById(R.id.txt_type);

        admin_domain = (LinearLayout) findViewById(R.id.admin_domain);
        user_domain = (LinearLayout) findViewById(R.id.user_domain);

        sharedPreferences = getSharedPreferences("sesi_login", MODE_PRIVATE);

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
        String type = intent.getStringExtra(Profil.EXTRA_P_TYPE);
        String type2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_TYPE);
        txt_type.setText(type);
        txt_type.setText(type2);

        String cekType = txt_type.getText().toString();

        if(cekType.equals("admin")){
            user_domain.setVisibility(View.GONE);
            admin_domain.setVisibility(View.VISIBLE);
        } else {
            user_domain.setVisibility(View.VISIBLE);
            admin_domain.setVisibility(View.GONE);
        }

        rowright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        ProfilSetting.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_ubahpass, (RelativeLayout) findViewById(R.id.bottomubahpassword)
                        );

                EditText eddpassebelum = (EditText) bottomSheetView.findViewById(R.id.eddpassebelum);
                EditText eddpassesudah = (EditText) bottomSheetView.findViewById(R.id.eddpassesudah);

                bottomSheetView.findViewById(R.id.btnclosemodal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btnubahpass).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String oldpassword = eddpassebelum.getText().toString().trim();
                        String newpassword = eddpassesudah.getText().toString().trim();

                        if(TextUtils.isEmpty(oldpassword)){
                            eddpassebelum.requestFocus();
                            eddpassebelum.setError("Tolong diisi");
                        }else if (newpassword.length()<6){
                            eddpassesudah.requestFocus();
                            eddpassesudah.setError("Tolong diisi");
                        } else {
                            updatePassword(oldpassword, newpassword);
                        }
                    }

                    private void updatePassword(String oldpassword, String newpassword) {
                        dialog.show();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(Profil.EXTRA_P_EMAIL);
                        String uid = intent.getStringExtra(Profil.EXTRA_P_UID);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,AllDb.SERVER_CHECK_PASS_USER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String resp = jsonObject.getString("server_response");
                                            if (resp.equals("[{\"status\":\"OK\"}]")){
                                                dialog.dismiss();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_PASS_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(ProfilSetting.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
                                                                finish();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(ProfilSetting.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("password", newpassword);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } else  {
                                                dialog.dismiss();
                                                Toast.makeText(ProfilSetting.this, resp, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("email", email);
                                params.put("password", oldpassword);
                                return  params;
                            }
                        };
                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                    }

                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String nama = intent.getStringExtra(Profil.EXTRA_P_NAMA);
                String email = intent.getStringExtra(Profil.EXTRA_P_EMAIL);
                String nis = intent.getStringExtra(Profil.EXTRA_P_NIS);
                String uid = intent.getStringExtra(Profil.EXTRA_P_UID);
                String pass = intent.getStringExtra(Profil.EXTRA_P_PASSWORD);
                String hp = intent.getStringExtra(Profil.EXTRA_P_HP);
                String kls = intent.getStringExtra(Profil.EXTRA_P_KLS);
                String jk = intent.getStringExtra(Profil.EXTRA_P_JK);
                String alamat = intent.getStringExtra(Profil.EXTRA_P_ALAMAT);

                Intent in =  new Intent(ProfilSetting.this, UbahDataProfil.class);
                in.putExtra(EXTRA_PS_NAMA, nama);
                in.putExtra(EXTRA_PS_EMAIL, email);
                in.putExtra(EXTRA_PS_NIS, nis);
                in.putExtra(EXTRA_PS_UID, uid);
                in.putExtra(EXTRA_PS_PASSWORD, pass);
                in.putExtra(EXTRA_PS_HP, hp);
                in.putExtra(EXTRA_PS_KLS, kls);
                in.putExtra(EXTRA_PS_JK, jk);
                in.putExtra(EXTRA_PS_ALAMAT, alamat);
                in.putExtra(EXTRA_PS_TYPE, "user");
                startActivity(in);
            }
        });

        rowright3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                        ProfilSetting.this, R.style.BottomSheetDialogTheme
//                );
//                View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                        .inflate(
//                                R.layout.modal_callme, (RelativeLayout) findViewById(R.id.bottomcallme)
//                        );
//
////                ImageView wa = (ImageView) bottomSheetView.findViewById(R.id.logo_wa);
////                ImageView ig = (ImageView) bottomSheetView.findViewById(R.id.logo_ig);
//
//                bottomSheetView.findViewById(R.id.btnclosemodal).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//
////                bottomSheetView.findViewById(R.id.logo_wa).setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        final String no_admin = "+6282121072289";
////                        Intent intent = getIntent();
////                        String nama = intent.getStringExtra(Profil.EXTRA_P_NAMA);
////                        String nis = intent.getStringExtra(Profil.EXTRA_P_NIS);
////                        String uid = intent.getStringExtra(Profil.EXTRA_P_UID);
////                        String kls = intent.getStringExtra(Profil.EXTRA_P_KLS);
////
////                        String semuapesan = "Email :"+nama+"\n"+"NIS :"+nis+"\n"+"Kelas :"+kls;
////
////                        Intent kirimWa = new Intent(Intent.ACTION_SEND);
////                        kirimWa.setType("text/plaint");
////                        kirimWa.putExtra(Intent.EXTRA_TEXT, semuapesan);
////                        kirimWa.putExtra("jid", "6282121072289"+"@s.whatsapp.net");
////                        kirimWa.setPackage("com.whatsapp");
////
////                        startActivity(kirimWa);
////
////                    }
////                });
////
////                bottomSheetView.findViewById(R.id.logo_ig).setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Uri uri = Uri.parse("https://www.instagram.com/osissmknsituraja/");
////                        Intent followme = new Intent(Intent.ACTION_VIEW, uri);
////
////                        followme.setPackage("com.instagram.android");
////
////                        try {
////                            startActivity(followme);
////                        } catch (ActivityNotFoundException e) {
////                            startActivity(new Intent(Intent.ACTION_VIEW,
////                                    Uri.parse("http://instagram.com/osissmknsituraja")));
////                        }
////
////                    }
////                });
////
//                bottomSheetDialog.setContentView(bottomSheetView);
//                bottomSheetDialog.show();

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        ProfilSetting.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_callme, (RelativeLayout) findViewById(R.id.bottomcallme)
                        );


                bottomSheetView.findViewById(R.id.btnclosefilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.logo_wa).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String no_admin = "+6282121072289";
                        Intent intent = getIntent();
                        String nama = intent.getStringExtra(Profil.EXTRA_P_NAMA);
                        String nis = intent.getStringExtra(Profil.EXTRA_P_NIS);
                        String uid = intent.getStringExtra(Profil.EXTRA_P_UID);
                        String kls = intent.getStringExtra(Profil.EXTRA_P_KLS);

                        String semuapesan = "Email :"+nama+"\n"+"NIS :"+nis+"\n"+"Kelas :"+kls;

                        Intent kirimWa = new Intent(Intent.ACTION_SEND);
                        kirimWa.setType("text/plaint");
                        kirimWa.putExtra(Intent.EXTRA_TEXT, semuapesan);
                        kirimWa.putExtra("jid", "6282121072289"+"@s.whatsapp.net");
                        kirimWa.setPackage("com.whatsapp");

                        startActivity(kirimWa);

                    }
                });

                bottomSheetView.findViewById(R.id.logo_ig).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("https://www.instagram.com/osissmknsituraja/");
                        Intent followme = new Intent(Intent.ACTION_VIEW, uri);

                        followme.setPackage("com.instagram.android");

                        try {
                            startActivity(followme);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://instagram.com/osissmknsituraja")));
                        }

                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProfilSetting.this, Faq.class);
                startActivity(intent1);
            }
        });

        rowright5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(ProfilSetting.this, Login.class);
                startActivity(intent);
                finish();

            }
        });

        rowright1_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        ProfilSetting.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.modal_ubahpass, (RelativeLayout) findViewById(R.id.bottomubahpassword)
                        );

                EditText eddpassebelum = (EditText) bottomSheetView.findViewById(R.id.eddpassebelum);
                EditText eddpassesudah = (EditText) bottomSheetView.findViewById(R.id.eddpassesudah);

                bottomSheetView.findViewById(R.id.btnclosemodal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btnubahpass).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String oldpassword = eddpassebelum.getText().toString().trim();
                        String newpassword = eddpassesudah.getText().toString().trim();

                        if(TextUtils.isEmpty(oldpassword)){
                            eddpassebelum.requestFocus();
                            eddpassebelum.setError("Tolong diisi");
                        }else if (newpassword.length()<6){
                            eddpassesudah.requestFocus();
                            eddpassesudah.setError("Tolong diisi");
                        } else {
                            updatePassword(oldpassword, newpassword);
                        }
                    }

                    private void updatePassword(String oldpassword, String newpassword) {
                        dialog.show();
                        Intent intent = getIntent();
                        String email = intent.getStringExtra(ProfilAdmin.EXTRA_PA_EMAIL);
                        String uid = intent.getStringExtra(ProfilAdmin.EXTRA_PA_UID);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,AllDb.SERVER_CHECK_PASS_USER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String resp = jsonObject.getString("server_response");
                                            if (resp.equals("[{\"status\":\"OK\"}]")){
                                                dialog.dismiss();

                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, AllDb.SERVER_UPDATE_PASS_USER_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                dialog.dismiss();
                                                                Toast.makeText(ProfilSetting.this, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
                                                                finish();

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(ProfilSetting.this, "Data Gagal Diinput", Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("uid", uid);
                                                        params.put("password", newpassword);
                                                        return params;
                                                    }
                                                };
                                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

                                            } else  {
                                                dialog.dismiss();
                                                Toast.makeText(ProfilSetting.this, resp, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("email", email);
                                params.put("password", oldpassword);
                                return  params;
                            }
                        };
                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                    }

                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        rowright2_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String nama2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_NAMA);
                String email2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_EMAIL);
                String nis2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_NIS);
                String uid2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_UID);
                String pass2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_PASSWORD);
                String hp2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_HP);
                String jk2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_JK);
                String alamat2 = intent.getStringExtra(ProfilAdmin.EXTRA_PA_ALAMAT);
                String type = intent.getStringExtra(ProfilAdmin.EXTRA_PA_TYPE);

                Intent in =  new Intent(ProfilSetting.this, UbahDataProfil.class);
                in.putExtra(EXTRA_PS_NAMA, nama2);
                in.putExtra(EXTRA_PS_EMAIL, email2);
                in.putExtra(EXTRA_PS_NIS, nis2);
                in.putExtra(EXTRA_PS_UID, uid2);
                in.putExtra(EXTRA_PS_PASSWORD, pass2);
                in.putExtra(EXTRA_PS_HP, hp2);
                in.putExtra(EXTRA_PS_JK, jk2);
                in.putExtra(EXTRA_PS_ALAMAT, alamat2);
                in.putExtra(EXTRA_PS_TYPE, "admin");
                startActivity(in);
            }
        });

        rowright3_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(ProfilSetting.this, Login.class);
                startActivity(intent);
                finish();

            }
        });

    }
}