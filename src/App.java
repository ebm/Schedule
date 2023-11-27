import java.util.ArrayList;

public class App {
    public static final String url = "https://sis.rutgers.edu/soc/api/courses.json?year=2024&term=1&campus=NB";
    public static final String filename = "file.txt";
    public static final String scheduleFile = "schedule.txt";
    public static final String wishlistFile = "wishlist.txt";

    public static final int xDimension = 1280;
    public static final int yDimension = 720;

    public static ArrayList<Subject> wishlist = new ArrayList<>();
    public static Schedule schedule = new Schedule();

    public static Frame frame;
    public static void main(String[] args) throws Exception {
        PopulateValues.populate(filename, scheduleFile, wishlistFile);
        frame = new Frame();
    }
}
