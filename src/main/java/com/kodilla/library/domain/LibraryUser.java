package com.kodilla.library.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
