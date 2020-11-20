package com.tramntq2.dictionary.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.Loader;

import com.tramntq2.dictionary.activities.ContentActivity;
import com.tramntq2.dictionary.models.Dict;

import java.util.List;

/**
 * Author: quy.tra
 * Created on 19/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class EngVnFragment extends BaseDictFragment {
    private static final String TAG = EngVnFragment.class.getSimpleName();

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ContentActivity.TAG, mDicts.get(position));
        intent.putExtras(bundle);
        mainActivity.startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {
        // TODO: Handle long click
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Dict>> loader) {
        Log.i(TAG, "onLoaderReset: ");
    }
}
