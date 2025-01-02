import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class DefaultPanel extends JPanel implements ActionListener {
    JButton refreshButton = new JButton();
    JButton scheduleButton = new JButton();
    JButton showScheduleButton = new JButton();
    JButton wishlistButton = new JButton();

    public final int buttonWidth = 200;
    public final int buttonHeight = 100;

    public DefaultPanel() {
        this.setLayout(null);
        refreshButton.setSize(new Dimension(buttonWidth, buttonHeight));
        refreshButton.setLocation(70, 300);
        refreshButton.addActionListener(this);
        refreshButton.setText("Refresh Values");
        refreshButton.setFocusable(false);

        scheduleButton.setSize(new Dimension(buttonWidth, buttonHeight));
        scheduleButton.setLocation(70, 100);
        scheduleButton.addActionListener(this);
        scheduleButton.setText("Schedule");
        scheduleButton.setFocusable(false);

        showScheduleButton.setSize(new Dimension(buttonWidth, buttonHeight));
        showScheduleButton.setLocation(70, 500);
        showScheduleButton.addActionListener(this);
        showScheduleButton.setText("Show Schedule");
        showScheduleButton.setFocusable(false);

        wishlistButton.setSize(new Dimension(buttonWidth, buttonHeight));
        wishlistButton.setLocation(App.xDimension - buttonWidth - 70, 100);
        wishlistButton.addActionListener(this);
        wishlistButton.setText("Show Wishlist");
        wishlistButton.setFocusable(false);

        this.setPreferredSize(new Dimension(App.xDimension, App.yDimension));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.add(refreshButton);
        this.add(scheduleButton);   
        this.add(showScheduleButton);
        this.add(wishlistButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == refreshButton) {
            this.setVisible(false);
            App.frame.remove(this);
            App.frame.add(new RefreshPanel());
        }
        if (e.getSource() == scheduleButton) {
            App.frame.remove(this);
            App.frame.add(new SchedulePanel());
            App.frame.revalidate();
            App.frame.repaint();
        }
        if (e.getSource() == showScheduleButton) {
            App.frame.remove(this);
            App.frame.add(new CalendarPanel(App.schedule));
            App.frame.revalidate();
            App.frame.repaint();
        }
        if (e.getSource() == wishlistButton) {
            App.frame.remove(this);
            App.frame.add(new WishlistPanel());
            App.frame.revalidate();
            App.frame.repaint();
        }
    }
}
