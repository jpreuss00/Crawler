package src.main.java.com.github.jpreuss00.crawler.infrastructure;

import java.util.List;

import src.main.java.com.github.jpreuss00.crawler.domain.*;

public class Cli {

    private final ArticleUsecase articleUsecase;

    public Cli(ArticleUsecase articleUsecase){
        this.articleUsecase = articleUsecase;
    }

    public Cli(){
        this.articleUsecase = null;
    }

    public String handleInput(String args[]) {
        if (args.length == 0) {
            return "Please enter valid arguments! Formula: java Crawler.java [Category] [Amount]";
        } else if (args.length > 2) {
            return "Please enter 2 arguments maximum! Formula: java Crawler.java [Category] [Amount]";
        } else {
            String category = args[0];
            int maxAmount = articleAmount(args);
            List<Article> articles = articleUsecase.getArticlesOfCategory(category, maxAmount);
            String articleStr = "\n";
            int counter = 1;
            if(articles.size() == 0){
                return "Please enter a valid category!";
            }
            for(Article article : articles){
                articleStr += counter + ": " + article + "\n";
                counter++;
            }
            return "Searching for articles with the category: " + category + "!" + articleStr;
        }
    }

    private int articleAmount(String args[]) {
        int articleAmount = 1;
        if (args.length == 2) {
            articleAmount = Integer.parseInt(args[1]);
        }
        return articleAmount;
    }
}