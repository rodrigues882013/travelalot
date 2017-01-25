package com.travelalot.felipe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.travelalot.felipe.adapters.VacationPackageAdapter;
import com.travelalot.felipe.models.VacationPackage;
import com.travelalot.felipe.services.LoadImageTask;
import com.travelalot.felipe.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VacationPackageListActivity extends AppCompatActivity {

    private String url;
    private List<VacationPackage> packages;
    private VacationPackageAdapter vpAdapter;
    private ListView packageList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_package_list);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Utils.API_URL + "/travels";
        final Context ctx = this;
        packages = new ArrayList<VacationPackage>();
        packageList = (ListView) findViewById(R.id.package_list);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject vp = new JSONObject(response);
                    JSONArray packagesVacationJson = vp.getJSONArray("packages");
                    VacationPackage vPackage;
                    for (int i=0; i<packagesVacationJson.length(); i++){
                        JSONObject jsonObj = packagesVacationJson.getJSONObject(i);
                        String packageName = jsonObj.getString("packageName");
                        String description = jsonObj.getString("description");
                        String local = jsonObj.getString("local");
                        String url = jsonObj.getString("photos");
                        Double price = jsonObj.getDouble("price");

                        vPackage = VacationPackageListActivity.createVacationPackage(packageName,
                                description,
                                price,
                                local,
                                url);
                        packages.add(vPackage);
                    }

                    vpAdapter = new VacationPackageAdapter(ctx, Utils.GENERIC_RESOURCE_ID, packages);
                    packageList.setAdapter(vpAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    protected static InputStream extractImage(String url){
        return null;
    }
    protected static VacationPackage createVacationPackage(String packageName,
                                                           String description,
                                                           Double price,
                                                           String local,
                                                           String url){
        VacationPackage vp = new VacationPackage();
        vp.setPackageName(packageName);
        vp.setDescription(description);
        vp.setPrice(price);
        vp.setLocal(local);
        //vp.setImage(VacationPackageListActivity.extractImage(url));

        return vp;
    }
}
