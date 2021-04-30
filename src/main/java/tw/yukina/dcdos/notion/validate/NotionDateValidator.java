package tw.yukina.dcdos.notion.validate;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import tw.yukina.dcdos.notion.request.ProjectRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class NotionDateValidator implements ConstraintValidator<NotionDate, String> {

    @Autowired
    private ProjectRequest projectRequest;

    @Override
    public void initialize(NotionDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null) return true;
        return GenericValidator.isDate(value, "yyyy/MM/dd", true);
    }
}
