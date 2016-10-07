package currying;

import java.util.function.Function;

public class Curry {

    public static <T1, T2, T3, T4, T5, R> Function<T1, Function<T2, Function<T3, Function<T4, Function<T5, R>>>>> curry(Function5<T1, T2, T3, T4, T5, R> function) {
        return p1 -> p2 -> p3 -> p4 -> p5 -> function.apply(p1, p2, p3, p4, p5);
    }
}
