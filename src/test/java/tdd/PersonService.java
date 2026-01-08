package tdd;

import java.util.concurrent.atomic.AtomicLong;

public class PersonService implements IPersonService {
    @Override
    public Person createPerson(Person person) {
//        return new Person();
        if(person.getId() == null)
            throw new IllegalArgumentException("ID not be null");
        var id = new AtomicLong().incrementAndGet();
        person.setId(String.valueOf(id));
        return person;
    }
}
