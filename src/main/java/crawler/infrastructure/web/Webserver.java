package crawler.infrastructure.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crawler.infrastructure.database.ReadDatabase;
import crawler.domain.Article;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.json.JSONArray;
import org.json.JSONObject;

public class Webserver {

    private final Connection connection;

    public Webserver(Connection connection) {
        this.connection = connection;
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
        search.setHandler(new SearchHandler(connection));

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