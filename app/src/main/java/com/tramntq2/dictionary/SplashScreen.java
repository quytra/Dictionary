package com.tramntq2.dictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.tramntq2.dictionary.common.BaseAsyncTask;
import com.tramntq2.dictionary.common.DatabaseAccess;
import com.tramntq2.dictionary.models.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: quy.tra
 * Created on 20/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class SplashScreen extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Word>> {
    public static final String TAG = SplashScreen.class.getSimpleName();
    private ArrayList<String> words;
    private ProgressBar loading;
    protected LoaderManager loaderManager;
    private final int LOAD_DATA_FIRST_TIME = 99993;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loading = (ProgressBar) findViewById(R.id.loading);
        loaderManager = LoaderManager.getInstance(this);
        Loader<ArrayList<Word>> loader = loaderManager.initLoader(LOAD_DATA_FIRST_TIME, null, this);
        loader.forceLoad();
    }


    @NonNull
    @Override
    public Loader<ArrayList<Word>> onCreateLoader(int id, @Nullable Bundle args) {
        loading.setVisibility(View.VISIBLE);
        return new LoaderWords(this, App.getInstance().getDatabaseAccess());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Word>> loader, ArrayList<Word> data) {
        if (loader.getId() == LOAD_DATA_FIRST_TIME) {
            loaderManager.destroyLoader(loader.getId());
            App.getInstance().setWords(data);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Word>> loader) {

    }

    public static class LoaderWords extends BaseAsyncTask<ArrayList<Word>> {

        public LoaderWords(@NonNull Context context, DatabaseAccess databaseAccess) {
            super(context, databaseAccess);
        }

        @Override
        public ArrayList<Word> handle() {
            return databaseAccess.getWords();
        }
    }
}
