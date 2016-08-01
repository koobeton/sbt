package lesson4;

import lesson4.testmodel.BaseClass;
import lesson4.testmodel.SubClass;
import lesson4.testmodel.SuperClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionUtilsTest {

    private List<SuperClass> listSuper;
    private List<BaseClass> listBase;   //BaseClass extends SuperClass
    private List<SubClass> listSub;     //SubClass extends BaseClass

    @Before
    public void setUp() throws Exception {

        listSuper = new ArrayList<>();
        listSuper.add(new SuperClass());
        listSuper.add(new SuperClass());
        listSuper.add(new SuperClass());

        listBase = new ArrayList<>();
        listBase.add(new BaseClass());
        listBase.add(new BaseClass());
        listBase.add(new BaseClass());

        listSub = new ArrayList<>();
        listSub.add(new SubClass());
        listSub.add(new SubClass());
        listSub.add(new SubClass());
    }

    @Test
    public void addAll() throws Exception {

        CollectionUtils.addAll(listSub, listBase);
        assertEquals(6, listBase.size());

        CollectionUtils.addAll(listBase, listSuper);
        assertEquals(9, listSuper.size());
    }

    @Test
    public void newArrayList() throws Exception {

        SubClass subInstance = new SubClass();

        listSub = CollectionUtils.newArrayList();
        listSub.add(subInstance);
        listSub.add(new SubClass());
        assertEquals(2, listSub.size());

        listBase = CollectionUtils.newArrayList();
        listBase.add(subInstance);
        listBase.add(new BaseClass());
        assertEquals(2, listBase.size());

        listSuper = CollectionUtils.newArrayList();
        listSuper.add(subInstance);
        listSuper.add(new SuperClass());
        assertEquals(2, listSuper.size());
    }

    @Test
    public void indexOf() throws Exception {

        BaseClass baseInstance = new BaseClass();

        listBase.add(baseInstance);
        assertEquals(3, CollectionUtils.indexOf(listBase, baseInstance));

        listSuper.add(baseInstance);
        assertEquals(3, CollectionUtils.indexOf(listSuper, baseInstance));
    }

    @Test
    public void limit() throws Exception {

        assertEquals(2, CollectionUtils.limit(listBase, 2).size());
        assertEquals(1, CollectionUtils.limit(listSuper, 1).size());
    }

    @Test
    public void add() throws Exception {

        CollectionUtils.add(listSuper, new SubClass());
        assertEquals(4, listSuper.size());
    }

    @Test
    public void removeAll() throws Exception {

        listSuper.addAll(listSub);
        assertEquals(6, listSuper.size());

        CollectionUtils.removeAll(listSuper, listSub);
        assertEquals(3, listSuper.size());
    }

    @Test
    public void containsAll() throws Exception {

        listSuper.addAll(listSub);
        assertTrue(CollectionUtils.containsAll(listSuper, listSub));
        assertFalse(CollectionUtils.containsAll(listSub, listSuper));
    }

    @Test
    public void containsAny() throws Exception {

        listSuper.add(listSub.get(0));
        assertTrue(CollectionUtils.containsAny(listSuper, listSub));
        assertFalse(CollectionUtils.containsAny(listSub, listBase));
    }

    @Test
    public void range() throws Exception {

        List<Integer> list = CollectionUtils.range(Arrays.asList(8, 1, 3, 5, 6, 4), 3, 6);
        assertEquals(3, (int) list.get(0));
        assertEquals(4, (int) list.get(1));
        assertEquals(5, (int) list.get(2));
        assertEquals(6, (int) list.get(3));
    }

    @Test
    public void rangeWithComparator() throws Exception {

        List<Integer> list = CollectionUtils.range(Arrays.asList(8, 1, 3, 5, 6, 4), 3, 6, Integer::compare);
        assertEquals(3, (int) list.get(0));
        assertEquals(4, (int) list.get(1));
        assertEquals(5, (int) list.get(2));
        assertEquals(6, (int) list.get(3));
    }
}