import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;

class PopulateValues {
    public static Subject[] subjects;
    public static String filename;
    public static String scheduleFile;
    public static String wishlistFile;
    
    // Initalizer
    public static void populate(String fileString, String scheduleFilename, String wishlistFilename) {
        filename = fileString;
        scheduleFile = scheduleFilename;
        wishlistFile = wishlistFilename;
        createSubjects(readFile(fileString), readFile(scheduleFile), readFile(wishlistFile));
    }

    // Creates subjects
    private static void createSubjects(String file, String scheduleString, String wishlistString) {
        Gson gson = new Gson();
        if (file != null) {
            subjects = gson.fromJson(file, Subject[].class);
            for (int i = 0; i < subjects.length; i++) {
                for (int j = 0; j < subjects[i].sections.length; j++) {
                    subjects[i].sections[j].subjectIndex = i;
                }
            }
        }
        
        if (scheduleString != null) {
            Section[] schedule = gson.fromJson(scheduleString, Section[].class);
            for (int i = 0; i < schedule.length; i++) {
                App.schedule.calendar.add(schedule[i]);
            }
        }
        if (wishlistString != null) {
            Subject[] wishlist = gson.fromJson(wishlistString, Subject[].class);    
            for (int i = 0; i < wishlist.length; i++) {
                App.wishlist.add(wishlist[i]);
            }
        }
        
    }

    public static boolean subjectsSectionsToFile(ArrayList<Section> schedule, ArrayList<Subject> wishlist) {
        Gson gson = new Gson();
        String scheduleString = gson.toJson(schedule);
        String wishlistString = gson.toJson(wishlist);
        if (RefreshResults.writeStringToFile(scheduleString, "schedule.txt") == false) return false;
        if (RefreshResults.writeStringToFile(wishlistString, "wishlist.txt") == false) return false;
        return true;
    }

    // Reads File from filename
    private static String readFile(String fileString) {
        try {
            File file = new File(fileString);
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