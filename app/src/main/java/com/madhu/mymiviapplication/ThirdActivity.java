package com.madhu.mymiviapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    TextView productPrice, subId, dataBalance, productName;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        productPrice = findViewById(R.id.price);
        subId = findViewById(R.id.sub_id);
        dataBalance = findViewById(R.id.balance);
        productName = findViewById(R.id.product_name);
        finish = findViewById(R.id.button);

        final SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE);
        final String sub_id = sharedPreferences.getString("sub_id", "");
        final Integer balance = sharedPreferences.getInt("balance", 0);
        final String product_name = sharedPreferences.getString("product_name", "");
        final Integer product_price = sharedPreferences.getInt("product_price", 0);

        productPrice.setText(product_price.toString());
        subId.setText(sub_id.toString());
        dataBalance.setText(balance.toString());
        productName.setText(product_name.toString());

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
