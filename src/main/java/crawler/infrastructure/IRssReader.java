package crawler.infrastructure;

import crawler.domain.Article;
import java.util.*;

public interface IRssReader{
    List<Article> fetchArticles(final String urlAdress, int maxAmount, String category);
    String urlBuilder(String categoryInput);
}