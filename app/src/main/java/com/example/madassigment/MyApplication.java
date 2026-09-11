package com.example.madassigment;

import android.app.Application;
import com.example.madassigment.utils.ThemeManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Apply theme globally at the start of the application
        ThemeManager.applyTheme(this);
    }
}
