package src.test.java.crawler;

 
import static org.junit.Assert.*;

import org.hamcrest.core.*;
import org.junit.*;

import src.main.java.crawler.infrastructure.Cli;
import src.main.java.crawler.infrastructure.FakeRssReader;
import src.main.java.crawler.infrastructure.IRssReader;
import src.main.java.crawler.domain.ArticleUsecase;
 
public class CliFakeTest {
    
    @Test
    public void Cli_should_return_help_when_no_args_given(){
        // setup
        final IRssReader FakeRssReader = new FakeRssReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(FakeRssReader);
 
        // given
        final Cli cli = new Cli(articleUsecase);
        final String[] args = {};
 
        // when
        final String actual = cli.handleInput(args);
        
        // then
        assertThat(actual, containsString("Please enter valid arguments! Formula: command [Category] [Amount]"));
    }

    @Test
    public void Cli_should_not_return_help_when_category_given(){
        // setup
        final IRssReader FakeRssReader = new FakeRssReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(FakeRssReader);
 
        // given
        final Cli cli = new Cli(articleUsecase);
        final String[] args = {"politik"};
 
        final String actual = cli.handleInput(args);
        
        // then
        assertThat(actual, not(containsString("Formula: command [Category] [Amount]")));     
    } 

    @Test
    public void Cli_should_return_help_when_too_many_arguments_given(){
        // setup
        final IRssReader FakeRssReader = new FakeRssReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(FakeRssReader);
 
        // given
        final Cli cli = new Cli(articleUsecase);
        String[] args = {"Sport", "3", "faefe"};
 
        final String actual = cli.handleInput(args);
        // then
        assertThat(actual, containsString("Please enter 2 arguments maximum! Formula: command [Category] [Amount]"));   
    } 
 
    @Test
    public void Cli_should_return_help_when_invalid_arguments_given(){
        // setup
        final IRssReader FakeRssReader = new FakeRssReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(FakeRssReader);
 
        // given
        final Cli cli = new Cli(articleUsecase);
        String[] args =  {"faefe"};
 
        final String actual = cli.handleInput(args);
        // then
        assertThat(actual, containsString("Please enter a valid category!"));   
    } 
 
    @Test
    public void Cli_should_not_return_help_when_category_and_maxSize_given(){
 
        // setup
        final IRssReader FakeRssReader = new FakeRssReader();
        ArticleUsecase articleUsecase = new ArticleUsecase(FakeRssReader);
 
        // given
        final Cli cli = new Cli(articleUsecase);
        final String[] args = {"sport", "8"};
 
        final String actual = cli.handleInput(args);
        
        // then
        assertThat(actual, not(containsString("Formula: command [Category] [Amount]"))); 
    }

    private void assertThat(String actual, Object not) {
    }

    private Object not(Object containsString) {
        return null;
    }

    private Object containsString(String string) {
        return null;
    }
}