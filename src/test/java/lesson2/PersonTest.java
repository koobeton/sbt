package lesson2;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class PersonTest {

    private Person eddard;
    private Person robert;
    private Person catelyn;
    private Person cersei;

    @org.junit.Before
    public void setUp() throws Exception {
        eddard = new Person(true, "Eddard Stark");
        robert = new Person(true, "Robert Baratheon");
        catelyn = new Person(false, "Catelyn Stark");
        cersei = new Person(false, "Cersei Lannister");
    }

    /**
     * Проверяем, что человека нельзя женить на null
     */
    @org.junit.Test
    public void marryToNull() throws Exception {
        assertFalse(eddard.marry(null));
    }

    /**
     * Проверяем, что человека нельзя женить на самом себе
     */
    @org.junit.Test
    public void marryToSelf() throws Exception {
        assertFalse(eddard.marry(eddard));
    }

    /**
     * Проверяем, что нельзя женить двух мужчин
     */
    @org.junit.Test
    public void marryTwoMan() throws Exception {
        assertFalse(eddard.marry(robert));
    }

    /**
     * Проверяем, что нельзя женить двух женщин
     */
    @org.junit.Test
    public void marryTwoWoman() throws Exception {
        assertFalse(catelyn.marry(cersei));
    }

    /**
     * Проверяем, что нельзя женить уже женатых друг на друге
     */
    @org.junit.Test
    public void marryAgain() throws Exception {
        eddard.marry(catelyn);
        assertFalse(eddard.marry(catelyn));
    }

    /**
     * Проверяем женитьбу разнополых людей
     */
    @org.junit.Test
    public void marry() throws Exception {
        assertTrue(eddard.marry(catelyn));
    }

    /**
     * Проверяем состояние супругов после свадьбы
     */
    @org.junit.Test
    public void marrySpouseState() throws Exception {
        eddard.marry(catelyn);
        Field spouse = Person.class.getDeclaredField("spouse");
        spouse.setAccessible(true);
        assertTrue(spouse.get(eddard).equals(catelyn)
                && spouse.get(catelyn).equals(eddard));
        spouse.setAccessible(false);
    }

    /**
     * Проверяем женитьбу людей, уже женатых на других
     */
    @org.junit.Test
    public void marryAndDivorce() throws Exception {
        eddard.marry(catelyn);
        robert.marry(cersei);
        assertTrue(eddard.marry(cersei));
    }

    /**
     * Проверяем состояние 4 человек после свадьбы и разводов
     */
    @org.junit.Test
    public void marryAndDivorceSpouseState() throws Exception {
        eddard.marry(catelyn);
        robert.marry(cersei);
        eddard.marry(cersei);
        Field spouse = Person.class.getDeclaredField("spouse");
        spouse.setAccessible(true);
        assertTrue(spouse.get(eddard).equals(cersei)
                && spouse.get(cersei).equals(eddard)
                && spouse.get(catelyn) == null
                && spouse.get(robert) == null);
        spouse.setAccessible(false);
    }

    /**
     * Проверяем, что нельзя дать развод неженатому человеку
     */
    @org.junit.Test
    public void divorceUnmarried() throws Exception {
        assertFalse(eddard.divorce());
    }

    /**
     * Проверяем развод
     */
    @org.junit.Test
    public void divorce() throws Exception {
        eddard.marry(catelyn);
        assertTrue(catelyn.divorce());
    }

    /**
     * Проверяем состояние супругов после развода
     */
    @org.junit.Test
    public void divorceSpouseState() throws Exception {
        eddard.marry(catelyn);
        catelyn.divorce();
        Field spouse = Person.class.getDeclaredField("spouse");
        spouse.setAccessible(true);
        assertTrue(spouse.get(eddard) == null
                && spouse.get(catelyn) == null);
        spouse.setAccessible(false);
    }
}