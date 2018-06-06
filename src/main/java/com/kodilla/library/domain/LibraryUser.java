package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "LIBRARY_USERS")
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @NotNull
    @Column(name = "JOINED_DATE")
    private Date joinedDate;

    @OneToMany(
            targetEntity = BorrowEntry.class,
            mappedBy = "libraryUser",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<BorrowEntry> borrowEntries = new ArrayList<>();

    //For test only
    public LibraryUser(String name, String surname, Date joinedDate) {
        this.name = name;
        this.surname = surname;
        this.joinedDate = joinedDate;
        this.borrowEntries = new ArrayList<>();
    }
}
