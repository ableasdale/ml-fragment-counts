import com.marklogic.fragmentcounts.util.Consts;
import com.marklogic.fragmentcounts.util.CsvManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 09/05/15
 * Time: 11:34
 */
public class Test {

    public static void main(String[] args) throws IOException {

        Logger LOG = LoggerFactory.getLogger(Test.class);
        CsvManager cm = new CsvManager();

        // traverse a directory
        LOG.info(MessageFormat.format("Traversing Directory: {0}", Consts.DIRECTORY));

        File file = new File(Consts.DIRECTORY);
        Collection<File> files = FileUtils.listFiles(file, null, true);
        for (File file2 : files) {
            LOG.info(file2.getName());
            cm.processCsvFile(file2);

        }
    }
}
