package com.marklogic.fragmentcounts.resources;

import com.marklogic.fragmentcounts.beans.*;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 11/05/15
 * Time: 19:20
 */
@Path("/date")
public class DateResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(DateResource.class);
    private Map<String, Counts> pertainingToDate;
    //private Map<String, List<String>> accruedTotalsPerForest = new LinkedHashMap<String, List<String>>();

    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Details by Date");
        map.put("dataSet", FragmentCountMap.getInstance());
        map.put("allKnownDates", UniqueDateList.getInstance());
        map.put("hostData", HostList.getInstance());
        map.put("pertainingToDate", pertainingToDate);
        map.put("allInMap", AllInfoMap.getInstance());
        //  map.put("accruedTotals", accruedTotalsPerForest);
        map.put("id", id);
        //map.put("lines", Consts.MAX_LINES_FOR_LOG_PREVIEW);
        return map;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDetails(@PathParam("id") String id) {
        LOG.debug(MessageFormat.format("Viewing date: {0}", id));
        pertainingToDate = new LinkedHashMap<String, Counts>();
        for (String s : FragmentCountMap.getInstance().keySet()) {
            List<Counts> l = FragmentCountMap.getInstance().get(s);
            for (Counts c : l) {
                if (id.equals(c.getDate())) {
                    if (pertainingToDate.containsKey(s)) {
                        LOG.warn("Key already exists - check this file out " + s);
                    }
                    pertainingToDate.put(s, c);
                }
            }
        }
        return new Viewable("/date", createModel(id));
    }
}
