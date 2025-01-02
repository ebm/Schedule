public class Subject {
    public String title;
    public String courseString;
    public Section[] sections;
    public String credits;
    public int numOpenSections;
    public boolean openSectionCalculated = false;

    public Subject(Subject subject) {
        this.title = subject.title;
        this.courseString = subject.courseString;
        this.sections = subject.sections;
        this.credits = subject.credits;
    }
    public Subject(String title, String courseString, Section[] sections, String credits) {
        this.title = title;
        this.courseString = courseString;
        this.sections = sections;
        this.credits = credits;
        numOpenSections = 0;
        openSectionCalculated = false;
    }

    public int getNumberOfOpenSections() {
        if (openSectionCalculated == false) return calculateNumberOfOpenSections();
        return numOpenSections;
    }
    
    private int calculateNumberOfOpenSections() {
        int openSectionsNum = 0;
        for (int i = 0; i < sections.length; i++) {
            if (sections[i].openStatus == true) {
                openSectionsNum++;
            }
        }
        numOpenSections = openSectionsNum;
        openSectionCalculated = true;
        return numOpenSections;
    }
    public double getCredits() {
        //System.out.println("Credits: " + credits);
        if (credits == null) return 0;
        //System.out.println(Double.parseDouble(credits));
        return Double.parseDouble(credits);
    }
}
