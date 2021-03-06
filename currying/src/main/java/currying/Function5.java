package currying;

@FunctionalInterface
public interface Function5<T1, T2, T3, T4, T5, R> {
    R apply(T1 p1, T2 p2, T3 p3, T4 p4, T5 p5);
}
