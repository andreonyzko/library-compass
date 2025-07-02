package com.andre.librarycompass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonIgnore  // Ignore field when object is parsed to json
    @OneToMany(mappedBy = "user") // A user has many loans
    private List<Loan> loans;
}
