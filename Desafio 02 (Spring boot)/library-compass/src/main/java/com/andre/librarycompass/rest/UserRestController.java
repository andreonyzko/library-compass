package com.andre.librarycompass.rest;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    // GET /api/usuarios/{id}: Search user by id.
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId){
        return userService.findById(userId);
    }

    // POST /api/usuarios: Register new user.
    @PostMapping
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    // PUT /api/usuarios/{id}: Update existing user.
    @PutMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable Long userId){
        return userService.updateUser(user, userId);
    }

    // DELETE /api/usuarios/{id}: Delete user by id.
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
        return "Usuário " + userId + " deletado.";
    }

    // GET /api/usuarios/{usuarioId}/livros-emprestados: List all borrowed books by the user.
    @GetMapping("/{userId}/livros-emprestados")
    public List<Book> getUserBorrowedBooks(@PathVariable Long userId){
        return userService.getUserBorrowedBooks(userId);
    }
}
