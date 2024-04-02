package se.ju23.typespeeder;

import jakarta.persistence.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static se.ju23.typespeeder.TypeSpeederApplication.userService;

public class Challenge {
    public static ResourceBundle messages = ResourceBundle.getBundle("Messages");
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    public static Scanner input = new Scanner(System.in);
    public static Scanner scanner = new Scanner(System.in);

    public static boolean stopTimer = false;
    public static long startTime;
    public static String game;
    public static String game2;
    public static String colorWords;
    public static String colorLetters;
    public static int countWords;
    public static int countLetters;
    public static int countOrder;
    public static long timeSeconds;
    public static int gameChoice;
    public static int difficulty;
    public static List<String> redWords = new ArrayList<>();
    public static List<String> redLetters = new ArrayList<>();
    public static final String WHITE = "\u001B[37m";
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREY = "\u001B[32m";
    public static ArrayList<PlayerRanking>rankingList = new ArrayList<>();
    public static Thread timer;

    public static void changeLanguage() throws IOException {
        long startTime = System.nanoTime();

        System.out.print(messages.getString("language"));
        String language = input.nextLine().toLowerCase();

        if ("en".equalsIgnoreCase(language)) {
            messages = ResourceBundle.getBundle("messages", new Locale(language, "US"));

        } else if ("sv".equalsIgnoreCase(language)) {
            messages = ResourceBundle.getBundle("messages", new Locale(language, "SE"));

        } else {
            System.out.println("Ogiltigt språkval. Använder systemets standardspråk.");
            messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        }
        String selectedLanguage = "";
        if(language.equals("sv")){
            selectedLanguage = "Svenska";
        } else if (language.equals("en")) {
            selectedLanguage = "English";
        }
        System.out.println(messages.getString("language.changed") + selectedLanguage);
        returnToMenu();

    }

    public static void startGameAfterLanguageSelection() throws IOException {
        System.out.print(messages.getString("you.want.play"));
        String playGame = input.nextLine().toLowerCase();
        if ("ja".equalsIgnoreCase(playGame) || "yes".equalsIgnoreCase(playGame)) {
            startChallenge();
        } else {
            returnToMenu();
        }
    }

    public static void startChallenge() throws IOException {
        while (true){
            System.out.println(messages.getString("game.type"));
            System.out.println(messages.getString("word"));
            System.out.println(messages.getString("characters"));
            System.out.print(messages.getString("choose.number"));
            try{
                gameChoice = input.nextInt();
                input.nextLine();
                switch (gameChoice){
                    case 1 -> typeWords();
                    case 2 -> lettersToType();
                    default -> System.out.println("Felaktig inmatning, försök igen.");
                }
            } catch(InputMismatchException e) {
                System.out.println("Ogiltlig inmatning. Försök igen->");
                input.nextLine();
            }
        }


    }
    public static void typeWords() throws IOException {
        boolean continueGame = true;
        gameLevel();
        while(continueGame){
            stopTimer = false;
            game = "";
            redWords = new ArrayList<>();
            countWords = 0;
            countOrder = 0;
            System.out.println(messages.getString("game.instructions"));
            System.out.println(messages.getString("time.starts"));
            System.out.print(messages.getString("press.enter.to.play"));
            input.nextLine();
            openGame1();
            timer();
            timer.start();
            System.out.println();
            System.out.print(messages.getString("write.here"));
            game = input.nextLine();
            stopTimer = true;
            try {
                timer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkSpelling();
            checkOrder();
            returnToMenu();
        }
    }
    public static void gameLevel(){
        boolean level = true;
        while (level) {
            System.out.println(messages.getString("choose.level"));
            System.out.println(messages.getString("easy"));
            System.out.println(messages.getString("hard"));
            System.out.print(messages.getString("choose.number"));
            try {
                difficulty = input.nextInt();
                input.nextLine();
                if (difficulty==1||difficulty==2){
                    level=false;
                }
            }catch(InputMismatchException e) {
                System.out.println("Ogiltlig inmatning. Försök igen->");
                input.nextLine();
            }
        }
    }

    public static void lettersToType() throws IOException {
        boolean continueGame2 = true;
        while(continueGame2){
            stopTimer = false;
            game2 = "";
            redLetters = new ArrayList<>();
            countLetters = 0;
            countOrder = 0;
            System.out.println(messages.getString("game2.instructions"));
            System.out.println(messages.getString("time.starts"));
            System.out.print(messages.getString("press.enter.to.play"));
            input.nextLine();
            openGame2();
            timer();
            timer.start();
            System.out.println();
            System.out.print(messages.getString("write.here"));
            game2 = input.nextLine();
            stopTimer = true;
            try {
                timer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkLetters();
            checkOrder();
            returnToMenu();
        }

    }
    public static void openGame1() throws IOException {
        String textFile = "";
        StringBuilder colorWordsBuilder = new StringBuilder();
        if (gameChoice == 1) {
            File file;
            if (difficulty == 1) {
                List<String> lines = Files.readAllLines(new File("./src/main/resources/TextFile").toPath());
                int randomIndex = new Random().nextInt(lines.size());
                String randomLine = lines.get(randomIndex);
                textFile = randomLine;

            } else if (difficulty == 2) {
                BufferedReader reader;
                String[]filepath = {
                        "./src/main/resources/TextFile2",
                        "./src/main/resources/TextFile3"
                };
                String randomFilePath = pickRandomFilePath(filepath);
                reader = new BufferedReader(new FileReader(randomFilePath));
                String lines = reader.readLine();

                while (lines != null) {
                    textFile += lines + "\n";
                    lines = reader.readLine();
                }
            } else {
                System.out.println("Alternativt finns, inte försök igen.");
            }
            String[] words = textFile.split(" ");
            for (String word : words) {
                String color = Math.random() < 0.5 ? RED : WHITE;
                colorWordsBuilder.append(color).append(word).append(" ");
                System.out.print(color + word + " " + RESET);
            }
        }
        colorWords = colorWordsBuilder.toString();
    }
    public static void openGame2() throws IOException {
        String textFile = "";
        StringBuilder colorWordsBuilder = new StringBuilder();
        List<String> lines = Files.readAllLines(new File("./src/main/resources/TextFile").toPath());
        int randomIndex = new Random().nextInt(lines.size());
        String randomLine = lines.get(randomIndex);
        textFile = randomLine;
        char[] letters = textFile.toCharArray();
        for (char letter : letters) {
            String color = Math.random() < 0.5 ? RED : WHITE;
            colorWordsBuilder.append(color).append(letter).append(" ");
            System.out.print(color + letter + " " + RESET);
        }
        colorWords = colorWordsBuilder.toString();

    }
    public static void timer(){
            timer = new Thread(()->{
            startTime = System.currentTimeMillis();
            while (!stopTimer) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            timeSeconds = (System.currentTimeMillis() - startTime) / 1000;

                System.out.print(messages.getString("your.time") + timeSeconds + " ");
                System.out.println(messages.getString("seconds"));
        });

    }
    public static void checkSpelling(){
        String [] words = colorWords.split("\\s+");
        for (String word : words) {
            if (word.startsWith(RED)) {
                redWords.add(word.substring(RED.length()));
            }
        }
        String [] gameList = game.split("\\s+");
        for (String list : gameList){
            for(String red : redWords){
                if (red.equals(list)){
                    countWords++;
                }
            }
        }
       System.out.println(messages.getString("correct.words") + countWords);

    }
    public static void checkLetters(){
        String[] letters = colorWords.split("\\s+");
        for(String letter : letters){
            if (letter.startsWith(RED)) {
                String trimmedLetters = letter.substring(RED.length()).trim();
                String cleanLetters = trimmedLetters.replaceAll("[^a-öA-Ö]", "");
                if(!cleanLetters.isEmpty()) {
                    redLetters.add(cleanLetters);
                }
            }
        }
        //System.out.println(redLetters);
        String [] gameLetters = game2.split("\\s+");
        System.out.println(game2);
        for (String list : gameLetters){
            for(String red : redLetters){
                if (red.equals(list.trim())){
                    countLetters++;
                }
            }
        }
        System.out.println(messages.getString("correct.characters") + countLetters);
    }
    public static void checkOrder(){
        if(gameChoice == 1) {
            String[] gameList = game.split("\\s+");
            int minWord = Math.min(redWords.size(), gameList.length);
            for (int i = 0; i < minWord; i++) {
                if (gameList[i].equals(redWords.get(i))) {
                    countOrder++;
                }
            }
            System.out.println(messages.getString("right.order") + countOrder);
        } else {
            String [] gameList2 = game2.split("\\s+");
            int minWord = Math.min(redLetters.size(), gameList2.length);
            for (int i = 0; i < minWord; i++) {
                if (gameList2[i].equals(redLetters.get(i))) {
                    countOrder++;
                }
            }
            System.out.println(messages.getString("right.char.order") + countOrder);
        }
    }
    public static void returnToMenu() throws IOException {
        System.out.print(messages.getString("return.menu"));
        String goBack = input.nextLine().toLowerCase();
        if ("ja".equalsIgnoreCase(goBack) || "yes".equalsIgnoreCase(goBack)) {
            Menu.displayMenu(messages);
        }
    }
    public static String pickRandomFilePath(String[] filepath){
        int randomPath = ThreadLocalRandom.current().nextInt(filepath.length);
        return filepath[randomPath];
    }

}
