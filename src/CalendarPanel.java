import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPanel extends JPanel implements ActionListener {
    JButton backButton = new JButton();

    public final int unitSizeX = 150;
    public final int unitSizeY = 36;
    public final int borderX = 115;
    public final int borderY = 90;
    public final int shiftUp = 30;

    public MeetingTime[] meetingTimes;
    public Schedule schedule = new Schedule();
    public ArrayList<Section> sections = new ArrayList<>();
    public boolean addSection;
    JButton removeAddSectionButton = new JButton();
    JLabel statusLabel = new JLabel();
    JButton saveDataButton = new JButton();

    int colorIndex = 0;
    ArrayList<ArrayList<Rectangle>> rectArr = new ArrayList<>();
    Section sectionClicked = null;
    ArrayList<JLabel> timesArr = new ArrayList<>();

    JButton nextScheduleButton = new JButton();
    JButton previousScheduleButton = new JButton();
    ArrayList<Schedule> scheduleOfClasses;
    int scheduleIndex = 0;

    // ************************* blue ******************** red ********************* lime ******************* yellow ****************** pink ******************* cyan ****************** orange *************** green ************** gray ***********
    Color[] color = {new Color(100, 100, 255), new Color(255, 100, 100), new Color(100, 255, 100), new Color(255, 223, 0), new Color (251, 72, 196), new Color(0, 188, 227), new Color(225, 127, 0), new Color(0, 120, 60), new Color(100, 100, 100)};
    Color[] darkColor = {new Color(75, 75, 200), new Color(175, 75, 75), new Color(75, 200, 75), new Color(200, 185, 0), new Color (200, 50, 175), new Color(0, 158, 197), new Color(185, 100, 0), new Color(0, 100, 40), new Color(70, 70, 70)};

    public void createCalendar() {
        this.setLayout(null);

        removeAddSectionButton.setSize(new Dimension(150, 75));
        removeAddSectionButton.setLocation(App.xDimension - 170, App.yDimension - 95);
        removeAddSectionButton.addActionListener(this);
        removeAddSectionButton.setFocusable(false);

        saveDataButton.setSize(new Dimension(150, 50));
        saveDataButton.setLocation(App.xDimension / 2 - 75, App.yDimension - 75);
        saveDataButton.addActionListener(this);
        saveDataButton.setText("Save Data");
        saveDataButton.setFocusable(false);
        
        backButton.setSize(new Dimension(150, 75));
        backButton.setLocation(20, App.yDimension - 95);
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setFocusable(false);
        
        for (int i = 0; i < sections.size(); i++) {
            sections.get(i).RGB = color[i].getRGB();
            sections.get(i).darkRGB = darkColor[i].getRGB();
        }

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            JLabel day = new JLabel();
            day.setText(daysOfWeek[i]);
            day.setForeground(Color.black);
            day.setBounds(40 + borderX + i * unitSizeX, 70 - shiftUp, 150, 15);
            day.setFont(new Font("Arial", Font.PLAIN, 15));
            this.add(day);
        }

        String[] times = {"8:00 am", "9:00 am", "10:00 am", "11:00 am", "12:00 pm", "1:00 pm", "2:00 pm", 
        "3:00 pm", "4:00 pm", "5:00 pm", "6:00 pm", "7:00 pm", "8:00 pm", "9:00 pm", "10:00 pm", "11:00 pm", };
        for (int i = 0; i < times.length; i++) {
            JLabel time = new JLabel();
            time.setText(times[i]);
            time.setForeground(Color.black);
            time.setBounds(65, 85 + i * unitSizeY - shiftUp, 150, 15);
            time.setFont(new Font("Arial", Font.PLAIN, 10));
            this.add(time);
        }

        this.setPreferredSize(new Dimension(App.xDimension, App.yDimension));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.add(backButton);
        this.add(removeAddSectionButton);
        this.add(saveDataButton);

        class MouseClick implements MouseListener {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println(rectArr.size());
                //System.out.println(rectArr.size() + "<=>" + sections.size());
                    if (addSection == false) {
                        for (int i = 0; i < rectArr.size(); i++) {
                        for (int j = 0; j < rectArr.get(i).size(); j++) {
                            if (rectArr.get(i).get(j).contains(e.getPoint())) {
                                sectionClicked = sections.get(i);
                                App.frame.revalidate();
                                App.frame.repaint();
                                //System.out.println(sectionClicked.index);
                                return;
                            } else {
                                sectionClicked = null;
                                App.frame.revalidate();
                                App.frame.repaint();
                            }
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
        }
        this.addMouseListener(new MouseClick());
    }
    public CalendarPanel(Section section) {
        sections.add(section);
        removeAddSectionButton.setText("Add Section");

        createCalendar();
        addSection = true;
    }
    public CalendarPanel(Schedule schedule) {
        sections = schedule.calendar;
        removeAddSectionButton.setText("Remove Section");

        createCalendar();
        addSection = false;
    }
    public CalendarPanel(ArrayList<Schedule> schedule, int scheduleIndex) {
        this.scheduleOfClasses = schedule;
        this.scheduleIndex = scheduleIndex;
        if (schedule.size() == 0) {
            sections = new ArrayList<>();
        } else {
            sections = schedule.get(scheduleIndex).calendar;
        }
        removeAddSectionButton.setText("Remove Section");

        nextScheduleButton.setSize(new Dimension(75, 25));
        nextScheduleButton.setLocation(80, 0);
        nextScheduleButton.addActionListener(this);
        nextScheduleButton.setText(">");
        nextScheduleButton.setFocusable(false);

        previousScheduleButton.setSize(new Dimension(75, 25));
        previousScheduleButton.setLocation(0, 0);
        previousScheduleButton.addActionListener(this);
        previousScheduleButton.setText("<");
        previousScheduleButton.setFocusable(false);

        JLabel currentScheduleNumber = new JLabel();
        currentScheduleNumber.setText(scheduleIndex + "/" + scheduleOfClasses.size());
        currentScheduleNumber.setForeground(Color.black);
        currentScheduleNumber.setBounds(160, 0, 100, 15);
        currentScheduleNumber.setFont(new Font("Arial", Font.PLAIN, 15));

        this.add(nextScheduleButton);
        this.add(previousScheduleButton);
        this.add(currentScheduleNumber);

        createCalendar();
        addSection = false;
    }
    public void draw(Graphics g) {
        
        g.setColor(Color.black);
        for (int i = borderX; i <= App.xDimension - borderX; i += unitSizeX) {
            g.drawLine(i, borderY - shiftUp, i, App.yDimension - borderY - shiftUp);
        }
        for (int i = borderY - shiftUp; i <= App.yDimension - borderY - shiftUp; i += unitSizeY) {
            g.drawLine(borderX, i, App.xDimension - borderX, i);
        }
        //System.out.println(sections.size());
        for (int i = 0; i < timesArr.size(); i++) {
            this.remove(timesArr.get(i));
        }
        timesArr = new ArrayList<>();
        rectArr = new ArrayList<>();
        for (int i = 0; i < sections.size(); i++) {
            rectArr.add(new ArrayList<Rectangle>());
        }
        for (int sectionNum = 0; sectionNum < sections.size(); sectionNum++) {
            g.setColor(new Color(sections.get(sectionNum).RGB));
            colorIndex++;
            meetingTimes = sections.get(sectionNum).meetingTimes;
            for (int i = 0; i < meetingTimes.length; i++) {
                String[] daysOfWeek = {"M", "T", "W", "H", "F", "S", "U"};
                int indexX = 0;
                for (int j = 0; j < daysOfWeek.length; j++) {
                    if (daysOfWeek[j].equals(meetingTimes[i].meetingDay)) {
                        indexX = j;
                        break;
                    }
                }
                if (sectionClicked != null && sectionClicked.meetingTimes.equals(meetingTimes)) {
                    g.setColor(new Color(sections.get(sectionNum).darkRGB));
                    //g.setColor(color[colorIndex]);
                    //System.out.println(sectionClicked.index);
                }
                //System.out.println(meetingTimes[i].getStartTime() + meetingTimes[i].pmCode + "->" + meetingTimes[i].getEndTime() + meetingTimes[i].pmCode);
                int xCoord = borderX + 1 + unitSizeX * indexX;
                int yCoord = (int) (borderY + unitSizeY * (MeetingTime.getPointTime(meetingTimes[i].getStartTime()) - 8));
                int yHeight = (int) (unitSizeY * (MeetingTime.getPointTime(meetingTimes[i].getEndTime()) - MeetingTime.getPointTime(meetingTimes[i].getStartTime())));
                g.fillRect(xCoord, yCoord - shiftUp, unitSizeX - 1, yHeight + 1);
                rectArr.get(sectionNum).add(new Rectangle(xCoord, yCoord - shiftUp, unitSizeX - 1, yHeight + 1));
                JLabel times = new JLabel();
                times.setText(MeetingTime.convertTimeToString(meetingTimes[i].getStartTime()) + " -> " + MeetingTime.convertTimeToString(meetingTimes[i].getEndTime()));
                times.setForeground(Color.black);
                times.setBounds(xCoord + 8, (int) (yCoord - 15 + yHeight / 2) - shiftUp + 10, 150, 15);
                times.setFont(new Font("Arial", Font.PLAIN, 15));
                timesArr.add(times);
                this.add(times);
            }
        }
        colorIndex = 0;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            if (scheduleOfClasses != null) {
                App.frame.remove(this);
                App.frame.wishlistPanel.setVisible(true);
                App.frame.revalidate();
                App.frame.repaint();
            }
            else if (addSection == true) {
                App.frame.remove(this);
                App.frame.schedulePanel.setVisible(true);
                App.frame.revalidate();
                App.frame.repaint();
            }
            else {
                App.frame.remove(this);
                App.frame.add(new DefaultPanel());
                App.frame.revalidate();
                App.frame.repaint();
            }
        }
        if (e.getSource() == removeAddSectionButton) {
            App.frame.revalidate();
            App.frame.repaint();
            this.remove(statusLabel);
            if (addSection == true) {
                Section section = sections.get(0);
                int canAddSectionToSchedule = App.schedule.addSection(section);
                Section conflict = App.schedule.lastConflict;
                if (canAddSectionToSchedule == 1) {
                    statusLabel.setText("Section successfully added");
                    statusLabel.setBounds(App.xDimension - 370, App.yDimension - 75, 250, 15);
                } else if (canAddSectionToSchedule == -2) {
                    statusLabel.setText("You have exceeded the maximum of 20 credits");
                    statusLabel.setBounds(App.xDimension - 520, App.yDimension - 75, 350, 15);
                } else if (canAddSectionToSchedule == 0) {
                    statusLabel.setText("Duplicate section detected");
                    statusLabel.setBounds(App.xDimension - 370, App.yDimension - 75, 250, 15);
                }
                else if (canAddSectionToSchedule == -1) {
                    statusLabel.setText("Section conflicting with section " + conflict.index +" different time");
                    statusLabel.setBounds(App.xDimension - 520, App.yDimension - 75, 350, 15);
                }
                statusLabel.setForeground(Color.black);
                statusLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                this.add(statusLabel);
                App.frame.revalidate();
                App.frame.repaint();
            }
            else {
                //System.out.println(sectionClicked.index);
                int result = App.schedule.removeSection(sectionClicked);
                if (result == 1) {
                    statusLabel.setText("Section successfully removed!");
                    statusLabel.setBounds(App.xDimension - 380, App.yDimension - 75, 250, 15);
                    sectionClicked = null;
                } else if (result == 0) {
                    statusLabel.setText("Please select a section to remove.");
                    statusLabel.setBounds(App.xDimension - 400, App.yDimension - 75, 350, 15);
                }
                else if (result == -1) {
                    statusLabel.setText("Removal failed");
                    statusLabel.setBounds(App.xDimension - 370, App.yDimension - 75, 250, 15);
                }
                statusLabel.setForeground(Color.black);
                statusLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                this.add(statusLabel);
            }
        }
        if (e.getSource() == saveDataButton) {
            boolean saveSuccessful = PopulateValues.subjectsSectionsToFile(App.schedule.calendar, App.wishlist);
            if (saveSuccessful == true) {
                statusLabel.setText("Saved!");
                statusLabel.setBounds(App.xDimension / 2 - 25, App.yDimension - 95, 250, 15);
            }
            else {
                statusLabel.setText("Save failed.");
                statusLabel.setBounds(App.xDimension / 2 - 40, App.yDimension - 95, 250, 15);
            }
            statusLabel.setForeground(Color.black);
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            this.add(statusLabel);
            App.frame.revalidate();
            App.frame.repaint();
        }
        if (e.getSource() == nextScheduleButton && scheduleIndex + 1 < scheduleOfClasses.size()) {
            scheduleIndex++;
            sections = scheduleOfClasses.get(scheduleIndex).calendar;
            App.frame.remove(this);
            App.frame.add(new CalendarPanel(scheduleOfClasses, scheduleIndex));
            App.frame.revalidate();
            App.frame.repaint();
        }
        if (e.getSource() == previousScheduleButton && scheduleIndex - 1 >= 0) {
            scheduleIndex--;
            sections = scheduleOfClasses.get(scheduleIndex).calendar;
            for (int i = 0; i < sections.size(); i++) {
                System.out.println(sections.get(i).index);
            }
            System.out.println("============");
            App.frame.remove(this);
            App.frame.add(new CalendarPanel(scheduleOfClasses, scheduleIndex));
            App.frame.revalidate();
            App.frame.repaint();
        }
    }
}
