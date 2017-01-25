package com.travelalot.felipe.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelalot.felipe.R;
import com.travelalot.felipe.models.VacationPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by felipe on 23/01/17.
 */

public class VacationPackageAdapter extends ArrayAdapter<VacationPackage> {

    private List<VacationPackage> packages;
    private Context context;
    private HashMap<VacationPackage, Integer> mIdMap = new HashMap<VacationPackage, Integer>();


    public VacationPackageAdapter(Context context, int resource, List<VacationPackage> packages) {
        super(context, resource);
        this.packages = packages;
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
        View v = convertView;

        if (v == null){
            LayoutInflater inflater =
                    (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.vacation_package_card, null);
        }


//
//        View v = inflater.inflate(R.layout.vacation_package_card, null);
//
//        if (convertView == null) {
//            convertView = v;
//        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.package_name);
        String packageName = vp.getPackageName();
        if (packageName != null) {
            txtTitle.setText(packageName);
        }

        TextView txtPrice = (TextView) convertView.findViewById(R.id.package_value);
        Double packageValue = vp.getPrice();
        if (packageValue != null) {
            txtPrice.setText(String.format(Locale.US, "%10.0f", packageValue));
        }

//        ImageView imgLocal = (ImageView) convertView.findViewById(R.id.local_image);
//        Bitmap img = BitmapFactory.decodeStream(vp.getImage());
//        if (img != null){
//            imgLocal.setImageBitmap(img);
//        }

        return convertView;



    }

}
