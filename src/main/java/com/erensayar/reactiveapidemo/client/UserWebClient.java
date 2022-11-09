package com.erensayar.reactiveapidemo.client;

import com.erensayar.reactiveapidemo.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserWebClient {

    private final WebClient webClient;
    private final static String USER_URL_SERVICE_PREFIX = "/api/v1/users/{id}";

    public Mono<User> getUserByIdWithWebClient(UUID userId) {
        return webClient
                .get()
                .uri(USER_URL_SERVICE_PREFIX, userId)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("There is an error while retrieving the user: " + userId)))
                .bodyToMono(User.class)
                .doOnError(error -> log.error("There is an error while sending request {}", error.getMessage()))
                .onErrorResume(error -> Mono.just(new User()))
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(2)))
                .map(user -> {
                            log.info(user.getName());
                            return user;
                        }
                );
    }
}


