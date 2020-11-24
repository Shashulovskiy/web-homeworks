package ru.itmo.wp.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserToggleRequest {

    @NotNull
    private Long id;

    @NotNull
    private Boolean disabled;
}
