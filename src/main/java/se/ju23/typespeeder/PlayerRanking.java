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
    Long userId;

    //@Column(name = "name")
    //String name;
    //@JoinColumn(name = "user", referencedColumnName = "username")

    @Column(name = "score")
    double score;

    @Column(name = "level")
    int level;

    public PlayerRanking(Long userId, double score, int level) {
        this.userId = userId;
        this.score = score;
        this.level = level;
    }

    public PlayerRanking() {

    }

    public PlayerRanking(double score, int levelNumber) {
    }

    /*public String getName() {
        return name;
    }*/


    public Long getUserId() {
        return userId;
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
                "userId=" + userId +
                ", score=" + score +
                ", level=" + level +
                '}';
    }
}
