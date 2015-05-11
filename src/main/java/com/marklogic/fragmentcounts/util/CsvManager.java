package com.marklogic.fragmentcounts.util;

import com.marklogic.fragmentcounts.beans.Counts;
import com.marklogic.fragmentcounts.beans.FragmentCountMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 09/05/15
 * Time: 11:38
 */
public class CsvManager {
    Map<String, List<Counts>> fileMap = FragmentCountMap.getInstance();
    Logger LOG = LoggerFactory.getLogger(CsvManager.class);

    public void processCsvFile(File file) throws IOException {
        String fn = file.getName();
        List<String> lines = readFileAsLines(file.getPath(), StandardCharsets.UTF_8);
        List<Counts> countsList = new ArrayList<Counts>();

        // is this a monitoring file or the orphaned fragment file?
        if (fn.endsWith(".txt")) {
            //LOG.info("meters file found!");
            // remove headers
            lines.remove(0);
            // Hack up the filename so the keys match for the two files
            String keyFromFn =  fn.substring(  (fn.indexOf('-') + 1) , fn.lastIndexOf('.') );
            if(fileMap.containsKey(keyFromFn)){

                List<Counts> countList = fileMap.get(keyFromFn);
                Map<String, Counts> counts = new LinkedHashMap<String, Counts>();
                // Keying our countList by date
                for (Counts c : countList){
                    counts.put(c.getDate(), c);
                }

                for (String l : lines) {
                    // hack all the string data in the files so it'll map to our objects
                    l=l.replace(" ", "");
                    String[] s = l.split(",");
                    String date = s[0].split("T")[0];
                    //LOG.info(s[0].split("T")[0]);
                    if(counts.containsKey(date)) {
                        // LOG.info("matching date "+ date + " key: "+keyFromFn);
                        Counts c = counts.get(date);
                        c.setActiveFragments(s[1]);
                        c.setNascentFragments(s[2]);
                        c.setDeletedFragments(s[3]);
                        counts.put(date, c);
                    } else {
                        //LOG.error("No matching date - is this right?: "+ date + " key: "+keyFromFn);
                        Counts c = new Counts();
                        c.setDate(date);
                        c.setOrphanedProperties("0");
                        c.setTotalFragmentsIngestedByForest("0");
                        c.setTotalFragmentsIngestedInDatabase("0");
                        c.setActiveFragments(s[1]);
                        c.setNascentFragments(s[2]);
                        c.setDeletedFragments(s[3]);
                        counts.put(date, c);
                    }
                }
                // Now put these back!
                List<Counts> newCountList = new ArrayList<Counts>();
                for (String s : counts.keySet()){
                    newCountList.add(counts.get(s));
                }
                fileMap.put(keyFromFn, newCountList);
            } else {
                LOG.error("Something is going wrong - please debug using: "+fn);
            }

        } else {
            for (String l : lines) {
                String[] s = l.split(",");
                Counts c = new Counts();
                c.setDate(s[0].replace(" ", ""));
                c.setOrphanedProperties(s[1]);
                c.setTotalFragmentsIngestedByForest(s[2]);
                c.setTotalFragmentsIngestedInDatabase(s[3]);
                c.setActiveFragments("0");
                c.setNascentFragments("0");
                c.setDeletedFragments("0");
                countsList.add(c);
            }
            fileMap.put(fn.substring(0, fn.lastIndexOf('-')), countsList);
        }
        LOG.info("Processed file: " + file.getName());
    }

    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public List<String> readFileAsLines(String path, Charset encoding) throws IOException {
        return Files.readAllLines(Paths.get(path), Charset.defaultCharset());
    }


}
