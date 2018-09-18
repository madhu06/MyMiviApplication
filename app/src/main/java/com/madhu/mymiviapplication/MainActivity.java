package com.madhu.mymiviapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText editTextUName, editTextPassword;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUName = findViewById(R.id.textView);
        editTextPassword = findViewById(R.id.textView2);
        signIn = findViewById(R.id.sign_in);
        final SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail = editTextUName.getText().toString().trim();
                final String userPassword = editTextPassword.getText().toString().trim();

                if (userEmail.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please enter Email ID", Toast.LENGTH_SHORT).show();
                    editTextUName.requestFocus();
                    return;
                }


                if (userPassword.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                    return;
                }


                try {
                    JSONObject obj = new JSONObject(readJSONFromAsset());
                    System.out.println("MainActivity.onClick -- Json Object" + obj);

                    if(obj != null) {

                        JSONArray included = obj.getJSONArray("included");
                        JSONObject servicesObject = included.getJSONObject(0).getJSONObject("attributes");
                        final String msn = servicesObject.getString("msn");

                        if(userEmail.equalsIgnoreCase(msn) && userPassword.equalsIgnoreCase(msn)) {

                            JSONObject subObject = included.getJSONObject(1);
                            final String id = subObject.getString("id");
                            JSONObject subsciptionObject = subObject.getJSONObject("attributes");
                            final Integer balance = subsciptionObject.getInt("included-data-balance");

                            JSONObject productObject = included.getJSONObject(2);
                            JSONObject prodObject = productObject.getJSONObject("attributes");
                            final Integer price = prodObject.getInt("price");
                            final String name = prodObject.getString("name");

                            editor.putString("sub_id", id);
                            editor.putInt("balance", balance);
                            editor.putString("product_name", name);
                            editor.putInt("product_price", price);

                            editor.apply();
//                            Toast.makeText(MainActivity.this, "Logged inSuccessfully.", Toast.LENGTH_SHORT).show();
                            final Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Collection is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public String readJSONFromAsset() {
        String myjsonstring = null;
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(
                    "collection.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null)
                    br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myjsonstring = sb.toString();
        return myjsonstring;
    }

}
