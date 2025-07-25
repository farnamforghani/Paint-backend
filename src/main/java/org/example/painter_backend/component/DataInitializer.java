package org.example.painter_backend.component;

import org.example.painter_backend.model.User;
import org.example.painter_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if users already exist
        if (userRepository.count() == 0) {
            // Create mock users
            User user1 = new User("john_doe", "john@example.com");
            User user2 = new User("jane_smith", "jane@example.com");
            User user3 = new User("mike_wilson", "mike@example.com");
            User user4 = new User("sarah_davis", "sarah@example.com");

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

            System.out.println("Mock users created successfully!");
        } else {
            System.out.println("Users already exist in database");
        }
    }
}