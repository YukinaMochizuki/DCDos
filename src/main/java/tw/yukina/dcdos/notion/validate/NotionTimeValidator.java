package tw.yukina.dcdos.notion.validate;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import tw.yukina.dcdos.notion.request.ProjectRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotionTimeValidator implements ConstraintValidator<NotionTime, String> {

    @Override
    public void initialize(NotionTime constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null) return true;
        return GenericValidator.isDate(value, "HH:mm", true);
    }
}
