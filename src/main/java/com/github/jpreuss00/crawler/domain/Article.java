package src.main.java.com.github.jpreuss00.crawler.domain;

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

/*
Und in welchen Ring von Clean Architecture gehört eure CLI Klasse? Adapters
Wo gehört die Klasse "Article"? Entities
Wie könnte eine UseCase Klasse heißen? validateCategoryInput, 
 */