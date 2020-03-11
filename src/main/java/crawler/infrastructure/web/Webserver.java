package crawler.infrastructure.web;

import java.sql.Connection;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class Webserver {

    private final Connection connection;
    private final Authorization authorization;

    public Webserver(Connection connection, Authorization authorization) {
        this.connection = connection;
        this.authorization = authorization;
    }

    public void startJetty() throws Exception {
        final ContextHandler health = new ContextHandler("/health");
        final ContextHandler pingPong = new ContextHandler("/api/pingpong");
        final ContextHandler search = new ContextHandler("/api/search");

        health.setAllowNullPathInfo(true);
        pingPong.setAllowNullPathInfo(true);
        search.setAllowNullPathInfo(true);

        health.setHandler(new HealthHandler());
        pingPong.setHandler(new PingPongHandler());
        search.setHandler(new SearchHandler(connection, authorization));

        ContextHandlerCollection contexts = new ContextHandlerCollection(health, pingPong, search);

        String port = System.getenv("PORT");
        if (port == null) {
            port = "8080";
        }
        System.out.println("PORT: " + port);
        final Server server = new Server(Integer.parseInt(port));

        server.setHandler(contexts);
        server.start();
        server.join();
    }
}