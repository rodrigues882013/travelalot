package com.travelalot.felipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.travelalot.felipe.adapters.OrdersAdapter;
import com.travelalot.felipe.adapters.VacationPackageAdapter;
import com.travelalot.felipe.helpers.DatabaseHelper;
import com.travelalot.felipe.models.Order;
import com.travelalot.felipe.models.VacationPackage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersListActivity extends AppCompatActivity {

    private List<Order> orders = null;
    private OrdersAdapter oAdapter;
    private ListView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        this.getOrders();

        orderList = (ListView) findViewById(R.id.orders_list);
        oAdapter = new OrdersAdapter(this, orders);
        orderList.setAdapter(oAdapter);

    }

    public void getOrders(){

        try {


            Dao<Order, Integer> orderDao = OpenHelperManager
                    .getHelper(this.getApplicationContext(), DatabaseHelper.class)
                    .getOrderDao();

            orders = (ArrayList<Order>) orderDao.queryForAll();


        } catch (SQLException e) {
            e.printStackTrace();
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
}
