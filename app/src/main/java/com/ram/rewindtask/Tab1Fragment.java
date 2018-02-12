package com.ram.rewindtask;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {

    ArrayList<String> imageList = new ArrayList<>();
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager ;
    MyAdapter myAdapter;
    private int PICK_IMAGE_REQUEST = 1;

    public static SQLiteHelper sqLiteHelper;


    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        sqLiteHelper = new SQLiteHelper(container.getContext(),"PICKDB.sqlite", null,1);

        sqLiteHelper.queryDAta("CREATE TABLE IF NOT EXISTS TAB1( id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOB )");

        recyclerView = view.findViewById(R.id.tab1_recyclerView);

        gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        myAdapter = new MyAdapter(imageList,getContext());
        recyclerView.setAdapter(myAdapter);


        //----------to get image from database -------------

        Cursor cursor = sqLiteHelper.getData("SELECT * FROM TAB1");
        imageList.clear();
        while(cursor.moveToNext()){

            int id = cursor.getInt(0);
            String img = cursor.getString(1);
            imageList.add(img);
            myAdapter.notifyDataSetChanged();
        }


        //------------Click listener for RecyclerView Item---------
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent viewImage = new Intent(getContext(), ViewImageActivity.class);

                        String uri = imageList.get(position);
                        viewImage.putExtra("image", uri);
                        startActivity(viewImage);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return view;
    }





}
