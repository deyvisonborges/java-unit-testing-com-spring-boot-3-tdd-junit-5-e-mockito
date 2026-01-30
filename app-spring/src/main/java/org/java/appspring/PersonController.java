package org.java.appspring;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<Person> readPerson() { return this.personService.findAll();}

    @GetMapping("/{id}")
    public Person readOnePerson(@PathVariable(value = "id") Long id) {
        return this.personService.findById(id);
    }

    @PostMapping
    public void createPerson(@RequestBody Person person) {
        this.personService.createPerson(person);
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
