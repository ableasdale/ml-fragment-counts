package com.marklogic.fragmentcounts.beans;

import java.util.List;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 09/05/15
 * Time: 11:52
 */

/*
1st Column – Date
2nd Column – Total number of orphaned properties on that date
3rd Column – Total number of fragments ingested on that date in that particular forest
4th Column – Total number of fragments ingested on that date in the database
 */
public class Counts {

    private String date;
    private String orphanedProperties;
    private String totalFragmentsIngestedByForest;
    private String activeFragments;
    private String nascentFragments;
    private String deletedFragments;

    private Map<String, List<String>> totalFragmentsIngestedByForestOverTime;
    private String totalFragmentsIngestedInDatabase;

    /* Getters / Setters */

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrphanedProperties() {
        return orphanedProperties;
    }

    public void setOrphanedProperties(String orphanedProperties) {
        this.orphanedProperties = orphanedProperties;
    }

    public String getTotalFragmentsIngestedByForest() {
        return totalFragmentsIngestedByForest;
    }

    public void setTotalFragmentsIngestedByForest(String totalFragmentsIngestedByForest) {
        this.totalFragmentsIngestedByForest = totalFragmentsIngestedByForest;
    }

    public String getActiveFragments() {
        return activeFragments;
    }

    public void setActiveFragments(String activeFragments) {
        this.activeFragments = activeFragments;
    }

    public String getNascentFragments() {
        return nascentFragments;
    }

    public void setNascentFragments(String nascentFragments) {
        this.nascentFragments = nascentFragments;
    }

    public String getDeletedFragments() {
        return deletedFragments;
    }

    public void setDeletedFragments(String deletedFragments) {
        this.deletedFragments = deletedFragments;
    }

    public Map<String, List<String>> getTotalFragmentsIngestedByForestOverTime() {
        return totalFragmentsIngestedByForestOverTime;
    }

    public void setTotalFragmentsIngestedByForestOverTime(Map<String, List<String>> totalFragmentsIngestedByForestOverTime) {
        this.totalFragmentsIngestedByForestOverTime = totalFragmentsIngestedByForestOverTime;
    }

    public String getTotalFragmentsIngestedInDatabase() {
        return totalFragmentsIngestedInDatabase;
    }

    public void setTotalFragmentsIngestedInDatabase(String totalFragmentsIngestedInDatabase) {
        this.totalFragmentsIngestedInDatabase = totalFragmentsIngestedInDatabase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Counts)) return false;

        Counts counts = (Counts) o;

        if (!activeFragments.equals(counts.activeFragments)) return false;
        if (!date.equals(counts.date)) return false;
        if (!deletedFragments.equals(counts.deletedFragments)) return false;
        if (!nascentFragments.equals(counts.nascentFragments)) return false;
        if (!orphanedProperties.equals(counts.orphanedProperties)) return false;
        if (!totalFragmentsIngestedByForest.equals(counts.totalFragmentsIngestedByForest)) return false;
        if (!totalFragmentsIngestedByForestOverTime.equals(counts.totalFragmentsIngestedByForestOverTime)) return false;
        if (!totalFragmentsIngestedInDatabase.equals(counts.totalFragmentsIngestedInDatabase)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + orphanedProperties.hashCode();
        result = 31 * result + totalFragmentsIngestedByForest.hashCode();
        result = 31 * result + activeFragments.hashCode();
        result = 31 * result + nascentFragments.hashCode();
        result = 31 * result + deletedFragments.hashCode();
        result = 31 * result + totalFragmentsIngestedByForestOverTime.hashCode();
        result = 31 * result + totalFragmentsIngestedInDatabase.hashCode();
        return result;
    }
}
