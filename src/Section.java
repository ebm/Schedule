public class Section {
    public String index;
    public MeetingTime[] meetingTimes;
    public boolean openStatus;
    public int subjectIndex;
    public int RGB;
    public int darkRGB;

    // Initializer
    public Section(String index, MeetingTime[] meetingTimes, boolean openStatus) {
        this.index = index;
        this.meetingTimes = meetingTimes;
        this.openStatus = openStatus;
    }
    public Section (Section section) {
        this.index = section.index;
        this.meetingTimes = section.meetingTimes;
        this.openStatus = section.openStatus;
    }
}
