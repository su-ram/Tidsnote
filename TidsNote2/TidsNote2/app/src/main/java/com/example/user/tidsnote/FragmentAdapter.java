package com.example.user.tidsnote;


import android.os.Parcelable;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

public abstract class FragmentAdapter extends PagerAdapter {
    private static final String TAG="FragmentAdapter";
    private static final boolean DEBUG=false;

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction=null;
    private Fragment mCurrentPrimaryItem=null;

    public FragmentAdapter(FragmentManager fm){
        mFragmentManager=fm;
    }

    public abstract Fragment getItem(int position);
    @Override
    public void startUpdate(ViewGroup container){
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        if(mCurTransaction==null){
            mCurTransaction=mFragmentManager.beginTransaction();
        }
        final long itemId=getItemId(position);
        String name=makeFragmentName(container.getId(),itemId);
        Fragment fragment=mFragmentManager.findFragmentByTag(name);
        if(fragment!=null){
            if(DEBUG)Log.v(TAG,"Attaching item #"+itemId+": f="+fragment);
            mCurTransaction.attach(fragment);
        }else{
            fragment=getItem(position);
            if(DEBUG) Log.v(TAG,"Adding item #"+itemId+":f="+fragment);
            mCurTransaction.add(container.getId(),fragment,makeFragmentName(container.getId(),itemId));
        }
        if(fragment!=mCurrentPrimaryItem){
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container,int position, Object object){
        if(mCurTransaction==null){
            mCurTransaction=mFragmentManager.beginTransaction();
        }
        if (DEBUG) Log.v(TAG, "Detaching item #" + getItemId(position) + ": f=" + object
                + " v=" + ((Fragment)object).getView());
        mCurTransaction.detach((Fragment)object);
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment)object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }
    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment)object).getView() == view;
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
    public long getItemId(int position) {
        return position;
    }
    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

}
