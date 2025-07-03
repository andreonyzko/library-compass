package com.andre.librarycompass.service;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.entity.enums.BookStatus;
import com.andre.librarycompass.exception.BookStatusException;
import com.andre.librarycompass.exception.InvalidDataException;
import com.andre.librarycompass.exception.NotFoundException;
import com.andre.librarycompass.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookService extends AbstractService<Book> {

    private final UserService userService;
    private final LoanService loanService;

    @Autowired
    public BookService(BookRepository bookRepository, UserService userService, LoanService loanService) {
        super(bookRepository);
        this.userService = userService;
        this.loanService = loanService;
    }

    public Book registerNewBook(Book book){
        if(book.getId() != null)
            throw new InvalidDataException("Não é permitido definir o id");
        book.setId(null); // set id as null to be sure a new one will be created
        book.setStatus(BookStatus.DISPONIVEL); // set new book status as available

        if(book.getTitle() == null)
            throw new InvalidDataException("Título não informado");
        if(book.getAuthor() == null)
            throw new InvalidDataException("Autor não informado");
        if(book.getYearPublication() == null)
            throw new InvalidDataException("Ano de Publicação não informado");
        if(book.getYearPublication() > LocalDate.now().getYear())
            throw new InvalidDataException("Ano de publicação não pode ser um ano futuro");

        return save(book);
    }

    public Book update(Book book, Long bookId) {
        if(book.getId() != null)
            throw new InvalidDataException("Não é permitido alterar o id");
        if(book.getTitle() == null & book.getAuthor() == null && book.getYearPublication() == null)
            throw new InvalidDataException("Nenhum campo informado");

        Book dbBook = findById(bookId); // check if book exists and get it
        book.setId(bookId); // set book id as the path variable
        book.setStatus(dbBook.getStatus());

        if(book.getTitle() == null)
            book.setTitle(dbBook.getTitle());
        if(book.getAuthor() == null)
            book.setAuthor(dbBook.getAuthor());
        if(book.getYearPublication() == null)
            book.setYearPublication(dbBook.getYearPublication());
        if(book.getYearPublication() > LocalDate.now().getYear())
            throw new InvalidDataException("Ano de publicação não pode ser um ano futuro");

        return save(book);
    }

    public Loan loanBookToUser(Long bookId, Long userId){
        Book book = findById(bookId);
        User user = userService.findById(userId);

        if(book.getStatus() != BookStatus.DISPONIVEL)
            throw new BookStatusException("Livro indisponível, já está emprestado");

        Loan loan = new Loan();

        loan.setBook(book); // assign book to loan
        loan.setUser(user); // assign user to loan

        book.setStatus(BookStatus.EMPRESTADO); // set book status as unavailable

        return loanService.save(loan); // when save loan, book will update because cascade
    }

    public Book giveBackBook(Long bookId){
        Book book = findById(bookId);

        if(book.getStatus() != BookStatus.EMPRESTADO || book.getLoan() == null)
            throw new BookStatusException("Esse livro não está emprestado");

        Loan loan = book.getLoan(); // get book loan associated
        User user = loan.getUser();

        // break association
        book.setLoan(null);
        user.getLoans().remove(loan);
        loanService.delete(loan);

        book.setStatus(BookStatus.DISPONIVEL); // set book status as available
        save(book);

        return book;
    }

    @Override
    public String getEntityName() {
        return "Livro";
    }
}
