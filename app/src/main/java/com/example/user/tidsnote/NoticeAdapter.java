package com.example.user.tidsnote;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {


    private ArrayList<Notice> Noticelist = null;
    View view;
    int mode=0;

    public NoticeAdapter(ArrayList<Notice> item,int mode){
        this.Noticelist =item;
        this.mode=mode;
    }

    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {

        Notice name = Noticelist.get(position);

        if (mode == 0) {


            holder.writer.setText(name.getWriter()+" 선생님");
            holder.notice_content.setText("\" "+name.getContent()+" \"");
            SimpleDateFormat s = new SimpleDateFormat("MM/dd");
            String curDate = s.format(name.getDate());
            holder.date.setText(curDate);


        }else{

            if (position == 0 )
                holder.bookmark.setVisibility(View.VISIBLE);
            holder.notice_content.setText(name.getContent());
            holder.writer.setText("["+name.getWriter()+"]");
           // holder.num.setText(Integer.toString(position+1));


        }

    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        if (mode ==0)
            view = inflater.inflate(R.layout.notice_list, parent, false) ;
        else
            view = inflater.inflate(R.layout.notice_list_more, parent, false) ;
        NoticeAdapter.ViewHolder vh = new NoticeAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public int getItemCount() {
        return Noticelist.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView notice_content, writer,num,date;
        RelativeLayout relativeLayout;
        ImageView bookmark;

        ViewHolder(final View itemView) {
            super(itemView);

            notice_content = (TextView) itemView.findViewById(R.id.notice_content);
            writer = (TextView) itemView.findViewById(R.id.writer);
            date=(TextView)itemView.findViewById(R.id.notice_date);

            if (mode == 0) {

                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.cover_notice);
                //relativeLayout.setAlpha((float) 0.3);
            }else{
                bookmark=(ImageView)itemView.findViewById(R.id.bookmark);
                //num=(TextView)itemView.findViewById(R.id.num_notice);


            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }



    }
}
