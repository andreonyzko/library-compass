package com.andre.librarycompass.rest;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class BookRestController {

    private BookService bookService;

    @Autowired
    public BookRestController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable Long bookId){
        return bookService.findById(bookId);
    }

    @PostMapping
    public Book registerBook(@RequestBody Book book){
        book.setId(null);
        return bookService.save(book);
    }

    @PutMapping("/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @RequestBody Book book){
        book.setId(bookId);
        return bookService.update(book);
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId){
        bookService.deleteById(bookId);
        return "Book deleted: " + bookId;
    }

}
