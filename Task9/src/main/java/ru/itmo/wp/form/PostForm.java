package ru.itmo.wp.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostForm {
    @NotEmpty
    @NotNull
    @Size(max = 45)
    private String title;

    @NotEmpty
    @NotNull
    @Size(max = 1000)
    private String text;

    @NotNull
    private String tags;
}
