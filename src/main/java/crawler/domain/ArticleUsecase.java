package crawler.domain;

import java.util.List;

import crawler.infrastructure.IRssReader;

public class ArticleUsecase {

    final private IRssReader reader;

    public ArticleUsecase(IRssReader reader){
        this.reader = reader;
    }

    public List<Article> getArticlesOfCategory(String category, int maxAmount){
        String urlAdress = reader.urlBuilder(category);
        return reader.fetchArticles(urlAdress, 30, category);
    }
}