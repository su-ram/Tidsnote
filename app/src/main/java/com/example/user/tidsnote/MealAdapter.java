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

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {


    private ArrayList<String> menulist = null;
    View view;
    int mode=0;

    public MealAdapter(ArrayList<String> item){
        this.menulist =item;

    }

    @Override
    public void onBindViewHolder(MealAdapter.ViewHolder holder, int position) {

        String menu = menulist.get(position);
        if (menu != null) {
            holder.menuname.setText(menu);
        }else{
            holder.menuname.setText("오늘은 식단이 없습니다.");
        }
    }

    @Override
    public MealAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        view = inflater.inflate(R.layout.meal_menu, parent, false) ;
        MealAdapter.ViewHolder vh = new MealAdapter.ViewHolder(view) ;

        return vh;
    }

    @Override
    public int getItemCount() {
        return menulist.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView menuname;

        ViewHolder(final View itemView) {
            super(itemView);

            menuname=(TextView)itemView.findViewById(R.id.meal_menu);




        }



    }
}
