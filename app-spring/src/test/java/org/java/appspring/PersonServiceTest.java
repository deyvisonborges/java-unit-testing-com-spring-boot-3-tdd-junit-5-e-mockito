package org.java.appspring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

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
    @DisplayName("Verifica se esta criando um usuario")
    void test_() {
        // quando eu chamar, a busca do repsotorio buscando por email, devo retonrar...
        Mockito.when(
            this.personRepository.findByEmail(
                ArgumentMatchers.anyString()
            )
        ).thenReturn(Optional.empty());
         Mockito.when(this.personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(person);
        Person savedPerson = personService.createPerson(person);

        Assertions.assertNotNull(savedPerson);
        Assertions.assertEquals(person.getName(), savedPerson.getName());
        // Ele não chama Hibernate, não acessa banco, não gera ID.
        // Se eu tentar verificar o ID, ele da erro de NullPoint
        // Assertions.assertTrue(savedPerson.getId() > 0);
    }

    @Test()
    @DisplayName("Verifica se ja existe uma pessoa registrada no processo de criacao")
    void creationExists() {
        // "Quando o método findByEmail for chamado com qualquer String, retorne uma person ja existente
        Mockito.when(
            this.personRepository.findByEmail(
                ArgumentMatchers.anyString()
            )
        ).thenReturn(Optional.of(person));

        // lança uma exceção do tipo RuntimeException. Se não lançar, o teste falha.
        Assertions.assertThrows(RuntimeException.class, () -> personService.createPerson(person));
        // em nenhuma situacao, o metodo save deve ser chamado
        Mockito.verify(personRepository, Mockito.never()).save(ArgumentMatchers.any(Person.class));
    }

    @Test()
    @DisplayName("findAllPersonsPositiveScenario")
    void findAllPersonsPositiveScenario() {
        Mockito.when(this.personRepository.findAll()).thenReturn(List.of(person, person2));
        List<Person> persons = personService.findAll();
        Assertions.assertNotNull(persons);
        Assertions.assertEquals(2, persons.size());
    }

    @Test()
    @DisplayName("findAllPersonsNegativeScenario")
    void findAllPersonsNegativeScenario() {
        Mockito.when(this.personRepository.findAll()).thenReturn(Collections.emptyList());
        List<Person> persons = personService.findAll();
        Assertions.assertTrue(persons.isEmpty());
        Assertions.assertEquals(0, persons.size());
    }

    @Test()
    @DisplayName("findById")
    void findById() {
        Mockito.when(
            this.personRepository.findById(
                ArgumentMatchers.anyLong()
            )
        ).thenReturn(Optional.of(person));

        Person findedPerson = personService.findById(1L);

        Assertions.assertNotNull(findedPerson);
        Assertions.assertEquals(person.getName(), findedPerson.getName());
    }

    @Test()
    @DisplayName("updatePerson")
    void updatePerson() {

        ReflectionTestUtils.setField(person, "id", 1L);

        Mockito.when(this.personRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(person));
        person.setName("New name");
        person.setEmail("New email");
        Mockito.when(this.personRepository.save(person)).thenReturn(person);


        Person updatedPerson = personService.update(person);

        Assertions.assertNotNull(updatedPerson);
        Assertions.assertEquals(person.getName(), updatedPerson.getName());
        Assertions.assertEquals("New email", updatedPerson.getEmail());

        Mockito.verify(personRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.personRepository, Mockito.times(1)).save(person);
    }

    @Test()
    @DisplayName("deletePerson")
    void deletePerson() {
        ReflectionTestUtils.setField(person, "id", 1L);
        Mockito.when(this.personRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(person));
        // deve retornar void, quando metodo delete for chamado
        Mockito.doNothing().when(this.personRepository).delete(person);
        personService.delete(1L);

        Mockito.verify(this.personRepository, Mockito.times(1)).delete(person);
    }
}
