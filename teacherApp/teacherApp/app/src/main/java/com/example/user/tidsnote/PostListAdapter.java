package com.example.user.tidsnote;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> implements Serializable {

    LayoutInflater inflater=null;
    ArrayList<Notice> notices;
    View view;
    String t_id = null;
    FragmentActivity fragmentActivity;

    public void addItem(Notice item){
        notices.add(item);
    }

    public PostListAdapter(ArrayList<Notice> data) {

        //this.postdata = postdata;

        notices=data;


    }

    public void SetTeacher(String id){
        this.t_id=id;
    }


    public PostListAdapter(ArrayList<Notice> data, FragmentActivity fragmentActivity) {

        //this.postdata = postdata;

        notices=data;
        fragmentActivity=fragmentActivity;

    }

    public int getItemCount() {
       return this.notices.size();
    }

    public Notice getItem(int position) {
        return notices.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.Binding(notices.get(position));

        Log.i("onBindViewHolder","");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        view = inflater.inflate(R.layout.notice_list_more, parent, false) ;
        PostListAdapter.ViewHolder vh = new PostListAdapter.ViewHolder(view) ;



        return vh;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView postTitle,postDate,postWriter;
        ViewHolder(final View itemView){
            super(itemView);

            postTitle=(TextView)itemView.findViewById(R.id.notice_content);
            postDate=(TextView)itemView.findViewById(R.id.check_time);
            postWriter=(TextView)itemView.findViewById(R.id.writer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n_id=0;
                    n_id = notices.get(getAdapterPosition()).rowid;

                    Intent intent = new Intent(v.getContext(),postDetail.class);
                    intent.putExtra("title",postTitle.getText().toString());
                    intent.putExtra("writer",postWriter.getText().toString());
                    intent.putExtra("content",notices.get(getAdapterPosition()).getContent());
                    intent.putExtra("date",postDate.getText().toString());
                    intent.putExtra("n_id",n_id);

                    v.getContext().startActivity(intent);

                }
            });






        }

        public void Binding(Notice data){

            if (t_id == null) {
                postTitle.setText(data.getTitle());
                postWriter.setText("[" + data.getWriter() + "]");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd (E) hh:mm");
                postDate.setText(simpleDateFormat.format(data.getDate()));
            }else {

                if ((data.getT_id()).equals(t_id)){
                    Log.i("My article",t_id+", "+data.getT_id());

                    postTitle.setText(data.getTitle());
                    postWriter.setText("[" + data.getWriter() + "]");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd (E) hh:mm");
                    postDate.setText(simpleDateFormat.format(data.getDate()));

                }else {

                }






            }

        }


    }

























    }
