package com.kodilla.library.domain;

import com.kodilla.library.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BORROW_ENTRIES")
public class BorrowEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENTRY_ID")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "LIBRARY_USER_ID")
    private LibraryUser libraryUser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "BOOK_COPY_ID")
    private BookCopy bookCopy;

    @NotNull
    @Column(name = "START")
    private Date borrowStart;

    @Column(name = "PLANNED_END")
    private Date borrowEnd = null;

    @NotNull
    @Column(name = "STATUS")
    private BorrowStatus borrowStatus;
}
