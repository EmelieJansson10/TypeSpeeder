package se.ju23.typespeeder;

import jakarta.persistence.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static se.ju23.typespeeder.Challenge.timeSeconds;

@Entity
@Table(name="playerranking")

public class PlayerRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    static String name;

    @Column(name = "result")
    static double result;

    @Column(name = "level")
    static int level;

    public static float score;
    public static int levelNumber;
    public static String username = Menu.loggedInUsername;
    public static ArrayList<PlayerRanking>rankingList = new ArrayList<>();

    public PlayerRanking(String name, double result) {
        this.name = name;
        this.result = result;
    }
    public PlayerRanking(String name, double result, int level) {
        this.name = name;
        this.result = result;
        this.level = level;
    }

    public PlayerRanking() {

    }

    public String getName() {
        return name;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public static ArrayList<PlayerRanking> rankingList(Connection conn){
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
        for (PlayerRanking player : rankingList) {
            if (player.getName().equals(username)) {
                double result = player.getResult();
                if(score>1){
                    player.setResult(result + score);
                    playerExist = true;
                    break;
                } else {
                    player.setResult(result - score);
                    playerExist = true;
                    break;
                }

            }
        }
        level();
        if (!playerExist){
            rankingList.add(new PlayerRanking(name, result, level));
            try {
                savePlayerData(name, result, level, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rankingList;
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
    public static void showRankingList() throws IOException {
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

    public static void level(){
        levelNumber = 1;
        for (PlayerRanking player : rankingList) {
            if (player.getName().equals(username)) {
                double result = player.getResult();
                if (result>=5){
                    levelNumber = (int) (result / 5 + 1);
                } else {
                    levelNumber = 1;
                }
                player.setLevel(levelNumber);
            }
        }
    }
}
