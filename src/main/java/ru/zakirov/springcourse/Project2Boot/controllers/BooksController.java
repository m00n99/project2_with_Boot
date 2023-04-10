package ru.zakirov.springcourse.Project2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zakirov.springcourse.Project2Boot.models.Book;
import ru.zakirov.springcourse.Project2Boot.models.Person;
import ru.zakirov.springcourse.Project2Boot.services.BooksService;
import ru.zakirov.springcourse.Project2Boot.services.PeopleService;

import java.util.Collections;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String allBooks(Model model,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                           @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){

        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sortByYear));
        else
            model.addAttribute("books", booksService.findAllWithPagination(page, booksPerPage, sortByYear));

        return "books/all-books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "text", required = false) String text, Model model){
        if (text != null && !text.isEmpty()) {
            model.addAttribute("books", booksService.findBooksByTitleStartingWith(text));
        } else
            model.addAttribute("books", Collections.emptyList());
        return "books/search-book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new-book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                            BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/new-book";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String bookInfo(@PathVariable int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOne(id));

        Person bookOwner = booksService.getBookOwner(id);

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.findAll());
        return "books/book-info";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable int id, Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit-book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/edit-book";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable int id, @ModelAttribute("person") Person selectedPerson){
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable int id){
        booksService.release(id);
        return "redirect:/books/" + id;
    }
}
