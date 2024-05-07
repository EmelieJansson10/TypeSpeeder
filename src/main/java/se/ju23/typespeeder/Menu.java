package se.ju23.typespeeder;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

import static se.ju23.typespeeder.Challenge.startChallenge;


@Component
public class Menu implements MenuService {
    private static UserService userService;
    public static User loggedInUser;
    private static Object LoggedInUser;
    public static String loggedInUsername;
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    public static ResourceBundle messages = ResourceBundle.getBundle("Messages");
    static List<String> MenuOptions = new ArrayList<>();
    public static Scanner input = new Scanner(System.in);

    public static String userName;
    public static String passWord;
    public static boolean loggedIn=false;
    private static PlayerRankingService playerRankingService;


    public Menu (PlayerRankingService playerRankingService) {
        this.playerRankingService = playerRankingService;
    }

    public static void displayMenu(ResourceBundle messages) throws IOException {
        UserService userService = TypeSpeederApplication.userService;
        while (true){
            MenuOptions.clear();
            MenuOptions.add(messages.getString("menu1"));
            MenuOptions.add(messages.getString("menu2"));
            MenuOptions.add(messages.getString("menu3"));
            MenuOptions.add(messages.getString("menu4"));
            MenuOptions.add(messages.getString("menu5"));
            MenuOptions.add(messages.getString("menu6"));
            for (String option : MenuOptions) {
                System.out.println(option);
            }
            System.out.print(messages.getString("choose.number"));


            try {
                int menuChoice = input.nextInt();
                input.nextLine();

            if (loggedInUser == null && menuChoice == 0) {
                logIn();
            } else {
                switch (menuChoice) {
                    case 6 -> logOut();
                    case 1 -> startChallenge();
                    case 2 -> playerRankingService.showRankingList();
                    case 3 -> NewsLetter.showNewsAndUpdates();
                    case 4 -> Challenge.changeLanguage();
                    case 5 -> updateUser();

                        default -> System.out.println("Felaktig inmatning, försök igen.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Felaktig inmatning, ange en siffra.");
                input.nextLine();
            }
        }
    }


    public static void displayLoginOrRegisterOptions() {
        System.out.println("1. Registrera ny användare");
        System.out.println("2. Logga in.");

        try {
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> logIn();

                default -> System.out.println("Felaktig inmatning, välj 1 eller 2.");

            }
        } catch (InputMismatchException e) {
            System.out.println("Felaktig inmatning, välj 1 eller 2.");
            input.nextLine();
            displayLoginOrRegisterOptions();
        }
    }

    public static void registerUser() {
        System.out.println("Registrera ny användare: ");

        System.out.print("Ange användarnamn:");
        String username = input.nextLine();

        System.out.print("Ange lösenord:");
        String password = input.nextLine();

        System.out.println("Ange spelnamn: ");
        String displayname = input.nextLine();

        User newUser = new User(username, password, displayname);
        userService.userRepository.save(newUser);

        System.out.println("Registrering lyckades, vänligen logga in som din nya användare nedan.");

        logIn();
    }



    public static void logIn() {


        while (!loggedIn) {
            System.out.print("Ange användarnamn: ");
            userName = input.nextLine();
            System.out.print("Ange lösenord: ");
            passWord = input.nextLine();

            loggedInUser = userService.userRepository.findByUsernameAndPassword(userName, passWord);

            if (loggedInUser != null) {
                System.out.println("Inloggning lyckades!");
                loggedInUsername = loggedInUser.getUsername();
                loggedIn = true;
            } else {
                System.out.println("Felaktiga inloggningsuppgifter. Försök igen.");
            }
        }

    }



    public static void updateUser() throws IOException {
        while (true) {
            if (loggedInUser != null) {
                System.out.println("Vill du uppdatera användarnamn, lösenord eller spelnamn?");
                System.out.println("1. Användarnamn");
                System.out.println("2. Lösenord");
                System.out.println("3. Spelnamn");
                System.out.println("4. Tillbaka till menyn");
                int choice = Integer.parseInt(input.nextLine());

                switch (choice) {
                    case 1 -> {
                        System.out.print("Ange nytt användarnamn: ");
                        String newUsername = input.nextLine();
                        loggedInUser.setUsername(newUsername);

                    }
                    case 2 -> {
                        System.out.print("Ange nytt lösenord: ");
                        String newPassword = input.nextLine();
                        loggedInUser.setPassword(newPassword);

                    }
                    case 3 -> {
                        System.out.print("Ange nytt spelnamn: ");
                        String newDisplayName = input.nextLine();
                        loggedInUser.setDisplayname(newDisplayName);

                    }
                    case 4 -> {
                        Challenge.returnToMenu();
                        return;
                    }
                    default -> {
                        System.out.println("Ogiltigt val. Försök igen.");
                        continue;
                    }
                }

                userService.userRepository.save(loggedInUser);
                System.out.println("Uppdatering lyckades.");
                Challenge.returnToMenu();
                break;
            }
        }
    }



    public static void logOut() {
        LoggedInUser = null;
        System.out.println(Challenge.messages.getString("logged.out"));
        System.exit(0);
    }


    static void setUserService(UserService userService) {
       Menu.userService = userService;
    }

    public static void loadResources() {
        messages = ResourceBundle.getBundle("messages", Locale.getDefault());
    }


    public List<String> getMenuOptions() {
        return MenuOptions;
    }
}
