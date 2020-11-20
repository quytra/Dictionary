package com.tramntq2.dictionary;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.tramntq2.dictionary.common.BaseActivity;
import com.tramntq2.dictionary.fragments.EngVnFragment;
import com.tramntq2.dictionary.models.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: quy.tra
 * Created on 17/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    public FragmentTransaction transaction;
    private int mLastItemChecked;
    private TextView tvTitle;
    private AutoCompleteTextView autoCplTvSearch;
    private List<Word> lstWords = new ArrayList<>();

    @Override
    public int getLayoutById() {
        return R.layout.activity_main;
    }

    @Override
    public void setViews(@Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.toolbar_title);
        autoCplTvSearch = (AutoCompleteTextView) findViewById(R.id.autoCplTvSearch);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState != null) {
            if (mLastItemChecked != R.id.nav_eng_vn) {
                navigationView.getMenu().findItem(R.id.nav_eng_vn).setChecked(false);
                navigationView.getMenu().findItem(mLastItemChecked).setChecked(true);
            }
        } else {
            selectItem(R.id.nav_eng_vn);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setEvents() {
        lstWords = App.getInstance().getWords();
        ArrayAdapter<Word> adapterWords = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lstWords);
        autoCplTvSearch.setAdapter(adapterWords);
        autoCplTvSearch.setThreshold(1);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItem(item.getItemId());
        return true;
    }

    private void selectItem(int itemId) {
        transaction = fragmentManager.beginTransaction();
        switch (itemId) {
            case R.id.nav_eng_vn:
                tvTitle.setText("ENG-VIE");
                transaction.replace(R.id.container, new EngVnFragment(), EngVnFragment.class.getSimpleName());
                break;
            case R.id.nav_eng_eng:
                tvTitle.setText("ENG-ENG");
                break;
            case R.id.nav_vni_eng:
                tvTitle.setText("VIE-ENG");
                break;
            case R.id.nav_bookmark:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_help:
                break;
        }
        transaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        mLastItemChecked = itemId;
    }
}
