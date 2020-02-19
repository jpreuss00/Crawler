package src.main.java.crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import src.main.java.crawler.domain.StorageUsecase;

public class ExecuteTimer {
    public void timing(StorageUsecase storageUsecase) {

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ArrayList<String> categories = new ArrayList<String>(
                        Arrays.asList("latest", "topnews", "section/mediathek", "section/videos", "section/politik",
                                "section/wirtschaft", "section/wirtschaft/bilanz", "section/finanzen",
                                "section/wirtschaft/webwelt", "section/wissenschaft", "section/kultur", "section/sport",
                                "section/icon", "section/gesundheit", "section/vermischtes", "section/motor",
                                "section/reise", "section/regionales", "section/debatte"));
                for (String category : categories) {
                    storageUsecase.fetchAndSave(category, 30);
                }
            }
        }, 3000, TimeUnit.MINUTES.toMillis(5));
    }
}