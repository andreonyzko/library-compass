package com.andre.librarycompass.rest;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/livros")
public class BookRestController {

    // Services declaration
    private final BookService bookService;

    // Services instantiation
    @Autowired
    public BookRestController(BookService bookService){
        this.bookService = bookService;
    }

    // GET /api/livros: List all books.
    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }

    // GET /api/livros/{id}: Search book by id.
    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable Long bookId){
        return bookService.findById(bookId);
    }

    // POST /api/livros: Register new book.
    @PostMapping
    public Book registerBook(@RequestBody Book book){
        return bookService.registerNewBook(book);
    }

    // PUT /api/livros/{id}: Update an existing book.
    @PutMapping("/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @RequestBody Book book){
        return bookService.update(book, bookId);
    }

    // DELETE /api/livros/{id}: Delete book by id.
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId){
        bookService.deleteById(bookId);
        return "Livro " + bookId + " deletado.";
    }

    // POST /api/livros/{livroId}/emprestar/{usuarioId}: Loan a book to an user.
    @PostMapping("{bookId}/emprestar/{userId}")
    public Loan loanBookToUser(@PathVariable Long bookId, @PathVariable Long userId){
        return bookService.loanBookToUser(bookId, userId);
    }

    // POST /api/livros/{livroId}/devolver: Give back a book.
    @PostMapping("/{bookId}/devolver")
    public Book giveBackBook(@PathVariable Long bookId){
        return bookService.giveBackBook(bookId);
    }
}
