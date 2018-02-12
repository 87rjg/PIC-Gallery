package com.ram.rewindtask;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RAMJEE on 11-02-2018.
 */

//----------Recycler View Adapter class-----------

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

    private List<String> imageList;
    Context context;

    public MyAdapter(List<String> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_image_layout,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {



        //------------Picaso Library to setting image to image view of grid-----------
        Picasso.with(context).load(imageList.get(position)).resize(110, 100)
                .centerCrop().into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.single_image);
        }
    }
}


