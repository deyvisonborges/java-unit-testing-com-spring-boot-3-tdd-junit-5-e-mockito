package tdd;

import org.jspecify.annotations.Nullable;

public class Person {
    String id;
    String firstName;
    String secondName;
    String mail;

    public Person() {
    }

    public Person(String firstName, String secondName, String mail) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.mail = mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getMail() {
        return null;
    }

    public @Nullable Object getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
