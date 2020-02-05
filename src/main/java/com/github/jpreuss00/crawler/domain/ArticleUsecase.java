package src.main.java.com.github.jpreuss00.crawler.domain;

import java.util.*;

import src.main.java.com.github.jpreuss00.crawler.infrastructure.RssfeedReader;

public class ArticleUsecase {

    final private RssfeedReader reader;

    public ArticleUsecase(RssfeedReader reader){
        this.reader = reader;
    }

    public List<Article> getArticlesOfCategory(String category){
        return getArticlesOfCategory(category, 20);
    }

    public List<Article> getArticlesOfCategory(String category, int maxAmount){
        String urlAdress = reader.urlBuilder(category);
        return reader.readRssfeed(urlAdress, maxAmount);
    }
}