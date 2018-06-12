package com.kodilla.library.domain;

import com.kodilla.library.enums.BorrowStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Date borrowEnd;

    @NotNull
    @Column(name = "STATUS")
    private BorrowStatus borrowStatus;
}
