package fr.ensibs.collections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * JUnit test for the {@link SortedList} class
 *
 * @author Pascale Launay
 * @version 1
 */
public class SortedListTest {

    private static final String[] LIST1 = {"fred", "bob", "greg", "isabel", "erik"};
    private static final String[] LIST2 = {"dave", "alice", "harry", "carol"};

    /**
     * Test of addAll method, of class SortedList.
     */
    @Test
    public void testAddAll() {

        List instance = new SortedList();

        // add an empty set. The sorted list must be empty
        instance.addAll(new HashSet<>());
        assertEquals("The list doesn't have the right size", 0, instance.size());
        assertTrue("The list is not sorted", isSorted(instance));

        // add LIST1. The sorted list must have the same size as LIST1, contain all its elements, be sorted 
        List list1 = Arrays.asList(LIST1);
        instance.addAll(list1);
        assertEquals("The list doesn't have the right size", LIST1.length, instance.size());
        assertTrue("The list doesn't contain all the source elements", instance.containsAll(list1));
        assertTrue("The list is not sorted", isSorted(instance));

        // add LIST2. The sorted list must have the same size as LIST1+LIST2, contain all its elements, be sorted 
        List list2 = Arrays.asList(LIST2);
        instance.addAll(list2);
        assertEquals("The list doesn't have the right size", LIST1.length + LIST2.length, instance.size());
        assertTrue("The list doesn't contain all the source elements", instance.containsAll(list2));
        assertTrue("The list is not sorted", isSorted(instance));
    }

    /**
     * Test of add method, of class SortedList.
     */
    @Test
    public void testAdd() {
        int cpt = 0;
        List instance = new SortedList();

        // add LIST1 
        for (String elt : LIST1) {
            instance.add(elt);
            cpt++;
            assertEquals("The list doesn't have the right size", cpt, instance.size());
            assertTrue("The list doesn't contain the new element", instance.contains(elt));
            assertTrue("The list is not sorted", isSorted(instance));
        }
    }

    private <T extends Comparable<T>> boolean isSorted(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }
}
