package tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonServiceTest {
    Person person;
    PersonService service;
    @BeforeEach()
    void setup() {
        person = new Person(
        "First Name",
        "Second Name",
        "email@email.com"
        );
        service = new PersonService();
    }

    @Test()
    @DisplayName("When Create A Person With Success Should Return A Person Object")
    void testCreatePerson_WhenSuccess_ShouldReturnPersonObject() {
        // When / Act
        person.setId("Test");
        var actual = service.createPerson(person);
        // Then / Assert
        Assertions.assertNotNull(actual, () -> "The createPerson() should not have null");
    }

    @Test()
    void testCreatePerson_WhenSuccess_ShouldContainsFirstNameInReturnedPersonObject() {
        // When / Act
        person.setId("Test");
        var actual = service.createPerson(person);
        // Then / Assert
        Assertions.assertNotNull(person.getId(), () -> "Person ID is missing");
        Assertions.assertEquals(person.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(person.getSecondName(), actual.getSecondName());
    }

    @Test()
    void testCreatePerson_WhenNullId_ShouldThrowIllegalArgumentException() {
        person.setId(null);
        // When / Act
        // Then / Assert
        var expectedExceptionMessage = "ID not be null";
        var exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.createPerson(person),
                () -> "Empty ID should have cause an IllegalArgumentException"
        );
        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage(), () -> "Exception message is incorrect");
    }
}
