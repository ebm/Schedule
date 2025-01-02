import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class RefreshPanel extends JPanel implements ActionListener{
    JButton backButton;
    JProgressBar bar;
    JButton refreshButton;
    
    public RefreshPanel() {
        this.setLayout(null);
        backButton = new JButton();
        backButton.setSize(new Dimension(150, 75));
        backButton.setLocation(20, App.yDimension - 95);
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setFocusable(false);


        refreshButton = new JButton();
        refreshButton.setSize(new Dimension(200, 100));
        refreshButton.setLocation(App.xDimension / 2 - 100, App.yDimension / 2 - 50);
        refreshButton.addActionListener(this);
        refreshButton.setText("Refresh");
        refreshButton.setFocusable(false);

        bar = new JProgressBar();
        bar.setBounds(0, 0, App.xDimension, 75);
        bar.setValue(0);
        bar.setStringPainted(true);

        this.setPreferredSize(new Dimension(App.xDimension, App.yDimension));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.add(backButton);
        this.add(bar);
        this.add(refreshButton);
    }
    public void refresh() {
        if (RefreshResults.refresh(App.url, App.filename) == true) {
            bar.setString("Refreshed!");
            bar.setValue(100);
        }
        else {
            bar.setString("Refresh Failed.");
        }
        PopulateValues.populate(App.filename, App.scheduleFile, App.wishlistFile);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            App.frame.remove(this);
            App.frame.add(new DefaultPanel());
            App.frame.revalidate();
            App.frame.repaint();
        }
        if (e.getSource() == refreshButton) {
            refresh();
        }
    }
}
