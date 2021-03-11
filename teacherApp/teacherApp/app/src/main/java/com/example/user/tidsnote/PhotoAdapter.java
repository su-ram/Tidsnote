package com.example.user.tidsnote;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {


    private ArrayList<Bitmap> photolist = null;
    View view;
    int mode=0, len=0;

    public PhotoAdapter(ArrayList<Bitmap> item){
        this.photolist =item;

    }
    public void setType(int type){
        this.mode=type;
    }

    public void setLength(int len){
        this.len=(int)len/3;
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {
        holder.Binding(photolist.get(position));
        Log.i("Bind","onBindViewHolder");

    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        PhotoAdapter.ViewHolder vh;

        if (mode == 0 ) {
            view = inflater.inflate(R.layout.addphotolist, parent, false);
            vh = new PhotoAdapter.ViewHolder(view);
        }else{
            view = inflater.inflate(R.layout.studentphotolists, parent, false);
            vh = new PhotoAdapter.ViewHolder(view);
            LinearLayout layout = (LinearLayout)view.findViewById(R.id.showImage);
// Gets the layout params that will allow you to resize the layout
            ViewGroup.LayoutParams params = layout.getLayoutParams();


// Changes the height and width to the specified *pixels*
            params.height = len;
            params.width = len;
            //layout.setLayoutParams(params);
        }
        return vh;
    }

    @Override
    public int getItemCount() {
        return photolist.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        LinearLayout linearLayout;

        ViewHolder(final View itemView) {
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.photolist);

            if (mode == 0 ) {
            }else{


            }

        }

        public void Binding(Bitmap bitmap){
            photo.setImageBitmap(bitmap);


        }



    }
}
