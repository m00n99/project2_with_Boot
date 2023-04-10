package ru.zakirov.springcourse.Project2Boot.services;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.springcourse.Project2Boot.models.Book;
import ru.zakirov.springcourse.Project2Boot.models.Person;
import ru.zakirov.springcourse.Project2Boot.repositories.BooksRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final EntityManager entityManager;

    @Autowired
    public BooksService(BooksRepository booksRepository, EntityManager entityManager) {
        this.booksRepository = booksRepository;
        this.entityManager = entityManager;
    }

    public List<Book> findAll(boolean sortByYear){
        if (sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    @Transactional
    public void save(Book newBook){
        booksRepository.save(newBook);
    }

    public Book findOne(int id){
        return booksRepository.findById(id).orElse(null);
    }

    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public List<Book> findBooksByOwnerId(int id){
        List<Book> books = booksRepository.findBooksByOwnerId(id);

        for (int i = 0; i < books.size(); i++) {
            long difference = new Date().getTime() - books.get(i).getTimeTake().getTime();
            if ((int)(difference / (24 * 60 * 60 * 1000)) > 10){
                books.get(i).setOverdue(true);
            }
        }

        return books;
    }

    @Transactional
    public void assign(int id, Person selectedPerson){
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(selectedPerson);
            book.setTimeTake(new Date());
        });
    }

    @Transactional
    public void release(int id){
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setTimeTake(null);
        });
    }

    public Person getBookOwner(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    public List<Book> findBooksByTitleStartingWith(String text) {
        return booksRepository.findBooksByTitleStartingWith(text);
    }
}
