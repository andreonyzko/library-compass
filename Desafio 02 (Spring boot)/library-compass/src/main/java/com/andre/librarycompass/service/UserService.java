package com.andre.librarycompass.service;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.exception.DeletionNotAllowedException;
import com.andre.librarycompass.exception.InvalidDataException;
import com.andre.librarycompass.exception.NotFoundException;
import com.andre.librarycompass.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public User save(User user, Long userId) {
        if(user.getId() != null)
            throw new InvalidDataException("Não é permitido definir o id.");
        if (user.getName() == null)
            throw new InvalidDataException("Nenhum campo informado. Chaves: name");

        if(userId != null) findById(userId);
        user.setId(userId); // set new user id to null to be sure will be registered a new one

        return userRepository.save(user);
    }

    public void deleteById(Long userId) {
        User user = findById(userId);

        if (!user.getLoans().isEmpty()) {
            StringBuilder booksToGiveback = new StringBuilder();

            for (Loan loan : user.getLoans()) {
                Book book = loan.getBook();
                if (!booksToGiveback.isEmpty()) booksToGiveback.append(", ");
                booksToGiveback.append(String.format("[%d] %s", book.getId(), book.getTitle()));
            }

            throw new DeletionNotAllowedException("Esse usuário possui empréstimo pendente de devolução. Livros pendentes para devolver: " + booksToGiveback);
        }

        userRepository.delete(user);
    }

    public List<Book> getUserBorrowedBooks(Long userId) {
        User user = findById(userId);
        List<Book> borrowedBooks = new ArrayList<>();

        List<Loan> loans = user.getLoans(); // get all loans associated with the user
        for (Loan loan : loans)
            borrowedBooks.add(loan.getBook()); // for each loan get book and add it to borrowed books by the user list

        return borrowedBooks;
    }
}
