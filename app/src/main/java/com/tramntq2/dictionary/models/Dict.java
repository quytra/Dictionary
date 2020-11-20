package com.tramntq2.dictionary.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: quy.tra
 * Created on 19/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class Dict implements Serializable {
    private long id;
    private String word;
    private String content;
    private String explain = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplain() {
        Pattern pattern = Pattern.compile("(?<=<li>)(.*?)(?=<\\/li>)");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            explain = matcher.group(1);
        }
        return explain;
    }

    @NonNull
    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word= '" + word + '\'' +
                ", content= '" + content + '\'' +
                ", explain= '" + explain + '\'' +
                '}';
    }
}
