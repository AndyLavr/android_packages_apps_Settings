package com.android.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import com.android.settings.DeviceSettings;

public class DoubleTap2WakeDeadArea implements OnPreferenceChangeListener {

    private static final String FILE = "/sys/android_touch/s2w_double_tap_dead_area";

    public static boolean isSupported() {
        return Utils.fileExists(FILE);
    }

    /**
     * Restore DoubleTap2Wake DeadArea setting from SharedPreferences. (Write to kernel.)
     * @param ctx       The context to read the SharedPreferences from
     */
    public static void restore(Context ctx) {
        if (!isSupported()) {
            return;
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Utils.writeValue(FILE, sharedPrefs.getString(DeviceSettings.KEY_S2WDTDEADAREA, "1780"));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Utils.writeValue(FILE, (String) newValue);
        return true;
    }

}
