package se.ju23.typespeeder;

import org.springframework.format.datetime.DateFormatter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import  java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


import static se.ju23.typespeeder.Menu.input;

public class NewsLetter {
    public String content;
    public LocalDateTime publishDateTime;

    public NewsLetter(String content, LocalDateTime publishDateTime)throws IOException{
        this.content=content;
        this.publishDateTime=publishDateTime;
    }


    public static void showNewsAndUpdates() throws IOException {
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.FEBRUARY,13,13,23,35);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, Month.FEBRUARY,15,16,07,12);
        LocalDateTime dateTime3 = LocalDateTime.of(2024, Month.FEBRUARY,20,15,14,55);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTime.format(formatter);
        LocalDateTime formattedDateTime = LocalDateTime.parse(formattedDate,formatter);
        ArrayList<NewsLetter>newsLetterArrayList = new ArrayList<>();
        NewsLetter news1 = new NewsLetter(Challenge.messages.getString("news1"), LocalDateTime.parse(dateTime.format(formatter),formatter));
        NewsLetter news2 = new NewsLetter(Challenge.messages.getString("news2"), LocalDateTime.parse(dateTime2.format(formatter),formatter));
        NewsLetter news3 = new NewsLetter(Challenge.messages.getString("news3"), LocalDateTime.parse(dateTime3.format(formatter),formatter));

        newsLetterArrayList.add(news1);
        newsLetterArrayList.add(news2);
        newsLetterArrayList.add(news3);
        for(NewsLetter letter : newsLetterArrayList){
            System.out.println(letter);
        }
        Challenge.returnToMenu();

    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(LocalDateTime publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    @Override
    public String toString() {
        return Challenge.messages.getString("news") + content + Challenge.messages.getString("publish") + publishDateTime;
    }
}
