package com.example.youngeun.parentapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.ViewHolder>{

    messageData messageData;
    ArrayList<Bitmap> mData;
    View view;
    FragmentActivity fragmentActivity;
    LayoutInflater inflater;



    public PhotoViewAdapter(ArrayList<Bitmap> item){
        mData=item;
    }

    public PhotoViewAdapter(ArrayList<Bitmap> item, FragmentActivity fragmentactivity){
        mData=item;
        fragmentActivity=fragmentactivity;
    }

    public int getItemCount(){

        return this.mData.size();
    }
    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public PhotoViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        Context context=parent.getContext();
        LayoutInflater  inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.photoview,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);

        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull PhotoViewAdapter.ViewHolder viewHoler, int position){
        viewHoler.photo.setImageBitmap(mData.get(position));
    }



    class ViewHolder extends RecyclerView.ViewHolder{


        ImageView photo;
        ViewHolder(final View itemView){
            super(itemView);
            photo = (ImageView)itemView.findViewById(R.id.show);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    try {

                    }catch (Exception e){

                    }

                }
            });
        }


    }

}
