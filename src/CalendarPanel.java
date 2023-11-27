import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

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

    int colorIndex = 0;

    public void createCalendar() {
        this.setLayout(null);

        removeAddSectionButton.setSize(new Dimension(150, 75));
        removeAddSectionButton.setLocation(App.xDimension - 170, App.yDimension - 95);
        removeAddSectionButton.addActionListener(this);
        removeAddSectionButton.setFocusable(false);
        
        backButton.setSize(new Dimension(150, 75));
        backButton.setLocation(20, App.yDimension - 95);
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setFocusable(false);

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
    }
    public CalendarPanel(Section section) {
        sections.add(section);
        removeAddSectionButton.setText("Add Section");

        createCalendar();
        addSection = true;
    }
    public CalendarPanel(Schedule schedule) {
        sections = schedule.calendar;
        removeAddSectionButton.setText("Save Data");

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
        Color[] color = {Color.blue, Color.red, Color.green, Color.orange, Color.yellow, Color.gray, Color.pink, Color.cyan, Color.magenta};

        for (int sectionNum = 0; sectionNum < sections.size(); sectionNum++) {
            g.setColor(color[colorIndex]);
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
                //System.out.println(meetingTimes[i].getStartTime() + meetingTimes[i].pmCode + "->" + meetingTimes[i].getEndTime() + meetingTimes[i].pmCode);
                int xCoord = borderX + 1 + unitSizeX * indexX;
                int yCoord = (int) (borderY + unitSizeY * (MeetingTime.getPointTime(meetingTimes[i].getStartTime()) - 8));
                int yHeight = (int) (unitSizeY * (MeetingTime.getPointTime(meetingTimes[i].getEndTime()) - MeetingTime.getPointTime(meetingTimes[i].getStartTime())));
                g.fillRect(xCoord, yCoord - shiftUp, unitSizeX - 1, yHeight);

                JLabel times = new JLabel();
                times.setText(MeetingTime.convertTimeToString(meetingTimes[i].getStartTime()) + " -> " + MeetingTime.convertTimeToString(meetingTimes[i].getEndTime()));
                times.setForeground(Color.white);
                times.setBounds(xCoord + 8, (int) (yCoord - 15 + yHeight / 2) - shiftUp + 10, 150, 15);
                times.setFont(new Font("Arial", Font.PLAIN, 15));
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
            if (addSection == true) {
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
            this.remove(statusLabel);
            App.frame.revalidate();
            App.frame.repaint();
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
            }
            else {
                boolean saveSuccessful = PopulateValues.subjectsSectionsToFile(App.schedule.calendar, App.wishlist);
                if (saveSuccessful == true) {
                    statusLabel.setText("Saved!");
                    statusLabel.setBounds(App.xDimension - 240, App.yDimension - 75, 250, 15);
                }
                else {
                    statusLabel.setText("Save failed.");
                    statusLabel.setBounds(App.xDimension - 260, App.yDimension - 75, 250, 15);
                }
                statusLabel.setForeground(Color.black);
                statusLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                this.add(statusLabel);
            }            
        }
    }
}
