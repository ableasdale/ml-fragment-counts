package com.marklogic.fragmentcounts.resources;

import com.marklogic.fragmentcounts.beans.*;
import com.marklogic.fragmentcounts.util.Consts;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * The "Root" dashboard resource
 * <p/>
 * User: ableasdale Date: 2/1/14 Time: 6:40 PM
 */

@Path("/")
public class RootResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(RootResource.class);
    private Map<String, List<String>> accruedTotalsPerForest = new LinkedHashMap<String, List<String>>();
    private List<String> dateSubset = new ArrayList<String>();

    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Dashboard and Overview");
        map.put("dataSet", FragmentCountMap.getInstance());
        map.put("allKnownDates", UniqueDateList.getInstance());
        map.put("allInMap", AllInfoMap.getInstance());
        map.put("hostData", HostList.getInstance());
        map.put("accruedTotals", accruedTotalsPerForest);
        map.put("dateSubset", dateSubset);
        return map;
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Viewable doPost() {
        return getDashboard();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {
        LOG.debug("Getting Dashboard ...");
        calculateDashboardTotals();
        return new Viewable("/dashboard", createModel("Dashboard"));
    }

    public void calculateDashboardTotals() {
        boolean ignoreDateFlag = true;
        for (String s : AllInfoMap.getInstance().keySet()) {
            // date keys
            if (s.equals(Consts.START)) {
                ignoreDateFlag = false;
            }

            if (!ignoreDateFlag) {
                dateSubset.add(s);
                Map<String, Counts> thisDay = AllInfoMap.getInstance().get(s);
                for (String t : thisDay.keySet()) {
                    String total = thisDay.get(t).getTotalFragmentsIngestedByForest();
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
    }
}