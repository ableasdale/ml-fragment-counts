package com.marklogic.fragmentcounts.beans;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * TODO - MAKE THIS ACTUALLY DO SOMETHING!!!
 * <p/>
 * User: Alex
 * Date: 10/05/15
 * Time: 14:58
 */
public class UniqueDateList {

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
