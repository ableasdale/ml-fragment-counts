package com.marklogic.fragmentcounts.resources;

import com.marklogic.fragmentcounts.beans.Counts;
import com.marklogic.fragmentcounts.beans.FragmentCountMap;
import com.marklogic.fragmentcounts.beans.HostList;
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
import java.text.MessageFormat;
import java.util.*;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 11/05/15
 * Time: 23:23
 */
@Path("/host")
public class HostsResource extends BaseResource {
    private static final Logger LOG = LoggerFactory.getLogger(HostsResource.class);

    private List<Counts> newForestData;
    private String currentHost;

    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Details by Host");
        map.put("dataSet", FragmentCountMap.getInstance());
        map.put("allKnownDates", UniqueDateList.getInstance());
        map.put("hostData", HostList.getInstance());
        map.put("currentHost", currentHost);
        return map;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getForests(@PathParam("id") String id) {


        LOG.debug(MessageFormat.format("Getting Forest data for: {0}", id));
        currentHost = id;

        /* TODO - breaks here
        List<Counts> forestData = FragmentCountMap.getInstance().get(currentHost);

        int startIdx = 0;
        for (Counts c : forestData) {
            // LOG.info(c.getDate());
            if (c.getDate().equals(Consts.START)) {
                // get the index and split
                startIdx = forestData.indexOf(c);
                LOG.info(MessageFormat.format("Slicing Array at index: {0} for start date: {1}", startIdx, Consts.START));
            }
        }
        newForestData = forestData.subList(startIdx, (forestData.size()));
        LOG.info(MessageFormat.format("Sizes - full dataset: {0} / sliced dataset: {1}", forestData.size(), newForestData.size()));
        */
        return new Viewable("/host", createModel(id));
    }
}





