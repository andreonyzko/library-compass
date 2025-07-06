package com.andre.librarycompass.dto.request;

import com.andre.librarycompass.validation.Year;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookPatchDTO {

    // Validation: regex pattern avoids fields with only spaces
    @Pattern(regexp = ".*\\S.*", message = "O título não pode ser vazio")
    private String title;

    // Validation: regex pattern avoids fields with only spaces
    @Pattern(regexp = ".*\\S.*", message = "O autor não pode ser vazio")
    private String author;

    @Positive(message = "Ano de publicação não pode ser negativo")
    @Year
    private Integer yearPublication;
}
