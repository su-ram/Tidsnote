package com.example.user.tidsnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StdListAdapter extends BaseAdapter {

    LayoutInflater inflater=null;
    Context context;
    ArrayList<studentData> studentdata=null;
    private int stdlistCount=0;

    public StdListAdapter(studentTab studentTab, ArrayList<studentData> data){
        studentdata=data;
        stdlistCount=studentdata.size();
    }

    @Override
    public int getCount() {
        return this.studentdata.size();
    }

    @Override
    public Object getItem(int position) {
        return studentdata.get(position);
    }

    public StdListAdapter(LayoutInflater inflater, Context context, ArrayList<studentData> studentdata, int stdlistCount) {
        this.inflater = inflater;
        this.context = context;
        this.studentdata = studentdata;
        this.stdlistCount = stdlistCount;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            final Context context=parent.getContext();
            if(inflater==null){
                inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView=inflater.inflate(R.layout.stdlist_item,parent,false);
        }

        TextView stdName=(TextView)convertView.findViewById(R.id.stdName);
        TextView stdClass=(TextView)convertView.findViewById(R.id.stdClass);

        stdName.setText(studentdata.get(position).stdName);
        stdClass.setText(studentdata.get(position).stdClass);

        return convertView;
    }
}
