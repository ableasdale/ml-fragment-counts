import com.marklogic.fragmentcounts.util.Consts;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.freemarker.FreemarkerViewProcessor;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.marklogic.fragmentcounts.resources.BaseResource;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 09/05/15
 * Time: 12:05
 */
public class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private static String path;

    public static final URI BASE_URI = getBaseURI();

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/")
                .port(Consts.GRIZZLY_HTTP_PORT).build();
    }

    /**
     * Start the Jersey FreeMarker application.
     *
     * @param args does not matter.
     * @throws java.io.IOException in case the application could not be started.
     */
    public static void main(String[] args) throws IOException {

        // PropertiesMap.getInstance().put("path", args[0]);
        // HTTP server invocation code below
        HttpServer httpServer = startServer();
        LOG.info("HTTP Application com.marklogic.analyser.Server Ready: " + BASE_URI);
        LOG.info("WADL Definition available at: " + BASE_URI
                + "application.wadl");
        LOG.info("Press enter to stop the application server...");
        System.in.read();
        httpServer.stop();
    }


    protected static HttpServer startServer() throws IOException {
        LOG.info("Starting Grizzly (HTTP Service).");

        ResourceConfig rc = new PackagesResourceConfig(BaseResource.class
                .getPackage().getName());
        rc.getProperties().put(
                FreemarkerViewProcessor.FREEMARKER_TEMPLATES_BASE_PATH,
                "freemarker");
        //rc.getProperties().put(com.marklogic.analyser.util.Consts.SOURCE_FILE_TO_PROCESS, path);
        rc.getFeatures().put(ResourceConfig.FEATURE_IMPLICIT_VIEWABLES, true);
        // TODO - not sure if both template base paths need to be "put" but this
        // works for now :)
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("com.sun.jersey.freemarker.templateBasePath",
                Consts.FREEMARKER_TEMPLATE_PATH);
        rc.setPropertiesAndFeatures(params);

        HttpServer server = GrizzlyServerFactory.createHttpServer(BASE_URI, rc);

        StaticHttpHandler staticHttpHandler = new StaticHttpHandler(Consts.STATIC_RESOURCE_DIRECTORY_ROOT);
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/vendor");

        /*(: server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("/libs"), "/libs");     :)*/
        /*
        server.getServerConfiguration().addHttpHandler(
                new CLStaticHttpHandler(new URLClassLoader(new URL[] {new URL("file:///home/username/staticfiles.jar")})), "/www");
          */
        return server;
    }
}
