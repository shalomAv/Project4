
/**
 * Class: CMSC204 
 * Program: Project 4 - Hashtable with buckets
 * Instructor: Huseyin Aygun
 * Summary of Description: test case for the CourseDBManager class
 * Due Date: 03/25/2024
 * Integrity Pledge: I pledge that I have completed the programming assignment
 * independently.
 * I have not copied the code from a student or any source.
 * 
 * @author Kodjo Avougla
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CourseDBStructure implements CourseDBStructureInterface {
    private final float LOAD_FACTOR = 1.5f;
    private int tableSize;
    private CourseDBElement[] table;

    /**
     * A database structure for holding CourseDBElements
     * 
     * @param estimatedSize the estimated number of elements being added
     */
    public CourseDBStructure(int estimatedSize) {
        // get the closest 4k+3 prime at the floor value of size/LOAD_FACTOR
        tableSize = get4kPrime((int) (estimatedSize / LOAD_FACTOR));
        table = new CourseDBElement[tableSize];
    }

    /**
     * Testing constructor used only for testing
     * 
     * @param estimatedSize used only for developer to know that this class is only
     *                      for testing purposes
     * @param size          the estimated number of elements being added
     */
    public CourseDBStructure(String name, int estimatedSize) {
        tableSize = estimatedSize;
        table = new CourseDBElement[tableSize];
    }

    @Override
    public void add(CourseDBElement element) {
        // if invalid input, print element and exit
        if (!element.isValid()) {
            System.out.println("Invalid input: " + element.toString());
            return;
        }
        int hash = getHash(element);

        // if there are no items at the current hash
        if (table[hash] == null)
            table[hash] = element;
        // else if the current hash has elements
        else {
            Iterator<CourseDBElement> itr = table[hash].iterator(); // get iterator
            CourseDBElement current = null;
            // traverse the linked list
            while (itr.hasNext()) {
                current = itr.next(); // store next element
                // if the current element already exists
                if (current.compareTo(element) == 0) {
                    // the code below would update the old CRNs data
                    // it as been commented as we
                    // "ignore (don't update) any CRNs that are already existed in the DB."

                    // // set the given elements next pointer to the
                    // // next pointer of the current node
                    // element.setNext(current.getNext());

                    // // set the given elements previous pointer to the
                    // // previous pointer of the current node
                    // element.setPrev(current.getPrev());

                    // // if it has a previous set the given elements previous pointer to the
                    // previous
                    // // pointer of the current
                    // if (current.getPrev() != null) {
                    // current.getPrev().setNext(element);
                    // }
                    // // update the element
                    // current.setTo(element);
                    System.out.println("CRN already exists: " + element.toString());
                    return; // exit add function
                }
            }

            // if crn does not exist in hashtable,
            // appened to end of linked list
            element.setPrev(current);
            current.setNext(element);
        }

    }

    @Override
    public CourseDBElement get(int crn) throws IOException {
        // for all elements in the hash table
        for (CourseDBElement element : table) {
            // skip all empty buckets
            if (element == null)
                continue;

            Iterator<CourseDBElement> itr = element.iterator();
            CourseDBElement current = null;
            // while there is other data in the current hash
            // traverse through all
            while (itr.hasNext()) {
                current = itr.next();
                if (current.getCRN() == crn) // if the crn is found return the element
                    return current;
            }
        }

        throw new IOException("Not found");
    }

    @Override
    public ArrayList<String> showAll() {
        ArrayList<String> list = new ArrayList<>();
        // for all elements in the hash table
        for (CourseDBElement element : table) {
            // skip all empty buckets
            if (element == null)
                continue;

            Iterator<CourseDBElement> itr = element.iterator();
            CourseDBElement current = null;
            // while there is other data in the current hash
            // traverse through all
            while (itr.hasNext()) {
                current = itr.next(); // go to next element
                list.add(current.toString()); // add to list
            }
        }

        return list;
    }

    @Override
    public int getTableSize() {
        return tableSize;
    }

    /**
     * calculates the hash index for a CRN
     * 
     * @param CRN the CRN to calculate the hash for
     * @return the hash index
     */
    private int getHash(CourseDBElement element) {
        String sCRN = Integer.toString(element.getCRN()); // convert to string
        int hash = sCRN.hashCode() % tableSize; // store the hash value
        if (hash < 0)
            hash += tableSize;
        return hash; // return the hash value
    }

    /**
     * Check if a number is prime
     * 
     * @param num the number being checked
     * @return true if prime, false otherwise
     */
    private boolean isPrime(int num) {
        if (num <= 1)
            return false; // if <= 1, return false
        // brute force check if prime
        for (int i = 2; i < num; i++) {
            // return false if divisable by any other number
            if (num % i == 0)
                return false;
        }
        return true; // otherwise return true
    }

    /**
     * Gets the closest 4k+3 prime
     * 
     * @param num starting number to increment from
     * @return the closest 4k+3 prime
     */
    private int get4kPrime(int num) {
        while (true) {
            // if is 4k+3 prime return num
            if (isPrime(num) && (num - 3) % 4 == 0)
                return num;
            num++; // else increment num
        }
    }

}
