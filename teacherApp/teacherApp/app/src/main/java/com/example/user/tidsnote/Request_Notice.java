package com.example.user.tidsnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.tidsnote.R.id.menu_done;
import static com.example.user.tidsnote.R.id.menu_search;

public class Request_Notice extends AppCompatActivity {

    Toolbar toolbar;
    CardView cardView,cardView2;
    TabLayout tabLayout;
    myViewPager viewPager;
    RequestFragment requestFragment;
    TextView textView,badgetext;
    TabPagerAdapter pagerAdapter;
    NestedScrollView nestedScrollView;
    FloatingActionButton fab;
    LayoutInflater inflater;
    View tabView,tabView2;
    List<Fragment> fragmentList;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    Menu mymenu;
    Bundle bundle;
    TabLayout.OnTabSelectedListener onTabSelectedListener;
    int startmode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__notice);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager = (myViewPager) findViewById(R.id.viewpager);
        textView=(TextView)findViewById(R.id.name);
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedscroll);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);

        Intent intent=new Intent(this.getIntent());
        //startmode = intent.getIntExtra("name",0);

        //Toast.makeText(getApplicationContext(),intent.getStringExtra("mode"),Toast.LENGTH_SHORT).show();



        nestedScrollView.setFillViewport(true);

        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tabLayout.addTab(tabLayout.newTab().setText("학부모 요청"));
        //tabLayout.addTab(tabLayout.newTab().setText("공지"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentList=new ArrayList<>();
        fragmentList.add(new RequestFragment());
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragmentList,1);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        setTab();
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);


        if (intent.getStringExtra("mode").equals("notice")){
            viewPager.setCurrentItem(1);
        }

    }

    public void setTab(){
        onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();
                viewPager.setCurrentItem(position);


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                switch (position){
                    case 0:
                        break;


                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                switch (position){
                    case 0:
                        break;


                }

            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final MenuItem menuItem = item;

        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            default :
                return super.onOptionsItemSelected(item) ;



        }

    }

}
