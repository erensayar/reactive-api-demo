package com.erensayar.reactiveapidemo.configuration;

import com.erensayar.reactiveapidemo.model.User;
import com.erensayar.reactiveapidemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class AppDataInitializer {

    @Bean
    public CommandLineRunner loadData(@Autowired final UserRepository userRepository) {
        return (args) -> {
            System.out.println("### APP STARTED ###");

            Flux<User> eren = userRepository.findByName("Eren");
            System.out.println(eren.blockFirst().toString());

            var users = Flux.just(
                    new User(null, "Harry", "harry@mail.com"),
                    new User(null, "Ezekiel", "ezekiel@mail.com"),
                    new User(null, "Tony", "tony@mail.com"));
            userRepository.saveAll(users);
        };
    }

}
