
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.time.LocalDateTime;
import java.io.*;

public class Savegame implements Serializable {

    /**
     * Play me!
     */
    public static void main(String...args) {
        final Savegame save = Savegame.loadXml();
        int level = 1000, experience = 0, score = 0;

        if (save == null) {
            System.out.println("Starting a new game .. welcome hero!");
        } else {
            System.out.println("Continuing game .. welcome back, hero!");
            level = save.currentLevel;
            experience = save.experience;
            score = save.currentScore;
        }

        level++;
        experience = 500 * level;
        score = level * experience + score;
        System.out.println("You're now level " + level + " and have " + experience + " XP! With a score of " + score + "!");

        final Savegame newSave = new Savegame();
        newSave.currentLevel = level;
        newSave.experience = experience;
        newSave.currentScore = score;

        Savegame.saveXml(newSave);
    }

    private LocalDateTime savedAt;
    private int experience;
    private int currentLevel;
    private int currentScore;

    public static Savegame loadXml() {
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final Document dom = db.parse(new File("savegame.dat"));
            final Savegame savegame = new Savegame();

            final Element doc = dom.getDocumentElement();

            final Node level = doc.getElementsByTagName("currentLevel").item(0);
            savegame.currentLevel = Integer.parseInt(level.getTextContent());

            final Node xp = doc.getElementsByTagName("currentXp").item(0);
            savegame.experience = Integer.parseInt(xp.getTextContent());

            final Node score = doc.getElementsByTagName("currentScore").item(0);
            savegame.currentScore = Integer.parseInt(score.getTextContent());

            return savegame;
        } catch (Exception e) {
            return null;
        }
    }

    public static void saveXml(Savegame savegame) {
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final Document dom = db.newDocument();

            final Element doc = dom.createElement("savegame");

            final Element savedAt = dom.createElement("savedAt");
            savedAt.setTextContent(LocalDateTime.now().toString());

            final Element currentLevel = dom.createElement("currentLevel");
            currentLevel.setTextContent(Integer.toString(savegame.currentLevel));

            final Element currentXp = dom.createElement("currentXp");
            currentXp.setTextContent(Integer.toString(savegame.experience));

            final Element currentScore = dom.createElement("currentScore");
            currentScore.setTextContent(Integer.toString(savegame.currentScore));

            dom.appendChild(doc);
            doc.appendChild(savedAt);
            doc.appendChild(currentLevel);
            doc.appendChild(currentXp);
            doc.appendChild(currentScore);

            final Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");

            tr.transform(new DOMSource(dom),
                    new StreamResult(new FileOutputStream("savegame.dat")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}