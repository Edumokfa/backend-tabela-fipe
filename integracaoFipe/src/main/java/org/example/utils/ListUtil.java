package org.example.utils;

import java.util.Collection;

public class ListUtil {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }
}
