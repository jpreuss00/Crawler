package src.main.java.crawler.domain;

import src.main.java.crawler.infrastructure.ArticleRepository;
import src.main.java.crawler.infrastructure.IRssReader;

public class StorageUsecase {

    private IRssReader iRssReader;
    private ArticleRepository articleRepository;

    public StorageUsecase(IRssReader iRssReader, ArticleRepository articleRepository){
        this.iRssReader = iRssReader;
        this.articleRepository = articleRepository;
    }
}