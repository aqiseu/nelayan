package com.learn.dimdimasdim.bidfishnelayan.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dimdimasdim on 11/02/2018.
 */

public class PreferenceManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    private static final String PREF_NAME = "intro";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PreferenceManager(Context context) {

        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
