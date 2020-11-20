package com.tramntq2.dictionary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.tramntq2.dictionary.R;
import com.tramntq2.dictionary.Utils;
import com.tramntq2.dictionary.common.OnItemClickListener;
import com.tramntq2.dictionary.models.Dict;

import java.util.List;

/**
 * Author: quy.tra
 * Created on 19/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class WordAdapter extends Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_ITEM = 1;
    private List<Dict> mDicts;
    private OnItemClickListener onItemClickListener;

    public WordAdapter(List<Dict> mDicts, OnItemClickListener onItemClickListener) {
        this.mDicts = mDicts;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
            return new WordViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WordViewHolder) {
            Dict dict = mDicts.get(position);
            WordViewHolder wordViewHolder = (WordViewHolder) holder;
            wordViewHolder.tvWord.setText(dict.getWord());
            wordViewHolder.tvExplain.setText(dict.getExplain());
            wordViewHolder.itemView.setOnClickListener(view -> {
                onItemClickListener.onClick(position);
            });
        } else {
            ((LoadingViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDicts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return Utils.isCollectionNotBlank(mDicts) ? mDicts.size() : 0;
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord;
        TextView tvExplain;
        View rowView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExplain = (TextView) itemView.findViewById(R.id.tvExplain);
            tvWord = (TextView) itemView.findViewById(R.id.tvWord);
            rowView = itemView;
        }
    }

}
