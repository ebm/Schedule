import java.util.ArrayList;

public class Schedule {
    public ArrayList<Section> calendar;
    public double totalCredits = 0.0;
    public Section lastConflict = null;
    
    public Schedule() {
        calendar = new ArrayList<>();
    }
    public Schedule(ArrayList<Section> sections) {
        calendar = new ArrayList<>();
        for (int i = 0; i < sections.size(); i++) {
            calendar.add(new Section(sections.get(i)));
        }
        for (int i = 0; i < calendar.size(); i++) {
            totalCredits += PopulateValues.subjects[calendar.get(i).subjectIndex].getCredits();
        }
    }
    public int addSection(Section section) {
        if (PopulateValues.subjects[section.subjectIndex].getCredits() + totalCredits > 20) {
            return -2;
        }
        for (int i = 0; i < calendar.size(); i++) {
            Section s = calendar.get(i);
            if (section.index == s.index) {
                return 0;
            }
            else if (CheckSchedule.scheduleChecker(section, s) == false) {
                lastConflict = s;
                return -1;
            }
        }
        calendar.add(section);
        totalCredits += PopulateValues.subjects[section.subjectIndex].getCredits();
        return 1;
    }
    public static Schedule returnAddedSchedule(Schedule schedule, Section section) {
        Schedule s = new Schedule(schedule.calendar);
        s.addSection(section);
        return s;
    }
    public int removeSection(Section section) {
        if (section == null) return 0;
        if (calendar.remove(section) == false) return -1;
        return 1;
    }
}
