package com.travelalot.felipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.travelalot.felipe.adapters.VacationPackageAdapter;
import com.travelalot.felipe.core.AppController;
import com.travelalot.felipe.models.VacationPackage;
import com.travelalot.felipe.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class VacationPackageListActivity extends AppCompatActivity {

    private List<VacationPackage> packages = new ArrayList<VacationPackage>();
    private VacationPackageAdapter vpAdapter;
    private ListView packageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_package_list);

        packageList = (ListView) findViewById(R.id.package_list);
        packageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                VacationPackage vp = (VacationPackage)packageList.getItemAtPosition(position);
                goToDetailActivity(vp);
            }
        });
        vpAdapter = new VacationPackageAdapter(this, packages);
        packageList.setAdapter(vpAdapter);


        RequestQueue queue = AppController.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = Utils.API_URL + "/travels";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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

                        vPackage = createVacationPackage(packageName,
                                description,
                                price,
                                local,
                                url);
                        packages.add(vPackage);
                    }

                    vpAdapter.notifyDataSetChanged();

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

    protected VacationPackage createVacationPackage(String packageName,
                                                    String description,
                                                    Double price,
                                                    String local,
                                                    String url){
        VacationPackage vp = new VacationPackage();
        try {

            vp.setPackageName(new String(packageName.getBytes("ISO-8859-1"), "UTF-8"));
            vp.setDescription(new String(description.getBytes("ISO-8859-1"), "UTF-8"));
            vp.setLocal(new String(local.getBytes("ISO-8859-1"), "UTF-8"));
            vp.setPrice(price);
            vp.setImage(url);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return vp;
    }


    protected boolean goToDetailActivity(VacationPackage _package) {
        Intent intent = new Intent(getBaseContext(), DetailPackageActivity.class);
        intent.putExtra("packageName", _package.getPackageName());
        intent.putExtra("description", _package.getDescription());
        intent.putExtra("url", _package.getImage());
        intent.putExtra("local", _package.getLocal());
        intent.putExtra("price", _package.getPrice().toString());
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon action bar is clicked; go to parent activity
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
