package src.main.java.com.github.jpreuss00.crawler.infrastructure;

import src.main.java.com.github.jpreuss00.crawler.domain.Article;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

public class RssfeedReader {

    public List<Article> readRssfeed(String urlAdress, int maxAmount) {
        try {
            List<Article> articles = new ArrayList<Article>();
            URL rssUrl = new URL(urlAdress);
            BufferedReader inputStreamRss = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String line;
            int counter = 0;
            while ((line = inputStreamRss.readLine()) != null) {
                if (line.contains("<title>")) {
                    int firstPos = line.indexOf("<title>");
                    String temp = line.substring(firstPos);
                    temp = temp.replace("<title>", "");
                    int lastPos = temp.indexOf("</title>");
                    temp = temp.substring(0, lastPos);
                    if (counter > 1) {
                        Article article = new Article(temp);
                        articles.add(article);
                    }
                    counter++;
                    if (counter - 2 == maxAmount) {
                        break;
                    }
                }
            }
            inputStreamRss.close();
            return articles;
        } catch (MalformedURLException ue) {
            System.out.println("Malformed URL");
        } catch (IOException ioe) {
            System.out.println("Something went wrong reading the contents");
        }
        return new ArrayList<Article>();
    }

    public String urlBuilder(String categoryInput) {
        return "https://www.welt.de/feeds/section/" + categoryInput + ".rss";
    }
}