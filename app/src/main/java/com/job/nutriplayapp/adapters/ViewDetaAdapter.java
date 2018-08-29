package com.job.nutriplayapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.job.nutriplayapp.fragments.AlimentoFragment;
import com.job.nutriplayapp.fragments.IngredientesFragment;
import com.job.nutriplayapp.fragments.ModuloFragment;
import com.job.nutriplayapp.fragments.PreparacionFragment;

public class ViewDetaAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Bundle bundleIngre;
    Bundle bundlePrepa;

    public ViewDetaAdapter(FragmentManager fm, int NumOfTabs, Bundle bundleIngre, Bundle bundlePrepa ) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.bundleIngre = bundleIngre;
        this.bundlePrepa = bundlePrepa;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                IngredientesFragment tab1 = new IngredientesFragment();
                tab1.setArguments(bundleIngre);
                return tab1;
            case 1:
                PreparacionFragment tab2 = new PreparacionFragment();
                tab2.setArguments(bundlePrepa);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

