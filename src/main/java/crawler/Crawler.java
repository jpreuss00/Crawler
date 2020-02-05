package src.main.java.crawler;

import src.main.java.crawler.domain.ArticleUsecase;
import src.main.java.crawler.infrastructure.Cli;
import src.main.java.crawler.infrastructure.RssfeedReader;

public class Crawler {

    public static void main(String... args) {

        RssfeedReader rssfeed = new RssfeedReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(rssfeed);
        Cli cli = new Cli(articleUsecase);

        System.out.println(cli.handleInput(args));
    }
}