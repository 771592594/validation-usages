import org.hibernate.validator.constraints.ScriptAssert;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AssertScriptTest {

    @ScriptAssert(
            lang = "groovy",
            script = "_.preferentialPrice < _.price",
            alias = "_",
            message = "preferentialPrice must less than price"
    )
    private class A {
        int price;
        int preferentialPrice;
    }

    @Test
    public void f() {
        Set<ConstraintViolation<A>> validate = ValidatorUtils.validate(new A());
        Assert.assertEquals(
                validate.iterator().next().getMessage(),
                "preferentialPrice must less than price"
        );
        validate = ValidatorUtils.validate(new A());
        Assert.assertEquals(
                validate.iterator().next().getMessage(),
                "preferentialPrice must less than price"
        );
    }
}
