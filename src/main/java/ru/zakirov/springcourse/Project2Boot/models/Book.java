package ru.zakirov.springcourse.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    @Column(name = "title")
    private String title;
    @NotEmpty(message =  "Автор не должен быть пустым")
    @Size(min =2, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    @Column(name = "author")
    private String author;

    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    @Column(name = "year")
    private int year;

    @Column(name = "time_take")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeTake;

    @Transient
    private boolean overdue;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTimeTake() {
        return timeTake;
    }

    public void setTimeTake(Date timeTake) {
        this.timeTake = timeTake;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", owner=" + owner +
                '}';
    }
}
