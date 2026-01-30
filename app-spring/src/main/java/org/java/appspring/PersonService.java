package org.java.appspring;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PersonService.class);
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());
    private final PersonRepository personRepository;

    public PersonService(final PersonRepository repository) {
        this.personRepository = repository;
    }

    public Person createPerson(Person person) {
        Optional<Person> saverPerson = this.personRepository.findByEmail(person.getEmail());
        if (saverPerson.isPresent()) {
            throw new RuntimeException("Person already exists with email: " + person.getEmail());
        }
        return this.personRepository.save(person);
    }

    public List<Person> findAll() {
        logger.info("Finding all people");
        return this.personRepository.findAll();
    }

    public Person findById(Long id) {
        System.out.println("Finding person with id: " + id);
        return this.personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person with id " + id + " not found"));
    }

    public Person update(Person person) {
        var entity = this.findById(person.getId());
        entity.setName(person.getName());
        entity.setEmail(person.getEmail());
        logger.info("Updating person with id " + person.getId() + " with name " + person.getName());
        return this.personRepository.save(person);
    }

    public void delete(Long id) {
        var entity = this.findById(id);
        this.personRepository.delete(entity);
    }

    private Person makePerson() {
        Person person = new Person();
        person.setName("John Doe");
        person.setEmail("email@gmail.com");
        return person;
    }
}
