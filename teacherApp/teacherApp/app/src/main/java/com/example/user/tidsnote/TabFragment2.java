package com.example.user.tidsnote;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;
import static com.example.user.tidsnote.menu2fragment.getOrientation;
import static com.example.user.tidsnote.menu2fragment.rotateImage;

public class TabFragment2 extends Fragment {


    View view;
    HashMap<String, String> map = new HashMap<String, String>();
    RadioButton radioButton;
    String status;
    ImageButton reset;
    int[] prev_status;
    RecyclerView recyclerView;
    ArrayList<Bitmap> photos;
    PhotoAdapter photoAdapter;
    ScrollView scrollView;
    ImageButton album;
    final int PICTURE_REQUEST_CODE = 200;
    ArrayList<String>  uris = new ArrayList<>();

    @Override
    public void setArguments(@Nullable Bundle args) {
        //super.setArguments(args);

        this.prev_status=args.getIntArray("status");


    }


    public void getAlbum(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        //사진을 여러개 선택할수 있도록 한다
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),  PICTURE_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if (requestCode == PICTURE_REQUEST_CODE && resultCode == RESULT_OK ) {

            if (data.getData() == null && data.getClipData() != null){//여러장일 경우

                ClipData clipData = data.getClipData();

                for (int i=0; i<clipData.getItemCount();i++){
                    Uri uri = clipData.getItemAt(i).getUri();
                    uris.add(uri.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int orientation = getOrientation(getContext(),uri);
                    Log.i("Orientation",String.valueOf(orientation));
                    Bitmap rotate;
                    switch(orientation) {

                        case 90:
                            rotate = rotateImage(bitmap, 90);
                            break;

                        case 180:
                            rotate = rotateImage(bitmap, 180);
                            break;

                        case 270:
                            rotate = rotateImage(bitmap, 270);
                            Log.i("Rotate","Start Rotate");
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotate = bitmap;
                    }



                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    rotate.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    photos.add(rotate);
                    //urilist.add(bytes);
                    photoAdapter.notifyDataSetChanged();





                }

            }else {//한 장일 경우

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    uris.add(data.getData().toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = getOrientation(getContext(), data.getData());
                Log.i("Orientation", String.valueOf(orientation));
                Bitmap rotate;
                switch (orientation) {

                    case 90:
                        rotate = rotateImage(bitmap, 90);
                        break;

                    case 180:
                        rotate = rotateImage(bitmap, 180);
                        break;

                    case 270:
                        rotate = rotateImage(bitmap, 270);
                        Log.i("Rotate", "Start Rotate");
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotate = bitmap;
                }



                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                rotate.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();

                photos.add(rotate);
                //urilist.add(bytes);
                photoAdapter.notifyDataSetChanged();


            }
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        setRadioButton();
        saveRadioButton(prev_status);
        reset = (ImageButton)view.findViewById(R.id.reset);
        album = (ImageButton)view.findViewById(R.id.album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbum();
            }
        });


        recyclerView = (RecyclerView)view.findViewById(R.id.stduent_photo_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setNestedScrollingEnabled(false);
        photos = new ArrayList<>();


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        photoAdapter = new PhotoAdapter(photos);
        photoAdapter.setLength(size.x);
        photoAdapter.setType(1);
        recyclerView.setAdapter(photoAdapter);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset();

            }
        });




        return view;
    }


    public void reset(){
        radioButton = (RadioButton)view.findViewById(R.id.health_first);
        radioButton.setChecked(false);

        radioButton = (RadioButton)view.findViewById(R.id.health_second);
        radioButton.setChecked(false);

        radioButton = (RadioButton)view.findViewById(R.id.health_third);
        radioButton.setChecked(false);

        radioButton = (RadioButton)view.findViewById(R.id.meal_first);
        radioButton.setChecked(false);

        radioButton = (RadioButton)view.findViewById(R.id.meal_second);
        radioButton.setChecked(false);

        radioButton = (RadioButton)view.findViewById(R.id.meal_third);
        radioButton.setChecked(false);

        radioButton = (RadioButton)view.findViewById(R.id.feeling_first);
        radioButton.setChecked(false);

        radioButton = (RadioButton)view.findViewById(R.id.feeling_second);
        radioButton.setChecked(false);

        radioButton = (RadioButton)view.findViewById(R.id.feeling_third);
        radioButton.setChecked(false);

        map.put("health","-1");
        map.put("meal","-1");
        map.put("feeling","-1");

    }
    public HashMap<String, String> getStatus(){

       return map;


    }

    public ArrayList<String> getPhotolist(){
        return uris;
    }


    public void setRadioButton(){

        map.put("health","-1");
        map.put("meal","-1");
        map.put("feeling","-1");

        ((RadioGroup)view.findViewById(R.id.health)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton)view.findViewById(checkedId);
                status = radioButton.getContentDescription().toString();
                map.put("health",status);

            }
        });

        ((RadioGroup)view.findViewById(R.id.meal)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton)view.findViewById(checkedId);
                status = radioButton.getContentDescription().toString();
                map.put("meal",status);
            }
        });

        ((RadioGroup)view.findViewById(R.id.feeling)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton)view.findViewById(checkedId);
                status = radioButton.getContentDescription().toString();
                map.put("feeling",status);
            }
        });


    }

    public void saveRadioButton(int[] status){

        //건강
        setHealth(status[0]);

        //식사
        setMeal(status[1]);

        //기분
        setFeeling(status[2]);


    }

    public void setHealth(int val){


        switch (val){
            case 0 :
                //좋음
                radioButton=(RadioButton)view.findViewById(R.id.health_first);
                radioButton.setChecked(true);
                break;
            case 1 :
                radioButton=(RadioButton)view.findViewById(R.id.health_second);
                radioButton.setChecked(true);
                break;
            case 2:
                radioButton=(RadioButton)view.findViewById(R.id.health_third);
                radioButton.setChecked(true);

                default:
                    break;



        }


    }

    public void setMeal(int val){

        switch (val){
            case 0 :
                //좋음
                radioButton=(RadioButton) view.findViewById(R.id.meal_first);
                radioButton.setChecked(true);
                break;
            case 1 :
                radioButton=(RadioButton) view.findViewById(R.id.meal_second);
                radioButton.setChecked(true);
                break;
            case 2:
                radioButton=(RadioButton) view.findViewById(R.id.meal_third);
                radioButton.setChecked(true);

            default:
                break;



        }
    }

    public void setFeeling(int val){

        switch (val){
            case 0 :
                //좋음
                radioButton=(RadioButton)view.findViewById(R.id.feeling_first);
                radioButton.setChecked(true);
                break;
            case 1 :
                radioButton=(RadioButton)view.findViewById(R.id.feeling_second);
                radioButton.setChecked(true);
                break;
            case 2:
                radioButton=(RadioButton)view.findViewById(R.id.feeling_third);
                radioButton.setChecked(true);

            default:
                break;



        }

    }
}
