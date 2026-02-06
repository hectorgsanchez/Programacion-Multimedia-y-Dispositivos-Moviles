package com.example.practic5;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.practic5.FragmentUno;
import com.example.practic5.FragmentDos;
import com.example.practic5.FragmentTres;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FragmentUno();
            case 1: return new FragmentDos();
            case 2: return new FragmentTres();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Formulario";
            case 1: return "Calculadora";
            case 2: return "Info";
            default: return null;
        }
    }
}
