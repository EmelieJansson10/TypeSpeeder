package se.ju23.typespeeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TypeSpeederApplication {

    public static void main(String[] args) {
        SpringApplication.run(TypeSpeederApplication.class, args);

        System.out.println("Välkommen till TypeSpeeder!");

        logIn();

       int menuChoise = 0;
        switch (menuChoise) {
            case 0 -> logOut();
            case 1 -> playGame();
            case 2 -> showRankingList();
            case 3 -> showNewsAndUpdates();
            case 4 -> changeLanguage();
            case 5 -> updateProfile();
            default -> System.out.println("Felaktig inmatning, försök igen.");
        }
    }

    public static void logIn(){
        System.out.println("Ange användarnamn och lösenord.");
    }

    public static void logOut(){
    }

    public static void playGame(){

    }

    public static void showRankingList(){

    }

    public static void showNewsAndUpdates(){

    }

    public static void changeLanguage(){

    }

    public static void updateProfile(){

    }

}
