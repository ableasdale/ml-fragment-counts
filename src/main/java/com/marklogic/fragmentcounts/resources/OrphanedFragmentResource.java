package com.marklogic.fragmentcounts.resources;

import com.marklogic.fragmentcounts.beans.AllInfoMap;
import com.marklogic.fragmentcounts.beans.Counts;
import com.marklogic.fragmentcounts.beans.FragmentCountMap;
import com.marklogic.fragmentcounts.beans.UniqueDateList;
import com.marklogic.fragmentcounts.util.Consts;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 10/05/15
 * Time: 14:53
 */

@Path("/orphans")
public class OrphanedFragmentResource extends BaseResource {
    private static final Logger LOG = LoggerFactory.getLogger(OrphanedFragmentResource.class);
    private Map<String, List<String>> accruedTotalsPerForest = new LinkedHashMap<String, List<String>>();
    private List<String> dateSubset = new ArrayList<String>();

    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Orphaned Properties Fragments");
        map.put("dataSet", FragmentCountMap.getInstance());
        map.put("allKnownDates", UniqueDateList.getInstance());
        map.put("allInMap", AllInfoMap.getInstance());
        map.put("accruedTotals", accruedTotalsPerForest);
        map.put("dateSubset", dateSubset);
/*
        map.put("allKnownDates", uniqueDates);
        map.put("pertainingToDate", pertainingToDate);
        map.put("allInMap", allInMap);
        map.put("accruedTotals", accruedTotalsPerForest);
*/
        //map.put("lines", Consts.MAX_LINES_FOR_LOG_PREVIEW);
        return map;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getOrphans() {

        LOG.debug("Getting Orphaned Properties Fragments...");


        boolean ignoreDateFlag = true;
        for (String s : AllInfoMap.getInstance().keySet()) {

            if (s.equals(Consts.START)) {
                ignoreDateFlag = false;
                LOG.debug("Found start key date for chart: " + s);
            }
            if (!ignoreDateFlag) {
                LOG.debug("NOT ignoring date "+s);
                dateSubset.add(s);
                // date keys
                Map<String, Counts> thisDay = AllInfoMap.getInstance().get(s);
                // private Map<String, List<String>> accruedTotalsPerForest;
                for (String t : thisDay.keySet()) {
                    // getTotalFragmentsIngestedByForest()
                    // String total = thisDay.get(t).getTotalFragmentsIngestedInDatabase();
                    String total = thisDay.get(t).getOrphanedProperties();
                    if (total == null || Integer.parseInt(total) < 1) {
                        total = "0";
                    }

                    if (accruedTotalsPerForest.containsKey(t)) {
                        List<String> totals = accruedTotalsPerForest.get(t);
                        totals.add(total);
                        accruedTotalsPerForest.put(t, totals);
                    } else {
                        List<String> totals = new ArrayList<String>();
                        totals.add(total);
                        accruedTotalsPerForest.put(t, totals);
                    }
                }
            }
        }

        //stackRecords = identifyCarriedOverStacks(pstacks);
        // renders the URI using "src/main/resources/freemarker/dashboard.ftl"
        return new Viewable("/orphans", createModel("ErrorLog.txt"));
    }


}
