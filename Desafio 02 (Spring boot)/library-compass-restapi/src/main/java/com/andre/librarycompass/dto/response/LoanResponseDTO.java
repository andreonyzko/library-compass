package com.andre.librarycompass.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoanResponseDTO {
    private BookResponseDTO book;
    private UserResponseDTO user;

    // Create LoanResponseDTO from bookDTO and userDTO
    public LoanResponseDTO(BookResponseDTO bookDTO, UserResponseDTO userDTO){
        book = bookDTO;
        user = userDTO;
    }
}
