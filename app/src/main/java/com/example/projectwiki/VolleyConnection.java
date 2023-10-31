package com.example.projectwiki;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyConnection {

    private static VolleyConnection volleyConnection;
    private RequestQueue requestQueue;
    private static Context context;

    public VolleyConnection(Context context) {
        context = context;
        requestQueue= getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    return requestQueue;
    }

    private static synchronized VolleyConnection getInstance(Context context){
        if(volleyConnection == null){
            volleyConnection = new VolleyConnection(context);
        }
        return volleyConnection;
    }

    public<T> void addToRequestQue (Request<T> request){
        getRequestQueue().add(request);
    }
}
