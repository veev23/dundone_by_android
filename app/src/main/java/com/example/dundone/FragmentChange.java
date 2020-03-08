package com.example.dundone;

import androidx.fragment.app.Fragment;

public interface FragmentChange {
    void replaceFragment(Fragment fragment, String calleeName);
    void addFragment(Fragment fragment, String calleeName);
    boolean backFragment();
}
