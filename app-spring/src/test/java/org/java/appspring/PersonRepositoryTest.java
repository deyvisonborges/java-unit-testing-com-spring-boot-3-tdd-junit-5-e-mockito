package org.java.appspring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    Person person;
    Person person2;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setName("Test name");
        person.setEmail("Test email");

        person2 = new Person();
        person2.setName("Test name2");
        person2.setEmail("Test email2");
    }

    @Test()
    @DisplayName("deveRetornarUmObjetoPersonQueFoiSalvo")
    void deveRetornarUmObjetoPersonQueFoiSalvo() {
        var result = this.personRepository.save(person);
        Assertions.assertNotNull(result);
        // precisa do equals e do hashcode
        Assertions.assertEquals(person, result);
        Assertions.assertEquals(1, this.personRepository.count());
        Assertions.assertEquals(person.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("verificarSeAListagemEstaRetornandoCorretamenteOsDadosSalvosNoBanco")
    void verificarSeAListagemEstaRetornandoCorretamenteOsDadosSalvosNoBanco() {
        this.personRepository.save(person);
        this.personRepository.save(person2);
        List<Person> result = this.personRepository.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(person, result.get(0));
        Assertions.assertEquals(person2, result.get(1));
    }

    @Test
    @DisplayName("buscarPersonPeloID")
    void buscarPersonPeloID() {
        personRepository.save(person);
        var savedPerson = this.personRepository.findById(person.getId());
        Assertions.assertNotNull(savedPerson);
        Assertions.assertEquals(1, this.personRepository.count());
    }
    @Test
    @DisplayName("buscarPersonPeloEmail")
    void buscarPersonPeloEmail() {
        personRepository.save(person);
        var savedPerson = this.personRepository.findByEmail(person.getEmail()).get();
        Assertions.assertNotNull(savedPerson);
        Assertions.assertEquals(1, this.personRepository.count());
        Assertions.assertEquals(person.getEmail(), savedPerson.getEmail());
    }

    @Test
    @DisplayName("atualizarPerson")
    void atualizarPerson() {
        personRepository.save(person);

        var personUpdated = this.personRepository.findById(person.getId()).get();
        personUpdated.setEmail("updated email");
        this.personRepository.save(personUpdated);

        var savedPerson = this.personRepository.findByEmail(personUpdated.getEmail()).get();
        Assertions.assertEquals(1, this.personRepository.count());
        Assertions.assertEquals(personUpdated.getEmail(), savedPerson.getEmail());
    }

    @Test
    @DisplayName("deletarPerson")
    void deletarPerson() {
        personRepository.save(person);
        Assertions.assertEquals(1, this.personRepository.count());
        personRepository.deleteById(person.getId());

        Optional<Person> result = this.personRepository.findById(person.getId());
        Assertions.assertFalse(result.isPresent());
        Assertions.assertEquals(0, this.personRepository.count());
    }

    @Test
    @DisplayName("buscarPersonComJPQL")
    void buscarPersonComJPQL() {
        personRepository.save(person);
        Person result = this.personRepository.findByJPQL(person.getName(), person.getEmail());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(person.getName(), result.getName());
    }

    @Test
    @DisplayName("buscarPersonComJPQLNamedParameters")
    void buscarPersonComJPQLNamedParameters() {
        personRepository.save(person);
        Person result = this.personRepository.findByJPQLNamedParameters(person.getName(), person.getEmail());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(person.getName(), result.getName());
    }

    @Test
    @DisplayName("buscarPersonComJPQLNativeSQL")
    void buscarPersonComJPQLNativeSQL() {
        personRepository.save(person);
        Person result = this.personRepository.findByJPQLNativeQuery("Test name", "Test email");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(person.getName(), result.getName());
    }

    @Test
    @DisplayName("buscarPersonComJPQLNativeSQLNamedParameters")
    void buscarPersonComJPQLNativeSQLNamedParameters() {
        personRepository.save(person);
        Person result = this.personRepository.findByJPQLNativeQueryNamedParameters("Test name", "Test email");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(person.getName(), result.getName());
    }
}
