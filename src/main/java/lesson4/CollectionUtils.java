package lesson4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {

    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static <T> List<T> limit(List<T> source, int size) {
        return source.subList(0, size);
    }

    public static <T> void add(List<? super T> source, T o) {
        source.add(o);
    }

    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    //true если первый лист содержит все элементы второго
    public static <T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    //true если первый лист содержит хотя-бы 1 второго
    public static <T> boolean containsAny(List<? extends T> c1, List<? extends T> c2) {
        for (T e : c2) {
            if (c1.contains(e)) return true;
        }
        return false;
    }

    //Нужно создать копию листа, отсортировать его, и вернуть sublist,
    // в котором находятся элементы в диапазоне от min до max.
    // Элементы сравнивать через Comparable.
    // Пример range(Arrays.asList(8, 1, 3, 5, 6, 4), 3, 6) вернет {3, 4, 5, 6}
    public static <T extends Comparable<? super T>> List<T> range(List<? extends T> list, T min, T max) {
        List<T> copy = new ArrayList<>();
        list.sort(null);
        for (T e : list) {
            if (e.compareTo(min) >= 0 && e.compareTo(max) <= 0) {
                copy.add(e);
            }
        }
        return copy;
    }

    //Нужно создать копию листа, отсортировать его, и вернуть sublist,
    // в котором находятся элементы в диапазоне от min до max.
    // Элементы сравнивать через Comparator.
    // Пример range(Arrays.asList(8, 1, 3, 5, 6, 4), 3, 6) вернет {3, 4, 5, 6}
    public static <T> List<T> range(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {
        List<T> copy = new ArrayList<>();
        list.sort(comparator);
        for (T e : list) {
            if (comparator.compare(e, min) >= 0 && comparator.compare(e, max) <= 0) {
                copy.add(e);
            }
        }
        return copy;
    }
}
