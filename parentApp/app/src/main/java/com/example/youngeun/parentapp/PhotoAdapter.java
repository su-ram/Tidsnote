package com.example.youngeun.parentapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

    messageData messageData;
    ArrayList<Bitmap> mData;
    View view;
    FragmentActivity fragmentActivity;
    LayoutInflater inflater;



    public PhotoAdapter(ArrayList<Bitmap> item){
        mData=item;
    }

    public PhotoAdapter(ArrayList<Bitmap> item, FragmentActivity fragmentactivity){
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
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        Context context=parent.getContext();
        LayoutInflater  inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.photoitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);

        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder viewHoler, int position){
        viewHoler.photo.setImageBitmap(mData.get(position));
    }



    class ViewHolder extends RecyclerView.ViewHolder{


        ImageView photo;
        ViewHolder(final View itemView){
            super(itemView);
            photo = (ImageView)itemView.findViewById(R.id.photolist);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    try {
                        Intent intent = new Intent(v.getContext(), PhotoDetial.class);
                        FileOutputStream stream;
                        Bitmap b;
                        ArrayList<String> urilist = new ArrayList<>();
                        File photo;


                        for (int i=0; i<mData.size();i++){
                            b=mData.get(i);
                            photo = File.createTempFile("JPEG_", ".jpg", v.getContext().getCacheDir());
                            stream = new FileOutputStream(photo);
                            b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            urilist.add(photo.getAbsolutePath());

                        }
                        intent.putExtra("photopath",urilist);
                        intent.putExtra("curr",getAdapterPosition());
                        v.getContext().startActivity(intent);
                    }catch (Exception e){

                    }

                }
            });
        }

        public Bitmap getImage(byte[] b){
            Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
            return bitmap;
        }
    }

}
