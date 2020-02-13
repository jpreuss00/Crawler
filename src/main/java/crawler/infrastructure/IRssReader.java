package src.main.java.crawler.infrastructure;

import src.main.java.crawler.domain.Article;
import java.util.*;

public interface IRssReader{
    public List<Article> fetchArticles(final String urlAdress, int maxAmount);
    public String urlBuilder(String categoryInput);
}