package com.andre.librarycompass.controller;

import com.andre.librarycompass.dto.request.BookDTO;
import com.andre.librarycompass.dto.response.BookResponseDTO;
import com.andre.librarycompass.service.BookService;
import com.andre.librarycompass.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor

@Controller
@RequestMapping("/livros")
public class BookController {

    private final UserService userService;
    private BookService bookService;

    // Get all books as BookResponseDTO and display books page passing books by model
    @GetMapping
    public String getAllBooks(Model model){
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    // Create new bookResponseDTO, and display book form page passing the new book through model
    @GetMapping("/novo")
    public String newBook(Model model){
        model.addAttribute("book", new BookResponseDTO());
        return "bookForm";
    }

    // Get bookResponseDTO from the model it came, check for errors, pass it to BookDTO and save it
    @PostMapping("/salvar")
    public String newBook(@Valid @ModelAttribute("book") BookResponseDTO bookResponseDTO, BindingResult bindingResults){
        if(bindingResults.hasErrors()) return "bookForm";
        BookDTO bookDTO = new BookDTO(bookResponseDTO.toEntity());

        bookService.save(bookDTO, bookResponseDTO.getId());
        return "redirect:/livros";
    }

   // Find book by the URI, put it in the model and return the form
    @GetMapping("/editar")
    public String editBook(@RequestParam("id") Long bookId, Model model){
        BookResponseDTO bookResponseDTO = bookService.findById(bookId);
        model.addAttribute("book", bookResponseDTO);

        return "bookForm";
    }

    // Delete book from uri and redirect to books page
    @GetMapping("/deletar")
    public String deleteBook(@RequestParam("id") Long bookId){
        bookService.deleteById(bookId);

        return "redirect:/livros";
    }

    // Give back book wich has id informed in the uri and redirect to books page
    @GetMapping("/devolver")
    public String giveBackBook(@RequestParam("id") Long bookId){
        bookService.giveBackBook(bookId);

        return "redirect:/livros";
    }

    // Add all users as UsersResponseDTO in the model, as well bookId, and return loan book form page
    @GetMapping("/emprestimo")
    public String loanForm(@RequestParam("id") Long bookId, Model model){
        model.addAttribute("users", userService.findAll());
        model.addAttribute("bookId", bookId);

        return "loanBook";
    }

    // loan book getting book and user from uri
    @PostMapping("/emprestar")
    public String loanBook(@RequestParam("book") Long bookId, @RequestParam("user") Long userId){
        bookService.loanBookToUser(bookId, userId);
        return "redirect:/livros";
    }
}
