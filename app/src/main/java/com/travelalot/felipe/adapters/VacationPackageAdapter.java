package com.travelalot.felipe.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.travelalot.felipe.R;
import com.travelalot.felipe.core.AppController;
import com.travelalot.felipe.models.VacationPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by felipe on 23/01/17.
 */

public class VacationPackageAdapter extends BaseAdapter {

    private List<VacationPackage> packages;
    private Activity activity;
    private LayoutInflater inflater;
    private HashMap<VacationPackage, Integer> mIdMap = new HashMap<VacationPackage, Integer>();


    public VacationPackageAdapter(Activity activity, List<VacationPackage> packages) {
        //super(activity, resource);
        this.packages = packages;
        this.activity = activity;
        int idx = 0;
        for (VacationPackage vp : packages){
            this.mIdMap.put(vp, idx++);
        }

    }

    @Override
    public int getCount() {
        return this.packages.size();
    }

    @Override
    public VacationPackage getItem(int i) {
        return this.packages.get(i);
    }

    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return -1;
        }
        VacationPackage item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return android.os.Build.VERSION.SDK_INT < 21;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        VacationPackage vp = this.packages.get(position);

        if (inflater == null)
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null)
            convertView = inflater.inflate(R.layout.vacation_package_card, null);



//
//        View v = inflater.inflate(R.layout.vacation_package_card, null);
//
//        if (convertView == null) {
//            convertView = v;
//        }

        //Get Image
        NetworkImageView imgLocal = (NetworkImageView) convertView.findViewById(R.id.local_image);
        imgLocal.setImageUrl(vp.getImage(), AppController.getInstance(activity).getImageLoader());

        //Get Texts
        TextView txtTitle = (TextView) convertView.findViewById(R.id.package_name);
        String packageName = vp.getPackageName();
        if (packageName != null) {
            txtTitle.setText(packageName);
        }

        TextView txtPrice = (TextView) convertView.findViewById(R.id.package_value);
        Double packageValue = vp.getPrice();
        if (packageValue != null) {
            txtPrice.setText(String.format(Locale.UK, "R$ " + "%,.2f", packageValue));
        }


        return convertView;



    }

}
