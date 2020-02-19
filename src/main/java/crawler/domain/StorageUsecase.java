package src.main.java.crawler.domain;

import java.util.List;

import src.main.java.crawler.ReadDatabase;
import src.main.java.crawler.infrastructure.ArticleRepository;

public class StorageUsecase {

    private ArticleRepository articleRepository;
    private ArticleUsecase articleUsecase;
    private ReadDatabase readDatabase;

    public StorageUsecase(ArticleUsecase articleUsecase, ArticleRepository articleRepository, ReadDatabase readDatabase) {
        this.articleUsecase = articleUsecase;
        this.articleRepository = articleRepository;
        this.readDatabase = readDatabase;
    }

    public void fetchAndSave(String category, int maxamount){
        List<Article> articlelist = articleUsecase.getArticlesOfCategory(category, 30);
        for ( Article article : articlelist) {
           articleRepository.save(article, readDatabase);
        };
    }
}