public class Subject {
    public String title;
    public String courseString;
    public Section[] sections;
    public int numOpenSections;
    public boolean openSectionCalculated = false;


    public Subject(String title, String courseString, Section[] sections) {
        this.title = title;
        this.courseString = courseString;
        this.sections = sections;
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
}
