package com.android.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import com.android.settings.DeviceSettings;

public class Sweep2WakeStroke implements OnPreferenceChangeListener {

    private static final String FILE = "/sys/android_touch/s2w_allow_stroke";

    public static boolean isSupported() {
        return Utils.fileExists(FILE);
    }

    /**
     * Restore Sweep2Wake stroke setting from SharedPreferences. (Write to kernel.)
     * @param ctx       The context to read the SharedPreferences from
     */
    public static void restore(Context ctx) {
        if (!isSupported()) {
            return;
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Utils.writeValue(FILE, sharedPrefs.getString(DeviceSettings.KEY_S2WSTROKE, "1"));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Utils.writeValue(FILE, (String) newValue);
        return true;
    }

}
