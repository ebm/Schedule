import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class TimeConstraint{
    public JSlider[] getTimeConstraints = new JSlider[2];
    public JLabel[] getTimeConstraintsLabel = new JLabel[2];
    public JTextField[] textField = new JTextField[2];

    public TimeConstraint() {
        getTimeConstraints[0] = new JSlider(JSlider.HORIZONTAL, 800, 2300, 800);
        getTimeConstraints[1] = new JSlider(JSlider.HORIZONTAL, 800, 2300, 2300);
        for (int i = 0; i < 2; i++) {
            //getTimeConstraints[i].setMajorTickSpacing(200);
            getTimeConstraints[i].setMinorTickSpacing(100);
            getTimeConstraints[i].setPaintTicks(true);
            //getTimeConstraints[i].setPaintLabels(true);
            getTimeConstraints[i].setFont(new Font("Arial", Font.PLAIN, 15));
            getTimeConstraints[i].setBounds(600, 300 + i * 150, 500, 50);

            textField[i] = new JTextField();
            textField[i].setSize(new Dimension(100, 20));
            textField[i].setEditable(false);
            textField[i].setFocusable(false);
            textField[i].setLocation(700, 275 + i * 150);
            textField[i].setText(MeetingTime.convertTimeToString(getTimeConstraints[i].getValue()));

            getTimeConstraintsLabel[i] = new JLabel();
            getTimeConstraintsLabel[i].setFont(new Font("Arial", Font.PLAIN, 15));
            getTimeConstraintsLabel[i].setBounds(600, 275 + i * 150, 100, 20);
        }
        getTimeConstraintsLabel[0].setText("Start Time");
        getTimeConstraintsLabel[1].setText("End Time");
    }
}
