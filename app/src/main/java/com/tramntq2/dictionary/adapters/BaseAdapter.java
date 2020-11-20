package com.tramntq2.dictionary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tramntq2.dictionary.R;

import java.util.List;

/**
 * Author: quy.tra
 * Created on 19/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public abstract class BaseAdapter<M> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_ITEM = 1;
    protected List<M> mListItem;

    public BaseAdapter(List<M> mListItem) {
        this.mListItem = mListItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            loadData();
        } else if (holder instanceof LoadingViewHolder) {

        }
    }

    public abstract void loadData();

    @Override
    public int getItemViewType(int position) {
        return mListItem != null && mListItem.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mListItem != null && mListItem.size() > 0 ? mListItem.size() : 0;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
