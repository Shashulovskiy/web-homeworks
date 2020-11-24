package ru.itmo.wp.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Table(
        indexes = {@Index(columnList = "creationTime")}
)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotEmpty
    @Size(max=100)
    @Column(name="content", length=512)
    private String content;

    @CreationTimestamp
    private Date creationTime;
}
