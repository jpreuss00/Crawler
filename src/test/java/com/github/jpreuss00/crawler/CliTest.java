package src.test.java.com.github.jpreuss00.crawler;

import org.junit.Test;
import static org.junit.Assert.*;

import src.main.java.com.github.jpreuss00.crawler.infrastructure.Cli;

public class CliTest{

    @Test
    public void testCli_for_correct_output_with_given_category_Politik_input(){
        Cli cli = new Cli();
        String[] args = new String[] {"Politik", "1"};
        String actual = cli.handleInput(args);
        String expected = "Searching for articles with the category: Politik!1: Ramelow scheitert auch im zweiten Wahlgang zum Ministerpräsidenten";
        assertEquals(actual, expected);
    }

    @Test
    public void testCli_for_correct_output_with_given_0_amount_input(){
        Cli cli = new Cli();
        String[] args = new String[] {"Politik", "0"};
        String actual = cli.handleInput(args);
        String expected = "Please enter a valid category!";
        assertEquals(actual, expected);
    }

    @Test
    public void testCli_for_correct_output_with_given_0_arguments_input(){
        Cli cli = new Cli();
        String[] args = new String[] {};
        String actual = cli.handleInput(args);
        String expected = "Please enter valid arguments! Formula: java Crawler.java [Category] [Amount]";
        assertEquals(actual, expected);
    }

    @Test
    public void testCli_for_correct_output_with_given_3_arguments_input(){
        Cli cli = new Cli();
        String[] args = new String[] {"Politik", "0", "fgjds9"};
        String actual = cli.handleInput(args);
        String expected = "Please enter 2 arguments maximum! Formula: java Crawler.java [Category] [Amount]";
        assertEquals(actual, expected);
    }
    
    @Test
    public void testCli_for_correct_output_with_given_invalid_arguments_input(){
        Cli cli = new Cli();
        String[] args = new String[] {"zututut", "0"};
        String actual = cli.handleInput(args);
        String expected = "Please enter 2 arguments maximum! Formula: java Crawler.java [Category] [Amount]";
        assertEquals(actual, expected);
    }

    private void assertEquals(String actual, String expected) {
    }
    
}