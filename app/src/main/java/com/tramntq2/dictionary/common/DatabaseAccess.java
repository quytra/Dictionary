package com.tramntq2.dictionary.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tramntq2.dictionary.AppConstant;
import com.tramntq2.dictionary.helper.DatabaseOpenHelper;
import com.tramntq2.dictionary.models.Dict;
import com.tramntq2.dictionary.models.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: quy.tra
 * Created on 17/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class DatabaseAccess {
    private static final String TAG = DatabaseAccess.class.getSimpleName();
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private String databaseName;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
        databaseName = openHelper.getDatabaseName().trim().replace(".db", "");
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DatabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all words from anh_viet dictionary
     *
     * @return a List of word from dictionary
     */
    public ArrayList<Word> getWords() {
        ArrayList<Word> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + databaseName, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = new Word();
            word.setWord(cursor.getString(1));
            list.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<Dict> getWords(int limit, int offset) {
        List<Dict> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + databaseName + " limit " + limit + " offset " + offset, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dict dict = new Dict();
            dict.setId(cursor.getInt(0));
            dict.setWord(cursor.getString(1));
            dict.setContent(cursor.getString(2));
            list.add(dict);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<Dict> getWordsByOffset(int offset) {
        List<Dict> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + databaseName + " limit " + AppConstant.DEFAULT_LIMIT_QUERY + " offset " + offset, null);
        cursor.moveToFirst();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dict dict = new Dict();
            dict.setId(cursor.getInt(0));
            dict.setWord(cursor.getString(1));
            dict.setContent(cursor.getString(2));
            list.add(dict);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<Dict> getWords(String filter) {
        List<Dict> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + databaseName + " where word like '" + filter + "%' limit 10", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dict dict = new Dict();
            dict.setId(cursor.getInt(0));
            dict.setWord(cursor.getString(1));
            dict.setContent(cursor.getString(2));
            list.add(dict);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getDefinition(String word) {
        String definition = "";
        Cursor cursor = database.rawQuery("SELECT * FROM " + databaseName + " where word='" + word + "'", null);
        cursor.moveToFirst();
        definition = cursor.getString(2);
        cursor.close();
        return definition;
    }
}
