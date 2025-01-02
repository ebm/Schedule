public class MeetingTime {
    public String meetingDay; // M = Monday | T = Tuesday | W = Wednesday | H = Thursday | F = Friday | S = Saturday | U = Sunday
    private String startTime;
    private String endTime;
    public String pmCode;
    public String campusName;

    public MeetingTime(String meetingDay, String startTime, String endTime, String pmCode, String campusName) {
        this.meetingDay = meetingDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pmCode = pmCode;
        this.campusName = campusName;
    }
    public static String convertTimeToString(int time) {
        String minutes = "";
        if (time % 100 == 0) {
            minutes = "00";
        }
        else {
            if (time % 100 < 10) minutes += "0";
            minutes += time % 100;
        }
        if (time < 1200) {
            return time / 100 + ":" + minutes + " am";
        }
        else if (time >= 1200 && time <= 1259) {
            return time / 100 + ":" + minutes + "pm";
        }
        else {
            return (time - 1200) / 100 + ":" + minutes + "pm";
        }
    }
    private int convertToMilitaryTime(int time) {
        if (time >= 1200 && time < 1259) {
            return time;
        }
        else if (pmCode.equals("P") || time < 400) {
            return time + 1200;
        }

        return time;
    }
    public int getStartTime() {
        if (startTime.equals("")) return -1;
        return convertToMilitaryTime(Integer.parseInt(startTime));
    }
    public int getEndTime() {
        if (endTime.equals("")) return -1;
        return convertToMilitaryTime(Integer.parseInt(endTime));
    }
    public static double getPointTime(int time) {
        double minutes = time % 100;
        double hours = (int) time / 100;

        return (double) hours + minutes / 60;
    }
}
