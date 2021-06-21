package com.example.afinal.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;

import com.example.afinal.R;
import com.example.afinal.notification.DailyReminder;
import com.example.afinal.notification.ReleaseReminder;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.setting));
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        SwitchPreference switchPreferencedaily, switchPreferencerelease;
        DailyReminder dailyReminder;
        ReleaseReminder releaseReminder;
        Preference preferenceLanguage, preferenceContact, preferenceAbout;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.settings_activity);

            switchPreferencedaily = findPreference(getString(R.string.key_daily));
            switchPreferencedaily.setOnPreferenceChangeListener(this);
            switchPreferencerelease = findPreference(getResources().getString(R.string.key_release));
            switchPreferencerelease.setOnPreferenceChangeListener(this);

            dailyReminder = new DailyReminder();
            releaseReminder = new ReleaseReminder();

            preferenceLanguage = findPreference(getString(R.string.key_language));
            preferenceLanguage.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });

            preferenceContact = findPreference(getString(R.string.key_contact));
            preferenceContact.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent toDial = new Intent(Intent.ACTION_DIAL);
                    toDial.setData(Uri.parse("tel:087855651181"));
                    startActivity(toDial);
                    return true;
                }
            });

            preferenceAbout = findPreference(getString(R.string.about_dev));
            preferenceAbout.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Intent toAbout = new Intent(getContext(), WebActivity.class);
                    startActivity(toAbout);
                    return true;
                }
            });
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String getKey = preference.getKey();
            boolean value = (boolean) newValue;
            if (getKey.equals(getString(R.string.key_daily))) {
                if (value) {
                    dailyReminder.setRepeatingAlarm(getContext(), getResources().getString(R.string.daily), "07:00", getResources().getString(R.string.daily_message) );
                }
                else {
                    dailyReminder.cancelAlarm(getContext(),DailyReminder.TYPE_REPEATING );
                }
            }
            else {
                if (value) {
                    releaseReminder.setAlarm(getContext(),getResources().getString(R.string.release), "08:00", getResources().getString(R.string.release_message));
                }
                else {
                    releaseReminder.cancelAlarm(getActivity());
                }
            }
            return true;
        }
    }
}