package com.example.projectwiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectwiki.Adapter.AdapterDaftarBarang;
import com.example.projectwiki.Adapter.AdapterFaq;
import com.example.projectwiki.Fragmen.DaftarBarang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Faq extends AppCompatActivity implements AdapterFaq.OnItemClickListener {


    List<FaqModel> faqModelList;
    private Context context;
    RecyclerView recfaq;
    ImageView backbtnfaq;
    private AdapterFaq adapterFaq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recfaq = findViewById(R.id.recfaq);

        recfaq.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        faqModelList = new ArrayList<>();

        backbtnfaq = findViewById(R.id.btn_back);
        backbtnfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, AllDb.SERVER_GET_DATA_FAQ_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <= response.length();i++){
                            try {

                                JSONObject dataObject = response.getJSONObject(i);
                                FaqModel faqModel = new FaqModel();
                                faqModel.setPertanyaan(dataObject.getString("pertanyaan").toString());
                                faqModel.setJawaban(dataObject.getString("jawaban").toString());
                                faqModelList.add(faqModel);

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapterFaq = new AdapterFaq(getApplicationContext(),faqModelList);
                        recfaq.setAdapter(adapterFaq);
                        adapterFaq.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(int position) {

    }
}