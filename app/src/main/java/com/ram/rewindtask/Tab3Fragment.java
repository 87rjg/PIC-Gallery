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
public class Tab3Fragment extends Fragment {


    ArrayList<String> imageList = new ArrayList<>();
    RecyclerView recyclerViewTab3;
    GridLayoutManager gridLayoutManager ;
    MyAdapter myAdapter;

    public static SQLiteHelper sqLiteHelper;
    public Tab3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        sqLiteHelper = new SQLiteHelper(container.getContext(),"PICKDB.sqlite", null,1);

        sqLiteHelper.queryDAta("CREATE TABLE IF NOT EXISTS TAB3( id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOB )");


        recyclerViewTab3 = view.findViewById(R.id.tab1_recyclerViewTab3);

        gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerViewTab3.setHasFixedSize(true);
        recyclerViewTab3.setLayoutManager(gridLayoutManager);

        myAdapter = new MyAdapter(imageList,getContext());
        recyclerViewTab3.setAdapter(myAdapter);


        //----------to get image from database -------------

        Cursor cursor = sqLiteHelper.getData("SELECT * FROM TAB3");
        imageList.clear();
        while(cursor.moveToNext()){

            int id = cursor.getInt(0);
            String img = cursor.getString(1);
            imageList.add(img);
            myAdapter.notifyDataSetChanged();
        }





        recyclerViewTab3.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerViewTab3 ,new RecyclerItemClickListener.OnItemClickListener() {
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
