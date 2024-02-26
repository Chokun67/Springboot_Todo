package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="todo")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;
}
