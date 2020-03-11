package crawler.infrastructure.web;

import crawler.domain.Article;
import crawler.infrastructure.database.ReadDatabase;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class SearchHandler extends AbstractHandler {

    private final Connection connection;

    public SearchHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setStatus(200);
            baseRequest.setHandled(true);
            return;
        }

        if (authorization(request.getHeader("Authorization"))) {

            ReadDatabase readDatabase = new ReadDatabase(connection);

            String category = "";
            String term = "";
            int limit = 10;

            if (request.getParameter("category") != null) {
                category = request.getParameter("category");
            }

            if (request.getParameter("term") != null) {
                term = request.getParameter("term");
            }

            if (request.getParameter("limit") != null) {
                limit = Integer.parseInt(request.getParameter("limit"));
            }

            List<Article> articles = readDatabase.articleReader(category, term, limit);
            response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
            JSONObject data = new JSONObject();
            JSONObject pagination = new JSONObject().put("total", articles.size());
            JSONArray documents = new JSONArray();
            for (Article article : articles) {
                JSONObject jsonArticle = new JSONObject(article);
                documents.put(jsonArticle);
            }
            data.put("pagination", pagination);
            data.put("documents", documents);
            response.getWriter().print(data);

        } else {
            response.setStatus(401);
            response.getWriter().println("401: Unauthorized");
        }
        baseRequest.setHandled(true);
        System.out.println("search page is running...");
    };

    private boolean authorization(String authorization) {
        if (authorization == null) {
            return false;
        } else if (authorization.isEmpty()) {
            return false;
        } else if (authorization.equals("Basic Sm9zaHVhOlRlc3Q=")) {
            return true;
        }
        return false;
    }
}