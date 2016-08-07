package lesson6.beanutils;

import lesson6.testmodel.Getter;
import lesson6.testmodel.Setter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanUtilsTest {

    private Getter getter;
    private Setter setter;
    private Object object1;
    private Object object2;

    @Before
    public void setUp() throws Exception {

        getter = new Getter();
        setter = new Setter();
        object1 = new Object();
        object2 = new Object();

        Getter.staticInteger = 100;
        Getter.staticString = "Static string";
        Getter.staticObject = object1;
        getter.instanceInteger = 500;
        getter.instanceString = "Instance string";
        getter.instanceObject = object2;
        getter.mismatchType = 100500;
        getter.nonPublic = "Non-public method";
    }

    @Test
    public void assign() throws Exception {

        BeanUtils.assign(setter, getter);

        assertEquals(100, Setter.staticInteger);
        assertEquals("Static string", Setter.staticString);
        assertEquals(object1, Setter.staticObject);
        assertEquals(500, setter.instanceInteger);
        assertEquals("Instance string", setter.instanceString);
        assertEquals(object2, setter.instanceObject);
        assertNull(setter.mismatchType);
        assertNull(setter.nonPublic);
        assertNull(setter.getterless);
    }
}