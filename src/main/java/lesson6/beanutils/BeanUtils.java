package lesson6.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {

        Map<String, Method> gettersOfFrom = Stream.of(from.getClass().getMethods())
                .filter(m -> m.getReturnType() != void.class)
                .filter(m -> m.getName().startsWith("get"))
                .filter(m -> m.getParameterCount() == 0)
                .collect(Collectors.toMap(
                        m -> m.getName().substring(3),
                        Function.identity()));

        Map<String, Method> settersOfTo = Stream.of(to.getClass().getMethods())
                .filter(m -> m.getName().startsWith("set"))
                .filter(m -> m.getParameterCount() == 1)
                .collect(Collectors.toMap(
                        m -> m.getName().substring(3),
                        Function.identity()));

        settersOfTo.forEach(
                (methodName, setter) -> {
                    Method getter = gettersOfFrom.get(methodName);
                    if (getter != null
                            && setter.getParameterTypes()[0].isAssignableFrom(getter.getReturnType())) {
                        try {
                            setter.invoke(to, getter.invoke(from));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException("Exception during access to method "
                                    + getter.getName(), e);
                        }
                    }
                });
    }
}
