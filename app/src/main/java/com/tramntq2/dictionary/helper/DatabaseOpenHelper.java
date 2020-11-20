package com.tramntq2.dictionary.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.tramntq2.dictionary.AppConstant;

/**
 * Author: quy.tra
 * Created on 17/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = AppConstant.DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
