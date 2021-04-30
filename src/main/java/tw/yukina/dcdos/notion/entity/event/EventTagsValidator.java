package tw.yukina.dcdos.notion.entity.event;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EventTagsValidator implements ConstraintValidator<EventTags, String[]> {
    @Override
    public void initialize(EventTags constraintAnnotation) {

    }

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return Arrays.stream(value).allMatch(s -> Arrays.asList(Event.getAllValidTags()).contains(s));
    }
}
