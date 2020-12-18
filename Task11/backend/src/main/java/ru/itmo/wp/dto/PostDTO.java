package ru.itmo.wp.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.wp.domain.User;

import java.util.Date;

@Data
@Builder
public class PostDTO {
    private Long id;

    private String title;

    private String text;

    private User user;

    private Integer commentsCount;

    private Date creationTime;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String text, User user, Integer commentsCount, Date creationTime) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.user = user;
        this.commentsCount = commentsCount;
        this.creationTime = creationTime;
    }
}
