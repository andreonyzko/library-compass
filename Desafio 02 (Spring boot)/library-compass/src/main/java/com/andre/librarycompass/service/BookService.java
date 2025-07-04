package com.andre.librarycompass.service;

import com.andre.librarycompass.dto.request.BookDTO;
import com.andre.librarycompass.dto.request.BookPatchDTO;
import com.andre.librarycompass.dto.response.BookResponseDTO;
import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.entity.enums.BookStatus;
import com.andre.librarycompass.exception.BookStatusException;
import com.andre.librarycompass.exception.DeletionNotAllowedException;
import com.andre.librarycompass.exception.NotFoundException;
import com.andre.librarycompass.repository.BookRepository;
import com.andre.librarycompass.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor // <- AutoWired repositories and service dependencies

@Service
public class BookService{

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserService userService;

    protected Book getBookById(Long id){
        // Return the Book or throw NotFoundException indicating Book was not found
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
    }

    public List<BookResponseDTO> findAll(){
        // Get list of all books, transform to a stream, and for each book convert to a BookResponseDTO
        return bookRepository.findAll().stream().map(BookResponseDTO::new).collect(Collectors.toList());
    }

    public BookResponseDTO findById(Long id){
        // Find the Book and return a new BookResponseDTO
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        return new BookResponseDTO(book);
    }

    @Transactional
    public BookResponseDTO save(BookDTO bookDTO, Long bookId){
        Book book;

        if(bookId == null){  // check if a new book
            book = bookDTO.toEntity();
            book.setStatus(BookStatus.DISPONIVEL);
        }
        else{ // else, is an existing book update, so
            book = getBookById(bookId); // get the book from database

            book.setTitle(bookDTO.getTitle()); // replace to the new title
            book.setAuthor(bookDTO.getAuthor()); // replace to the new author
            book.setYearPublication(bookDTO.getYearPublication()); // replace to the new YearPublication
            // status continues the same
        }

        return new BookResponseDTO(bookRepository.save(book));
    }

    @Transactional
    public BookResponseDTO partialUpdate(Long bookId, BookPatchDTO bookDTO){
        Book book = getBookById(bookId);

        // If BookDTO's field was informed, so update the Book Entity's field.
        if(bookDTO.getTitle() != null)
            book.setTitle(bookDTO.getTitle());
        if(bookDTO.getAuthor() != null)
            book.setAuthor(bookDTO.getAuthor());
        if(bookDTO.getYearPublication() != null)
            book.setYearPublication(bookDTO.getYearPublication());

        return new BookResponseDTO(bookRepository.save(book));
    }

    public void deleteById(Long bookId) {
        Book book = getBookById(bookId);

        // Check if the book is borrowed
        if(book.getLoan() != null) throw new DeletionNotAllowedException("Esse livro possui empréstimo pendente de devolução");

        bookRepository.delete(book);
    }

    @Transactional
    public Loan loanBookToUser(Long bookId, Long userId){
        Book book = getBookById(bookId);
        User user = userService.getUserById(userId);

        if(book.getStatus() != BookStatus.DISPONIVEL)
            throw new BookStatusException("Livro indisponível, já está emprestado.");

        Loan loan = new Loan();

        loan.setBook(book); // assign book to loan
        loan.setUser(user); // assign user to user
        book.setLoan(loan); // assign loan to user
        user.addLoan(loan); // add loan to user

        book.setStatus(BookStatus.EMPRESTADO); // set book status as unavailable

        return loanRepository.save(loan); // when save loan, book and user will update because of cascade
    }

    @Transactional
    public Book giveBackBook(Long bookId){
        Book book = getBookById(bookId);

        if(book.getStatus() != BookStatus.EMPRESTADO || book.getLoan() == null)
            throw new BookStatusException("Esse livro não está emprestado.");

        Loan loan = book.getLoan(); // get book loan associated
        User user = loan.getUser(); // get loan user associated

        // break association
        book.setLoan(null);
        user.removeLoan(loan);
        loan.setUser(null);
        loan.setBook(null);

        // delete loan
        loanRepository.delete(loan);

        book.setStatus(BookStatus.DISPONIVEL); // set book status as available
        bookRepository.save(book);

        return book;
    }
}
