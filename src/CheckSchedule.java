import java.util.ArrayList;

public class CheckSchedule {
    // Minimum time difference between classes (set by Rutgers)
    private static int timeDifference = 40;

    /**
     * Searches the list of subjects for the input string.
     * @param input a string
     * @param subjects an array with the list of subjects
     * @return an Arraylist<String> with the search results
     */
    public static ArrayList<String> search(String input, Subject[] subjects) {
        if (subjects == null) return null;
        ArrayList<String> searchedResults = new ArrayList<String>(); 
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i].title.indexOf(input.toUpperCase()) != -1) {
                searchedResults.add(subjects[i].title);
            }
        }
        return searchedResults;
    }
    /**
     * Searches the list of subjects for the input string.
     * @param input a string
     * @param subjects an array with the list of subjects
     * @return an index of the first element found
     */
    public static int searchIndex(String input, Subject[] subjects) {
        if (subjects == null) return -1;
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i].title.indexOf(input.toUpperCase()) != -1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if two sections can be in the same schedule. Checks their starting and ending times, and the meeting day
     * to determine if the time constraints conflict.
     * @param a first section
     * @param b second section
     * @return true or false
     */
    // Checks if two different sections can be in the same schedule
    public static boolean scheduleChecker(Section a, Section b) {
        MeetingTime[] meetingTimeA = a.meetingTimes;
        MeetingTime[] meetingTimeB = b.meetingTimes;
        
        for (int i = 0; i < meetingTimeA.length; i++) {
            for (int j = 0; j < meetingTimeB.length; j++) {
                if (meetingTimeA[i].meetingDay.equals(meetingTimeB[j].meetingDay)) {
                    if (meetingTimeA[i].getEndTime() + timeDifference > meetingTimeB[j].getStartTime() && meetingTimeB[j].getEndTime() + timeDifference > meetingTimeA[i].getStartTime()) {
                        if (meetingTimeA[i].getStartTime() != -1 && meetingTimeB[j].getStartTime() != -1) return false;
                    }
                }
            }
        }
        return true;
    }

    // Returns a list of sections of a subject within a time constraint

    /**
     * Called from the wishlist panel. Finds a class within given time constraints.
     * @param startTime any classes have to be after the startTime
     * @param endTime any classes have to be before the endTime
     * @param sections list of sections
     * @return an array of sections in the given time constraints.
     */
    public static Section[] getSectionsWithinTimeConstraint(int startTime, int endTime, Section[] sections) {
        ArrayList<Section> arrList = new ArrayList<Section>(); 
        for (int i = 0; i < sections.length; i++) {
            boolean withinTimeConstraint = true;
            for (int j = 0; j < sections[i].meetingTimes.length; j++) {
                if (sections[i].meetingTimes[j].getStartTime() < startTime || sections[i].meetingTimes[j].getEndTime() > endTime) {
                    withinTimeConstraint = false;
                }
            }
            if (withinTimeConstraint == true) {
                arrList.add(sections[i]);
            }
        }
        Section[] updatedSections = new Section[arrList.size()];
        for (int i = 0; i < updatedSections.length; i++) {
            updatedSections[i] = arrList.get(i);
        }
        return updatedSections;
    }

    /**
     * M = Monday | T = Tuesday | W = Wednesday | H = Thursday | F = Friday | S = Saturday | U = Sunday
     * Finds classes that are not on the specified free days. Useful for a student who might want a day off.
     * @param freeDays an array with a list of freeDays
     * @param sections an array with the list of sections
     * @return an array of sections which are not on the specified free days.
     */
    public static Section[] getSectionsWithinDays(String[] freeDays, Section[] sections) {
        ArrayList<Section> arrList = new ArrayList<Section>(); 
        for (int i = 0; i < sections.length; i++) {
            boolean withinDayConstraint = true;
            for (int j = 0; j < sections[i].meetingTimes.length; j++) {
                for (int k = 0; k < freeDays.length; k++) {
                    if (sections[i].meetingTimes[j].meetingDay.equals(freeDays[k])) {
                        withinDayConstraint = false;
                    }
                }
            }
            if (withinDayConstraint == true) {
                arrList.add(sections[i]);
            }
        }
        Section[] updatedSections = new Section[arrList.size()];
        for (int i = 0; i < updatedSections.length; i++) {
            updatedSections[i] = arrList.get(i);
        }
        return updatedSections;
    }
}
