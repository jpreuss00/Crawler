package src.main.java.crawler.domain;

public class Article {

    private final String title;

    public Article(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title;
    }
}