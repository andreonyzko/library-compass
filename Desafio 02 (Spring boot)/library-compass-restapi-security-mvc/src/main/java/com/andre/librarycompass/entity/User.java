package com.andre.librarycompass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data // Generate getters, setters, hashcode and equals
@NoArgsConstructor // Generate constructor without params
@AllArgsConstructor // Generate constructor with all fields as params

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user") // A user has many loans
    private List<Loan> loans = new ArrayList<>();

    // Add a loan to the user:
    public void addLoan(Loan loan){
        loans.add(loan);
    }

    // Remove a loan from the user:
    public void removeLoan(Loan loan){
        loans.remove(loan);
    }
}
