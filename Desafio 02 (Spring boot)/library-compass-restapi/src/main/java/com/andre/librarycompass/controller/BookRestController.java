package com.andre.librarycompass.controller;

import com.andre.librarycompass.dto.request.BookDTO;
import com.andre.librarycompass.dto.request.BookPatchDTO;
import com.andre.librarycompass.dto.response.BookResponseDTO;
import com.andre.librarycompass.dto.response.LoanResponseDTO;
import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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
    public List<BookResponseDTO> getAllBooks(){
        return bookService.findAll();
    }

    // GET /api/livros/{id}: Search book by id.
    @GetMapping("/{bookId}")
    public BookResponseDTO getBook(@PathVariable Long bookId){
        return bookService.findById(bookId);
    }

    // POST /api/livros: Register new book.
    @PostMapping
    public BookResponseDTO registerBook(@Valid @RequestBody BookDTO bookDTO){
        return bookService.save(bookDTO, null);
    }

    // PUT /api/livros/{id}: Update an existing book.
    @PutMapping("/{bookId}")
    public BookResponseDTO updateBook(@PathVariable Long bookId, @Valid @RequestBody BookDTO bookDTO){
        return bookService.save(bookDTO, bookId);
    }

    // PATCH /api/livros/{id}: Partial update an existing book.
    @PatchMapping("/{bookId}")
    public BookResponseDTO partialUpdateBook(@PathVariable Long bookId, @Valid @RequestBody BookPatchDTO bookDTO){
        return bookService.partialUpdate(bookId, bookDTO);
    }

    // DELETE /api/livros/{id}: Delete book by id.
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId){
        bookService.deleteById(bookId);
        return "Livro " + bookId + " deletado.";
    }

    // POST /api/livros/{livroId}/emprestar/{usuarioId}: Loan a book to an user.
    @PostMapping("{bookId}/emprestar/{userId}")
    public LoanResponseDTO loanBookToUser(@PathVariable Long bookId, @PathVariable Long userId){
        return bookService.loanBookToUser(bookId, userId);
    }

    // POST /api/livros/{livroId}/devolver: Give back a book.
    @PostMapping("/{bookId}/devolver")
    public BookResponseDTO giveBackBook(@PathVariable Long bookId){
        return bookService.giveBackBook(bookId);
    }
}
