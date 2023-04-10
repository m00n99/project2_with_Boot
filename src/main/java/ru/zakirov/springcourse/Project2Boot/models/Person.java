package ru.zakirov.springcourse.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;
    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(min = 2, max = 100, message = "ФИО должно быть от 2 до 100 символов длиной")
    @Column(name = "fio")
    private String fio;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Column(name = "year_of_birth")
    private int yearOfBirth;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Book> books;

    public Person(String fio, int yearOfBirth) {
        this.fio = fio;
        this.yearOfBirth = yearOfBirth;
    }

    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}
