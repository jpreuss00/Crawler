package crawler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

public class Jetty {

        public void startJetty() throws Exception {
                final ContextHandler context = new ContextHandler();
                final ContextHandler health = new ContextHandler("/health");

                context.setHandler(new AbstractHandler() {
                        @Override
                        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                                response.getWriter().println("Hello " + request.getRemoteAddr() + "!");
                                response.getWriter().println("Current time: " + LocalDateTime.now());
                                baseRequest.setHandled(true);
                                System.out.println("Normal page");
                        }
                });

                health.setHandler(new AbstractHandler() {
                        @Override
                        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                                response.getWriter().println("up");
                                baseRequest.setHandled(true);
                                System.out.println("Health Page");
                        }
                });

                final String port = System.getenv("PORT");
                System.out.println("PORT: " + port);
                final Server server = new Server(Integer.parseInt(port));
                server.setHandler(context);
                server.start();
                server.join();
        }
}