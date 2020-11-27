package ru.itmo.wp.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Tag implements Comparable<Tag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public int compareTo(Tag o) {
        return o.name.compareTo(this.name);
    }
}
