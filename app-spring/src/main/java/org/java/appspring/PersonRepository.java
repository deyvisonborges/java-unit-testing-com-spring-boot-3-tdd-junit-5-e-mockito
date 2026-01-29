package org.java.appspring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);
    Optional<Person> findByEmail(String email);
    @Query("select p from Person p where p.name = ?1 and p.email = ?2")
    Person findByJPQL(String name, String email);
}
