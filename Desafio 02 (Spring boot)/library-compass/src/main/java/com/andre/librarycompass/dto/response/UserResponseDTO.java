package com.andre.librarycompass.dto.response;

import com.andre.librarycompass.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserResponseDTO {
    private Long id;

    @NotBlank(message = "O nome do usuário não pode ser vazio")
    private String name;

    @JsonIgnore
    private boolean canDelete;

    public UserResponseDTO(User user){
        id = user.getId();
        name = user.getName();
        canDelete = user.getLoans().isEmpty();
    }

    public User toEntity(){
        User user = new User();
        user.setId(id);
        user.setName(name);

        return user;
    }
}
