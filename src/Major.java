import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class creates the major panel that let the user to choose major
 */
public class Major
{
    private JComboBox majorCombo;
    private JPanel majorPanel;
    private String majorChoose;

    /**
     * This constructor initialize everything
     */
    public Major()
    {
        majorChoose = "CS";
        createComboBox();
        createPanel();
    }

    /**
     * This class listens for the combo box and get the major that the user chose
     */
    public class ComboListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            majorChoose = (String) majorCombo.getSelectedItem();
        }
    }

    /**
     * This method creates the combo box choice (Math/CS)
     */
    public void createComboBox()
    {
        majorCombo = new JComboBox();
        majorCombo.addItem("CS");
        majorCombo.addItem("Math");
        ActionListener listener = new ComboListener();
        majorCombo.addActionListener(listener);
    }

    /**
     * This method creates a panel for the major choice
     */
    public void createPanel()
    {
        majorPanel = new JPanel();
        majorPanel.add(majorCombo);
        majorPanel.setBorder(new TitledBorder(new EtchedBorder(), "Major"));
    }

    /**
     * Gets the major that the user chose
     * @return major choose
     */
    public String getMajorChoose()
    {
        return majorChoose;
    }

    /**
     * Return the major panel
     * @return the majorPanel
     */
    public JPanel getMajorPanel()
    {
        return majorPanel;
    }
}
