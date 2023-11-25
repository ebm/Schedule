import javax.swing.*;

public class Frame extends JFrame{
    public SchedulePanel schedulePanel;
    public Frame() {
        this.add(new DefaultPanel());

        this.setTitle("Scheduler");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    public void setSchedulePanel(SchedulePanel schedulePanel) {
        this.schedulePanel = schedulePanel;
    }
}
