import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class draws all the panel in the frame
 */
public class GUI extends JFrame
{
    private int FRAME_WIDTH = 300;
    private int FRAME_HEIGHT = 600;
    private JPanel controlPanel;
    private JPanel questionPanel;
    private JButton enterButton;
    private String majorChose;
    private Major major;
    private PersonalInfo personalInfo;
    private String firstName;
    private String lastName;
    private String email;
    private String ID;

    /**
     * This constructor sets the frame
     */
    public GUI()
    {
        ID = "";
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        questionPanel = new JPanel();
        controlPanel = new JPanel();
        createControlPanel();
        add(controlPanel);
    }

    /**
     * This method draws the panels together
     */
    public void createControlPanel()
    {
        major = new Major();
        personalInfo = new PersonalInfo();
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));
        controlPanel.add(major.getMajorPanel(), BorderLayout.NORTH);
        controlPanel.add(personalInfo.getPersonalPanel(), BorderLayout.CENTER);
        controlPanel.add(createPurposePanel(), BorderLayout.SOUTH);
    }

    /**
     * This method draws the panel for the purpose
     * @return the purpose Panel
     */
    public JPanel createPurposePanel()
    {
        JPanel purposePanel = new JPanel();
        JPanel choosePanel = new JPanel();
        JPanel enterPanel = new JPanel();
        purposePanel.setBorder(new TitledBorder(new EtchedBorder(), "Purpose"));
        JRadioButton question = new JRadioButton("Question");
        JRadioButton appointment = new JRadioButton("Appointment");

        /**
         * This class listens for the enter button and output a new frame
         */
        class EnterListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                majorChose = major.getMajorChoose();
                firstName = personalInfo.getFirstName();
                lastName = personalInfo.getLastName();
                email = personalInfo.getEmail();
                ID = personalInfo.getID();
                if(!ID.equals(""))
                {
                    setVisible(false);
                    remove(controlPanel);
                    if (question.isSelected())
                    {
                        QuestionPanel questionSlide = new QuestionPanel(majorChose, email, firstName, lastName, ID);
                        FRAME_HEIGHT = 300;
                        FRAME_WIDTH = 600;
                        setSize(FRAME_WIDTH, FRAME_HEIGHT);
                        add(questionSlide.getQuestionPanel());
                    }
                    else
                    {
                        AppointmentPanel apptSlide = new AppointmentPanel(majorChose, email, firstName, lastName, ID);
                        FRAME_HEIGHT = 300;
                        setSize(FRAME_WIDTH, FRAME_HEIGHT);
                        add(apptSlide.getAppointmentPanel());
                    }
                    setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please enter your Personal Information");
                }
            }
        }

        enterButton = new JButton("Enter");
        ActionListener enterListener = new EnterListener();
        enterButton.addActionListener(enterListener);
        enterPanel.add(enterButton);

        ButtonGroup group = new ButtonGroup();
        group.add(question);
        group.add(appointment);

        choosePanel.add(question);
        choosePanel.add(appointment);
        purposePanel.setLayout(new GridLayout(2, 1));
        purposePanel.add(choosePanel, BorderLayout.CENTER);
        purposePanel.add(enterPanel, BorderLayout.SOUTH);

        return purposePanel;
    }

    /**
     * Start the GUI, which sets the visible to true
     */
    public void start()
    {
        setVisible(true);
    }
}
