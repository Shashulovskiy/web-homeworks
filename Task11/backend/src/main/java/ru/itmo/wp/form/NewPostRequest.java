package ru.itmo.wp.form;

import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class NewPostRequest {
    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    @NotEmpty
    @Size(min = 1, max = 10000)
    @Lob
    private String text;

    @NotEmpty
    private String jwt;
}
