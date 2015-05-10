package com.marklogic.fragmentcounts.resources;


import com.marklogic.fragmentcounts.beans.AllInfoMap;
import com.marklogic.fragmentcounts.beans.Counts;
import com.marklogic.fragmentcounts.beans.FragmentCountMap;
import com.marklogic.fragmentcounts.beans.UniqueDateList;
import com.marklogic.fragmentcounts.util.Consts;
import com.marklogic.fragmentcounts.util.CsvManager;
import com.sun.jersey.api.view.Viewable;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.text.MessageFormat.format;

//import com.xmlmachines.pstack.beans.SearchResults;
/*import com.xmlmachines.pstack.beans.PStackFrame;
import com.xmlmachines.pstack.beans.Thread;
import com.xmlmachines.pstack.util.Utils;
 */

/**
 * Created with IntelliJ IDEA. User: ableasdale Date: 2/1/14 Time: 6:10 PM
 */

public class BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(BaseResource.class);
    public List<File> files = new ArrayList<File>();
    @Context
    protected UriInfo uriInfo;


//	public List<PStackFrame> pstacks = PStackMovies.getInstance();

    /**
     * Attempt to make sure input is an integer.
     *
     * @param text the value passed to the method from the resource (a URI
     *             segment)
     * @return true or false
     */
    protected boolean canBeParsedAsInteger(String text) {
        try {
            new Integer(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Freemarker template parameter map for the HTTP Exception Page template
     * (exception.ftl)
     *
     * @param statusCode
     * @param message
     * @return
     */
    protected Map<String, Object> createExceptionModel(int statusCode,
                                                       String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", String.valueOf(statusCode));
        map.put("message", message);
        return map;
    }

    protected Response generalExceptionCheck(String id, int arrayBoundary) {
        Response r = null;
        // non-string check
        if (!canBeParsedAsInteger(id)) {
            r = wrapViewableExceptionResponse(format(
                    "<em>{0}</em> does not appear to be a valid integer.", id));
        } else {
            // bounds check
            if (Integer.parseInt(id) < 0) {
                r = wrapViewableExceptionResponse(format(
                        "Bounds check failed; It looks like you have requested a value ({0}) smaller than the number of stacks available ({1})",
                        id, arrayBoundary));
            }
            if (Integer.parseInt(id) > arrayBoundary) {
                r = wrapViewableExceptionResponse(format(
                        "Bounds check failed; It looks like you have requested a value ({0}) larger than the number of stacks available ({1})",
                        id, arrayBoundary));
            }
        }
        return r;
    }

    /**
     * Trims the String output by pstack to include only the important
     * information
     *
     * @param str
     * @return
     */
    private String getThreadInfo(String str) {
        return str.substring((str.indexOf("(") + 1), str.lastIndexOf(")"));
    }

    public URI getUri(Class c) {
        return uriInfo.getBaseUriBuilder().path(c).build();
    }

    /**
     * General handler for exceptions in the request made to the resource This
     * is how you do a custom com.marklogic.analyser.Server Exception with the Freemarker template
     *
     * @param message
     * @return
     */
    protected Response wrapViewableExceptionResponse(String message) {
        LOG.error(MessageFormat.format("Exception encountered: {0}", message));
        return Response
                .status(500)
                .entity(new Viewable("/exception", createExceptionModel(500,
                        message))).build();
    }

    /**
     * Wraps an 'OK' response with the Viewable (freemarker) template
     *
     * @param templateName
     * @param model
     * @return
     */
    protected Response wrapViewableResponse(String templateName,
                                            Map<String, Object> model) {
        return Response.status(Response.Status.OK)
                .entity(new Viewable(templateName, model)).build();
    }

    protected Set<String> getUniqueDateList() {
        if (UniqueDateList.getInstance().isEmpty()) {
            LOG.info("Setting up the unique date list");
            List<String> allDates = new ArrayList<String>();
            for (String s : FragmentCountMap.getInstance().keySet()) {
                List<Counts> l = FragmentCountMap.getInstance().get(s);
                for (Counts c : l) {
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
            UniqueDateList.getInstance().addAll(allDates);
        }
        return UniqueDateList.getInstance();
    }

    protected Map<String, Map<String, Counts>> getAllInfoMap() {

        if (AllInfoMap.getInstance().isEmpty()) {
            Map<String,Map<String,Counts>> tempMap = AllInfoMap.getInstance();
            LOG.info("Setting up the All Info Map");
            for (String date : UniqueDateList.getInstance()) {
                // LOG.info("UNIQUE DATE " + s);
                Map<String, Counts> pertainingToDate = new LinkedHashMap<String, Counts>();
                for (String s : FragmentCountMap.getInstance().keySet()) {
                    List<Counts> l = FragmentCountMap.getInstance().get(s);
                    for (Counts c : l) {
                        if (date.equals(c.getDate())) {
                            if (pertainingToDate.containsKey(s)) {
                                LOG.info("Key already exists - check this file out " + s);
                            }
                            pertainingToDate.put(s, c);
                        }

                    }
                }
                tempMap.put(date, pertainingToDate);
            }
            AllInfoMap.setInstance(tempMap);
        }
        return AllInfoMap.getInstance();
    }

    protected void analysePath(String path) {
        CsvManager cm = new CsvManager();

        // traverse a directory
        LOG.info(MessageFormat.format("Traversing Directory: {0}", Consts.DIRECTORY));

        File file = new File(Consts.DIRECTORY);
        Collection<File> files = FileUtils.listFiles(file, null, true);
        for (File file2 : files) {
            LOG.info(file2.getName());
            try {
                cm.processCsvFile(file2);
            } catch (IOException e) {
                LOG.error("Exception caught " + e.getMessage());
            }

        }

    }
}