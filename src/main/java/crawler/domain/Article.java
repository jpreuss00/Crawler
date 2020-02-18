package src.main.java.crawler.domain;

public class Article {

    private final String title;
    private final String category;
    private final int guid;
    private final String pubDate;
    private final String description;

    public Article(String title, String category, int guid, String pubDate, String description) {
        this.title = title;
        this.category = category;
        this.guid = guid;
        this.pubDate = pubDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory(){
        return category;
    }

    public int getGuid(){
        return guid;
    }

    public String getPubDate(){
        return pubDate;
    }

    public String getDescription(){
        return description;
    }

    public String toString() {
        return title;
    }
}