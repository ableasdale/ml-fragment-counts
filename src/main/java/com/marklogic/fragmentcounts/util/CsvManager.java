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
        List<String> lines = readFileAsLines(file.getPath(), StandardCharsets.UTF_8);
        List<Counts> countsList = new ArrayList<Counts>();

        for (String l : lines) {
            String[] s = l.split(",");
            Counts c = new Counts();
            c.setDate(s[0].replace(" ",""));
            c.setOrphanedProperties(s[1]);
            c.setTotalFragmentsIngestedByForest(s[2]);
            c.setTotalFragmentsIngestedInDatabase(s[3]);
            countsList.add(c);
        }
        fileMap.put(file.getName(), countsList);
        //To change body of created methods use File | Settings | File Templates.
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
