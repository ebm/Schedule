import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;


public class DefaultPanel extends JPanel implements ActionListener {
    JButton refreshButton = new JButton();
    JButton scheduleButton = new JButton();
    JButton showScheduleButton = new JButton();

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

        this.setPreferredSize(new Dimension(App.xDimension, App.yDimension));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.add(refreshButton);
        this.add(scheduleButton);   
        this.add(showScheduleButton);
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
            /*System.out.println("===========");
            System.out.println("Subjects:");
            for (Subject s : App.wishlist) {
                System.out.println(s.title);
            }
            System.out.println("===========");
            System.out.println("Sections:");
            for (Section s : App.schedule) {
                System.out.println(s.index);
            }
            System.out.println("===========");*/
            App.frame.remove(this);
            App.frame.add(new Calendar(App.schedule));
            App.frame.revalidate();
            App.frame.repaint();
        }
    }
}
