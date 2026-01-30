package org.java.appspring;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> readPerson() { return this.personService.findAll();}

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person readOnePerson(@PathVariable(value = "id") Long id) {
        return this.personService.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Person createPerson(@RequestBody Person person) {
        return this.personService.createPerson(person); // Retorne o objeto!
    }

    @PutMapping
    public void updatePerson(@RequestBody Person person) {
        this.personService.update(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        this.personService.delete(id);
    }
}
