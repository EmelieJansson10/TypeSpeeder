package se.ju23.typespeeder;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static se.ju23.typespeeder.Challenge.timeSeconds;

@Entity
@Table(name="playerranking")
public class PlayerRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", insertable=false, updatable=false)
    int userId;

    //@Column(name = "name")
    //String name;
    //@JoinColumn(name = "user", referencedColumnName = "username")
    @ManyToOne(fetch = FetchType.EAGER)
    User user;

    @Column(name = "score")
    double score;

    @Column(name = "level")
    int level;

    /*public float score;
    public static int levelNumber;
    public static String username = Menu.loggedInUsername;
    public static ArrayList<PlayerRanking>rankingList = new ArrayList<>();
    public static PlayerRanking playerResult;

    public static PlayerRankingService service;

    public static PlayerRankingRepository repository;*/

    /*public PlayerRanking(String name, double result) {
        this.name = name;
        this.result = result;
    }
    public PlayerRanking(String name, double result, int level) {
        this.name = name;
        this.result = result;
        this.level = level;
    }*/

    public PlayerRanking(User user, double score, int level) {
        this.user = user;
        this.score = score;
        this.level = level;
    }

    public PlayerRanking() {

    }

    public PlayerRanking(String username, double score, int level) {
        this.user.username = username;
        this.score = score;
        this.level = level;
    }

    /*public String getName() {
        return name;
    }*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "PlayerRanking{" +
                "user=" + user +
                ", score=" + score +
                ", level=" + level +
                '}';
    }
    /*public static PlayerRanking rankingList(){
        int wordsValue = 1;
        int orderValue = 2;
        int wordPoints = Challenge.countWords * wordsValue;
        int orderPoints = Challenge.countOrder * orderValue;
        int points = wordPoints + orderPoints;
        score = 0.0f;
        if(timeSeconds!=0) {
            score = (float) points / timeSeconds;
        }
        boolean playerExist = false;
        PlayerRanking player = repository.findByName(username);
            if (player!=null) {
                double result = player.getResult();
                if(score>1){
                    player.setResult(result + score);

                } else {
                    player.setResult(result - score);
                }
            }
            playerResult = repository.save(player);
        level();

        if (player==null){
            playerResult = repository.save(new PlayerRanking(username, score, levelNumber));
            /*try {
                savePlayerData(username, score, levelNumber, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return playerResult;
    }

    public static void savePlayerData(String name, double result, int level, Connection conn) throws SQLException{
        String query = "INSERT INTO playerranking (name, result, level) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, result);
            stmt.setInt(3, level);
            stmt.executeUpdate();
        }
    }

    public static void printRankingList(ArrayList<PlayerRanking> topList) {
        System.out.println("Ranking List:\nPlace    Player      Score       Level\n");
        int position = 1;
        topList.sort((p1, p2) -> Double.compare(p1.result, p2.result));

        for(PlayerRanking player : topList){
            System.out.printf(String.format("%-9d%-10s%7.2f%9d%n", position++, player.name, player.result, player.level));
        }
    }
    /*public static void showRankingList() throws IOException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/typespeeder", "player", "player123!");

            ArrayList<PlayerRanking> topList = rankingList(conn);
            printRankingList(topList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn!= null){
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        Challenge.returnToMenu();
    }
    public static void showRankingList() throws IOException {
        ArrayList<PlayerRanking>playerRankingArrayList = new ArrayList<>();
        rankingList.add(rankingList());
        printRankingList(rankingList);
        Challenge.returnToMenu();
    }

    public static void level(){
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
