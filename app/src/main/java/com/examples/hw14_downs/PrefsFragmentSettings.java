package com.examples.hw14_downs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class PrefsFragmentSettings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public PrefsFragmentSettings() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_fragment_settings);
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        Preference pref = getPreferenceScreen().findPreference("key_high_score");
        String s = "" + Assets.high_score;
        pref.setSummary(s);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
    }
}
