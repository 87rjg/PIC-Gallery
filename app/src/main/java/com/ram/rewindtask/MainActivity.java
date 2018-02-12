package com.ram.rewindtask;


import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewpager;
    private TabLayout mTablayout;
    private Toolbar mToolbar;
    private SectionPagerAdapter sectionPagerAdapter;
    private SQLiteHelper sqLiteHelper;
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_IMAGE_GALARY_REQUEST = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this, "PICKDB.sqlite", null, 1);


        mToolbar = findViewById(R.id.main_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("RewindTask");

        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewpager = findViewById(R.id.viewpager);
        mViewpager.setAdapter(sectionPagerAdapter);

        mTablayout = findViewById(R.id.main_tabs);
        mTablayout.setupWithViewPager(mViewpager);



        //--------Initialization of Float Button-------------
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.add_icon);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon).setBackgroundDrawable(R.drawable.add_icon)
                .build();
        //--------------click listener of float Button------------
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //-----------seek for permission------------------
                ActivityCompat.requestPermissions(MainActivity.this,new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PICK_IMAGE_GALARY_REQUEST);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PICK_IMAGE_GALARY_REQUEST){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }else{
                Toast.makeText(this,"Please Grant Permission to access Galary!",Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            String img = uri.toString();

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
           // getBytes(bitmap);

            int posi = mTablayout.getSelectedTabPosition();
            switch (posi){
                case 0:
                    sqLiteHelper.queryDAta("CREATE TABLE IF NOT EXISTS TAB1( id INTEGER PRIMARY KEY AUTOINCREMENT, image VARCHAR(500))");
                    try{

                        //----------Saving image URL to Sqlite DB for TAB1------------
                        sqLiteHelper.insertData(img, "TAB1");

                        /* Tab1Fragment myFragment = new Tab1Fragment();
                       // myFragment.loadImage();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.setAllowOptimization(false);
                        transaction.detach(myFragment).attach(myFragment).commitAllowingStateLoss();
                    */


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
                case 1:
                    sqLiteHelper.queryDAta("CREATE TABLE IF NOT EXISTS TAB2( id INTEGER PRIMARY KEY AUTOINCREMENT, image VARCHAR(500))");
                    try{
                        sqLiteHelper.insertData(img, "TAB2");

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    sqLiteHelper.queryDAta("CREATE TABLE IF NOT EXISTS TAB3( id INTEGER PRIMARY KEY AUTOINCREMENT, image VARCHAR(500))");
                    try{
                        sqLiteHelper.insertData(img, "TAB3");

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

            }
            //----------Refreshing Current Activity----------------
            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);



        }
    }

    private byte[] getBytes(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
