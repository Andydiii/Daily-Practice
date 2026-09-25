package com.andy.todo_api;

import org.springframework.data.jpa.repository.JpaRepository;

// first type parameter should be the type of the entity(a row in t able)
// second type parameter is the field we marked @Id i.e. unique identifier of the entity/row
public interface TaskRepository extends JpaRepository<Task, Integer> {}
