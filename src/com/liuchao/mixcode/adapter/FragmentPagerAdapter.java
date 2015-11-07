/**
 *   Copyright(c) 2012 DuoKan TV Group
 *    
 *   ViewPagerAdapter.java
 *
 *   @author tianli(tianli@duokan.com)
 *
 *   2012-8-17 
 */
package com.liuchao.mixcode.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianli
 * 
 */

public class FragmentPagerAdapter extends PagerAdapter {

    public static final String TAG = FragmentPagerAdapter.class.getName();
    
	private FragmentManager mFragmentManager;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	
	public FragmentPagerAdapter(FragmentManager fragmentManager) {
		this.mFragmentManager = fragmentManager;
	}
	
	public void refresh() {
		notifyDataSetChanged();
	}
	
	private void clear(){
	    mFragments.clear();
	}
	
    public void setPages(List<Fragment> pages) {
        Log.d(TAG, "setPages.");
	    clear();
		mFragments.addAll(pages);
		refresh();
	}

	public void setPages(Fragment[] pages) {
        Log.d(TAG, "setPages.");
	    clear();
		for (int i = 0; i < pages.length; i++) {
			this.mFragments.add(pages[i]);
		}
		refresh();
	}
	
	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	    if(mFragments != null && position >= 0 && position < mFragments.size()){
	        View view = mFragments.get(position).getView();
	        if(view != null && view.getParent() == null){
                container.removeView(view);  
	        }
	    }
	}
	
	private void addFragment(Fragment fragment){
	    if(mFragmentManager == null || fragment == null){
	        return;
	    }
        try{
            mFragmentManager.executePendingTransactions();
            if(!fragment.isAdded()) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.add(fragment, fragment.getClass().getName());
                ft.commitAllowingStateLoss();
                mFragmentManager.executePendingTransactions();
            }
        }catch(Exception e){
        }
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = mFragments.get(position);
		if(fragment != null) {
			addFragment(fragment);
			if(fragment.getView().getParent() == null) {
				container.addView(fragment.getView());
			}
			return fragment.getView();
		}
		return null;
	}
}