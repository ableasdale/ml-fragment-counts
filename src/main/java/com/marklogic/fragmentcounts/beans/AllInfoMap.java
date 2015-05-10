package com.marklogic.fragmentcounts.beans;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 10/05/15
 * Time: 15:16
 */
public class AllInfoMap {

    private static class LazyHolder {
        private static Map<String, Map<String, Counts>> INSTANCE = new LinkedHashMap<String, Map<String, Counts>>();
    }

    public static Map<String, Map<String, Counts>> getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void setInstance(Map<String, Map<String, Counts>> map) {
        LazyHolder.INSTANCE = map;
    }

}
