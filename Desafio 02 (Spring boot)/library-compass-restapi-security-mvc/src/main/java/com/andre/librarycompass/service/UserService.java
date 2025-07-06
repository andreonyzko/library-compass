package com.andre.librarycompass.service;

import com.andre.librarycompass.dto.response.BookResponseDTO;
import com.andre.librarycompass.dto.request.UserDTO;
import com.andre.librarycompass.dto.response.UserResponseDTO;
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
        // Return the User or throw NotFoundException indicating User was not found
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public List<UserResponseDTO> findAll(){
        // Get list of all user, transform to a stream, and for each user convert to an UserResponseDTO
        return userRepository.findAll().stream().map(UserResponseDTO::new).collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long userId){
        // Find the Book and return a new BookResponseDTO
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return new UserResponseDTO(user);
    }

    public UserResponseDTO save(UserDTO userDTO, Long userId) {
        User user;
        if(userId == null){ // if a register, so get Entity from DTO and set id as null
            user = userDTO.toEntity();
            user.setId(null);
        }
        else{ // else, get the user from database and update fields
            user = getUserById(userId);
            user.setName(userDTO.getName());
        }

        return new UserResponseDTO(userRepository.save(user));
    }

    public void deleteById(Long userId) {
        User user = getUserById(userId);

        // check if user has pending loans
        if (!user.getLoans().isEmpty()) {

            // Construct a String with the all books that is pending of return by user
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

        // for each loan get book convert to BookResponseDTO, and add it to borrowed books by the user list
        for (Loan loan : loans)
            borrowedBooks.add(new BookResponseDTO(loan.getBook()));

        return borrowedBooks;
    }
}
