package com.erensayar.reactiveapidemo.service.impl;

import com.erensayar.reactiveapidemo.model.User;
import com.erensayar.reactiveapidemo.repository.UserRepository;
import com.erensayar.reactiveapidemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> getById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> update(User user) {
        if (user.getId() == null)
            throw new IllegalArgumentException(); // throw new BadRequestException();
        Mono<User> userInDb = userRepository.findById(user.getId());
        userInDb.flatMap(userRepository::save);
        return userInDb;
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return userRepository.deleteById(id);
    }
}
