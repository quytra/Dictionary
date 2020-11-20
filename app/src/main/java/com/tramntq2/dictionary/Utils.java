package com.tramntq2.dictionary;

import java.util.List;

/**
 * Author: quy.tra
 * Created on 19/11/2020
 * Project Name: Dictionary
 * Version 1.0
 */
public class Utils {

    public static <T> boolean isCollectionNotBlank(List<T> list) {
        return list != null && list.size() > 0;
    }
}
