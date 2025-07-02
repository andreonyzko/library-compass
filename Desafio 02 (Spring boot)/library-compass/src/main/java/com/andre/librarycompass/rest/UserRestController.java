package com.andre.librarycompass.rest;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserRestController {

    private UserService userService;

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
        user.setId(null); // set new user id to null to be sure will be registered a new one
        return userService.save(user);
    }

    // PUT /api/usuarios/{id}: Update existing user.
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user){
        user.setId(userId); // set user id to path variable
        return userService.update(user);
    }

    // DELETE /api/usuarios/{id}: Delete user by id.
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
        return "User deleted: " + userId;
    }

    // GET /api/usuarios/{usuarioId}/livros-emprestados: List all borrowed books by the user.
    @GetMapping("/{userId}/livros-emprestados")
    public List<Book> getUserBorrowedBooks(@PathVariable Long userId){
        User user = userService.findById(userId);
        List<Book> borrowedBooks = new ArrayList<>();

        List<Loan> loans = user.getLoans(); // get all loans associated with the user
        for(Loan loan : loans) borrowedBooks.add(loan.getBook()); // for each loan get book and add it to borrowed books by the user list

        return borrowedBooks;
    }
}
