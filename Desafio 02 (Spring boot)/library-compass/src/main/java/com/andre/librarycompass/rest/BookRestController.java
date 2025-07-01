package com.andre.librarycompass.rest;

import com.andre.librarycompass.entity.Book;
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

}
