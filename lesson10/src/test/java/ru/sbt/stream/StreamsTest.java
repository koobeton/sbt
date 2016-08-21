package ru.sbt.stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.stream.domain.Child;
import ru.sbt.stream.domain.Person;

import java.util.*;

import static org.junit.Assert.*;

public class StreamsTest {

    private List<Person> personList;
    private Set<Child> childSet;

    @Before
    public void setUp() throws Exception {

        personList = new ArrayList<>();
        personList.add(new Person("Alex", 30));
        personList.add(new Person("Bob", 20));
        personList.add(new Person("Mary", 25));

        childSet = new HashSet<>();
        childSet.add(new Child("Alice", 15));
        childSet.add(new Child("Max", 10));
        childSet.add(new Child("John", 12));
    }

    @Test
    public void ofAndToMapList() throws Exception {

        Map<String, Integer> m = Streams.of(personList)
                .toMap(Person::getName, Person::getAge);

        assertEquals(3, m.size());

        assertEquals(30, (long) m.get("Alex"));
        assertEquals(20, (long) m.get("Bob"));
        assertEquals(25, (long) m.get("Mary"));
    }

    @Test
    public void ofAndToMapSet() throws Exception {

        Map<String, Integer> m = Streams.of(childSet)
                .toMap(Person::getName, Person::getAge);

        assertEquals(3, m.size());

        assertEquals(15, (long) m.get("Alice"));
        assertEquals(10, (long) m.get("Max"));
        assertEquals(12, (long) m.get("John"));
    }

    @Test
    public void filterList() throws Exception {

        Map<String, Integer> m = Streams.of(personList)
                .filter(p -> p.getName().length() > 3)
                .toMap(Person::getName, Person::getAge);

        assertEquals(2, m.size());

        assertEquals(30, (long) m.get("Alex"));
        assertEquals(25, (long) m.get("Mary"));
    }

    @Test
    public void filterSet() throws Exception {

        Map<String, Integer> m = Streams.of(childSet)
                .filter(p -> p.getName().length() > 3)
                .toMap(Person::getName, Person::getAge);

        assertEquals(2, m.size());

        assertEquals(15, (long) m.get("Alice"));
        assertEquals(12, (long) m.get("John"));
    }

    @Test
    public void transformList() throws Exception {

        Map<String, Integer> m = Streams.of(personList)
                .transform(p -> new Child(p.getName() + "'s child", p.getAge() - 20))
                .toMap(Person::getName, Person::getAge);

        assertEquals(3, m.size());

        assertEquals(10, (long) m.get("Alex's child"));
        assertEquals(0, (long) m.get("Bob's child"));
        assertEquals(5, (long) m.get("Mary's child"));
    }

    @Test
    public void transformSet() throws Exception {

        Map<String, Integer> m = Streams.of(childSet)
                .transform(p -> new Child(p.getName() + "'s sibling", p.getAge() + 5))
                .toMap(Person::getName, Person::getAge);

        assertEquals(3, m.size());

        assertEquals(20, (long) m.get("Alice's sibling"));
        assertEquals(15, (long) m.get("Max's sibling"));
        assertEquals(17, (long) m.get("John's sibling"));
    }

    @Test
    public void pipeline() throws Exception {

        Map<String, Person> m = Streams.of(personList)
                .filter(p -> p.getAge() > 20)
                .transform(p -> new Person(p.getAge() + 30))
                .toMap(Person::getName, p -> p);

        assertEquals(1, m.size());
        assertTrue(m.containsKey(null));
        assertNull(m.get(null).getName());
        assertEquals(55, m.get(null).getAge());
    }

    @After
    public void originalCollectionImmutability() throws Exception {

        assertEquals(3, personList.size());
        assertEquals("Alex", personList.get(0).getName());
        assertEquals(30, personList.get(0).getAge());
        assertEquals("Bob", personList.get(1).getName());
        assertEquals(20, personList.get(1).getAge());
        assertEquals("Mary", personList.get(2).getName());
        assertEquals(25, personList.get(2).getAge());

        assertEquals(3, childSet.size());
        for (Child c : childSet) {
            switch (c.getName()) {
                case "Alice":
                    assertEquals(15, c.getAge());
                    break;
                case "Max":
                    assertEquals(10, c.getAge());
                    break;
                case "John":
                    assertEquals(12, c.getAge());
                    break;
                default:
                    throw new IllegalStateException("Unexpected element in set");
            }
        }
    }
}