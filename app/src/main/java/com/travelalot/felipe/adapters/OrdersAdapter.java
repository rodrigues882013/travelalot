package com.travelalot.felipe.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.travelalot.felipe.R;
import com.travelalot.felipe.core.AppController;
import com.travelalot.felipe.helpers.DatabaseHelper;
import com.travelalot.felipe.models.Order;
import com.travelalot.felipe.models.VacationPackage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by felipe on 25/01/17.
 */

public class OrdersAdapter extends BaseAdapter {
    private List<Order> orders;
    private Activity activity;
    private LayoutInflater inflater;
    private HashMap<Order, Integer> mIdMap = new HashMap<Order, Integer>();


    public OrdersAdapter(Activity activity, List<Order> orders) {
        //super(activity, resource);
        this.orders = orders;
        this.activity = activity;
        int idx = 0;
        for (Order order : orders){
            this.mIdMap.put(order, idx++);
        }

    }

    @Override
    public int getCount() {
        return this.orders.size();
    }

    @Override
    public Order getItem(int i) {
        return this.orders.get(i);
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return -1;
        }
        Order item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return android.os.Build.VERSION.SDK_INT < 21;
    }


    private VacationPackage getPackage(Order order){
        VacationPackage vp = null;

        try {
            Dao<VacationPackage, Integer> packagesDao = OpenHelperManager
                    .getHelper(activity, DatabaseHelper.class)
                    .getPackagesDao();

            vp = packagesDao.queryForId(order.getPackage().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Order order = (Order) orders.get(position);
        VacationPackage vp = getPackage(order);

        if (inflater == null)
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null)
            convertView = inflater.inflate(R.layout.order_card, null);



        //Get Texts
        TextView txtTitle = (TextView) convertView.findViewById(R.id.order_package_name);
        String packageName = vp.getPackageName();
        if (packageName != null) {
            txtTitle.setText(packageName);
        }

        TextView txtPrice = (TextView) convertView.findViewById(R.id.order_package_value);
        Double packageValue = vp.getPrice();
        if (packageValue != null) {
            txtPrice.setText(String.format(Locale.UK, "R$ " + "%,.2f", packageValue));
        }


        return convertView;

    }
}
