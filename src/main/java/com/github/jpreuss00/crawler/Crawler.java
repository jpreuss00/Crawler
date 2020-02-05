package src.main.java.com.github.jpreuss00.crawler;

import src.main.java.com.github.jpreuss00.crawler.domain.*;
import src.main.java.com.github.jpreuss00.crawler.infrastructure.*;

public class Crawler {

    public static void main(String... args) {

        RssfeedReader rssfeed = new RssfeedReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(rssfeed);
        Cli cli = new Cli(articleUsecase);

        System.out.println(cli.handleInput(args));
    }
}