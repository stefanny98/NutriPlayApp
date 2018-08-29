package com.job.nutriplayapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.job.nutriplayapp.fragments.AlimentoFragment;
import com.job.nutriplayapp.fragments.ModuloFragment;
import com.job.nutriplayapp.fragments.TemaFragment;

public class ViewAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ModuloFragment tab1 = new ModuloFragment();
                return tab1;
            case 1:
                AlimentoFragment tab2 = new AlimentoFragment();
                return tab2;
            case 2:
                TemaFragment tab3 = new TemaFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

