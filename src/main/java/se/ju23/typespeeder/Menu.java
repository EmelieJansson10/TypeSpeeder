package se.ju23.typespeeder;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

import static se.ju23.typespeeder.Challenge.returnToMenu;
import static se.ju23.typespeeder.Challenge.startChallenge;
import static se.ju23.typespeeder.Challenge.*;


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
                    case 2 -> PlayerRanking.showRankingList();
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
                System.out.print("Ange nytt användarnamn (tryck enter om du inte vill ändra): ");
                String newUsername = input.nextLine();

                System.out.print("Ange nytt lösenord (tryck enter om du inte vill ändra): ");
                String newPassword = input.nextLine();

                if (!newUsername.isEmpty() || !newPassword.isEmpty()) {

                    loggedInUser.updateCredentials(newUsername, newPassword);
                    userService.userRepository.save(loggedInUser);
                    System.out.println("Användarnamn och/eller lösenord är uppdaterade!");
                    System.out.println("Vill du återgå till huvudmenyn? (ja/nej): ");
                    String goBack = input.nextLine().toLowerCase();

                    if ("ja".equals(goBack)) {
                        displayMenu(messages);
                    } else {
                        System.out.println("Programmet avslutas.");

                    }
                }

            }


        }
    }


    public static void logOut() {
        LoggedInUser = null;
        System.out.println("Du har loggats ut.");
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
