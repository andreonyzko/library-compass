package com.andre.librarycompass.rest;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.entity.enums.BookStatus;
import com.andre.librarycompass.service.BookService;
import com.andre.librarycompass.service.LoanService;
import com.andre.librarycompass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class BookRestController {

    // Services declaration
    private final UserService userService;
    private final LoanService loanService;
    private final BookService bookService;

    // Services instantiation
    @Autowired
    public BookRestController(BookService bookService, UserService userService, LoanService loanService){
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
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
        book.setId(null); // set id as null to be sure a new one will be created
        book.setAvailable(BookStatus.DISPONIVEL); // set new book status as available
        return bookService.save(book);
    }

    // PUT /api/livros/{id}: Update an existing book.
    @PutMapping("/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @RequestBody Book book){
        book.setId(bookId); // set book id as the path variable
        return bookService.update(book);
    }

    // DELETE /api/livros/{id}: Delete book by id.
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId){
        bookService.deleteById(bookId);
        return "Livro " + bookId + " deletado.";
    }

    // POST /api/livros/{livroId}/emprestar/{usuarioId}: Loan a book to an user.
    @PostMapping("{bookId}/emprestar/{userId}")
    public Loan loanBook(@PathVariable Long bookId, @PathVariable Long userId){
        Loan loan = new Loan();

        Book book = bookService.findById(bookId);
        book.setAvailable(BookStatus.EMPRESTADO); // set book status as unavailable

        User user = userService.findById(userId);

        loan.setBook(book); // assign book to loan
        loan.setUser(user); // assign user to loan

        return loanService.save(loan); // when save loan, book will update because cascade
    }

    // POST /api/livros/{livroId}/devolver: Give back a book.
    @PostMapping("/{bookId}/devolver")
    public Book giveBackBook(@PathVariable Long bookId){
        Book book = bookService.findById(bookId);
        Loan loan = book.getLoan(); // get book loan associated

        book.setLoan(null); // break association
        book.setAvailable(BookStatus.DISPONIVEL); // set book status as available

        bookService.update(book);
        loanService.delete(loan);

        return book;
    }
}
