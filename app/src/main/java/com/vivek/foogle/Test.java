package com.vivek.foogle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    private String mCurrentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView text = (TextView) findViewById(R.id.restaurantName);
        String rest = getIntent().getStringExtra("Restaurant");

        ImageView menu = (ImageView) findViewById(R.id.resMenu);

        if (rest.substring(3).equals("1")) {
            mCurrentUrl = "http://www.in-n-out.com/";
            text.setText("In and out Burger");
            menu.setImageResource(R.drawable.m1);
        } else if (rest.substring(3).equals("2")){
            mCurrentUrl = "http://lousrestaurant.net/";
            menu.setImageResource(R.drawable.m2);

            text.setText("Lou's Bakery");


        } else if (rest.substring(3).equals("3")){
            mCurrentUrl = "http://www.tasteofnepal.com.au/";
            text.setText("Taste of Nepal");
            menu.setImageResource(R.drawable.m3);

        }


    }


    public void gotolink(View v){

        Uri uri = Uri.parse(mCurrentUrl); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
