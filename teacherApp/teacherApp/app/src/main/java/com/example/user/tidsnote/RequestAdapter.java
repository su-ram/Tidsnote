package com.example.user.tidsnote;

import android.animation.ObjectAnimator;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {


    private ArrayList<Request> requestslist= null;
    View view;
    int mode=0;

    public RequestAdapter(ArrayList<Request> item, int mode){

        this.requestslist=item;
        this.mode=mode;
    }

    @Override
    public void onBindViewHolder(RequestAdapter.ViewHolder holder, int position) {

        Request request = requestslist.get(position);

        if (mode ==0 ) {

            holder.Binding(request,0);

        }else{

            //holder.request_more.setText(name.getContent());
            holder.num.setText(Integer.toString(position+1));
        }


    }

    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        if (mode==0){
            view = inflater.inflate(R.layout.request_list, parent, false) ;
        }else{
            view = inflater.inflate(R.layout.request_list_more, parent, false) ;
        }

        RequestAdapter.ViewHolder vh = new RequestAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public int getItemCount() {
        return requestslist.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView sname,pname,content,request_more,num;
        RelativeLayout relativeLayout;
        CheckBox checkBox;
        ImageView photo;


        ViewHolder(final View itemView) {
            super(itemView);


            if (mode == 0) {
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.cover);
                photo=(ImageView)itemView.findViewById(R.id.photo_request);
                checkBox = (CheckBox) itemView.findViewById(R.id.check);

                checkBox.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Client c = new Client("push");
                        c.setData(requestslist.get(getAdapterPosition()).getRequest_id());
                        c.start();


                        requestslist.get(getAdapterPosition()).setChecked(true);
                        relativeLayout.setAlpha((float)0.2);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relativeLayout, "alpha", 0.3f);
                        objectAnimator.setDuration(1500);
                        objectAnimator.start();
                        checkBox.setChecked(true);

/*
                        if (!checkBox.isChecked()) {
                            relativeLayout.setAlpha((float) 0);
                            //Toast.makeText(view.getContext(), ((TextView) itemView.findViewById(R.id.sname)).getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            relativeLayout.setAlpha((float)0.2);
                            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relativeLayout, "alpha", 0.3f);
                            objectAnimator.setDuration(1000);
                            objectAnimator.start();
                        }
*/


                    }
                });
                sname = (TextView) itemView.findViewById(R.id.sname);
                pname = (TextView) itemView.findViewById(R.id.pname);
                content = (TextView) itemView.findViewById(R.id.content);
                num=(TextView)itemView.findViewById(R.id.num);
                content.setMovementMethod(new ScrollingMovementMethod());
            }else{

                request_more = (TextView)itemView.findViewById(R.id.request_more_content);
                num=(TextView)itemView.findViewById(R.id.num);
            }
        }

        public Bitmap getImage(byte[] b){
            Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
            return bitmap;
        }

        public void Binding(Request request,  int mode){

            if (mode ==0){


                photo.setImageBitmap(getImage(request.getPhoto()));
                sname.setText(request.getStu_name());
                pname.setText("(부모님 이름)");
                content.setText(request.getContent());
                num.setText(Integer.toString(getAdapterPosition()+1));


                if (requestslist.get(getAdapterPosition()).isChecked()){
                    //터치하여 확인한 경우,

                    checkBox.setChecked(true);
                    relativeLayout.setAlpha((float)0.2);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relativeLayout, "alpha", 0.3f);
                    objectAnimator.setDuration(1500);
                    objectAnimator.start();


                }else{
                    relativeLayout.setAlpha((float) 0);
                    checkBox.setChecked(false);
                }


            }else{//mode 1





            }

        }



    }
}
