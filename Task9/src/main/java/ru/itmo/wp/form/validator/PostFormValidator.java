package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.UserCredentials;

@Component
public class PostFormValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return PostForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostForm postForm = (PostForm) target;
            postForm.getTags().chars().forEach(c -> {
                if (!(Character.isLetter(c) || Character.isWhitespace(c))) {
                    errors.rejectValue("tags", "tags.pattern-mismatch", "Tags can only contain latin letters");
                }
            });
        }
    }
}
