package ru.itmo.wp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Role {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Name name;

    /** @noinspection unused*/
    public Role() {
    }

    public Role(@NotNull Name name) {
        this.name = name;
    }

    public enum Name {
        WRITER,
        ADMIN
    }
}
