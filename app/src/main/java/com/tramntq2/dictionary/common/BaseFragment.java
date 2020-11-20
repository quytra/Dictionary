package com.tramntq2.dictionary.common;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tramntq2.dictionary.MainActivity;

/**
 * Author: quy.tra
 * Created on 17/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG(), "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            context = container.getContext();
        }
        return inflater.inflate(getLayoutById(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setViewEvents();
        initData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AppCompatActivity activity;
        if (context instanceof AppCompatActivity) {
            activity = (AppCompatActivity) context;
            mainActivity = (MainActivity) activity;
        }
        super.onAttach(context);
    }

    protected abstract void initViews(View view);
    protected abstract int getLayoutById();
    protected abstract void setViewEvents();
    protected abstract void initData();
    protected abstract String TAG();
}
