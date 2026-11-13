package com.andy.todo_api;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// first type parameter should be the type of the entity(a row in table)
// second type parameter is the field we marked @Id i.e. unique identifier of the entity/row
// Spring Data JPA discovers that interface, creates an implementation for it, and registers the resulting object as a bean. You did not write an @Bean method for it
public interface TaskRepository extends JpaRepository<Task, Integer> {
    /*
        findByOwner_Id follows your Java field names:
        - Owner refers to Task.owner.
        - _Id refers to that owner’s AppUser.id.
        - Spring Data JPA implements the query for us.
        Conceptually, it filters task rows like:
            SELECT *
            FROM task
            WHERE user_id = ?;

        | Return type | Expected number of results |
        |---|---|
        | `List<Task>` | Zero, one, or many tasks |
        | `Optional<Task>` | Zero or one task |
    */
    List<Task> findByOwner_Id(int userId); 
    // The extra owner condition checks whether the signed-in user is allowed to modify that task.
    Optional<Task> findByIdAndOwner_Id(int id, int userId);
    // Completed refers to Task.completed.
    // Spring Data JPA builds the query from these property names.
    /*
        SELECT *
        FROM task
        WHERE user_id = ?
        AND completed = ?;
    */
    List<Task> findByOwner_IdAndCompleted(int userId, boolean completed);

    /*
    | Type | Purpose |
    |---|---|
    | `Pageable` | Describes the requested page number, page size and sorting |
    | `Page<Task>` | Contains that page’s tasks plus metadata, such as total matching tasks and total pages |
    
    Why can we keep two methods with the same name? This is method overloading: the parameter lists differ. Java chooses the method based on the arguments supplied.
    */
    Page<Task> findByOwner_Id(int userId, Pageable pageable);
    Page<Task> findByOwner_IdAndCompleted(
        int userId,
        boolean completed,
        Pageable pageable
    );
}