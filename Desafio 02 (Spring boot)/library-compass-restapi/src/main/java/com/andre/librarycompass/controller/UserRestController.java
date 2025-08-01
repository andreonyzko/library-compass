package com.andre.librarycompass.controller;

import com.andre.librarycompass.dto.response.BookResponseDTO;
import com.andre.librarycompass.dto.request.UserDTO;
import com.andre.librarycompass.dto.response.UserResponseDTO;
import com.andre.librarycompass.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
    }

    // GET /api/usuarios: List all users.
    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userService.findAll();
    }

    // GET /api/usuarios/{id}: Search user by id.
    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable Long userId){
        return userService.findById(userId);
    }

    // POST /api/usuarios: Register new user.
    @PostMapping
    public UserResponseDTO registerUser(@Valid @RequestBody UserDTO userDTO){
        return userService.save(userDTO, null);
    }

    // PUT /api/usuarios/{id}: Update existing user.
    @PutMapping("/{userId}")
    public UserResponseDTO updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long userId){
        return userService.save(userDTO, userId);
    }

    // DELETE /api/usuarios/{id}: Delete user by id.
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
        return "Usuário " + userId + " deletado.";
    }

    // GET /api/usuarios/{usuarioId}/livros-emprestados: List all borrowed books by the user.
    @GetMapping("/{userId}/livros-emprestados")
    public List<BookResponseDTO> getUserBorrowedBooks(@PathVariable Long userId){
        return userService.getUserBorrowedBooks(userId);
    }
}
