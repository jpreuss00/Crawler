package src.main.java.crawler.domain;

import java.util.List;

import src.main.java.crawler.infrastructure.ArticleRepository;

public class StorageUsecase {

    private ArticleRepository articleRepository;
    private ArticleUsecase articleUsecase;

    public StorageUsecase(ArticleUsecase articleUsecase, ArticleRepository articleRepository) {
        this.articleUsecase = articleUsecase;
        this.articleRepository = articleRepository;
    }

    public void fetchAndSave(String category, int maxamount){
        List<Article> articlelist = articleUsecase.getArticlesOfCategory(category, 30);
        for ( Article article : articlelist) {
           articleRepository.save(article);
        };
    }
}