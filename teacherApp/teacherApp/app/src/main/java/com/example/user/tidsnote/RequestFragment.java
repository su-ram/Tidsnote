package com.example.user.tidsnote;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RequestFragment extends Fragment{

    private View view;
    Context context;
    Activity activity;
    RecyclerView recyclerView;


    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.request_fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_request_more);
        setContent();
        return  view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }

    public void setContent(){

        ArrayList<Request> requestArrayList = new ArrayList<>();
        LinearLayoutManager linearLayout = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        recyclerView.setLayoutManager(linearLayout);
        RequestAdapter requestAdapter;
        snapHelper.attachToRecyclerView(recyclerView);

        for (int i=0;i<20;i++){
            requestArrayList.add(new Request("Student"+Integer.toString(i+1),"Parent"+Integer.toString(i+1),"Wish me a happy landing, All I gotta do is jump."));
        }

        requestAdapter = new RequestAdapter(requestArrayList,1);
        recyclerView.setAdapter(requestAdapter);



    }


}
