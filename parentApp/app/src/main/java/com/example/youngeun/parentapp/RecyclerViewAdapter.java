package com.example.youngeun.parentapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Serializable {

    messageData messageData;
    ArrayList<messageData> mData;
    View view;
    FragmentActivity fragmentActivity;
    LayoutInflater inflater;
    public void addItem(messageData item){

        mData.add(item);
    }



    public RecyclerViewAdapter(ArrayList<messageData> item){
        mData=item;
    }

    public RecyclerViewAdapter(ArrayList<messageData> item, FragmentActivity fragmentactivity){
        mData=item;
        fragmentActivity=fragmentactivity;
    }

    public int getItemCount(){

        return this.mData.size();
    }
    public messageData getItem(int position){
        return mData.get(position);
    }
    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        Context context=parent.getContext();
        LayoutInflater  inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.mitem_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);

        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHoler,int position){

        messageData =mData.get(position);

            viewHoler.mdate.setText(messageData.getMdate());
            viewHoler.message.setText(messageData.getMessage());
            viewHoler.request.setText(messageData.getRequest());


    }



    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mdate,message,request;
        CardView cardView;
        LinearLayout linearLayout;

        ViewHolder(final View itemView){
            super(itemView);

            cardView=itemView.findViewById(R.id.card_view);
            mdate=itemView.findViewById(R.id.messageDate);
            message=itemView.findViewById(R.id.messageContent);
            request=itemView.findViewById(R.id.requestContent);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent=new Intent(v.getContext(),mDetail.class);

                    intent.putExtra("comment",message.getText().toString());
                    intent.putExtra("request",request.getText().toString());
                    intent.putExtra("date",mdate.getText().toString());

                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
