package com.marklogic.fragmentcounts.beans;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 11/05/15
 * Time: 23:43
 */
public class HostList {

    private static class LazyHolder {
        private static Set<String> INSTANCE = new LinkedHashSet<String>();
    }

    public static Set<String> getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void setInstance(Set<String> set) {
        LazyHolder.INSTANCE = set;
    }
}
