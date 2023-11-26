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

public class Wishlist extends JPanel implements ActionListener{
    JButton backButton = new JButton();
    JScrollPane scrollPane;

    JButton removeSubjectFromWishlistButton = new JButton();
    JScrollPane scrollPaneClass;

    JLabel statusLabel = new JLabel();

    int subjectNumber = 0;
    int sectionNumber = 0;
    
    public Wishlist() {
        this.setLayout(null);

        backButton = new JButton();
        backButton.setSize(new Dimension(150, 75));
        backButton.setLocation(20, App.yDimension - 95);
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setFocusable(false);

        removeSubjectFromWishlistButton.setSize(new Dimension(200, 75));
        removeSubjectFromWishlistButton.setLocation(600,525);
        removeSubjectFromWishlistButton.addActionListener(this);
        removeSubjectFromWishlistButton.setText("Remove Subject");
        removeSubjectFromWishlistButton.setFocusable(false);
        removeSubjectFromWishlistButton.setVisible(false);

        this.setPreferredSize(new Dimension(1920, 1080));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.add(backButton);
        this.add(removeSubjectFromWishlistButton);
        showData();
    }
    public void showData() {
        if (scrollPaneClass != null) this.remove(scrollPaneClass);
        
        this.setLayout(null);
        
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
                    subjectNumber = list.getSelectedIndex();
                    removeSubjectFromWishlistButton.setVisible(true);
                    System.out.println("test");
                }
            }
        });

        App.frame.revalidate();
        App.frame.repaint();
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
            App.wishlist.remove(PopulateValues.subjects[subjectNumber]);
            showData();
        }
    }
}
