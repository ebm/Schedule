import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.google.gson.Gson;

class PopulateValues {
    public static Subject[] subjects;
    public static String filename;

    // Initalizer
    public static void populate(String fileString) {
        filename = fileString;
        createSubjects(readFile());
    }

    // Creates subjects
    private static void createSubjects(String file) {
        Gson gson = new Gson();
        subjects = gson.fromJson(file, Subject[].class);
    }

    // Reads File from filename
    private static String readFile() {
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            String output = "";
            while (reader.hasNextLine()) {
                output += reader.nextLine();
            }
            reader.close();
            return output;
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
    }

    // Displays subject name, core code, and number of open sections
    public static void displaySubjects(Subject[] subjectArr) {
        for (int i = 0; i < subjectArr.length; i++) {
            System.out.println(subjectArr[i].title + " | " + subjectArr[i].courseString + " | Open Sections: " + subjectArr[i].getNumberOfOpenSections());
        }
    }

    public static void displayEverything(Subject[] subjectArr) {
        for (int i = 0; i < subjectArr.length; i++) {
            System.out.println(subjectArr[i].title + " | " + subjectArr[i].courseString + " | Open Sections: " + subjectArr[i].getNumberOfOpenSections());
            for (int j = 0; j < subjectArr[i].sections.length; j++) {
                System.out.print("Index: " + subjectArr[i].sections[j].index + " | Open Status: " + subjectArr[i].sections[j].openStatus + "\n| ");
                for (int k = 0; k < subjectArr[i].sections[j].meetingTimes.length; k++) {
                    System.out.print(subjectArr[i].sections[j].meetingTimes[k].campusName + "," +  subjectArr[i].sections[j].meetingTimes[k].meetingDay + " | " + subjectArr[i].sections[j].meetingTimes[k].getStartTime() + "->" + subjectArr[i].sections[j].meetingTimes[k].getEndTime() + " | ");
                }
                System.out.println("\n");
            }
        }
    }
    
}