package com.travelalot.felipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        configButton(R.id.btn_packages, 1);
        configButton(R.id.btn_orders, 2);

    }

    private void configButton(int btn_id, final int option) {
        final Button btn = (Button) findViewById(btn_id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (option){
                    case 1:
                        goToPackageListActivity();
                        break;
                    case 2:
                        goToOrderListActivity();
                        break;
                }
            }
        });
    }

    protected boolean goToOrderListActivity() {
        Intent intent = new Intent(getBaseContext(), OrdersListActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToPackageListActivity() {
        Intent intent = new Intent(getBaseContext(), VacationPackageListActivity.class);
        startActivity(intent);
        return true;
    }
}
