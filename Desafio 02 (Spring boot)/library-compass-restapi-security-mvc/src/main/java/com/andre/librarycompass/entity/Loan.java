package com.andre.librarycompass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generate getters, setters, hashcode and equals
@NoArgsConstructor // Generate constructor without params
@AllArgsConstructor // Generate constructor with all fields as params

@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(cascade = CascadeType.MERGE) // Many loans from one user
    @JoinColumn(name = "user_id")
    private User user;
}
