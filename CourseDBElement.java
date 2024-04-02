
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
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CourseDBElement implements Comparable<CourseDBElement>, Iterable<CourseDBElement> {
    private String courseID, roomNum, instructor;
    private int CRN, creditHours;
    private CourseDBElement next, prev;

    public CourseDBElement(String courseID, int CRN, int creditHours, String roomNum, String instructor) {
        this.courseID = courseID;
        this.CRN = CRN;
        this.creditHours = creditHours;
        this.roomNum = roomNum;
        this.instructor = instructor;
        this.next = null;
        this.prev = null;
    }

    public CourseDBElement(CourseDBElement element) {
        this.courseID = element.getID();
        this.CRN = element.getCRN();
        this.creditHours = element.getCreditHours();
        this.roomNum = element.getRoomNum();
        this.instructor = element.getInstructor();
        this.next = element.getNext();
        this.prev = element.getPrev();

    }

    public CourseDBElement() {

    }

    public void setTo(CourseDBElement element) {
        this.courseID = element.getID();
        this.CRN = element.getCRN();
        this.creditHours = element.getCreditHours();
        this.roomNum = element.getRoomNum();
        this.instructor = element.getInstructor();
        this.next = element.getNext();
        this.prev = element.getPrev();
    }

    public void setPrev(CourseDBElement prev) {
        this.prev = prev;
    }

    public void setNext(CourseDBElement next) {
        this.next = next;
    }

    public void setID(String courseID) {
        this.courseID = courseID;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setCRN(int CRN) {
        this.CRN = CRN;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public CourseDBElement getNext() {
        return next;
    }

    public CourseDBElement getPrev() {
        return prev;
    }

    public String getID() {
        return courseID;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getCRN() {
        return CRN;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public String toString() {
        return String.format("\nCourse:%s CRN:%d Credits:%d Instructor:%s Room:%s",
                courseID, CRN, creditHours, instructor, roomNum);
    }

    public Iterator<CourseDBElement> iterator() {
        return new CourseDBElementIterator(this);
    }

    @Override
    public int compareTo(CourseDBElement other) {
        return this.CRN - other.CRN;
    }

    /**
     * validates the specific element
     * 
     * @return true if is valid, false otherwise
     */
    protected boolean isValid() {
        return isCRNValid() && isCreditHoursValid() && isCourseIDValid() && areRequirementsValid();
    }

    private boolean isCRNValid() {
        return Integer.toString(CRN).length() == 5;
    }

    private boolean isCreditHoursValid() {
        return creditHours >= 1 && creditHours <= 4;
    }

    private boolean isCourseIDValid() {
        return courseID != null && courseID.length() == 7;
    }

    private boolean areRequirementsValid() {
        return roomNum != null && instructor != null;
    }

    // iterator class
    private class CourseDBElementIterator implements Iterator<CourseDBElement> {
        private CourseDBElement head;
        private CourseDBElement currentElement;

        CourseDBElementIterator(CourseDBElement head) {
            this.head = head;
            this.currentElement = null;
        }

        @Override
        public boolean hasNext() {
            if (currentElement == null) {
                return head != null;
            } else {
                return currentElement.getNext() != null;
            }
        }

        @Override
        public CourseDBElement next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (currentElement == null) {
                currentElement = head;
            } else {
                currentElement = currentElement.getNext();
            }

            return currentElement;
        }
    }
}