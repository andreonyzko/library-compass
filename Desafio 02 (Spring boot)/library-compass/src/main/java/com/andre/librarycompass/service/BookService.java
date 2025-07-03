package com.andre.librarycompass.service;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.entity.enums.BookStatus;
import com.andre.librarycompass.exception.BookStatusException;
import com.andre.librarycompass.exception.DeletionNotAllowedException;
import com.andre.librarycompass.exception.InvalidDataException;
import com.andre.librarycompass.exception.NotFoundException;
import com.andre.librarycompass.repository.BookRepository;
import com.andre.librarycompass.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor

@Service
public class BookService{


    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserService userService;

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
    }

    @Transactional
    public Book save(Book book, Long bookId){
        if(book.getId() != null)
            throw new InvalidDataException("Não é permitido definir o id.");
        if(book.getStatus() != null)
            throw new InvalidDataException("Não é permitido definir o status.");
        if(book.getTitle() == null && book.getAuthor() == null && book.getYearPublication() == null)
            throw new InvalidDataException("Nenhum campo informado. Chaves: title, author, yearPublication.");
        if(book.getTitle() == null)
            throw new InvalidDataException("Título não informado. Chave: title.");
        if(book.getAuthor() == null)
            throw new InvalidDataException("Autor não informado. Chave: author.");
        if(book.getYearPublication() == null)
            throw new InvalidDataException("Ano de Publicação não informado. Chave: yearPublication.");
        if(book.getYearPublication() > LocalDate.now().getYear())
            throw new InvalidDataException("Ano de publicação não pode ser futuro.");

        book.setId(bookId);
        if(bookId == null){ // check if is a new book
            book.setStatus(BookStatus.DISPONIVEL);
        }
        else{ // else, is an existing book update, so
            Book dbBook = findById(bookId);
            book.setStatus(dbBook.getStatus());
        }

        return bookRepository.save(book);
    }

    public void deleteById(Long bookId) {
        Book book = findById(bookId);
        if(book.getLoan() != null) throw new DeletionNotAllowedException("Esse livro possui empréstimo pendente de devolução");
        bookRepository.delete(book);
    }

    @Transactional
    public Loan loanBookToUser(Long bookId, Long userId){
        Book book = findById(bookId);
        User user = userService.findById(userId);

        if(book.getStatus() != BookStatus.DISPONIVEL)
            throw new BookStatusException("Livro indisponível, já está emprestado.");

        Loan loan = new Loan();

        loan.setBook(book); // assign book to loan
        loan.setUser(user); // assign user to loan
        book.setLoan(loan);
        user.getLoans().add(loan);

        book.setStatus(BookStatus.EMPRESTADO); // set book status as unavailable

        return loanRepository.save(loan); // when save loan, book will update because cascade
    }

    @Transactional
    public Book giveBackBook(Long bookId){
        Book book = findById(bookId);

        if(book.getStatus() != BookStatus.EMPRESTADO || book.getLoan() == null)
            throw new BookStatusException("Esse livro não está emprestado.");

        Loan loan = book.getLoan(); // get book loan associated
        User user = loan.getUser();

        // break association
        book.setLoan(null);
        user.getLoans().remove(loan);
        loanRepository.delete(loan);

        book.setStatus(BookStatus.DISPONIVEL); // set book status as available
        bookRepository.save(book);

        return book;
    }
}
