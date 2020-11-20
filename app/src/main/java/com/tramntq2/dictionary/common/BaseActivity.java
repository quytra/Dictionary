package com.tramntq2.dictionary.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Author: quy.tra
 * Created on 17/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {
    public FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutById());
        fragmentManager = getSupportFragmentManager();
        setViews(savedInstanceState);
        setEvents();
    }

    public abstract int getLayoutById();

    public abstract void setViews(@Nullable Bundle savedInstanceState);

    public abstract void setEvents();
}
