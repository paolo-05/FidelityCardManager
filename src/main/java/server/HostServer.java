package server;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author paolo
 */
public class HostServer {
    public static void main(String[] args) throws IOException {
        URI ServerAddress = URI.create("http://localhost:8888/");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer( //
                ServerAddress,new ResourceConfig(RestResources.class));
        server.start();
        while(true);
    }
}
