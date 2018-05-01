package com.kodilla.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kodilla.library.enums.BookCopyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(
        name = "BookCopy.retrieveAvailableCopiesByBookId",
        query = "SELECT * FROM BOOK_COPIES WHERE STATUS = 1 AND BOOK_ID = :BOOK_ID",
        resultClass = BookCopy.class
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_COPIES")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @Column(name = "STATUS")
    private BookCopyStatus status;

    @JsonIgnore
    @OneToMany(
            targetEntity = BorrowEntry.class,
            mappedBy = "bookCopy",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<BorrowEntry> borrowEntries = new ArrayList<>();
}
