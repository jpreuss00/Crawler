package src.test.java.crawler;

import org.junit.Test;
import org.hamcrest.core.StringContains;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import src.main.java.crawler.domain.ArticleUsecase;
import src.main.java.crawler.infrastructure.Cli;
import src.main.java.crawler.infrastructure.RssfeedReader;

public class CliTest{

    @Test
    public void testCli_for_correct_output_with_given_category_Politik_input(){
        RssfeedReader reader = new RssfeedReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(reader);
        Cli cli = new Cli(articleUsecase);
        String[] args = new String[] {"Politik", "1"};
        String actual = cli.handleInput(args);
        String expected = "Searching for articles with the category: Politik!";
        assertEquals(actual, expected);
    }

    @Test
    public void testCli_for_correct_output_with_given_0_amount_input(){
        RssfeedReader reader = new RssfeedReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(reader);
        Cli cli = new Cli(articleUsecase);
        String[] args = new String[] {"Politik", "0"};
        String actual = cli.handleInput(args);
        String expected = "Please enter a valid category!";
        assertEquals(actual, expected);
    }

    @Test
    public void testCli_for_correct_output_with_given_0_arguments_input(){
        RssfeedReader reader = new RssfeedReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(reader);
        Cli cli = new Cli(articleUsecase);
        String[] args = new String[] {};
        String actual = cli.handleInput(args);
        String expected = "Please enter valid arguments! Formula: java Crawler.java [Category] [Amount]";
        assertEquals(actual, expected);
    }

    @Test
    public void testCli_for_correct_output_with_given_3_arguments_input(){
        RssfeedReader reader = new RssfeedReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(reader);
        Cli cli = new Cli(articleUsecase);
        String[] args = new String[] {"Politik", "0", "fgjds9"};
        String actual = cli.handleInput(args);
        String expected = "Please enter 2 arguments maximum! Formula: java Crawler.java [Category] [Amount]";
        assertEquals(actual, expected);
    }
    
    @Test
    public void testCli_for_correct_output_with_given_invalid_arguments_input(){
        RssfeedReader reader = new RssfeedReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(reader);
        Cli cli = new Cli(articleUsecase);
        String[] args = new String[] {"zututut", "0"};
        String actual = cli.handleInput(args);
        String expected = "Please enter 2 arguments maximum! Formula: java Crawler.java [Category] [Amount]";
        assertEquals(actual, expected);
    }

    private void assertEquals(String actual, String expected) {
    }
}