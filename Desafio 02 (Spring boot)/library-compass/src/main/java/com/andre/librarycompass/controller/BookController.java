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

    @GetMapping
    public String getAllUsers(Model model){
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/novo")
    public String newBook(Model model){
        model.addAttribute("book", new BookResponseDTO());
        return "bookForm";
    }

    @PostMapping("/salvar")
    public String newBook(@Valid @ModelAttribute("book") BookResponseDTO bookResponseDTO, BindingResult bindingResults){
        BookDTO bookDTO = new BookDTO(bookResponseDTO.toEntity());
        if(bindingResults.hasErrors()) return "bookForm";

        bookService.save(bookDTO, bookResponseDTO.getId());
        return "redirect:/livros";
    }

    @GetMapping("/editar")
    public String editBook(@RequestParam("id") Long bookId, Model model){
        BookResponseDTO bookResponseDTO = bookService.findById(bookId);
        model.addAttribute("book", bookResponseDTO);

        return "bookForm";
    }

    @GetMapping("/deletar")
    public String deleteBook(@RequestParam("id") Long bookId){
        bookService.deleteById(bookId);

        return "redirect:/livros";
    }

    @GetMapping("/devolver")
    public String giveBackBook(@RequestParam("id") Long bookId){
        bookService.giveBackBook(bookId);

        return "redirect:/livros";
    }

    @GetMapping("/emprestimo")
    public String loanForm(@RequestParam("id") Long bookId, Model model){
        model.addAttribute("users", userService.findAll());
        model.addAttribute("bookId", bookId);

        return "loanBook";
    }

    @PostMapping("/emprestar")
    public String loanBook(@RequestParam("book") Long bookId, @RequestParam("user") Long userId){
        bookService.loanBookToUser(bookId, userId);
        return "redirect:/livros";
    }
}
