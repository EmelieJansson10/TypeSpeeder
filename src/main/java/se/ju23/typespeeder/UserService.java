package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserService implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hej och välkommen till TypeSpeeder!");
        System.out.println("Börja med att registrera ny användare eller logga in.");

        Menu.setUserService(this);
        Menu.displayLoginOrRegisterOptions();
        if (Menu.loggedInUser !=null){
            Menu.displayMenu(Challenge.messages);
        }
    }
}
