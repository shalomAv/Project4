
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
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseDBManager_STUDENT_Test {
    private CourseDBManager cdm;
    CourseDBElement e1, e2, e3, e4, e1updated, eInvalid1, eInvalid2, eInvalid3;

    @BeforeEach
    void setUp() throws Exception {
        cdm = new CourseDBManager();

        e1 = new CourseDBElement("course1", 39999, 2, "SC300", "name1");
        e1updated = new CourseDBElement("course1-updated", 39999, 4, "SC100", "name1");
        e2 = new CourseDBElement("course2", 39980, 3, "SC430", "name2");
        e3 = new CourseDBElement("course3", 40014, 1, "SC410", "name3");
        e4 = new CourseDBElement("course4", 44514, 1, "SC400", "name4");
        eInvalid1 = new CourseDBElement(null, 99988, 2, "SC300", "invalid course 1");
        eInvalid2 = new CourseDBElement("invalid2", 0, 4, "SC300", "invalid course 2");
        eInvalid3 = new CourseDBElement("indalid3", 99989, 0, "SC300", "invalid course 3");
    }

    @AfterEach
    void tearDown() throws Exception {
        cdm = null;
    }

    @Test
    void testAdd() {
        cdm.add(e1);
        cdm.add(e2);
        cdm.add(e3);
        cdm.add(eInvalid1);
        cdm.add(eInvalid2);
        cdm.add(eInvalid3);
        assertEquals(3, cdm.showAll().size());
        cdm.add(e1updated);
        assertEquals(3, cdm.showAll().size());
        cdm.add(e4);
        assertEquals(4, cdm.showAll().size());

        cdm.add(e1);
        cdm.add(e2);
        cdm.add(e3);
        cdm.add(e4);
        assertEquals(4, cdm.showAll().size());
    }

    @Test
    void testGet() {
        cdm.add(e1);
        cdm.add(e2);
        assertEquals(e1.getCRN(), cdm.get(e1.getCRN()).getCRN());
        assertEquals(e2.toString(), cdm.get(e2.getCRN()).toString());
        assertEquals(null, cdm.get(e3.getCRN()));
        cdm.add(e1updated);
        // check that it does not update
        assertEquals(e1.getID(), cdm.get(e1updated.getCRN()).getID());
        cdm.add(e3);
        cdm.add(e4);
        assertEquals(e3.getClass(), cdm.get(e3.getCRN()).getClass());
        assertEquals(e4.getInstructor(), cdm.get(e4.getCRN()).getInstructor());

        cdm.add(eInvalid1);
        assertEquals(null, cdm.get(eInvalid1.getCRN()));
        cdm.add(eInvalid2);
        assertEquals(null, cdm.get(eInvalid2.getCRN()));
        cdm.add(eInvalid3);
        assertEquals(null, cdm.get(eInvalid3.getCRN()));

    }

    @Test
    void testReadFile() {

        // System.out.println(selectedFile.getAbsolutePath());

        File file = new File(
                "C:\\college-github\\college-main\\s3-fall23\\CMSC204\\Week 8 - Due 10-23-23\\4-Release\\src\\courses-F23.txt");
        try {
            cdm.readFile(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void testShowAll() {
        cdm.add(e1);
        assertEquals(1, cdm.showAll().size());
        cdm.add(e2);
        assertEquals(2, cdm.showAll().size());
        cdm.add(e3);
        cdm.add(eInvalid1);
        cdm.add(eInvalid2);
        assertEquals(3, cdm.showAll().size());
        cdm.add(eInvalid3);
        assertEquals(3, cdm.showAll().size());
        cdm.add(e1);
        assertEquals(3, cdm.showAll().size());
        cdm.add(e4);
        assertEquals(4, cdm.showAll().size());
        cdm.add(e1updated);
        assertEquals(4, cdm.showAll().size());

        assertTrue(cdm.showAll().contains(e1.toString()));
        assertTrue(cdm.showAll().contains(e2.toString()));
        assertTrue(cdm.showAll().contains(e3.toString()));
        assertTrue(cdm.showAll().contains(e4.toString()));

        assertFalse(cdm.showAll().contains(e1updated.toString()));
        assertFalse(cdm.showAll().contains(eInvalid1.toString()));
        assertFalse(cdm.showAll().contains(eInvalid2.toString()));
        assertFalse(cdm.showAll().contains(eInvalid3.toString()));
    }

}
