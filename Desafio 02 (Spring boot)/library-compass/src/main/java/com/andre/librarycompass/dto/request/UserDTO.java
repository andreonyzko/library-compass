package com.andre.librarycompass.dto.request;

import com.andre.librarycompass.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDTO {
    @NotBlank(message = "O nome do usuário não pode ser vazio")
    private String name;

    public User toEntity(){
        User user = new User();
        user.setName(name);

        return user;
    }
}
