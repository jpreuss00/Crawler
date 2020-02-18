package src.main.java.crawler.infrastructure;

import java.util.List;
import java.util.ArrayList;

import src.main.java.crawler.domain.Article;

public class FakeRssReader implements IRssReader {

    @Override
    public List<Article> fetchArticles(String urlAdress, int maxAmount, String category){
        List<Article> articles = new ArrayList<Article>();

        for(int i = 0; i < 30; i++){
            Article article = new Article("sdasf", "aew", 7, "dfg", "325-534");
            articles.add(article);
        }
        return articles;
    }

    public String urlBuilder(String categoryInput) {
        return "https://www.welt.de/feeds/" + categoryInput + ".rss";
    }
}