package com.andre.librarycompass.entity;

import com.andre.librarycompass.entity.enums.BookStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "year_publication")
    private Integer yearPublication;

    @Column(name = "status")
    private BookStatus available;

    @OneToOne(mappedBy = "book", cascade = CascadeType.REMOVE)
    private Loan loan;
}
