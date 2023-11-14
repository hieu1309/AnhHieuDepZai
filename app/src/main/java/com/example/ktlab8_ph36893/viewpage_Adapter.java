package com.example.ktlab8_ph36893;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewpage_Adapter extends FragmentStateAdapter {
    public viewpage_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new frgdanhsachmonhoc();
            case 1: return new frgthongtin();
        }
        return new frgdanhsachmonhoc();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
