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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        map.put("forestData", newForestData);
        map.put("currentForest", currentForest);
        return map;
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getForests(@PathParam("id") String id) {
        LOG.info("getting Forest data for: " + id);
        currentForest = id;
        List<Counts> forestData = FragmentCountMap.getInstance().get(currentForest);

        int startIdx = 0;
        for (Counts c : forestData) {
            // LOG.info(c.getDate());
            if (c.getDate().equals(Consts.START)) {
                // get the index and split
                startIdx = forestData.indexOf(c);
                LOG.info("Slicing Array at index: " + startIdx + " for start date: " + Consts.START);

            }
        }
        newForestData = forestData.subList(startIdx, (forestData.size()));
        LOG.info("Sizes - full dataset: " + forestData.size() + " / Sliced dataset: " + newForestData.size());

        return new Viewable("/forest", createModel(id));
    }
}
