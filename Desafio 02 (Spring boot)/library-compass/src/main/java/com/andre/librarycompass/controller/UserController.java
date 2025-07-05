package com.andre.librarycompass.controller;

import com.andre.librarycompass.dto.request.UserDTO;
import com.andre.librarycompass.dto.response.BookResponseDTO;
import com.andre.librarycompass.dto.response.UserResponseDTO;
import com.andre.librarycompass.service.BookService;
import com.andre.librarycompass.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor

@Controller
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;
    private final BookService bookService;

    @GetMapping
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/novo")
    public String newUser(Model model){
        model.addAttribute("userResponseDTO", new UserResponseDTO());
        return "userForm";
    }

    @PostMapping("/salvar")
    public String saveUser(@Valid @ModelAttribute("userResponseDTO") UserResponseDTO userResponseDTO, BindingResult bindingResults){
        UserDTO userDTO = new UserDTO(userResponseDTO.toEntity());
        if(bindingResults.hasErrors()) return "userForm";

        userService.save(userDTO, userResponseDTO.getId());
        return "redirect:/usuarios";
    }

    @GetMapping("/editar")
    public String editUser(@RequestParam("id") Long userId, Model model){
        UserResponseDTO userResponseDTO = userService.findById(userId);
        model.addAttribute("userResponseDTO", userResponseDTO);
        return "userForm";
    }

    @GetMapping("/deletar")
    public String deleteUser(@RequestParam("id") Long userId){
        userService.deleteById(userId);

        return "redirect:/usuarios";
    }

    @GetMapping("/livros-emprestados")
    public String getUserLoans(@RequestParam("id") Long userId, Model model){
        model.addAttribute("books", userService.getUserBorrowedBooks(userId));

        return "viewLoans";
    }

    @GetMapping("/devolver")
    public String giveBackBook(@RequestParam("id") Long bookId, HttpServletRequest request){
        bookService.giveBackBook(bookId);
        
        return "redirect:" + request.getHeader("Referer");
    }
}
