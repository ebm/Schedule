import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WishlistPanel extends JPanel implements ActionListener{
    JButton backButton = new JButton();
    JScrollPane scrollPane;

    JButton removeSubjectFromWishlistButton = new JButton();
    JScrollPane scrollPaneClass;

    JButton viewWishlistScheduleButton = new JButton();
    JCheckBox[] getFreeDays = new JCheckBox[7];
    ArrayList<Schedule> listOfSchedules;
    TimeConstraint listTimeConstraints = new TimeConstraint();
    int timeConstraints[] = {800, 2300};

    JLabel totalCreditsLabel = new JLabel();
    String subjectSelected;

    double totalCredits = 0.0;
    
    public WishlistPanel() {
        App.frame.setWishlistPanel(this);
        this.setLayout(null);

        for (int i = 0; i < App.wishlist.size(); i++) {
            totalCredits += App.wishlist.get(i).getCredits();
        }

        backButton = new JButton();
        backButton.setSize(new Dimension(150, 75));
        backButton.setLocation(20, App.yDimension - 95);
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setFocusable(false);

        removeSubjectFromWishlistButton.setSize(new Dimension(200, 75));
        removeSubjectFromWishlistButton.setLocation(600, 100);
        removeSubjectFromWishlistButton.addActionListener(this);
        removeSubjectFromWishlistButton.setText("Remove Subject");
        removeSubjectFromWishlistButton.setFocusable(false);

        viewWishlistScheduleButton.setSize(new Dimension(150, 75));
        viewWishlistScheduleButton.setLocation(App.xDimension - 170, App.yDimension - 95);
        viewWishlistScheduleButton.addActionListener(this);
        viewWishlistScheduleButton.setText("View Schedule");
        viewWishlistScheduleButton.setFocusable(false);

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < getFreeDays.length; i++) {
            int j = 0;
            if (i >= 3) j = 1;
            getFreeDays[i] = new JCheckBox();
            getFreeDays[i].setText(daysOfWeek[i]);
            getFreeDays[i].setFocusable(false);
            getFreeDays[i].setFont(new Font("Arial", Font.PLAIN, 15));
            getFreeDays[i].setBounds(600 + i * 125 - 3 * j * 125, 200 + 30 * j, 125, 15);
            getFreeDays[i].setSelected(true);
            this.add(getFreeDays[i]);
        }
        getFreeDays[5].setSelected(false);
        getFreeDays[6].setSelected(false);

        class SlideListener implements ChangeListener {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                for (int i = 0; i < 2; i++) {
                    if (source == listTimeConstraints.getTimeConstraints[i]) {
                        int temp = (int) source.getValue();
                        timeConstraints[i] = ((int) temp / 100) * 100 + (temp % 100) * 60 / 100;
                        listTimeConstraints.textField[i].setText(MeetingTime.convertTimeToString(timeConstraints[i]));
                    }
                }

                /*if (!source.getValueIsAdjusting()) {
                    if (source)
                }*/
            }
        }

        for (int i = 0; i < 2; i++) {
            listTimeConstraints.getTimeConstraints[i].addChangeListener(new SlideListener());
            this.add(listTimeConstraints.getTimeConstraints[i]);
            this.add(listTimeConstraints.getTimeConstraintsLabel[i]);
            this.add(listTimeConstraints.textField[i]);
        }
        

        this.setPreferredSize(new Dimension(1920, 1080));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.add(backButton);
        this.add(removeSubjectFromWishlistButton);
        this.add(viewWishlistScheduleButton);
        showData();
    }
    public void showData() {
        if (scrollPaneClass != null) this.remove(scrollPaneClass);
        
        this.setLayout(null);

        totalCreditsLabel.setText("Total Credits: " + totalCredits);
        totalCreditsLabel.setForeground(Color.black);
        totalCreditsLabel.setBounds(50, 50, 150, 15);
        totalCreditsLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        
        this.add(totalCreditsLabel);
        
        JList<String> list = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < App.wishlist.size(); i++) {
            model.addElement(App.wishlist.get(i).title);
        }
        scrollPaneClass = new JScrollPane(list);
        scrollPaneClass.setBounds(50, 100, 500, 400);
        scrollPaneClass.setViewportView(list);
        this.add(scrollPaneClass);
        list.setModel(model);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    subjectSelected = list.getSelectedValue();
                    //System.out.println(subjectSelected);
                }
            }
        });
        App.frame.revalidate();
        App.frame.repaint();
    }
    public void everyClass(Subject[] wishlistSubjects, int index, Schedule currList) {
        for (int i = 0; i < wishlistSubjects[index].sections.length; i++) {
            Schedule s = Schedule.returnAddedSchedule(currList, wishlistSubjects[index].sections[i]);
            if (!s.calendar.equals(currList.calendar)) {
                if (index + 1 != wishlistSubjects.length) everyClass(wishlistSubjects, index + 1, s);
                if (s.calendar.size() == wishlistSubjects.length) listOfSchedules.add(s);
            }            
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            App.frame.remove(this);
            App.frame.add(new DefaultPanel());
            App.frame.revalidate();
            App.frame.repaint();
        }
        if (e.getSource() == removeSubjectFromWishlistButton) {
            for (int i = 0; i < App.wishlist.size(); i++) {
                Subject s = App.wishlist.get(i);
                if (subjectSelected != null && subjectSelected.equals(s.title)) {
                    App.wishlist.remove(s);
                    totalCredits -= s.getCredits();
                }
            }
            showData();
        }
        if (e.getSource() == viewWishlistScheduleButton) {
            ArrayList<String> freeDaysArrayList = new ArrayList<>();
            String[] daysOfWeek = {"M", "T", "W", "H", "F", "S", "U"};
            for (int i = 0; i < 7; i++) {
                if (getFreeDays[i].isSelected() == false) freeDaysArrayList.add(daysOfWeek[i]);
            }
            String[] freeDays = new String[freeDaysArrayList.size()];
            for (int i = 0; i < freeDays.length; i++) {
                freeDays[i] = freeDaysArrayList.get(i);
            }
            Subject[] wishlistSubjects = new Subject[App.wishlist.size()];
            for (int i = 0; i < wishlistSubjects.length; i++) {
                wishlistSubjects[i] = new Subject(App.wishlist.get(i));
            }
            for (int i = 0; i < wishlistSubjects.length; i++) {
                wishlistSubjects[i].sections = CheckSchedule.getSectionsWithinDays(freeDays, wishlistSubjects[i].sections);
                wishlistSubjects[i].sections = CheckSchedule.getSectionsWithinTimeConstraint(timeConstraints[0], timeConstraints[1], wishlistSubjects[i].sections);
            }
            listOfSchedules = new ArrayList<>();
            everyClass(wishlistSubjects, 0, new Schedule());
            this.setVisible(false);
            App.frame.add(new CalendarPanel(listOfSchedules, 0));
            App.frame.revalidate();
            App.frame.repaint();
        }
    }
}
