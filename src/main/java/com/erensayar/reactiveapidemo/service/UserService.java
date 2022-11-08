package com.erensayar.reactiveapidemo.service;

import com.erensayar.reactiveapidemo.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {

    Mono<User> getById(UUID id);

    Flux<User> getAll();

    Mono<User> save(User user);

    Mono<User> update(User user);

    Mono<Void> deleteById(UUID id);

}
