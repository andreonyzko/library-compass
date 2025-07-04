package com.andre.librarycompass.service;

import com.andre.librarycompass.dto.BookResponseDTO;
import com.andre.librarycompass.dto.UserDTO;
import com.andre.librarycompass.dto.UserResponseDTO;
import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.Loan;
import com.andre.librarycompass.entity.User;
import com.andre.librarycompass.exception.DeletionNotAllowedException;
import com.andre.librarycompass.exception.NotFoundException;
import com.andre.librarycompass.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;

    protected User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll().stream().map(UserResponseDTO::new).collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return new UserResponseDTO(user);
    }

    public UserResponseDTO save(UserDTO userDTO, Long userId) {
        if(userId != null) findById(userId);

        User user = userDTO.toEntity();
        user.setId(userId); // set new user id to null to be sure will be registered a new one

        return new UserResponseDTO(userRepository.save(user));
    }

    public void deleteById(Long userId) {
        User user = getUserById(userId);

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

    public List<BookResponseDTO> getUserBorrowedBooks(Long userId) {
        User user = getUserById(userId);
        List<BookResponseDTO> borrowedBooks = new ArrayList<>();
        List<Loan> loans = user.getLoans(); // get all loans associated with the user

        for (Loan loan : loans)
            borrowedBooks.add(new BookResponseDTO(loan.getBook())); // for each loan get book and add it to borrowed books by the user list

        return borrowedBooks;
    }
}
