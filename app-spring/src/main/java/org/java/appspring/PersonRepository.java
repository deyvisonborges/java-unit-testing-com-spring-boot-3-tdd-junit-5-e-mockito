package org.java.appspring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);
    Optional<Person> findByEmail(String email);
    // Define custom query using JPQL with index parameters
    @Query("select p from Person p where p.name = ?1 and p.email = ?2")
    Person findByJPQL(String name, String email);

    // Define custom query using JPQL with named parameters
    @Query("select p from Person p where p.name = :name and p.email = :email")
    Person findByJPQLNamedParameters(@Param("name") String name, @Param("email") String email);

    // Define custom query using native query / native sql with index parameters
    @Query(value = "select * from person p where p.name =?1 and p.email =?2", nativeQuery = true)
    Person findByJPQLNativeQuery(String name, String email);
}
