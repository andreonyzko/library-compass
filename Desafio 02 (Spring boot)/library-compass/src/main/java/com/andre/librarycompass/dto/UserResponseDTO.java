package com.andre.librarycompass.dto;

import com.andre.librarycompass.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserResponseDTO {
    private Long id;
    private String name;

    public UserResponseDTO(User user){
        id = user.getId();
        name = user.getName();
    }
}
