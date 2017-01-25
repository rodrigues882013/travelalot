package com.travelalot.felipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.travelalot.felipe.core.AppController;
import com.travelalot.felipe.models.VacationPackage;

import java.util.Locale;

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
                        buyPackage();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void buyPackage() {
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
}
