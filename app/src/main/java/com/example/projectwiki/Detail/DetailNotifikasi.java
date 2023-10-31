package com.example.projectwiki.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectwiki.Fragmen.Notivication;
import com.example.projectwiki.R;

public class DetailNotifikasi extends AppCompatActivity {

    TextView txttglnotif2, txtjudul2, isinotif2;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notifikasi);

        txtjudul2 = (TextView) findViewById(R.id.txtjudul2);
        isinotif2 = (TextView) findViewById(R.id.isinotif2);
        txttglnotif2 = (TextView) findViewById(R.id.txttglnotif2);
        btn_back = (ImageView) findViewById(R.id.btn_back);

        Intent intent = getIntent();
        String bpid = intent.getStringExtra(Notivication.EXTRA_N_BPID);
        String email = intent.getStringExtra(Notivication.EXTRA_N_EMAIL);
        String judul = intent.getStringExtra(Notivication.EXTRA_N_JDL);
        String ket = intent.getStringExtra(Notivication.EXTRA_N_KET);
        String tgl = intent.getStringExtra(Notivication.EXTRA_N_TGL);

        txttglnotif2.setText(tgl);
        txtjudul2.setText(judul);
        isinotif2.setText(ket);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}