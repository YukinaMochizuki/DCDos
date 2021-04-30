package tw.yukina.dcdos.notion.entity.event;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EventTypeValidator implements ConstraintValidator<EventType, String> {
    @Override
    public void initialize(EventType constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null) return true;
        return Arrays.asList(Event.getAllValidType()).contains(value);
    }
}
