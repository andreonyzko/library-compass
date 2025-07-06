package com.andre.librarycompass.dto.response;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.entity.enums.BookStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookResponseDTO {
    private Long id;
    private String title, author;
    private Integer yearPublication;
    private BookStatus status;

    public BookResponseDTO(Book book){
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        yearPublication = book.getYearPublication();
        status = book.getStatus();
    }
}