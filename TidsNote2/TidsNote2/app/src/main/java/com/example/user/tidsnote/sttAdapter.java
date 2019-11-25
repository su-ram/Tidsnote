package com.example.user.tidsnote;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

public class sttAdapter extends RecyclerView.Adapter<sttAdapter.ViewHolder> {


    private ArrayList<stt_msg> sttlist= null;
    private String name;
    View view;

    public sttAdapter(ArrayList<stt_msg> item){
        this.sttlist=item;
    }

    @Override
    public void onBindViewHolder(sttAdapter.ViewHolder holder, int position) {

        holder.Binding(sttlist.get(position));
    }

    @Override
    public sttAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        view = inflater.inflate(R.layout.stt_list, parent, false) ;
        sttAdapter.ViewHolder vh = new sttAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public int getItemCount() {
        return sttlist.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView memo,message;
        CardView cardView;
        Animation animationDown,animationUp;
        ImageButton viewmore, viewmore2;
        TextView date;
        boolean selected1=false,selected2=false;

        ViewHolder(View itemView) {
            super(itemView);

            date=(TextView)itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.stt_item_bg);
                    //linearLayout.setBackgroundColor(Color.WHITE);


                }
            });
            cardView=(CardView)itemView.findViewById(R.id.collapsing_card);
            viewmore=(ImageButton)itemView.findViewById(R.id.viewmore);
            viewmore2=(ImageButton)itemView.findViewById(R.id.viewmore2);
            TransitionManager.beginDelayedTransition(cardView);
            memo=itemView.findViewById(R.id.content);
            memo.setVisibility(View.GONE);


            animationDown = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(),R.anim.slide_down);
            animationUp=AnimationUtils.loadAnimation(view.getContext().getApplicationContext(),R.anim.slide_up);


            itemView.findViewById(R.id.viewmore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //음성녹음 더보기 플러스 버튼
                    if (memo.isShown()){
                        memo.setVisibility(View.GONE);
                        viewmore.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);

                    }else{

                        memo.setVisibility(View.VISIBLE);
                        viewmore.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp);
                        //memo.startAnimation(animationDown);
                    }

                    if (sttlist.get(getAdapterPosition()).msg_selected)
                        date.setBackgroundColor(Color.parseColor("#efefef"));

                    sttlist.get(getAdapterPosition()).stt_selected=true;




                }
            });




            message=itemView.findViewById(R.id.content2);
            message.setVisibility(View.GONE);

            itemView.findViewById(R.id.viewmore2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //학부모 메세지 더보기 플러스
                    if (message.isShown()){
                        message.setVisibility(View.GONE);
                        viewmore2.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                        //message.startAnimation(animationUp);
                    }else{
                        message.setVisibility(View.VISIBLE);
                        viewmore2.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp);
                        //message.startAnimation(animationDown);
                    }

                    if (sttlist.get(getAdapterPosition()).stt_selected)
                        date.setBackgroundColor(Color.parseColor("#efefef"));

                    sttlist.get(getAdapterPosition()).msg_selected=true;






                }
            });

        }

        public void Binding(stt_msg item){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd \n(E)");

            date.setText(simpleDateFormat.format(item.getDate()));
            memo.setText(item.getMsg()) ;
            message.setText(item.getMsg());

            if (item.msg_selected && item.stt_selected)
                date.setBackgroundColor(Color.parseColor("#efefef"));
            else
                date.setBackgroundColor(Color.parseColor("#FAF0E6"));



        }

    }
}
