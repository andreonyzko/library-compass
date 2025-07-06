package com.andre.librarycompass.entity;

import com.andre.librarycompass.entity.enums.BookStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generate getters, setters, hashcode and equals
@NoArgsConstructor // Generate constructor without params
@AllArgsConstructor // Generate constructor with all fields as params

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
    private BookStatus status;

    @OneToOne(mappedBy = "book") // A book on loan
    private Loan loan;
}
