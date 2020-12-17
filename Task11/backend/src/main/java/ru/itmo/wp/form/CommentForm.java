package ru.itmo.wp.form;

import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentForm {

    @Lob
    @NotEmpty
    @Size(max = 400)
    private String text;

    @NotEmpty
    private String jwt;
}
