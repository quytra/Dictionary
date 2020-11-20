package com.tramntq2.dictionary.common;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

/**
 * Author: quy.tra
 * Created on 19/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public abstract class BaseAsyncTask<M> extends AsyncTaskLoader<M> {

    protected DatabaseAccess databaseAccess;

    public BaseAsyncTask(@NonNull Context context) {
        super(context);
    }

    public BaseAsyncTask(@NonNull Context context, DatabaseAccess databaseAccess) {
        super(context);
        this.databaseAccess = databaseAccess;
    }

    @Nullable
    @Override
    public M loadInBackground() {
        return handle();
    }

    public abstract M handle();

}
