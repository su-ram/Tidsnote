package com.example.youngeun.parentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhotoDetial extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView pos,sum;
    FloatingActionButton fab;
    String mCurrentPhotoPath;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detial);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        recyclerView = (RecyclerView)findViewById(R.id.photoview);
        pos = (TextView)findViewById(R.id.pos);
        sum = (TextView)findViewById(R.id.sum);



        ArrayList<String> path = getIntent().getStringArrayListExtra("photopath");
        Bitmap bitmap = null;
        File imgFile;

        sum.setText(String.valueOf(path.size()));

        for (int i=0; i<path.size(); i++){
            imgFile = new File(path.get(i));
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            bitmaps.add(bitmap);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        final LinearSnapHelper snaphelper = new LinearSnapHelper();
        snaphelper.attachToRecyclerView(recyclerView);
        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter(bitmaps);
        recyclerView.setAdapter(photoViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                View snapview = snaphelper.findSnapView(recyclerView.getLayoutManager());
                final int position = recyclerView.getLayoutManager().getPosition(snapview) + 1;
                pos.setText(Integer.toString(position));
                super.onScrollStateChanged(recyclerView, newState);

           }
        });
        int curr = getIntent().getIntExtra("curr",0);
        recyclerView.scrollToPosition(curr);




    }
    public Bitmap getImage(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i("PhotoPath",mCurrentPhotoPath);
        return image;
    }
    public void SavePhoto(View v){
        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
        int index = Integer.parseInt(pos.getText().toString())-1;
        Bitmap b = bitmaps.get(index);
        FileOutputStream stream;


        try{
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.youngeun.parentapp",
                        photoFile);
                stream = new FileOutputStream(photoFile);
                b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                MediaStore.Images.Media.insertImage(getContentResolver(), b, photoFile.getName() , "");
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                mediaScanIntent.setData(photoURI);

                this.sendBroadcast(mediaScanIntent);

            }


        }catch(Exception e){

            }

        }
    }

