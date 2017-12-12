package fr.ensibs.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * A list whose elements are sorted using their natural order or a given
 * {@link Comparator}
 *
 * @param <T> the type of the elements in the list
 *
 * @author Pascale Launay
 * @version 1
 */
public class SortedList<T extends Comparable<T>> extends ArrayList<T> {

    private final Comparator<T> comparator;  // to compare the elements while adding them

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor of a sorted list whose elements are sorted using their
     * natural order
     */
    public SortedList() {
        this.comparator = null;
    }

    /**
     * Constructor of a sorted list whose elements are sorted using a
     * {@link Comparator}
     *
     * @param comparator to compare the elements while adding them
     */
    public SortedList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    //---------------------------------------------------------------
    // Setters
    //---------------------------------------------------------------
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean added = true;
        for (T elt : collection) {
            added = add(elt) && added;
        }
        return added;
    }

    @Override
    public boolean add(T elt) {
        int idx = findIdx(elt);
        if (idx >= 0) {
            add(idx, elt);
            return true;
        }
        return false;
    }

    //---------------------------------------------------------------
    // Private methods
    //---------------------------------------------------------------
    /**
     * Give the index where the element should be added
     *
     * @param elt the element to be added
     * @return the index where the element should be added
     */
    private int findIdx(T elt) {
        for (int i = 0; i < size(); i++) {
            if (compare(elt, get(i)) <= 0) {
                return i;
            }
        }
        return size();
    }

    /**
     * Compare two elements from their natural order or the comparator
     *
     * @param elt an element
     * @param other an other element
     * @return an integer negative if the first element is smaller than the
     * other one
     */
    private int compare(T elt, T other) {
        if (comparator == null) {
            return elt.compareTo(other);
        } else {
            return comparator.compare(elt, other);
        }
    }
}
