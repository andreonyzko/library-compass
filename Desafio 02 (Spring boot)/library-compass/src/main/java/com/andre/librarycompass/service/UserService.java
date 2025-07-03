package com.andre.librarycompass.service;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.exception.InvalidDataException;
import com.andre.librarycompass.exception.NotFoundException;
import com.andre.librarycompass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends AbstractService<User> {

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    public User registerUser(User user){
        user.setId(null); // set new user id to null to be sure will be registered a new one

        if(user.getName() == null)
            throw new InvalidDataException("Nome do usuário não informado");

        return save(user);
    }

    public User updateUser(User user, Long userId){
        findById(userId); // check if user exists

        if(user.getName() == null)
            throw new InvalidDataException("Nenhum campo informado");

        user.setId(userId); // set user id to path variable
        return save(user);
    }

    public List<Book> getUserBorrowedBooks(Long userId){
        User user = findById(userId);
        List<Book> borrowedBooks = new ArrayList<>();

        List<Loan> loans = user.getLoans(); // get all loans associated with the user
        for(Loan loan : loans) borrowedBooks.add(loan.getBook()); // for each loan get book and add it to borrowed books by the user list

        return borrowedBooks;
    }

    @Override
    public String getEntityName() {
        return "Usuário";
    }
}
