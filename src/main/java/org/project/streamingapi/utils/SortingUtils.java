package org.project.streamingapi.utils;

import java.util.Comparator;
import java.util.List;

public class SortingUtils {
    public static <T> void sort(List<T> list, Comparator<T> comparator){
        list.sort(comparator);
    }
}
