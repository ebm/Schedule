import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WishlistPanel extends JPanel implements ActionListener{
    JButton backButton = new JButton();
    JScrollPane scrollPane;

    JButton removeSubjectFromWishlistButton = new JButton();
    JScrollPane scrollPaneClass;

    JButton viewWishlistScheduleButton = new JButton();
    JCheckBox[] getFreeDays = new JCheckBox[7];

    JLabel totalCreditsLabel = new JLabel();
    String subjectSelected;

    double totalCredits = 0.0;
    
    public WishlistPanel() {
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

        viewWishlistScheduleButton.setSize(new Dimension(200, 75));
        viewWishlistScheduleButton.setLocation(600, 425);
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
    ArrayList<Schedule> curr = new ArrayList<>();
    public ArrayList<Schedule> everyClass(Subject[] wishlistSubjects, int index, ArrayList<Schedule> currSchedule, Schedule currList) {
        for (int i = 0; i < wishlistSubjects[index].sections.length; i++) {
            /*System.out.println("Section to add: " + PopulateValues.subjects[wishlistSubjects[index].sections[i].subjectIndex].title + "," + wishlistSubjects[index].sections[i].index);
            for (int j = 0; j < currList.calendar.size(); j++) {
                Section s = currList.calendar.get(j);
                System.out.print(PopulateValues.subjects[s.subjectIndex].title + ": " + s.index + ",");
            }
            System.out.println();*/
            if (currList.addSection(wishlistSubjects[index].sections[i]) == 1) {
                if (index + 1 != wishlistSubjects.length) currSchedule = everyClass(wishlistSubjects, index + 1, currSchedule, currList);
                if (currList.calendar.size() == wishlistSubjects.length) {
                    curr.add(currList);
                }
            }
        }
        for (int i = 0; i < curr.size(); i++) {
            System.out.println(curr.get(i).calendar.size());
        }
        return currSchedule;
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
                //System.out.println(freeDaysArrayList.get(i));
                freeDays[i] = freeDaysArrayList.get(i);
            }
            Subject[] wishlistSubjects = new Subject[App.wishlist.size()];
            for (int i = 0; i < wishlistSubjects.length; i++) {
                wishlistSubjects[i] = new Subject(App.wishlist.get(i));
            }
            /*for (int i = 0; i < wishlistSubjects.length; i++) {
                for (int j = 0; j < wishlistSubjects[i].sections.length; j++) {
                    System.out.println(wishlistSubjects[i].sections[j].index);
                }
                System.out.println("================");
            }*/
            for (int i = 0; i < wishlistSubjects.length; i++) {
                wishlistSubjects[i].sections = CheckSchedule.getSectionsWithinDays(freeDays, wishlistSubjects[i].sections);
            }
            ArrayList<Schedule> listOfSchedules = everyClass(wishlistSubjects, 0, new ArrayList<>(), new Schedule());
            for(int i = 0; i < listOfSchedules.size(); i++) {
                System.out.println(listOfSchedules.get(i).calendar.size());
            }
            System.out.println(listOfSchedules.size());
            //System.out.println(App.wishlist.get(0).sections.length);
            App.frame.remove(this);
            App.frame.add(new CalendarPanel(listOfSchedules, 0));
            App.frame.revalidate();
            App.frame.repaint();
        }
    }
}
