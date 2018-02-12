package com.ram.rewindtask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

public class ViewImageActivity extends AppCompatActivity {

    private ImageView imageView ;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView = findViewById(R.id.imageView);

        String image = getIntent().getStringExtra("image");

        mToolbar = findViewById(R.id.viewSingleImage);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("View Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Piccaso Library to access image----------
        Picasso.with(this).load(image).into(imageView);
    }
}
