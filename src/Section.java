public class Section {
    public String index;
    public MeetingTime[] meetingTimes;
    public boolean openStatus;

    // Initializer
    public Section(String index, MeetingTime[] meetingTimes, boolean openStatus) {
        this.index = index;
        this.meetingTimes = meetingTimes;
        this.openStatus = openStatus;
    }
}
