package crawler.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import crawler.domain.Article;

public class RssfeedReader implements IRssReader {

    public List<Article> fetchArticles(final String urlAdress, int maxAmount, String category) {

        try {
            List<Article> articles = new ArrayList<Article>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(urlAdress);
            doc.getDocumentElement().normalize();

            for (int i = 0; i < maxAmount; i++) {

                int guid = 0;

                final Node nodeTitle = doc.getElementsByTagName("title").item(i + 2);
                String title = nodeTitle.getTextContent();
                final Node nodeGuid = doc.getElementsByTagName("guid").item(i);
                String guidAsString = nodeGuid.getTextContent();
                if (!guidAsString.equals(null)) {
                    guid = Integer.parseInt(guidAsString);
                }
                final Node nodePubDate = doc.getElementsByTagName("pubDate").item(i + 1);
                String pubDate = nodePubDate.getTextContent();
                final Node nodeDescription = doc.getElementsByTagName("description").item(i + 1);
                String description = nodeDescription.getTextContent();
                description = description.replace("'", "");
                title = title.replace("'", "");
                description = description.replace("-", " ");
                title = title.replace("-", " ");
                final Node nodeLink = doc.getElementsByTagName("link").item(i + 2);
                String link = nodeLink.getTextContent();

                if (guid != 0) {
                    Article article = new Article(title, category, guid, pubDate, description, link);
                    articles.add(article);
                }
            }
            return articles;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Article>();
    }

    public String urlBuilder(String categoryInput) {
        return "https://www.welt.de/feeds/" + categoryInput + ".rss";
    }
}