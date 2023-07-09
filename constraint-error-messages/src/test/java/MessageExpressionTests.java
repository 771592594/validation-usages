import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MessageExpressionTests {

    private class Car {
        @NotNull
        private String manufacturer;

        @Size(
                min = 2,
                max = 14,
                message = "The license plate '${validatedValue}' must be between {min} and {max} characters long"
        )
        private String licensePlate;

        @Min(
                value = 2,
                message = "There must be at least {value} seat${value > 1 ? 's' : ''}"
        )
        private int seatCount;

        @DecimalMax(
                value = "350",
                message = "The top speed ${formatter.format('%1$.2f', validatedValue)} is higher than {value}"
        )
        private double topSpeed;
    }

    @Test
    public void test() {
        Car car = new Car();
        car.licensePlate = "a";
        car.seatCount = 0;
        car.topSpeed = 400;
        Assert.assertEquals(
                ValidatorUtils.validateProperty(car, "licensePlate").iterator().next().getMessage(),
                "The license plate 'a' must be between 2 and 14 characters long"
        );
        Assert.assertEquals(
                ValidatorUtils.validateProperty(car, "seatCount").iterator().next().getMessage(),
                "There must be at least 2 seats"
        );
        Assert.assertEquals(
                ValidatorUtils.validateProperty(car, "topSpeed").iterator().next().getMessage(),
                "The top speed 400.00 is higher than 350"
        );
    }
}
