package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @NotNull
    @Column(name = "PUBLICATION_YEAR")
    private int publicationYear;

    @OneToMany(
            targetEntity = BookCopy.class,
            mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<BookCopy> bookCopies = new ArrayList<>();

    //For test only
    public Book(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.bookCopies = new ArrayList<>();
    }
}
