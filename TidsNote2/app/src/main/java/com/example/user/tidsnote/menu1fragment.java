package com.example.user.tidsnote;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class menu1fragment extends Fragment {

    View view;
    RecyclerView recyclerView,recyclerView2;
    ArrayList<Request> requestArrayList = new ArrayList<>();
    ArrayList<Notice> noticeArrayList = new ArrayList<>();
    RequestAdapter requestAdapter;
    NoticeAdapter noticeAdapter;
    TextView page,total,page_notice,total_notice,total_request,totalnotice,norequest,nonotice, button;
    LinearSnapHelper snapHelper,snapHelper2;
    CardView cardView;
    ImageButton more_request, more_notice;
    View.OnClickListener onClickListener;
    boolean new_check=false;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menu1, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.parent_request);
        recyclerView2=(RecyclerView)view.findViewById(R.id.notice_list);
        page=(TextView)view.findViewById(R.id.page);
        total=(TextView)view.findViewById(R.id.total);
        page_notice=(TextView)view.findViewById(R.id.page_notice);
        total_notice=(TextView)view.findViewById(R.id.total_notice);
        total_request=(TextView)view.findViewById(R.id.total_reuqest);
        norequest=(TextView)view.findViewById(R.id.no_request);
        nonotice=(TextView)view.findViewById(R.id.no_notice);

        totalnotice=(TextView)view.findViewById(R.id.totalnotice);
        cardView=((MainActivity)getActivity()).cardView;
        button=((MainActivity)getActivity()).button;
        more_request=(ImageButton) view.findViewById(R.id.more_request);
        more_notice=(ImageButton) view.findViewById(R.id.more_notice);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(recyclerView2);
        noticeAdapter = new NoticeAdapter(((MainActivity) getActivity()).noticeList,0);
        recyclerView2.setAdapter(noticeAdapter);
        noticeArrayList=((MainActivity) getActivity()).noticeList;

        total_notice.setText("/"+Integer.toString(noticeArrayList.size()));
        totalnotice.setText(Integer.toString(noticeArrayList.size()));

        if (noticeArrayList.size() != 0 ) {
            recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                    View snapview = snapHelper2.findSnapView(recyclerView.getLayoutManager());
                    final int position = recyclerView.getLayoutManager().getPosition(snapview) + 1;
                    page_notice.setText(Integer.toString(position));
                    super.onScrollStateChanged(recyclerView, newState);


                }
            });

        }else
            nonotice.setVisibility(View.VISIBLE);



        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        requestAdapter = new RequestAdapter(requestArrayList,0);
        //recyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
        total.setText("/"+Integer.toString(requestAdapter.getItemCount()));
        total_request.setText(Integer.toString(requestAdapter.getItemCount()));

        if(requestArrayList.size() != 0 ) {

            page.setText("1");

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                    View snapview = snapHelper.findSnapView(recyclerView.getLayoutManager());
                    final int position = recyclerView.getLayoutManager().getPosition(snapview) + 1;

                    page.setText(Integer.toString(position));
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }else
            norequest.setVisibility(View.VISIBLE);

        recyclerView.setAdapter(requestAdapter);

        if(!new_check)
            card_Show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               card_Hide();

            }
        });



        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                Intent intent = new Intent(getContext(), Request_Notice.class);

                if (v.getContentDescription().equals("more_notice"))
                    //Toast.makeText(getContext(),"Notice",Toast.LENGTH_SHORT).show();
                    intent.putExtra("mode", "notice");
                else
                    intent.putExtra("mode", "request");


                startActivity(intent);
            }

        };


        more_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).viewMoreNotice();


            }
        });
        more_request.setOnClickListener(onClickListener);





        return view;
    }


    public void card_Show(){

        if (!new_check){
        cardView.setVisibility(View.VISIBLE);
        Animation aniSlide = AnimationUtils.loadAnimation(view.getContext(),R.anim.slide_up);
        aniSlide.setDuration(1500);
        aniSlide.setFillAfter(true);
        cardView.startAnimation(aniSlide);
        }else{
            button.setClickable(false);
        }

    }

    public void card_Hide(){

        Animation aniSlide = AnimationUtils.loadAnimation(view.getContext(),R.anim.slide_down);
        aniSlide.setDuration(1500);
        aniSlide.setFillAfter(true);
        cardView.startAnimation(aniSlide);
        cardView.setClickable(false);
        button.setClickable(false);
        new_check=true;

    }
    @Override
    public void onResume() {
        super.onResume();
        if (new_check){
            cardView.setClickable(false);
            button.setClickable(false);
        }
    }
}
