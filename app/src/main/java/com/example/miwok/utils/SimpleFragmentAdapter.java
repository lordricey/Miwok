package com.example.miwok.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.miwok.fragments.ColorsFragment;
import com.example.miwok.fragments.FamilyFragment;
import com.example.miwok.fragments.NumbersFragment;
import com.example.miwok.fragments.PhrasesFragment;

public class SimpleFragmentAdapter extends FragmentStateAdapter {

    public SimpleFragmentAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
