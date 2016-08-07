package lesson6.classwork.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.*;

public class StringConstantsValidator {

    public static boolean validate(Class<?> clazz) {

        List<Field> stringConstants = Stream.of(clazz.getFields())
                .filter(f -> f.getModifiers() == PUBLIC + STATIC + FINAL)
                .filter(f -> f.getType() == String.class)
                .collect(Collectors.toList());

        StringJoiner errors = new StringJoiner(", ");
        for (Field f : stringConstants) {
            try {
                if (!(f.getName().toUpperCase().equals(f.getName())
                        && f.getName().equals(f.get(null)))) {
                    errors.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Exception during access to field: "
                        + f.getName(), e);
            }
        }

        if (errors.length() == 0) {
            return true;
        } else {
            throw new RuntimeException("These fields are not valid String constants: "
                    + errors);
        }
    }
}
