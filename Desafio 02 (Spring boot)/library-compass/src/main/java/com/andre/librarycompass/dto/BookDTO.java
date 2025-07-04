package com.andre.librarycompass.dto;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.validation.Year;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookDTO {
    @NotBlank(message = "O título não pode ser vazio")
    private String title;

    @NotBlank(message = "O autor não pode ser vazio")
    private String author;

    @NotNull(message = "Ano de publicação do livro não informado")
    @Positive(message = "Ano de publicação não pode ser negativo")
    @Year
    private Integer yearPublication;

    public Book toEntity(){
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setYearPublication(yearPublication);

        return book;
    }
}
