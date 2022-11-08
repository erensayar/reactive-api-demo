package com.erensayar.reactiveapidemo.controller;

import com.erensayar.reactiveapidemo.model.User;
import com.erensayar.reactiveapidemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;


@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<User> all() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable UUID id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Streaming
    @GetMapping(path = "/flux", produces = MediaType.APPLICATION_NDJSON_VALUE) // MediaType.APPLICATION_JSON_VALUE)
    public Flux<User> getFlux() {
        return userService.getAll().delayElements(Duration.ofSeconds(1)).log();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping
    public Mono<User> updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable UUID id) {
        return userService.deleteById(id);
    }

    /*
    Mono<User> userMono
    Mono<ResponseEntity<User>> x =
                userMono.flatMap(userService::updateUser)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    */

}
