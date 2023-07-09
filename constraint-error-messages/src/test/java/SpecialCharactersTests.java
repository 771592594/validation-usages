import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SpecialCharactersTests {

    private class A {
        @NotBlank(message = "\\$\\{hello\\}\\")
        private String name;
    }

    @Test
    public void test() {
        Set<ConstraintViolation<A>> validate = ValidatorUtils.validate(new A());
        Assert.assertEquals("${hello}\\", validate.iterator().next().getMessage());
    }
}
