package crawler;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crawler.domain.Article;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.JSONObject;

public class Jetty {

        private final Connection connection;

        public Jetty(Connection connection){
                this.connection = connection;
        }

        public void startJetty() throws Exception {
                final ContextHandler root = new ContextHandler();
                final ContextHandler health = new ContextHandler("/health");
                final ContextHandler pingPong = new ContextHandler("/api/pingpong");
                final ContextHandler search = new ContextHandler("/api/search");

                root.setHandler(new AbstractHandler() {
                        @Override
                        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                                response.getWriter().println("Hello " + request.getRemoteAddr() + "!");
                                response.getWriter().println("Current time: " + LocalDateTime.now());
                                baseRequest.setHandled(true);
                                System.out.println("Normal page is running...");
                        }
                });

                health.setHandler(new AbstractHandler() {
                        @Override
                        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                                JSONObject json = new JSONObject().put("Status", "up");
                                response.getWriter().print(json);
                                baseRequest.setHandled(true);
                                System.out.println("Health Page is running...");
                        }
                });

                pingPong.setHandler(new AbstractHandler() {
                        @Override
                        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                                String echo = "";
                                if(request.getParameter("echo") != null){
                                        echo = request.getParameter("echo");
                                }
                                if(!echo.isEmpty()){
                                        if(echo.equals("ping")){
                                                response.getWriter().println("pong");
                                        } else if(echo.equals("pong")){
                                                response.getWriter().println("ping");
                                        }  else {
                                                response.getWriter().println("you don't understand this game don't you");
                                        }
                                } else {
                                        response.getWriter().println("Wanna play a round of ping pong with me? Just go ahead and echo me something then");
                                }
                                baseRequest.setHandled(true);
                                System.out.println("pingPong Page is running...");
                        }
                });

                search.setHandler(new AbstractHandler() {
                        @Override
                        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                                ReadDatabase readDatabase = new ReadDatabase(connection);
                                String category = "";
                                String term = "";
                                if(request.getParameter("category") != null){
                                        category = request.getParameter("category");
                                }
                                System.out.println(category);
                                if(request.getParameter("term") != null){
                                        term = request.getParameter("term");
                                }
                                System.out.println(term);
                                List<Article> articles = readDatabase.articleReader(category, term);
                                for (Article article : articles){
                                        JSONObject json = new JSONObject(article);
                                        response.getWriter().print(json);
                                        response.getWriter().print("\n\n");
                                }
                                baseRequest.setHandled(true);
                                System.out.println("search page is running...");
                        }
                });

                ContextHandlerCollection contexts = new ContextHandlerCollection(root, health, pingPong, search);
                final String port = System.getenv("PORT");
                System.out.println("PORT: " + port);
                final Server server = new Server(Integer.parseInt(port));
                server.setHandler(contexts);
                server.start();
                server.join();
        }
}