package com.travelalot.felipe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.travelalot.felipe.core.AppController;
import com.travelalot.felipe.helpers.DatabaseHelper;
import com.travelalot.felipe.models.Order;
import com.travelalot.felipe.models.VacationPackage;
import com.travelalot.felipe.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Locale;


/**
 * Created by felipe on 23/01/17.
 */

public class DetailPackageActivity extends AppCompatActivity {
    private VacationPackage vp;
    private NetworkImageView image;
    final int BTN_BUY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_package);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        vp = createPackage(extra);

        configWidgets();

    }

    public VacationPackage createPackage(Bundle bdn){
        String packageName = bdn.getString("packageName");
        String description = bdn.getString("description");
        String url = bdn.getString("url");
        String local = bdn.getString("local");
        Double price = Double.parseDouble(bdn.getString("price"));

        return new VacationPackage(packageName, price, description, url, local);


    }


    public void configWidgets(){

        //Images
        configImageView(R.id.img_detail_image, vp.getImage());

        //Texts View
        configTextView(R.id.txt_detail_description, vp.getDescription());
        configTextView(R.id.txt_detail_price, String.format(Locale.UK, "R$ " + "%,.2f", vp.getPrice()));
        configTextView(R.id.txt_detail_title, vp.getPackageName());

        //Button
        configButton(R.id.btn_buy, BTN_BUY);
    }

    private void configButton(int btn_buy, final int option) {
        final Button btn = (Button) findViewById(btn_buy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (option){
                    case 1:
                        confirmBuy();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void buyPackage(){
        RequestQueue queue = AppController.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = Utils.API_URL + "/orders";

        final String requestBody = buildJson();

        final Context ctx = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                persistData();
                Toast toast = Toast.makeText(ctx, "Dados salvos com sucesso", Toast.LENGTH_LONG);
                toast.show();
                goToOrderListActivity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        queue.add(stringRequest);


    }


    private void persistData() {
        try {
            Dao<VacationPackage, Integer> packageDao = OpenHelperManager
                    .getHelper(this.getApplicationContext(), DatabaseHelper.class)
                    .getPackagesDao();

            Dao<Order, Integer> orderDao = OpenHelperManager
                    .getHelper(this.getApplicationContext(), DatabaseHelper.class)
                    .getOrderDao();

            packageDao.create(vp);
            Order order = new Order(vp.getPrice());
            order.setPackage(vp);
            orderDao.create(order);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void confirmBuy() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar compra")
                .setMessage("Deseja realmente efetuar este pedido?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        buyPackage();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    private String buildJson() {
        JSONObject jsonOrder = new JSONObject();
        JSONObject jsonPackage = new JSONObject();
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        Integer version = Build.VERSION.SDK_INT;

        try {
            jsonPackage.put("packageName", vp.getPackageName());
            jsonPackage.put("description", vp.getDescription());
            jsonPackage.put("photos", vp.getImage());
            jsonPackage.put("local", vp.getLocal());
            jsonPackage.put("price", vp.getPrice().toString());

            jsonOrder.put("total", vp.getPrice().toString());
            jsonOrder.put("package", jsonPackage);
            jsonOrder.put("ANDROID_SDK" , version.toString());
            jsonOrder.put("ANDROID_MANUFACTURE", manufacturer);
            jsonOrder.put("ANDROID_MODEL", model);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonOrder.toString();
    }

    private void configImageView(int imgDetailImage, String image) {
        final NetworkImageView imgDetail = (NetworkImageView) findViewById(imgDetailImage);
        imgDetail.setImageUrl(image, AppController.getInstance(this).getImageLoader());
    }

    public void configTextView(final int vid, Object value) {
        final TextView txt = (TextView) findViewById(vid);
        txt.setText("");
        if (value != null) {
            String text = value.toString();
            txt.setText(text);
        }
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

    protected boolean goToOrderListActivity() {
        Intent intent = new Intent(getBaseContext(), OrdersListActivity.class);
        startActivity(intent);
        return true;
    }
}
