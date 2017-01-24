package com.travelalot.felipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.travelalot.felipe.models.VacationPackage;
import com.travelalot.felipe.utils.Utils;

import java.util.List;

public class VacationPackageListActivity extends AppCompatActivity {

    private String url;
    private List<VacationPackage> packages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_package_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Utils.API_URL + "/travels";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TravelALot", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }
}
