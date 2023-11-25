import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calendar extends JPanel implements ActionListener {
    JButton backButton = new JButton();

    public final int unitSizeX = 150;
    public final int unitSizeY = 36;
    public final int borderX = 115;
    public final int borderY = 90;
    public final int shiftUp = 10;

    public MeetingTime[] meetingTimes;
    public ArrayList<Section> sections = new ArrayList<>();
    public boolean addSection;
    JButton addSectionButton = new JButton();
    JLabel statusLabel = new JLabel();

    int colorIndex = 0;

    public void createCalendar() {
        this.setLayout(null);

        backButton.setSize(new Dimension(150, 75));
        backButton.setLocation(0, App.yDimension - 75);
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setFocusable(false);

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            JLabel day = new JLabel();
            day.setText(daysOfWeek[i]);
            day.setForeground(Color.black);
            day.setBounds(40 + borderX + i * unitSizeX, 60, 150, 15);
            day.setFont(new Font("Arial", Font.PLAIN, 15));
            this.add(day);
        }

        String[] times = {"8:00 am", "9:00 am", "10:00 am", "11:00 am", "12:00 pm", "1:00 pm", "2:00 pm", 
        "3:00 pm", "4:00 pm", "5:00 pm", "6:00 pm", "7:00 pm", "8:00 pm", "9:00 pm", "10:00 pm", "11:00 pm", };
        for (int i = 0; i < times.length; i++) {
            JLabel time = new JLabel();
            time.setText(times[i]);
            time.setForeground(Color.black);
            time.setBounds(65, 75 + i * unitSizeY, 150, 15);
            time.setFont(new Font("Arial", Font.PLAIN, 10));
            this.add(time);
        }

        this.setPreferredSize(new Dimension(App.xDimension, App.yDimension));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.add(backButton);
    }
    public Calendar(Section section) {
        sections.add(section);

        addSectionButton.setSize(new Dimension(150, 75));
        addSectionButton.setLocation(App.xDimension - 150, App.yDimension - 75);
        addSectionButton.addActionListener(this);
        addSectionButton.setText("Add Section");
        addSectionButton.setFocusable(false);
        this.add(addSectionButton);

        createCalendar();
        addSection = true;
    }
    public Calendar(ArrayList<Section> sections) {
        this.sections = sections;
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
                times.setBounds(xCoord + 8, (int) (yCoord - 15 + yHeight / 2), 150, 15);
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
        if (e.getSource() == addSectionButton && addSection == true) {
            Section section = sections.get(0);
            this.remove(statusLabel);
            App.frame.revalidate();
            App.frame.repaint();
            int canAddSectionToSchedule = 2;
            Section conflict = null;
            for (Section s : App.schedule) {
                if (section.index == s.index) {
                    canAddSectionToSchedule = 1;
                    break;
                }
                if (CheckSchedule.scheduleChecker(section, s) == false) {
                    canAddSectionToSchedule = 0;
                    conflict = s;
                    break;
                }
            }
            if (canAddSectionToSchedule == 2) {
                App.schedule.add(section);
                statusLabel.setText("Section successfully added");
                statusLabel.setBounds(App.xDimension - 350, App.yDimension - 75, 250, 15);
            } else if (canAddSectionToSchedule == 1) {
                statusLabel.setText("Duplicate section detected");
                statusLabel.setBounds(App.xDimension - 350, App.yDimension - 75, 250, 15);
            }
            else {
                statusLabel.setText("Section conflicting with section " + conflict.index +" different time");
                statusLabel.setBounds(App.xDimension - 500, App.yDimension - 75, 350, 15);
            }
            statusLabel.setForeground(Color.black);
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            this.add(statusLabel);
        }
    }
}
