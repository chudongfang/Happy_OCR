package myapp.lenovo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Lenovo on 2016/11/3.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> myfragments){
        super(fm);
        this.fragments=myfragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
