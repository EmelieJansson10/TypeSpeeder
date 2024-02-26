package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TypeSpeederApplication implements CommandLineRunner {


    @Autowired
    public static UserService userService;


    public static void main(String[] args) {

        SpringApplication.run(TypeSpeederApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
      Menu.setUserService(userService);
    }





}


