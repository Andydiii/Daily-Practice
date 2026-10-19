package com.andy.todo_api;

import org.springframework.data.jpa.repository.JpaRepository;

// first type parameter should be the type of the entity(a row in table)
// second type parameter is the field we marked @Id i.e. unique identifier of the entity/row
// Spring Data JPA discovers that interface, creates an implementation for it, and registers the resulting object as a bean. You did not write an @Bean method for it
public interface TaskRepository extends JpaRepository<Task, Integer> {}
