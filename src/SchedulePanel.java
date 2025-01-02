import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SchedulePanel extends JPanel implements ActionListener{
    JButton backButton = new JButton();
    JButton searchButton = new JButton();
    JTextField textField = new JTextField();
    JScrollPane scrollPane;

    JButton viewSectionButton = new JButton();
    JScrollPane scrollPaneClass;
    JLabel classTitle = new JLabel();
    JLabel classCourseString = new JLabel();

    JButton addSubjectToWishlistButton = new JButton();
    JLabel statusLabel = new JLabel();

    int subjectNumber = 0;
    int sectionNumber = 0;
    
    public SchedulePanel() {
        App.frame.setSchedulePanel(this);

        this.setLayout(null);

        backButton = new JButton();
        backButton.setSize(new Dimension(150, 75));
        backButton.setLocation(20, App.yDimension - 95);
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setFocusable(false);

        textField.setSize(new Dimension(380, 40));
        textField.setLocation(50, 50);

        searchButton.setSize(new Dimension(100, 40));
        searchButton.setLocation(450, 50);
        searchButton.addActionListener(this);
        searchButton.setText("Search");
        searchButton.setFocusable(false);

        viewSectionButton.setSize(new Dimension(200, 75));
        viewSectionButton.setLocation(600,525);
        viewSectionButton.addActionListener(this);
        viewSectionButton.setText("View Section");
        viewSectionButton.setFocusable(false);
        viewSectionButton.setVisible(false);
        
        addSubjectToWishlistButton.setSize(new Dimension(200, 75));
        addSubjectToWishlistButton.setLocation(350, 525);
        addSubjectToWishlistButton.addActionListener(this);
        addSubjectToWishlistButton.setText("Add Subject to Wishlist");
        addSubjectToWishlistButton.setFocusable(false);
        addSubjectToWishlistButton.setVisible(false);

        this.setPreferredSize(new Dimension(1920, 1080));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.add(backButton);
        this.add(textField);
        this.add(searchButton);
        this.add(viewSectionButton);
        this.add(addSubjectToWishlistButton);
    }
    public void showData() {
        if (scrollPaneClass != null) this.remove(scrollPaneClass);
        if (classTitle != null) this.remove(classTitle);
        if (classCourseString != null) this.remove(classCourseString);
        
        this.setLayout(null);
        classTitle.setText("Course Name: " + PopulateValues.subjects[subjectNumber].title);
        classTitle.setForeground(Color.black);
        classTitle.setBounds(0, 0, 600, 30);
        classTitle.setFont(new Font("Arial", Font.PLAIN, 20));

        classCourseString.setText("Course Code: " + PopulateValues.subjects[subjectNumber].courseString);
        classCourseString.setForeground(Color.black);
        classCourseString.setBounds(600, 0, 600, 30);
        classCourseString.setFont(new Font("Arial", Font.PLAIN, 20));

        Section[] sections = PopulateValues.subjects[subjectNumber].sections;
        
        JList<String> list = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < sections.length; i++) {
            model.addElement(sections[i].index);
        }
        scrollPaneClass = new JScrollPane(list);
        scrollPaneClass.setBounds(600, 100, 500, 400);
        scrollPaneClass.setViewportView(list);
        this.add(scrollPaneClass);
        list.setModel(model);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    sectionNumber = list.getSelectedIndex();
                    viewSectionButton.setVisible(true);
                }
            }
        });
        this.add(classTitle);
        this.add(classCourseString);
        addSubjectToWishlistButton.setVisible(true);


        App.frame.revalidate();
        App.frame.repaint();
    }
    public void search(String input) {
        if (PopulateValues.subjects == null) {
            RefreshResults.refresh(App.url, App.filename);
            PopulateValues.populate(App.filename, App.scheduleFile, App.wishlistFile);
        }
        ArrayList<String> searchedResults = CheckSchedule.search(input, PopulateValues.subjects);
        
        JList<String> list = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < searchedResults.size(); i++) {
            model.addElement(searchedResults.get(i));
        }
        scrollPane = new JScrollPane(list);
        scrollPane.setBounds(50, 100, 500, 400);
        scrollPane.setViewportView(list);
        this.add(scrollPane);
        list.setModel(model);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    viewSectionButton.setVisible(false);
                    subjectNumber = CheckSchedule.searchIndex(list.getSelectedValue(), PopulateValues.subjects);
                    showData();
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            App.frame.remove(this);
            App.frame.add(new DefaultPanel());
            App.frame.revalidate();
            App.frame.repaint();
        }
        if (e.getSource() == searchButton) {
            if (scrollPane != null) this.remove(scrollPane);
            search(textField.getText());
        }
        if (e.getSource() == viewSectionButton) {
            //System.out.println("View Button");
            this.setVisible(false);
            Section section = PopulateValues.subjects[subjectNumber].sections[sectionNumber];
            App.frame.add(new CalendarPanel(section));
        }
        if (e.getSource() == addSubjectToWishlistButton) {
            //System.out.println("Attempted to add Subject");
            this.remove(statusLabel);
            App.frame.revalidate();
            App.frame.repaint();
            boolean canAddSubjectToSchedule = true;
            //System.out.println("=============");
            for (Subject s : App.wishlist) {
                //System.out.println("Wishlist Course: " + s.courseString);
                //System.out.println("Course to be added: " + PopulateValues.subjects[subjectNumber].courseString);

                if (PopulateValues.subjects[subjectNumber].equals(s)) {
                    canAddSubjectToSchedule = false;
                    break;
                }
            }
            //System.out.println("=============");
            //System.out.println(canAddSubjectToSchedule);
            if (canAddSubjectToSchedule == true) {
                App.wishlist.add(PopulateValues.subjects[subjectNumber]);
                statusLabel.setText("Subject successfully added to wishlist");
            } else {
                statusLabel.setText("Duplicate subject detected");
            }
            statusLabel.setBounds(App.xDimension - 300, App.yDimension - 75, 250, 15);
            statusLabel.setForeground(Color.black);
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            this.add(statusLabel);
        }
    }
}
