package com.tramntq2.dictionary;

import android.app.Application;

import com.tramntq2.dictionary.common.DatabaseAccess;
import com.tramntq2.dictionary.models.Word;

import java.util.List;

/**
 * Author: quy.tra
 * Created on 17/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class App extends Application {

    private static App sInstance;
    private DatabaseAccess databaseAccess;
    private List<Word> lstWords;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        lstWords = databaseAccess.getWords();
    }

    public DatabaseAccess getDatabaseAccess() {
        return this.databaseAccess;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        databaseAccess.close();
    }

    public List<Word> getWords() {
        return this.lstWords;
    }
}
