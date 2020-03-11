package crawler.infrastructure.web;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PingPongHandler extends AbstractHandler {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        String echo = "";
        if (request.getParameter("echo") != null) {
            echo = request.getParameter("echo");
        }
        if (!echo.isEmpty()) {
            if (echo.equals("ping")) {
                response.getWriter().println("pong");
            } else if (echo.equals("pong")) {
                response.getWriter().println("ping");
            } else {
                response.getWriter().println("you don't understand this game don't you");
            }
        } else {
            response.getWriter().println("Wanna play a round of ping pong with me? Just go ahead and echo me something then");
        }
        baseRequest.setHandled(true);
        System.out.println("pingPong Page is running...");
    };
}