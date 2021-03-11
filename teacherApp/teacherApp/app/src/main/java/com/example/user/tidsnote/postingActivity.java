package com.example.user.tidsnote;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.user.tidsnote.Message2.message;
import static com.example.user.tidsnote.menu2fragment.getOrientation;
import static com.example.user.tidsnote.menu2fragment.rotateImage;


public class postingActivity extends AppCompatActivity {

    TextInputEditText notice_input,title_input;
    TextInputLayout textInputLayout;
    TextView date_input, writer;
    postDBAdapter helper;
    postData postdata;
    ListView postListView;
    PostListAdapter adapter;
    ArrayList<postData> postItems;
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;
    ArrayList<Bitmap> photos;
    LinearLayout addphoto;
    String mCurrentPhotoPath;
    final int REQUEST_TAKE_PHOTO = 1;
    final int PICTURE_REQUEST_CODE = 200;
    ArrayList<byte[]> urilist;
    ArrayList<String>  uris = new ArrayList<>();

    public String setDate_input() {

        SimpleDateFormat s=new SimpleDateFormat("yyyy/dd/MM hh:mm");
        String curDate=s.format(new Date());
        return curDate;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if (requestCode == PICTURE_REQUEST_CODE && resultCode == RESULT_OK ) {

            if (data.getData() == null && data.getClipData() != null){//여러장일 경우

                ClipData clipData = data.getClipData();

                for (int i=0; i<clipData.getItemCount();i++){
                    Uri uri = clipData.getItemAt(i).getUri();
                    uris.add(uri.toString());
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int orientation = getOrientation(this,uri);
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
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    uris.add(data.getData().toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = getOrientation(this, data.getData());
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
            }}

    public byte[] bitmapToByteArray(Bitmap bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        title_input=(TextInputEditText) findViewById(R.id.title_input);
        notice_input=(TextInputEditText) findViewById(R.id.notice_input);
        date_input=(TextView)findViewById(R.id.date_input);
        writer = (TextView)findViewById(R.id.wrtier);
        date_input.setText(setDate_input());
        writer.setText("작성자 | "+((MainActivity)MainActivity.context).t_name);
        //writer.setText(((MainActivity)MainActivity.context).t_name);

        helper = new postDBAdapter(this);
        addphoto = (LinearLayout)findViewById(R.id.add_notice_photo);
        urilist = new ArrayList<>();
        photos = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.notice_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        photoAdapter = new PhotoAdapter(photos);
        recyclerView.setAdapter(photoAdapter);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MenuItem menuItem = item;
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작


                onBackPressed();


                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void addphoto(View v){
        getAlbum();
    }
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

    }

    public void NoticeContent(View v) {


        String title_in = title_input.getText().toString();
        String notice_in = notice_input.getText().toString();
        String date_in=date_input.getText().toString();


        if(title_in.isEmpty()||notice_in.isEmpty()){
            message(getApplicationContext(),"정보를 입력해주세요.");

        }else{
            long id=helper.insertData(title_in,notice_in,date_in);
            if(id<=0){
                message(getApplicationContext(),"에러발생");
                title_input.setText("");
                notice_input.setText("");

            }else{
                Notice newNotice = (Notice)getIntent().getSerializableExtra("notice");
                Date curdate = new Date();

                newNotice.setTitle(title_in);
                newNotice.setContent(notice_in);

                newNotice.setDate(curdate);
                newNotice.setT_id(((MainActivity)MainActivity.context).t_id);
                newNotice.setWriter(((MainActivity)MainActivity.context).t_name);
                //newNotice.setPhotos(urilist);


                Intent intent = new Intent();
                intent.putExtra("notice",newNotice);
                intent.putExtra("uri",uris);
                setResult(RESULT_OK,intent);
                //title_input.setText("");
                //notice_input.setText("");
                //((MainActivity)MainActivity.context).send_Data("add_notice");//서버에 새로운 공지사항 추가

                finish();



            }
        }

    }
}
