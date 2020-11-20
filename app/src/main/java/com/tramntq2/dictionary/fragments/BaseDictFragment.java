package com.tramntq2.dictionary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tramntq2.dictionary.App;
import com.tramntq2.dictionary.AppConstant;
import com.tramntq2.dictionary.R;
import com.tramntq2.dictionary.adapters.WordAdapter;
import com.tramntq2.dictionary.common.BaseAsyncTask;
import com.tramntq2.dictionary.common.BaseFragment;
import com.tramntq2.dictionary.common.DatabaseAccess;
import com.tramntq2.dictionary.common.OnItemClickListener;
import com.tramntq2.dictionary.models.Dict;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: quy.tra
 * Created on 19/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public abstract class BaseDictFragment extends BaseFragment implements OnItemClickListener,
        LoaderManager.LoaderCallbacks<List<Dict>> {
    private static final String TAG = BaseDictFragment.class.getSimpleName();
    private static final int LOADER_MAIN_ID = 10000;
    private static final int LOAD_MORE_ID = 9999;
    private final String OFFSET_KEY_EXTRAS = "offset_key";

    protected RecyclerView rcvWords;
    protected DatabaseAccess databaseAccess;
    protected List<Dict> mDicts;
    protected WordAdapter adapter;
    protected ProgressBar prgLoadingContent;
    protected LoaderManager loaderManager;
    private int startOffset = 0;
    private boolean isLoading = false;

    @Override
    protected void initViews(View view) {
        rcvWords = (RecyclerView) view.findViewById(R.id.rcvWords);
        prgLoadingContent = (ProgressBar) view.findViewById(R.id.prgLoadingContent);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
        rcvWords.setLayoutManager(layoutManager);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.content_main;
    }

    @Override
    protected void initData() {
        loaderManager = LoaderManager.getInstance(this);
        databaseAccess = App.getInstance().getDatabaseAccess();
        mDicts = new ArrayList<>();
        adapter = new WordAdapter(mDicts, this);
        rcvWords.setAdapter(adapter);
        Bundle bundle = new Bundle();
        bundle.putInt(OFFSET_KEY_EXTRAS, startOffset);
        Loader<List<Dict>> loader = loaderManager.initLoader(LOADER_MAIN_ID, bundle, this);
        loader.forceLoad();
    }

    @Override
    protected String TAG() {
        return TAG;
    }

    @NonNull
    @Override
    public Loader<List<Dict>> onCreateLoader(int id, @Nullable Bundle args) {
        if (args != null) {
            int offset = args.getInt(OFFSET_KEY_EXTRAS, 0);
            if (id == LOADER_MAIN_ID) {
                prgLoadingContent.setVisibility(View.VISIBLE);
                rcvWords.setVisibility(View.INVISIBLE);
            } else if (id == LOAD_MORE_ID) {
                prgLoadingContent.setVisibility(View.GONE);
                rcvWords.setVisibility(View.VISIBLE);
            }
            return new DictLoader(mainActivity, databaseAccess, offset);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Dict>> loader, List<Dict> data) {
        if (loader.getId() == LOADER_MAIN_ID) {
            loaderManager.destroyLoader(loader.getId());
            rcvWords.setVisibility(View.VISIBLE);
            prgLoadingContent.setVisibility(View.GONE);
        } else if (loader.getId() == LOAD_MORE_ID) {
            isLoading = false;
            loaderManager.destroyLoader(loader.getId());
            mDicts.remove(mDicts.size() - 1);
            adapter.notifyItemRemoved(mDicts.size());
        }
        mDicts.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Loader<List<Dict>> loader = this.loaderManager.getLoader(LOADER_MAIN_ID);
        if (loader != null) {
            loader.cancelLoad();
        }
    }

    @Override
    protected void setViewEvents() {
        rcvWords.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mDicts.size() - 1) {
                        startOffset += AppConstant.DEFAULT_OFFSET_QUERY;
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });

    }

    private void loadMore() {
        mDicts.add(null);
        adapter.notifyItemInserted(mDicts.size() - 1);
        Bundle bundle = new Bundle();
        bundle.putInt(OFFSET_KEY_EXTRAS, startOffset);
        Loader<List<Dict>> loader = loaderManager.initLoader(LOAD_MORE_ID, bundle, this);
        loader.forceLoad();
    }

    public static class DictLoader extends BaseAsyncTask<List<Dict>> {
        private DatabaseAccess databaseAccess;
        private int offset = 0;

        public DictLoader(@NonNull Context context, DatabaseAccess databaseAccess) {
            super(context);
            this.databaseAccess = databaseAccess;
        }

        public DictLoader(@NonNull Context context, DatabaseAccess databaseAccess, int offset) {
            super(context);
            this.databaseAccess = databaseAccess;
            this.offset = offset;
        }

        @Override
        public List<Dict> handle() {
            return databaseAccess.getWordsByOffset(offset);
        }
    }
}
