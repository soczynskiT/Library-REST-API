package com.kodilla.library.domain;

import com.kodilla.library.enums.BookCopyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
