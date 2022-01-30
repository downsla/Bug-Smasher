package com.examples.hw14_downs;

import android.annotation.SuppressLint;
import android.preference.PreferenceActivity;
import android.os.Build;
import java.util.List;

public class PrefsActivity extends PreferenceActivity {
    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected boolean isValidFragment(String fragmentName) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return true;
        } else {
            return PrefsFragmentSettings.class.getName().equals(fragmentName);
        }
    }
    @Override
    public void onBuildHeaders(List<Header> target) {
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragmentSettings()).commit();
    }
}