package ru.itmo.wp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.wp.domain.User;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;

    private String title;

    private String text;

    private User user;

    private Integer commentsCount;

    private Date creationTime;
}
