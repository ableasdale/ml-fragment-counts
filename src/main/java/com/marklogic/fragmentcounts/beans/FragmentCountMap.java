package com.marklogic.fragmentcounts.beans;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 09/05/15
 * Time: 12:15
 */
public class FragmentCountMap {


    // Map<String, List<Counts>> fileMap = new LinkedHashMap<String, List<Counts>>();
        private static class LazyHolder {
            private static Map<String, List<Counts>> INSTANCE = new LinkedHashMap<String, List<Counts>>();
        }

        public static Map<String, List<Counts>> getInstance() {
            return LazyHolder.INSTANCE;
        }

        public static void setInstance(Map<String, List<Counts>> map) {
            LazyHolder.INSTANCE = map;
        }


}
