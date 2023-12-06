import java.util.ArrayList;

public class CheckSchedule {
    private static int timeDifference = 40;

    // Basic search function
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

    public static int searchIndex(String input, Subject[] subjects) {
        if (subjects == null) return -1;
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i].title.indexOf(input.toUpperCase()) != -1) {
                return i;
            }
        }
        return -1;
    }

    // Checks if two different sections can be in the same schedule
    public static boolean scheduleChecker(Section a, Section b) {
        MeetingTime[] meetingTimeA = a.meetingTimes;
        MeetingTime[] meetingTimeB = b.meetingTimes;
        
        for (int i = 0; i < meetingTimeA.length; i++) {
            for (int j = 0; j < meetingTimeB.length; j++) {
                if (meetingTimeA[i].meetingDay.equals(meetingTimeB[j].meetingDay)) {
                    if (meetingTimeA[i].getEndTime() + timeDifference > meetingTimeB[j].getStartTime() && meetingTimeB[j].getEndTime() + timeDifference > meetingTimeA[i].getStartTime()) {
                        if (meetingTimeA[i].getStartTime() != -1 && meetingTimeB[j].getStartTime() != -1) return false;
                        //System.out.println("=====================");
                        //System.out.println(meetingTimeA[i].getStartTime() + meetingTimeA[i].pmCode + "->" + meetingTimeA[i].getEndTime() + meetingTimeA[i].pmCode);
                        //System.out.println(meetingTimeB[i].getStartTime() + meetingTimeB[i].pmCode + "->" + meetingTimeB[i].getEndTime() + meetingTimeB[i].pmCode);
                        //System.out.println("=====================");
                    }
                }
            }
        }
        return true;
    }

    // Returns a list of sections of a subject within a time constraint
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

    // Returns a list of classes that only occur on certain days
    // M = Monday | T = Tuesday | W = Wednesday | H = Thursday | F = Friday | S = Saturday | U = Sunday
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

    // Returns a list of classes that only occur on certain campuses
    //public static Section[] getSectionsByCampus(String[] allowedCampuses, Section[] sections) {
    //    ArrayList<Section> arrList = new ArrayList<Section>(); 
    //    for (int i = 0; i < sections.length; i++) {
    //        boolean withinDayConstraint = true;
    //        for (int j = 0; j < sections[i].meetingTimes.length; j++) {
    //            for (int k = 0; k < freeDays.length; k++) {
    //                if (sections[i].meetingTimes[j].meetingDay.equals(freeDays[k])) {
    //                    withinDayConstraint = false;
    //                }
    //            }
    //        }
    //        if (withinDayConstraint == true) {
    //            arrList.add(sections[i]);
    //        }
    //    }
    //    Section[] updatedSections = new Section[arrList.size()];
    //    for (int i = 0; i < updatedSections.length; i++) {
    //        updatedSections[i] = arrList.get(i);
    //    }
    //    return updatedSections;
    //}
}
