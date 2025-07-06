package com.andre.librarycompass.dto.response;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.enums.BookStatus;
import com.andre.librarycompass.validation.Year;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class BookResponseDTO {
    private Long id;

    @NotBlank(message = "O título não pode ser vazio")
    private String title;

    @NotBlank(message = "O autor não pode ser vazio")
    private String author;

    @NotNull(message = "Ano de publicação do livro não informado")
    @Positive(message = "Ano de publicação não pode ser negativo")
    @Year
    private Integer yearPublication;

    private BookStatus status;

    // Create a BookResponseDTO from a book entity
    public BookResponseDTO(Book book){
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        yearPublication = book.getYearPublication();
        status = book.getStatus();
    }

    // Pass BookResponseDTO to book entity
    public Book toEntity(){
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setYearPublication(yearPublication);
        book.setStatus(status);

        return book;
    }
}