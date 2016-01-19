package com.android.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import com.android.settings.DeviceSettings;

public class Fastcharge implements OnPreferenceChangeListener {

    private static final String FILE = "/sys/devices/platform/htc_battery/fast_charge";

    public static boolean isSupported() {
        return Utils.fileExists(FILE);
    }

    /**
     * Restore fast charge setting from SharedPreferences. (Write to kernel.)
     * @param ctx       The context to read the SharedPreferences from
     */
    public static void restore(Context ctx) {
        if (!isSupported()) {
            return;
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        boolean enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_FASTCHARGE, false);
        if(enabled)
            Utils.writeValue(FILE, "1");
        else
            Utils.writeValue(FILE, "0");
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Boolean enabled = (Boolean) newValue;
        if(enabled)
            Utils.writeValue(FILE, "1");
        else
            Utils.writeValue(FILE, "0");
        return true;
    }

}
