package org.java.appspring;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Person> readOnePerson(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(this.personService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Person createPerson(@RequestBody Person person) {
        return this.personService.createPerson(person); // Retorne o objeto!
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        try {
            return ResponseEntity.ok(this.personService.update(person));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        this.personService.delete(id);
    }
}
