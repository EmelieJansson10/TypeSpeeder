package se.ju23.typespeeder;

import jakarta.persistence.*;


@Entity
@Table(name = "user")
public class User {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
    long id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "displayname")
    String displayname;

    public User(){}

    public User(String username, String password, String displayname) {
        this.username = username;
        this.password = password;
        this.displayname = displayname;
    }

    public User(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", displayname='" + displayname + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void updateCredentials(String newUsername, String newPassword) {
        this.username = newUsername;
        this.password = newPassword;
    }



}