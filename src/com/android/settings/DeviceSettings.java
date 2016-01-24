package com.android.settings;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.preference.CheckBoxPreference;
import android.preference.TwoStatePreference;
import android.preference.PreferenceActivity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.SearchIndexableResource;
import android.provider.Settings;

import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.WindowManagerGlobal;

import com.android.internal.util.cm.ScreenType;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import cyanogenmod.hardware.CMHardwareManager;
import cyanogenmod.providers.CMSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.settings.device.Fastcharge;
import com.android.settings.device.DoubleTap2Wake;
import com.android.settings.device.DoubleTap2WakeDeadArea;
import com.android.settings.device.Sweep2WakeSwitch;
import com.android.settings.device.Sweep2WakeStroke;
import com.android.settings.device.Sweep2WakeMinLength;

public class DeviceSettings extends SettingsPreferenceFragment implements Indexable {
    private static final String TAG = "DeviceSettings";

    public static final String KEY_S2WSWITCH = "s2w_switch";
    public static final String KEY_S2WSTROKE = "s2w_stroke";
    public static final String KEY_S2WLENGTH = "s2w_length";
    public static final String KEY_FASTCHARGE = "fastcharge";
    public static final String KEY_S2WDT = "s2w_double_tap";
    public static final String KEY_S2WDTDEADAREA = "s2w_double_tap_dead_area";

    private TwoStatePreference mS2WSwitch;
    private ListPreference mS2WStroke;
    private ListPreference mS2WLength;
    private TwoStatePreference mFastcharge;
    private TwoStatePreference mS2WDT;
    private ListPreference mS2WDTDeadArea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.device_settings);

        mS2WSwitch = (TwoStatePreference) findPreference(KEY_S2WSWITCH);
        mS2WSwitch.setEnabled(Sweep2WakeSwitch.isSupported());
        mS2WSwitch.setOnPreferenceChangeListener(new Sweep2WakeSwitch());

        mS2WStroke = (ListPreference) findPreference(KEY_S2WSTROKE);
        mS2WStroke.setEnabled(Sweep2WakeStroke.isSupported());
        mS2WStroke.setOnPreferenceChangeListener(new Sweep2WakeStroke());

        mS2WLength = (ListPreference) findPreference(KEY_S2WLENGTH);
        mS2WLength.setEnabled(Sweep2WakeMinLength.isSupported());
        mS2WLength.setOnPreferenceChangeListener(new Sweep2WakeMinLength());

        mFastcharge = (TwoStatePreference) findPreference(KEY_FASTCHARGE);
        mFastcharge.setEnabled(Fastcharge.isSupported());
        mFastcharge.setOnPreferenceChangeListener(new Fastcharge());

        mS2WDT = (TwoStatePreference) findPreference(KEY_S2WDT);
        mS2WDT.setEnabled(DoubleTap2Wake.isSupported());
        mS2WDT.setOnPreferenceChangeListener(new DoubleTap2Wake());

        mS2WDTDeadArea = (ListPreference) findPreference(KEY_S2WDTDEADAREA);
        mS2WDTDeadArea.setEnabled(DoubleTap2WakeDeadArea.isSupported());
        mS2WDTDeadArea.setOnPreferenceChangeListener(new DoubleTap2WakeDeadArea());

    }

}
