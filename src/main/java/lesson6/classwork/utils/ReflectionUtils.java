package lesson6.classwork.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static void main(String... args) {

        Object[] targets = {new Object(), 1, "test", Class.class};

        Arrays.stream(targets).forEach(o -> {
            System.out.printf("%n=== %s ===%n", o.getClass());
            printHierarchy(o);
        });

        Arrays.stream(targets).forEach(o -> {
            System.out.printf("%n=== %s ===%n", o.getClass());
            printAllMethods(o).forEach(System.out::println);
        });

        Arrays.stream(targets).forEach(o -> {
            System.out.printf("%n=== %s ===%n", o.getClass());
            invokeAllGetters(o);
        });
    }

    public static void printHierarchy(Object o) {
        Class<?> clazz = o.getClass();
        while (clazz != null) {
            System.out.println(clazz);
            clazz = clazz.getSuperclass();
        }
    }

    public static List<String> printAllMethods(Object o) {

        List<String> result = new ArrayList<>();
        Class<?> clazz = o.getClass();
        while (clazz != null) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                result.add(method.getName());
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    public static void invokeAllGetters(Object o) {

        Class<?> clazz = o.getClass();
        while (clazz != null) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getReturnType() != void.class
                        && method.getName().startsWith("get")
                        && method.getParameterCount() == 0) {
                    method.setAccessible(true);
                    try {
                        System.out.printf("%s = %s%n", method.getName(), method.invoke(o));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    } finally {
                        method.setAccessible(false);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    public static void invokeMethod(Object o, String methodName) {
        Class<?> clazz = o.getClass();
        try {
            Method method = clazz.getMethod(methodName);
            if (method.getParameterCount() > 0) {
                throw new IllegalArgumentException("Method " + methodName + " should have exact 0 params");
            }
            if (method.getReturnType() != void.class) {
                System.out.println(method.invoke(o));
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Exception during reflection access", e);
        }
    }
}
