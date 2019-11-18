package com.example.user.tidsnote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> implements Serializable {


    SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);


    private ArrayList<Student> studentlist = null;
    ArrayList<Bitmap> photos;
    Student Student;
    MainActivity activity;
    boolean state;
    int mode = 0;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public StudentAdapter(ArrayList<Student> item) {
        this.studentlist = item;
    }

    public StudentAdapter(ArrayList<Student> item, int mode) {
        this.studentlist = item;
        this.mode = mode;
    }

    public void setPhotos(ArrayList<Bitmap> photos) {
        this.photos = photos;

    }
    public void addPhotos(Bitmap bitmap){
        this.photos.add(bitmap);
    }

    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {


        if (mode == 1) {
            holder.binding(studentlist.get(position), mode);
        } else {
            Student = studentlist.get(position);
            holder.binding(Student, mode);

        }
    }

    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.itemlist, parent, false);

        if (mode == 1) {
            view = inflater.inflate(R.layout.student_setting, parent, false);

        }
        StudentAdapter.ViewHolder vh = new StudentAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public int getItemCount() {
        return studentlist.size();
    }

    public void setActivity(Activity activity) {
        this.activity = (MainActivity) activity;
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        TextView name, student_name,writing;
        ImageView image;
        LinearLayout linearLayout;
        ImageView done;

        ViewHolder(final View itemView) {
            super(itemView);

            if (mode == 1) {

                student_name = (TextView) itemView.findViewById(R.id.student_name);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(v.getContext(),StudentDetail.class);
                        intent.putExtra("Student",studentlist.get(getAdapterPosition()));
                        v.getContext().startActivity(intent);

                    }
                });


            } else {
                name = (TextView) itemView.findViewById(R.id.name);
                image = (ImageView) itemView.findViewById(R.id.imageview);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.active);
                done = (ImageView) itemView.findViewById(R.id.done_check);
                writing=(TextView)itemView.findViewById(R.id.writing);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //아이템 클릭했을 경우

                        int position = getAdapterPosition();
                        studentlist.get(position).setPos(position);
                        Intent intent = new Intent(v.getContext(), InputForm.class);
                        intent.putExtra("Student", studentlist.get(position));
                        intent.putExtra("position", Integer.toString(position));
                        activity.startActivityForResult(intent, 000);

                    }
                });

            }
        }

        public byte[] getByteArrayFromBitmap(Bitmap bitmap){
            //이미지 저장하기

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] data = stream.toByteArray();

            return data;
        }
        public void binding(Student Student, int mode) {

            if (mode == 1) {

                student_name.setText(Student.getStudent_name());

            } else {
                image.setImageBitmap(photos.get(Student.getPos()));

                Log.i("Photo Binding",Integer.toString(studentlist.size())+", "+Integer.toString(photos.size()));


                if (studentlist.size() > photos.size()){


                }


                name.setText(Student.getStudent_name());
                name.setContentDescription(Student.student_id);

                if (Student.isDone()) {
//작성완료
                    linearLayout.setAlpha(0.4f);
                    done.setVisibility(View.VISIBLE);
                    writing.setVisibility(View.GONE);
                } else {

                    //아예 미입력 , 작성중
                    if (Student.isWriting()){
                        //작성중
                        linearLayout.setAlpha(0);
                        done.setVisibility(View.GONE);
                        writing.setVisibility(View.VISIBLE);
                    }else{
                        //아예 미입력
                        linearLayout.setAlpha(0);
                        done.setVisibility(View.GONE);
                        writing.setVisibility(View.GONE);
                    }

                }
            }


        }

    }
}