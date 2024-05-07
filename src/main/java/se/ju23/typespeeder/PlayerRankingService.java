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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static se.ju23.typespeeder.Challenge.timeSeconds;

@Service
public class PlayerRankingService  {
    @Autowired
    PlayerRankingRepository repository;
    @Autowired
    UserRepository userRepository;

    public void updatePlayerRanking(){
        Long userid = Menu.loggedInUser.getId();
        PlayerRanking player = repository.findByUserId(userid);

        if (player!=null) {
            double score = calculateScore();
            double result = player.getScore();
            player.setScore(score > 1 ? result + score : result - score);
            updateLevel(player);
            repository.save(player);
        } else {
            double score = calculateScore();
            int levelNumber = calculateLevel(score);
            PlayerRanking newPlayer = new PlayerRanking(userid, score, levelNumber);
            repository.save(newPlayer);
        }
    }
    private double calculateScore(){
        int wordsValue = 1;
        int orderValue = 2;
        int wordPoints = Challenge.countWords * wordsValue;
        int orderPoints = Challenge.countOrder * orderValue;
        double points = wordPoints + orderPoints;
        if(points==0.0){
            return points;
        } else {
            return timeSeconds != 0 ? (double) points / timeSeconds : 0.0;
        }
    }
    private void updateLevel(PlayerRanking player) {
        int levelNumber = 1;
        double result = player.getScore();
        levelNumber = result >= 5 ? (int) (result / 5 + 1) : 1;
        player.setLevel(levelNumber);
    }
    private int calculateLevel(double score) {
        return score >= 5 ? (int) (score / 5 + 1) : 1;
    }
    public void printRankingList(List<PlayerRanking> topList) {
        System.out.println("Ranking List:\nPlace    Player            Score      Level\n");
        int position = 1;

        for(PlayerRanking player : topList){
            Long userid = player.getUserId();
            User users = userRepository.findById(userid);
            String displayname = users.getDisplayname();


            System.out.printf(String.format("%-9d%-13s%10.2f%9d%n", position++,displayname, player.score, player.level));
        }
    }
    public void showRankingList() throws IOException {
        updatePlayerRanking();
        List<PlayerRanking> playerRankings = repository.findAllByOrderByLevelDesc();
        printRankingList(playerRankings);
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

    public PlayerRankingService(PlayerRankingRepository repository) {
        this.repository = repository;
    }
}
