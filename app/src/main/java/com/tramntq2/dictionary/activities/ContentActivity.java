package com.tramntq2.dictionary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.tramntq2.dictionary.R;
import com.tramntq2.dictionary.common.BaseActivity;
import com.tramntq2.dictionary.models.Dict;

/**
 * Author: quy.tra
 * Created on 20/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class ContentActivity extends BaseActivity {
    public static final String TAG = ContentActivity.class.getSimpleName();

    @Override
    public int getLayoutById() {
        return R.layout.activity_content;
    }

    @Override
    public void setViews(@Nullable Bundle savedInstanceState) {
        WebView webview = (WebView) findViewById(R.id.wvContent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            Dict dict = (Dict) bundle.getSerializable(TAG);
            if (dict != null) {
                toolbar.setTitle(dict.getWord());
                setSupportActionBar(toolbar);
                webview.loadDataWithBaseURL(null, dict.getContent(), "text/html", "utf-8", null);
            }
        }
    }

    @Override
    public void setEvents() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
