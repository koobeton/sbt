package lesson6.classwork.utils;

import lesson6.testmodel.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class StringConstantsValidatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void validClass() throws Exception {
        assertTrue(StringConstantsValidator.validate(ValidConstantsClass.class));
    }

    @Test
    public void validInterface() throws Exception {
        assertTrue(StringConstantsValidator.validate(ValidConstantsInterface.class));
    }

    @Test
    public void invalidClass() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("These fields are not valid String constants");
        StringConstantsValidator.validate(InvalidConstantsClass.class);

    }

    @Test
    public void invalidInterface() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("These fields are not valid String constants");
        StringConstantsValidator.validate(InvalidConstantsInterface.class);
    }
}