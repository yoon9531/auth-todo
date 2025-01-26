package com.example.authtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    @Size(min = 3, max = 50)
    @ToString.Exclude
    private String username;

    @Column(nullable = false)
    @NotNull
    @JsonIgnore
    @Size(min = 8, max = 100)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnoreProperties({"user"})
    @ToString.Exclude
    private List<Task> tasks = new ArrayList<>();
}