package com.marklogic.fragmentcounts.resources;

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
import java.util.HashMap;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 10/05/15
 * Time: 14:53
 */

@Path("/orphans")
public class OrphanedFragmentResource extends BaseResource  {
    private static final Logger LOG = LoggerFactory.getLogger(OrphanedFragmentResource.class);

    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Orphaned Fragments");
        map.put("dataSet", FragmentCountMap.getInstance());
        map.put("allKnownDates", getUniqueDateList());
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

        LOG.info("getting Orphans...");

        for (String s : UniqueDateList.getInstance()){

        }

        //stackRecords = identifyCarriedOverStacks(pstacks);
        // renders the URI using "src/main/resources/freemarker/dashboard.ftl"
        return new Viewable("/orphans", createModel("ErrorLog.txt"));
    }


}
