package com.marklogic.fragmentcounts.resources;


import com.marklogic.fragmentcounts.beans.Counts;
import com.marklogic.fragmentcounts.beans.FragmentCountMap;
import com.marklogic.fragmentcounts.util.Consts;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The "Root" dashboard resource
 * <p/>
 * User: ableasdale Date: 2/1/14 Time: 6:40 PM
 */

@Path("/")
public class RootResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(RootResource.class);
    private Set<String> uniqueDates;
    private Map<String, Counts> pertainingToDate = new LinkedHashMap<String, Counts>();
    private Map<String, Map<String, Counts>> allInMap;
    private Map<String, List<String>> accruedTotalsPerForest = new LinkedHashMap<String, List<String>>();;
    /* data model for freemarker .ftl template
    private Map<String, Object> createModel() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Dashboard and Overview");
        map.put("errorlog", ErrorLogMap.getInstance().get("ErrorLog.txt"));
        //  map.put("path", PropertiesMap.getInstance().get("path"));
        map.put("errorlogs", ErrorLogMap.getInstance());
        //  map.put("stacksCarried", stackRecords);
        return map;
    } */

    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Dashboard and Overview");
        map.put("dataSet", FragmentCountMap.getInstance());
        map.put("allKnownDates", uniqueDates);
        map.put("pertainingToDate", pertainingToDate);
        map.put("allInMap", allInMap);
        map.put("accruedTotals", accruedTotalsPerForest);
        //map.put("lines", Consts.MAX_LINES_FOR_LOG_PREVIEW);
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

        LOG.info("getting Dash...")     ;
        if (FragmentCountMap.getInstance().size() == 0) {
            analysePath(Consts.DIRECTORY);

            List<String> allDates = new ArrayList<String>();
            for (String s : FragmentCountMap.getInstance().keySet() ){
                List<Counts> l = FragmentCountMap.getInstance().get(s);
                for (Counts c : l){
                    allDates.add(c.getDate());
                }
            }

            Collections.sort(allDates, new Comparator<String>() {
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                @Override
                public int compare(String o1, String o2) {
                    try {
                        return f.parse(o1).compareTo(f.parse(o2));
                    } catch (ParseException e) {
                        LOG.error("Unparseable date: " + e.getMessage());
                        //throw new IllegalArgumentException(e);
                        return -1;
                    }
                }
            });
            uniqueDates = new LinkedHashSet<String>(allDates);

            allInMap = new LinkedHashMap<String, Map<String, Counts>>();

            for (String date : uniqueDates) {
                // LOG.info("UNIQUE DATE " + s);
                pertainingToDate = new LinkedHashMap<String, Counts>();
                for (String s : FragmentCountMap.getInstance().keySet() ){
                    List<Counts> l = FragmentCountMap.getInstance().get(s);
                    for (Counts c : l){
                        if (date.equals(c.getDate())){
                            if(pertainingToDate.containsKey(s)){
                                LOG.info("Key already exists - check this file out "+s);
                            }
                            pertainingToDate.put(s, c);
                        }

                    }
                }
                allInMap.put(date, pertainingToDate);
            }

        }

        /// NExt attempt - get totals as seq
        for (String s : allInMap.keySet()){
        // date keys
            Map<String,Counts> thisDay = allInMap.get(s);
    // private Map<String, List<String>> accruedTotalsPerForest;
            for (String t : thisDay.keySet()) {
                if(accruedTotalsPerForest.containsKey(t)){
                    List<String> totals = accruedTotalsPerForest.get(t);
                    totals.add(thisDay.get(t).getTotalFragmentsIngestedByForest());
                    accruedTotalsPerForest.put(t, totals);
                } else {
                    List<String> totals = new ArrayList<String>();
                    totals.add(thisDay.get(t).getTotalFragmentsIngestedByForest());
                    accruedTotalsPerForest.put(t, totals);
                }

            }
        }


        //stackRecords = identifyCarriedOverStacks(pstacks);
        // renders the URI using "src/main/resources/freemarker/dashboard.ftl"
        return new Viewable("/dashboard", createModel("ErrorLog.txt"));
    }

    @GET
    @Path("/date/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDetails(@PathParam("id") String id) {
        LOG.debug("Viewing date: " + id);

        for (String s : FragmentCountMap.getInstance().keySet() ){
            List<Counts> l = FragmentCountMap.getInstance().get(s);
            for (Counts c : l){
                if (id.equals(c.getDate())){
                    if(pertainingToDate.containsKey(s)){
                        LOG.info("Key already exists - check this file out "+s);
                    }
                    pertainingToDate.put(s, c);
                }

            }
        }

       /*
        List<String> allDates = new ArrayList<String>();
        for (String s : FragmentCountMap.getInstance().keySet() ){
             List<Counts> l = FragmentCountMap.getInstance().get(s);
             for (Counts c : l){
                 allDates.add(c.getDate());
             }
        }

        uniqueDates = new HashSet<String>(allDates);

        for (String s : uniqueDates){
            LOG.info("UNIQUE DATE ", s);
        }
        //List<String> = new S */
        return new Viewable("/date", createModel(id));
    }

}