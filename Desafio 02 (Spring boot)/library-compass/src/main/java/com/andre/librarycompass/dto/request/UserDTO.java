package com.andre.librarycompass.dto.request;

import com.andre.librarycompass.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserDTO {
    @NotBlank(message = "O nome do usuário não pode ser vazio")
    private String name;

    public UserDTO(User user){
        name = user.getName();
    }

    public User toEntity(){
        User user = new User();
        user.setName(name);

        return user;
    }
}
