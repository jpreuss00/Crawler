package src.main.java.crawler.infrastructure;

import java.util.List;
import java.util.ArrayList;

import src.main.java.crawler.domain.Article;

public class FakeRssReader implements IRssReader {

    @Override
    public List<Article> fetchArticles(String urlAdress, int maxAmount){
        List<Article> articles = new ArrayList<Article>();

        for(int i = 0; i < maxAmount; i++){
            Article article = new Article("sdasf");
            articles.add(article);
        }
        return articles;
    }

    public String urlBuilder(String categoryInput) {
        return "https://www.welt.de/feeds/section/" + categoryInput + ".rss";
    }
}