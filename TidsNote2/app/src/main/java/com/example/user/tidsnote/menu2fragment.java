package com.example.user.tidsnote;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class menu2fragment extends Fragment {

    View view;
    BannerCard cardView;
    MaterialButton okay, filepicker, upload;
    View.OnClickListener onClickListener, onClickListener2;
    TextInputEditText textInputEditText;
    MaterialButton floatingActionButton;
    MenuInflater menuInflater;
    Menu mymenu;
    ArrayList<Student> list;
    StudentAdapter StudentAdapter =null;
    PhotoAdapter photoAdapter;
    Activity activity;
    Chip chip;
    ArrayList<String> menulist;
    ArrayList<String> urilist;
    ArrayList<Bitmap> photolist;
    ArrayList<Bitmap> addphotolist;
    ArrayList<String> monthArrayList;

    String mCurrentPhotoPath;
    final int REQUEST_TAKE_PHOTO = 1;
    final int PICTURE_REQUEST_CODE = 200;
    LinearLayout addbutton;
    private Spinner MonthSpinner;
    TextView file_path;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Log.i("menu2fragment","onCreateView");
        view = inflater.inflate(R.layout.menu2, container, false);
        cardView=(BannerCard) view.findViewById(R.id.common_content);
        okay=(MaterialButton)view.findViewById(R.id.common_content_ok);
        textInputEditText=(TextInputEditText)view.findViewById(R.id.common_editText);
        MonthSpinner=(Spinner)view.findViewById(R.id.MonthSpinner);
        file_path=(TextView)view.findViewById(R.id.file_path);
        filepicker=(MaterialButton)view.findViewById(R.id.filepicker);
        upload=(MaterialButton)view.findViewById(R.id.upload);

        addbutton = (LinearLayout) view.findViewById(R.id.addbutton);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbum();
            }
        });

        RecyclerView menu_recyclerView = (RecyclerView)view.findViewById(R.id.menu_recycler);
        final LinearLayoutManager menu_linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        menu_recyclerView.setLayoutManager(menu_linearLayoutManager);
        menu_recyclerView.setAdapter(new MealAdapter(menulist));


        RecyclerView photo_recycler = (RecyclerView)view.findViewById(R.id.addphoto);
        final LinearLayoutManager photo_linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        photo_recycler.setLayoutManager(photo_linearLayoutManager);
        addphotolist = new ArrayList<>();
        urilist = new ArrayList<>();













        //floatingActionButton=(MaterialButton)view.findViewById(R.id.send_all);
        menuInflater = ((MainActivity)getActivity()).getMenuInflater();
        mymenu=((MainActivity)getActivity()).mymenu;
        activity=((MainActivity)getActivity());




        DialogProperties properties=new DialogProperties();

        properties.selection_mode=DialogConfigs.SINGLE_MODE;
        properties.selection_type=DialogConfigs.FILE_SELECT;
        properties.root=new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir=new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir=new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions=null;

        final FilePickerDialog dialog=new FilePickerDialog(getContext(),properties);
        dialog.setTitle("Select File");

        filepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                file_path.setText(files[0]);
            }
        });




        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();
                cardHide();
                cardView.setWriting(false);


            }
        };

        onClickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String filepath;

                if(file_path.getText().toString().equals("")){
                    Toast.makeText(getContext(),"no file",Toast.LENGTH_LONG).show();
                }else {

                    filepath=file_path.getText().toString();
                    byte[] byte_check=convertPdfToByteArray(filepath);

                    for(int i=0;i<byte_check.length;i++){
                        System.out.println(byte_check[i]);
                    }
                    SimpleDateFormat s = new SimpleDateFormat("yyyy");
                    String curDate = s.format(new Date());
                    String pos;
                    int i=MonthSpinner.getSelectedItemPosition()+1;

                    if(i<10){
                        pos="0"+String.valueOf(i);
                    }else {pos=String.valueOf(i);  }

                    pos=curDate+pos;
//sent MenuFile to server
                    MenuFile newMenuFile=new MenuFile();

                    newMenuFile.setFileName(pos);
                    newMenuFile.setPdfFile(byte_check);
                    send_Data("upload_pdf",newMenuFile);




                }


            }
        };

        okay.setOnClickListener(onClickListener);
        upload.setOnClickListener(onClickListener2);

        MonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();

                SimpleDateFormat s = new SimpleDateFormat("yyyy");
                String curDate = s.format(new Date());
                String pos;
                if(position+1<10){
                    pos="0"+String.valueOf(position+1);
                }else {
                    pos=String.valueOf(position+1);
                }
                String check_month=curDate+pos;
                check_Data("upload_check",check_month);


                Log.i("get(0)",monthArrayList.get(0));
                if (monthArrayList.get(0).equals("1")) {
                    file_path.setText("업로드 완료");
                    filepicker.setClickable(false);
                    Toast.makeText(getContext(), "이미 업로드 되었습니다.", Toast.LENGTH_LONG).show();


                } else {
                    file_path.setText("");
                    filepicker.setClickable(true);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });























        RecyclerView recyclerView = view.findViewById(R.id.rcv);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager) ;
        StudentAdapter = new StudentAdapter(list);
        getPhoto(list);
        StudentAdapter.setActivity(activity);
        recyclerView.setAdapter(StudentAdapter);
        photoAdapter=new PhotoAdapter(addphotolist);
        photo_recycler.setAdapter(photoAdapter);



        ((MainActivity)getActivity()).mymenu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                cardView.setWriting(true);
                cardShow();

                return false;
            }
        });

        ((MainActivity)getActivity()).mymenu.getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //전송 버튼 누르면

                ((MainActivity)getActivity()).send_All(getComment(),urilist);


                return false;
            }
        });








        return view;
    }


    public void cardShow(){

        cardView.setVisibility(View.VISIBLE);
        Animation aniSlide = AnimationUtils.loadAnimation(view.getContext(),R.anim.slide_up);
        aniSlide.setDuration(1000);
        aniSlide.setFillAfter(true);
        cardView.startAnimation(aniSlide);
        //cardView.setFocusableInTouchMode(true);
        cardView.setClickable(true);
        //okay.setClickable(true);


    }

    public void cardHide(){

        Animation aniSlide = AnimationUtils.loadAnimation(view.getContext(),R.anim.slide_down);
        aniSlide.setDuration(1000);
        aniSlide.setFillAfter(true);
        cardView.startAnimation(aniSlide);
        cardView.setClickable(false);
        //cardView.setFocusable(false);
        //cardView.setFocusableInTouchMode(false);
        //textInputEditText.setFocusable(false);
        //okay.setClickable(false);

    }

    public int checkDone(ArrayList<Student> list){
        int count=0;
        for (int i=0;i<list.size();i++){

            //Log.i("STUDENT",list.get(i).name+" : "+Boolean.toString(list.get(i).getDone()));


        }

        return count;
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textInputEditText.getWindowToken(), 0);

    }
    public String getComment(){
            String str = textInputEditText.getText().toString();
            return str;
    }

    public byte[] getByteArrayFromDrawable(Drawable d){
        //이미지 저장하기

        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();

        return data;
    }

    public Bitmap getImage(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }

    public void addPhoto(Bitmap bitmap){

        //StudentAdapter.photos.add(bitmap);
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
    public void onResume() {
        super.onResume();
        if (photoAdapter!=null)
            photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        Bitmap bitmap = null;

        if (requestCode == PICTURE_REQUEST_CODE && resultCode == RESULT_OK ) {

            if (data.getData() == null && data.getClipData() != null){//여러장일 경우

                ClipData clipData = data.getClipData();

                for (int i=0; i<clipData.getItemCount();i++){
                    Uri uri = clipData.getItemAt(i).getUri();
                    urilist.add(uri.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int orientation = getOrientation(getActivity(),uri);
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


                    addphotolist.add(rotate);
                   // urilist.add(bitmapToByteArray(rotate));
                    photoAdapter.notifyDataSetChanged();





                }

            }else{//한 장일 경우

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = getOrientation(getActivity(),data.getData());
                urilist.add(data.getData().toString());
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


                addphotolist.add(rotate);

                //urilist.add(bitmapToByteArray(rotate));
                photoAdapter.notifyDataSetChanged();





            }

        }
    }

    public byte[] bitmapToByteArray( Bitmap bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }



    private String getRealPathFromURI(Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        return cursor.getString(column_index);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        int result = -1;
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                result = cursor.getInt(0);
            }
            cursor.close();
        }

        return result;
    }

    public void getPhoto(ArrayList<Student> students){

        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase("tidsnotedb",MODE_PRIVATE,null);

        byte[] result=null;
        String id;
        Cursor c;

        photolist=new ArrayList<>();

        for (int i=0;i<students.size();i++){

            id=students.get(i).getStudent_id();
            c = sqLiteDatabase.rawQuery("SELECT * FROM student_tbl where s_id = '"+id+"'", null);

            while(c.moveToNext()){

                result =c.getBlob(c.getColumnIndex("s_portrait"));
                photolist.add(getImage(result));

            }
        }
        StudentAdapter.setPhotos(photolist);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.user.tidsnote.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void check_Permission(){

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(getApplicationContext(),"Granted",Toast.LENGTH_SHORT).show();
                //startActivityForResult(camera_intent,REQUEST_TAKE_PHOTO);

                dispatchTakePictureIntent();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                //Toast.makeText(getApplicationContext(),"Denied",Toast.LENGTH_SHORT).show();

            }
        };

        new TedPermission(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

    }


    public void send_Data(String type, @Nullable Object obj){
        Log.i("실행순서","send_Data()");

        final Handler handler = new Handler(){

        };

        Client client = new Client(this,type);

        client.setData(obj);
        client.start();
        try{
            client.join();


            Log.i("Init","Finished");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static byte[] convertPdfToByteArray(String filePath) {

        File pdfFile=new File(filePath);
        FileInputStream fis=null;
        byte[] byteArray=new byte[(int)pdfFile.length()];
        try {
            fis = new FileInputStream(pdfFile);
            fis.read(byteArray);
            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //byteArray=pdfFile.toString().getBytes();

        return byteArray;

    }

    public void check_Data(String type, @Nullable String str){
        Log.i("실행순서","send_Data()");

        final Handler handler = new Handler(){

        };

        Client client = new Client(this,type);

        client.setData(str);
        client.start();
        try{
            client.join();


            Log.i("Init","Finished");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
