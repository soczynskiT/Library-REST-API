package com.kodilla.library.domain;

import com.kodilla.library.enums.BookCopyStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(
            targetEntity = BorrowEntry.class,
            mappedBy = "bookCopy",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<BorrowEntry> borrowEntries = new ArrayList<>();

    //For test only
    public BookCopy(Book book, BookCopyStatus status) {
        this.book = book;
        this.status = status;
        this.borrowEntries = new ArrayList<>();
    }
}
