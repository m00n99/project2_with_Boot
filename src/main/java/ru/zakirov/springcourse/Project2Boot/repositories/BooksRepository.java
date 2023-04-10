package ru.zakirov.springcourse.Project2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zakirov.springcourse.Project2Boot.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByOwnerId(int id);

    List<Book> findBooksByTitleStartingWith(String text);
}
