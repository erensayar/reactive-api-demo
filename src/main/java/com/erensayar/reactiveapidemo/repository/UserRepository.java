package com.erensayar.reactiveapidemo.repository;

import com.erensayar.reactiveapidemo.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<User, UUID> {

    @Query("select id, name, mail from REACTIVE_USER where name = $1")
    Flux<User> findByName(String name);

    Mono<User> findById(UUID id);

    Mono<Void> deleteById(UUID id);

}
