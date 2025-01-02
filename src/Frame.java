import javax.swing.*;

public class Frame extends JFrame{
    public SchedulePanel schedulePanel;
    public WishlistPanel wishlistPanel;
    public Frame() {
        this.add(new DefaultPanel());

        this.setTitle("Scheduler");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    public void setSchedulePanel(SchedulePanel schedulePanel) {
        this.schedulePanel = schedulePanel;
    }
    public void setWishlistPanel(WishlistPanel wishlistPanel) {
        this.wishlistPanel = wishlistPanel;
    }
}
