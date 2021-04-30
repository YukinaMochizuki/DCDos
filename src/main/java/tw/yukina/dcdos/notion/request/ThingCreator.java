package tw.yukina.dcdos.notion.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import tw.yukina.dcdos.notion.entity.thing.Thing;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ThingCreator {

    @Getter
    private final Thing.ThingBuilder thingBuilder;

    @Getter
    @Setter
    private Validator validator;

    private final ThingRequest thingRequest;

    public ThingCreator(AutowireCapableBeanFactory autowireCapableBeanFactory, ThingRequest thingRequest){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();

        thingBuilder = Thing.builder();
        this.thingRequest = thingRequest;
    }

    public Set<ConstraintViolation<Thing>> validate(){
        return validator.validate(thingBuilder.build());
    }

    public String validateAndCreate(){
        Set<ConstraintViolation<Thing>> constraintViolations = validate();
        String validateMessage = "";
        if(!constraintViolations.isEmpty())validateMessage = constraintViolations.iterator().next().getMessage();
        return RequestUtil.getResponse(validateMessage , thingRequest.postThing(thingBuilder.build()));
    }
}
