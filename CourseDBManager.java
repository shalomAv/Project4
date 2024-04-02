
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseDBManager implements CourseDBManagerInterface {
    private final static int DEFAULT_CAPACITY = 20;
    CourseDBStructure database;

    public CourseDBManager() {
    }

    @Override
    public void add(String id, int crn, int credits, String roomNum, String instructor) {
        CourseDBElement element = new CourseDBElement(id, crn, credits, roomNum, instructor);
        initilizeDatabase(DEFAULT_CAPACITY);
        database.add(element);
    }

    /**
     * adds a course element to the database
     * 
     * @param element the element being added to the database
     */
    public void add(CourseDBElement element) {
        initilizeDatabase(DEFAULT_CAPACITY);
        CourseDBElement el = new CourseDBElement(element);
        database.add(el);
    }

    @Override
    public CourseDBElement get(int crn) {
        try {
            return database.get(crn);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void readFile(File input) throws IOException {
        CourseDBElement currentElement;
        ArrayList<CourseDBElement> elementList = new ArrayList<>();
        try {
            Scanner file = new Scanner(input);
            while (file.hasNextLine()) {
                String currentLine = file.nextLine();

                // if the current line is blank or empty skip it
                if (currentLine.isBlank() || currentLine.isEmpty())
                    continue;

                // if the current line starts with '#' it is a comment
                // therefore skip it
                if (currentLine.charAt(0) == '#')
                    continue;

                // if the current line is not valid, then print it out,
                // however do not add it to the database
                currentElement = getElementFromLine(currentLine);
                if (getElementFromLine(currentLine) == null)
                    continue;

                // add the line to the element list
                elementList.add(currentElement);
            }

            file.close(); // close the file

            // create a new database with an expected size of the amount of valid data
            initilizeDatabase(elementList.size());
            // add all the valid data into the database
            for (CourseDBElement elem : elementList)
                database.add(elem);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<String> showAll() {
        return database.showAll();
    }

    /**
     * Initializes the database with the specified size
     * 
     * @param size the expected size of the database
     */
    protected void initilizeDatabase(int size) {
        if (database == null)
            database = new CourseDBStructure(size);
    }

    /**
     * validates a line of data and returns a CourseDBElement if the data is valid
     * 
     * @param line the line of data to be validated
     * @return a CourseDBElement if the data is valid, null otherwise
     */
    protected CourseDBElement getElementFromLine(String line) {
        String[] lineData = line.split(" ");
        // if there are comments after the name, set legnth to 7
        // else set it to the lineData array length
        int length = (7 > lineData.length) ? lineData.length : 7;

        try {
            // split all data by space and store parts of array to proper variables
            String courseName = lineData[0];
            int crn = Integer.parseInt(lineData[1]);
            int creditHours = Integer.parseInt(lineData[2]);
            String roomNum = lineData[3];
            String instructor = new String();
            for (int i = 4; i < length; i++)
                instructor += lineData[i] + " "; // append instructor name

            // if no issues occured above, create a new elemetn and validate it
            CourseDBElement element = new CourseDBElement(courseName, crn, creditHours, roomNum, instructor);
            if (element.isValid()) {
                return element; // if valie return element
            }
            return null; // else return null
        } catch (Exception e) {
            // if any errors occur when reading the data, the data is invalid
            System.out.println("Invalid input: " + line);
            return null;
        }
    }
}

