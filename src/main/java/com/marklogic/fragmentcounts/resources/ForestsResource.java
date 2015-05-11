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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 11/05/15
 * Time: 12:40
 */
@Path("/forest")
public class ForestsResource extends BaseResource {
    private static final Logger LOG = LoggerFactory.getLogger(ForestsResource.class);

    private List<Counts> newForestData;
    private String currentForest;
    //private Map<String, List<String>> accruedTotalsPerForest = new LinkedHashMap<String, List<String>>();

    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Details by Forest");
        map.put("dataSet", FragmentCountMap.getInstance());
        map.put("allKnownDates", UniqueDateList.getInstance());
        map.put("allInMap", AllInfoMap.getInstance());

        map.put("forestData", newForestData);
        map.put("currentForest", currentForest);
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
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getForests(@PathParam("id") String id) {
        LOG.info("getting Forest data for: "+id);
        currentForest = id;
        List<Counts> forestData = FragmentCountMap.getInstance().get(currentForest);

        int startIdx = 0;
        for(Counts c : forestData){
            LOG.info(c.getDate());
            if(c.getDate().equals(Consts.START)){
                // get the index and split
                startIdx = forestData.indexOf(c);
                LOG.info("Slicing Array at index: " + startIdx + " for start date: "+Consts.START);

            }
        }
        newForestData = forestData.subList(startIdx, (forestData.size()));
        LOG.info("sizes: " + forestData.size() + " / "+ newForestData.size());

        /*
        for (String s : AllInfoMap.getInstance().keySet()) {
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
        }    */

        //stackRecords = identifyCarriedOverStacks(pstacks);
        // renders the URI using "src/main/resources/freemarker/dashboard.ftl"
        return new Viewable("/forest", createModel(id));
    }
}
