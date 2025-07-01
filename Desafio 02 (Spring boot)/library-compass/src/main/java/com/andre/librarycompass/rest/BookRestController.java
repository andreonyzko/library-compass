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

    private final UserService userService;
    private final LoanService loanService;
    private BookService bookService;

    @Autowired
    public BookRestController(BookService bookService, UserService userService, LoanService loanService){
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
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
        book.setAvailable(BookStatus.DISPONIVEL);
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
        return "Livro " + bookId + " deletado.";
    }

    @PostMapping("{bookId}/emprestar/{userId}")
    public Loan loanBook(@PathVariable Long bookId, @PathVariable Long userId){
        Loan loan = new Loan();

        Book book = bookService.findById(bookId);
        book.setAvailable(BookStatus.EMPRESTADO);

        User user = userService.findById(userId);

        loan.setBook(book);
        loan.setUser(user);

        return loanService.save(loan);
    }

    @PostMapping("/{bookId}/devolver")
    public Book giveBackBook(@PathVariable Long bookId){
        Book book = bookService.findById(bookId);
        Loan loan = book.getLoan();

        book.setLoan(null);
        book.setAvailable(BookStatus.DISPONIVEL);

        bookService.save(book);
        loanService.delete(loan);

        return book;
    }
}
