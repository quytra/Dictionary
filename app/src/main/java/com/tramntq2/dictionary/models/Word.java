package com.tramntq2.dictionary.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: quy.tra
 * Created on 20/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class Word implements Parcelable {
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.word);
    }

    public Word() {
    }

    protected Word(Parcel in) {
        this.word = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
