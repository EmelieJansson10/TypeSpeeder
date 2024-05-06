package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static se.ju23.typespeeder.Challenge.timeSeconds;

@Service
public class PlayerRankingService  {
    @Autowired
    PlayerRankingRepository repository;
    @Autowired
    UserRepository userRepository;

    public void updatePlayerRanking(){
        String username = Menu.loggedInUser.getUsername();
        System.out.println(username);
        User playerUser = userRepository.findByUsername(username);
        System.out.println(playerUser);
        PlayerRanking player = repository.findByUserUsername(username);
        System.out.println(player);
        if (player!=null) {
            double score = calculateScore();
            double result = player.getResult();
            player.setResult(score > 1 ? result + score : result - score);
            updateLevel(player);
            repository.save(player);
        } else {
            double score = calculateScore();
            int levelNumber = calculateLevel(score);
            PlayerRanking newPlayer = new PlayerRanking(playerUser, score, levelNumber);
            repository.save(newPlayer);
        }
    }
    private double calculateScore(){
        int wordsValue = 1;
        int orderValue = 2;
        int wordPoints = Challenge.countWords * wordsValue;
        int orderPoints = Challenge.countOrder * orderValue;
        int points = wordPoints + orderPoints;
        return timeSeconds != 0 ? (double) points / timeSeconds : 0.0;
    }
    private void updateLevel(PlayerRanking player) {
        double result = player.getResult();
        int levelNumber = result >= 5 ? (int) (result / 5 + 1) : 1;
        player.setLevel(levelNumber);
    }
    private int calculateLevel(double score) {
        return score >= 5 ? (int) (score / 5 + 1) : 1;
    }
    public void printRankingList(List<PlayerRanking> topList) {
        System.out.println("Ranking List:\nPlace    Player      Score       Level\n");
        int position = 1;
        topList.sort((p1, p2) -> Double.compare(p1.result, p2.result));

        for(PlayerRanking player : topList){
            System.out.printf(String.format("%-9d%-10s%7.2f%9d%n", position++, player.user.username, player.result, player.level));
        }
    }
    public void showRankingList() throws IOException {
        updatePlayerRanking();
        String username = Menu.loggedInUser.getUsername();
        List<PlayerRanking> playerRankingList = new ArrayList<>();
        PlayerRanking player = repository.findByUserUsername(username);
        if(player!=null){
            playerRankingList.add(player);
            printRankingList(playerRankingList);
        }else{
            System.out.println("Hitta inte spelare");
        }
        Challenge.returnToMenu();
    }

    /*public void level(){
        levelNumber = 1;
        PlayerRanking player = repository.findByName(username);
        if (player!=null) {
            double result = player.getResult();
            if (result>=5){
                levelNumber = (int) (result / 5 + 1);
            } else {
                levelNumber = 1;
            }
            player.setLevel(levelNumber);
            repository.save(player);
        }

    }*/

}
