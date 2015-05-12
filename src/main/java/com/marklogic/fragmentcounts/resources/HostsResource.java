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
    private Map<String, List<Counts>> hostForestList;

    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Details by Host");
        map.put("dataSet", FragmentCountMap.getInstance());
        map.put("allKnownDates", UniqueDateList.getInstance());
        map.put("hostData", HostList.getInstance());
        map.put("hostForestList", hostForestList);
        map.put("currentHost", id);
        return map;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getForests(@PathParam("id") String id) {
        LOG.debug(MessageFormat.format("Getting Forest data for: {0}", id));
        hostForestList = new LinkedHashMap<String, List<Counts>>();
            for (String f : FragmentCountMap.getInstance().keySet()){
                if(f.contains(id)){
                    LOG.debug(MessageFormat.format("Found forest: {0} assigned to host: {1}", f, id));
                    // TODO - can probably be made more efficient?
                    int startIdx = 0;
                    List<Counts> fc = FragmentCountMap.getInstance().get(f);
                    for (Counts c : fc) {
                        if (c.getDate().equals(Consts.START)) {
                            startIdx = fc.indexOf(c);
                            LOG.debug(MessageFormat.format("HostsResource :: getForests :: Slicing Array at index: {0} for start date: {1}", startIdx, Consts.START));
                        }
                    }
                        hostForestList.put(f, fc.subList(startIdx, (fc.size())));
                }
            }
        return new Viewable("/host", createModel(id));
    }
}





