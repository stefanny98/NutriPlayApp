package com.job.nutriplayapp.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionPreference {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String NAME = "historia";
    private static final String FIRST_TIME = "primera vez";

    public SesionPreference(Context context){
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(NAME, PRIVATE_MODE);
        sharedEditor = sharedPreferences.edit();
    }

    public void setFirstTime(boolean firstTime){
        sharedEditor.putBoolean(FIRST_TIME, firstTime);
        sharedEditor.commit();
    }

    public boolean firstTime(){
        return sharedPreferences.getBoolean(FIRST_TIME, true);
    }


}