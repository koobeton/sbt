package lesson4;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CountMapTest {

    private static final String ONCE = "once";
    private static final String TWICE = "twice";
    private static final String THREE_TIMES = "three times";
    private static final String STRING_NEVER_WAS_ADDED = "never was added";
    private static final int INT_NEVER_WAS_ADDED = 100500;

    private CountMap<Integer> integerCountMap;
    private CountMap<String> stringCountMap;

    @Before
    public void setUp() throws Exception {

        integerCountMap = new CountMapImpl<>();
        stringCountMap = new CountMapImpl<>();

        integerCountMap.add(10);
        integerCountMap.add(10);
        integerCountMap.add(5);
        integerCountMap.add(6);
        integerCountMap.add(5);
        integerCountMap.add(10);

        stringCountMap.add(ONCE);
        stringCountMap.add(TWICE);
        stringCountMap.add(THREE_TIMES);
        stringCountMap.add(THREE_TIMES);
        stringCountMap.add(TWICE);
        stringCountMap.add(THREE_TIMES);
    }

    @Test
    public void add() throws Exception {

        assertEquals(2, integerCountMap.getCount(5));
        assertEquals(1, integerCountMap.getCount(6));
        assertEquals(3, integerCountMap.getCount(10));

        assertEquals(1, stringCountMap.getCount(ONCE));
        assertEquals(2, stringCountMap.getCount(TWICE));
        assertEquals(3, stringCountMap.getCount(THREE_TIMES));
    }

    @Test
    public void getCount() throws Exception {

        assertEquals(3, integerCountMap.getCount(10));
        assertEquals(0, integerCountMap.getCount(INT_NEVER_WAS_ADDED));

        assertEquals(3, stringCountMap.getCount(THREE_TIMES));
        assertEquals(0, stringCountMap.getCount(STRING_NEVER_WAS_ADDED));
    }

    @Test
    public void remove() throws Exception {

        assertEquals(3, integerCountMap.remove(10));
        assertEquals(2, integerCountMap.getCount(10));

        assertEquals(1, integerCountMap.remove(6));
        assertEquals(0, integerCountMap.getCount(6));

        assertEquals(0, integerCountMap.remove(INT_NEVER_WAS_ADDED));

        assertEquals(3, stringCountMap.remove(THREE_TIMES));
        assertEquals(2, stringCountMap.getCount(THREE_TIMES));

        assertEquals(1, stringCountMap.remove(ONCE));
        assertEquals(0, stringCountMap.getCount(ONCE));

        assertEquals(0, stringCountMap.remove(STRING_NEVER_WAS_ADDED));
    }

    @Test
    public void size() throws Exception {

        assertEquals(3, integerCountMap.size());

        integerCountMap.remove(10);
        assertEquals(3, integerCountMap.size());

        integerCountMap.remove(6);
        assertEquals(2, integerCountMap.size());

        integerCountMap.add(100);
        integerCountMap.add(100);
        integerCountMap.add(200);
        assertEquals(4, integerCountMap.size());


        assertEquals(3, stringCountMap.size());

        stringCountMap.remove(THREE_TIMES);
        assertEquals(3, stringCountMap.size());

        stringCountMap.remove(ONCE);
        assertEquals(2, stringCountMap.size());

        stringCountMap.add("one more string");
        stringCountMap.add("one more string");
        stringCountMap.add("another string");
        assertEquals(4, stringCountMap.size());
    }

    @Test
    public void addAll() throws Exception {

        CountMap<Integer> integerSource = new CountMapImpl<>();
        integerSource.add(100);
        integerSource.add(10);
        integerSource.add(10);
        integerCountMap.addAll(integerSource);
        assertEquals(4, integerCountMap.size());
        assertEquals(1, integerCountMap.getCount(100));
        assertEquals(2, integerCountMap.getCount(5));
        assertEquals(1, integerCountMap.getCount(6));
        assertEquals(5, integerCountMap.getCount(10));

        CountMap<String> stringSource = new CountMapImpl<>();
        stringSource.add("one more string");
        stringSource.add(THREE_TIMES);
        stringSource.add(THREE_TIMES);
        stringCountMap.addAll(stringSource);
        assertEquals(4, stringCountMap.size());
        assertEquals(1, stringCountMap.getCount("one more string"));
        assertEquals(1, stringCountMap.getCount(ONCE));
        assertEquals(2, stringCountMap.getCount(TWICE));
        assertEquals(5, stringCountMap.getCount(THREE_TIMES));
    }

    @Test
    public void toMap() throws Exception {

        Map<Integer, Integer> integerClone = integerCountMap.toMap();
        assertEquals(integerCountMap.size(), integerClone.size());
        for (Integer key : integerClone.keySet()) {
            assertEquals(integerCountMap.getCount(key), (int) integerClone.get(key));
        }

        Map<String, Integer> stringClone = stringCountMap.toMap();
        assertEquals(stringCountMap.size(), stringClone.size());
        for (String key : stringClone.keySet()) {
            assertEquals(stringCountMap.getCount(key), (int) stringClone.get(key));
        }
    }

    @Test
    public void toDestinationMap() throws Exception {

        Map<Integer, Integer> integerDestination = new HashMap<>();
        integerDestination.put(100, 100);
        integerDestination.put(10, 10);
        integerCountMap.toMap(integerDestination);
        assertEquals(4, integerDestination.size());
        assertEquals(100, (int) integerDestination.get(100));
        assertEquals(2, (int) integerDestination.get(5));
        assertEquals(1, (int) integerDestination.get(6));
        assertEquals(3, (int) integerDestination.get(10));

        Map<String, Integer> stringDestination = new HashMap<>();
        stringDestination.put("one more string", 100);
        stringDestination.put(THREE_TIMES, 300);
        stringCountMap.toMap(stringDestination);
        assertEquals(4, stringDestination.size());
        assertEquals(100, (int) stringDestination.get("one more string"));
        assertEquals(1, (int) stringDestination.get(ONCE));
        assertEquals(2, (int) stringDestination.get(TWICE));
        assertEquals(3, (int) stringDestination.get(THREE_TIMES));
    }
}