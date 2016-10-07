package currying;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CurryTest {

    @Test
    public void curry() throws Exception {

        Function5<Integer, Double, Long, BigDecimal, String, String> f5 = (a, b, c, d, e) -> a - b / c + d.negate().doubleValue() + e;

        String result = Curry.curry(f5).apply(200).apply(100.0).apply(2L).apply(new BigDecimal("15.0")).apply(" chickens with curry");
        assertEquals("135.0 chickens with curry", result);
    }
}