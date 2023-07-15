import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.junit.Test;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.stream.Collectors;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


public class CompositedConstraintTests {

    @ConstraintComposition(value = CompositionType.OR)
    @Digits(integer = 10, fraction = 10)
    @Size(min = 6, max = 10)
    @Documented
    @Constraint(validatedBy = {})
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @interface Password {
        String message() default "fail";

        Class<?>[] groups() default { };

        Class<? extends Payload>[] payload() default { };
    }


    class A {
        @Password
        String password = "1234aa";
    }

    @Test
    public void test() {
        System.out.println(ValidatorUtils.validate(new A()).stream().map(it -> it.getMessage()).collect(Collectors.joining("\n")));
    }
}
