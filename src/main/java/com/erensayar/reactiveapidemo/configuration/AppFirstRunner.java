package com.erensayar.reactiveapidemo.configuration;

import com.erensayar.reactiveapidemo.client.UserWebClient;
import com.erensayar.reactiveapidemo.model.User;
import com.erensayar.reactiveapidemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
public class AppFirstRunner {

    @Bean
    public CommandLineRunner loadData(@Autowired final UserRepository userRepository,
                                      @Autowired final UserWebClient userWebClient) {
        return (args) -> {
            System.out.println("-------------------");
            System.out.println("### APP STARTED ###");

            System.out.println("-------------------");
            System.out.println("### Get One With Repo ###");
            Flux<User> user = userRepository.findByName("Eren");
            System.out.println(user.blockFirst().toString());

            System.out.println("-------------------");
            System.out.println("### Get One With WebClient ###");
            UUID userId = user.map(User::getId).blockFirst();
            Mono<User> userByIdWithWebClient = userWebClient.getUserByIdWithWebClient(userId);
            System.out.println(userByIdWithWebClient.block().toString());

            // Save Doesn't Work
            var users = Flux.just(
                    new User(null, "Harry", "harry@mail.com"),
                    new User(null, "Ezekiel", "ezekiel@mail.com"),
                    new User(null, "Tony", "tony@mail.com"));
            userRepository.saveAll(users);
        };
    }

}
