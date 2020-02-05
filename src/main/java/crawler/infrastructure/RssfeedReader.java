package src.main.java.crawler.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import src.main.java.crawler.domain.Article;

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