package crawler.infrastructure;

import crawler.domain.Article;
import java.util.*;

public interface IRssReader{
    public List<Article> fetchArticles(final String urlAdress, int maxAmount, String category);
    public String urlBuilder(String categoryInput);
}