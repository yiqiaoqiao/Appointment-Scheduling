import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * This class makes the appointment panel
 */
public class AppointmentPanel
{
    private JPanel appointmentPanel;
    private ArrayList<String> facultyNames;
    private JComboBox timeCombo;
    private AppointmentFile appointmentFile;
    private String email;
    private String firstName;
    private String lastName;
    private String ID;
    private ArrayList<Integer> availableTime;
    private JComboBox dayCombo;
    private JComboBox personCombo;

    /**
     * This constructor creates the panel
     * @param majorChoose the major that the user chose
     */
    public AppointmentPanel(String majorChoose, String email, String firstName, String lastName, String ID)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;

        appointmentFile = new AppointmentFile(majorChoose);
        availableTime = new ArrayList<>();

        facultyNames = new ArrayList<>();
        setMajorChoose(majorChoose);
        appointmentPanel = new JPanel();
        appointmentPanel.setLayout(new GridLayout(5, 1));
        appointmentPanel.add(createDayPanel());
        appointmentPanel.add(createPersonPanel());
        appointmentPanel.add(createEnterPanel());
        appointmentPanel.add(createAvailableTimePanel());
        appointmentPanel.add(createMakeAppointmentPanel());
    }

    /**
     * This sets the faculty names depends on the major
     * @param majorChoose the major that the user chose
     */
    public void setMajorChoose(String majorChoose)
    {
        if(majorChoose.equals("CS"))
        {
            facultyNames.add("Jamal Ashraf");
            facultyNames.add("Barkeshli Sassan");
            facultyNames.add("Erion Jupe");
            facultyNames.add("Keng, Chang");
        }
        else
        {
            facultyNames.add("Jay Cho");
            facultyNames.add("Dan Guo");
            facultyNames.add("He Fendi");
            facultyNames.add("Kun Niu");
        }
    }

    /**
     * Creates the panel for the selecting day
     * @return the day panel
     */
    public JPanel createDayPanel()
    {
        JPanel dayPanel = new JPanel();
        dayCombo = new JComboBox();
        dayCombo.addItem("Monday");
        dayCombo.addItem("Tuesday");
        dayCombo.addItem("Wednesday");
        dayCombo.addItem("Thursday");
        dayCombo.addItem("Friday");
        dayCombo.setPreferredSize(new Dimension(150, 20));
        JLabel dayLabel = new JLabel("Day");
        dayPanel.add(dayLabel);
        dayPanel.add(dayCombo);
        return dayPanel;
    }

    /**
     * Creates the panel for the person panel
     * @return person panel
     */
    public JPanel createPersonPanel()
    {
        JPanel personPanel = new JPanel();
        personCombo = new JComboBox();
        personCombo.addItem("Chair of Department");
        personCombo.addItem("Dean");
        for(int i = 0; i < facultyNames.size(); i++)
        {
            personCombo.addItem(facultyNames.get(i));
        }
        personCombo.setPreferredSize(new Dimension(150, 20));
        JLabel personLabel = new JLabel("Person");
        personPanel.add(personLabel);
        personPanel.add(personCombo);
        return personPanel;
    }

    /**
     * Creates the panel for the enter
     * @return enter panel
     */
    public JPanel createEnterPanel()
    {
        /**
         * This class is the listener of the enter class
         */
        class EnterListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                ArrayList<Integer> availableTime;
                String names = (String) personCombo.getSelectedItem();
                String day = (String) dayCombo.getSelectedItem();
                availableTime = appointmentFile.getAvailableTime(names, day);
                for(int i = 0; i < availableTime.size(); i++)
                {
                    timeCombo.addItem("" + availableTime.get(i) + ":00");
                }
                timeCombo.setEditable(true);
            }
        }

        JButton enterButton = new JButton("Enter");
        ActionListener enterListener = new EnterListener();
        enterButton.addActionListener(enterListener);
        JPanel enterPanel = new JPanel();
        enterPanel.setLayout(new GridLayout(2, 4));
        enterPanel.add(new JPanel());
        enterPanel.add(new JPanel());
        enterPanel.add(new JPanel());
        enterPanel.add(enterButton);
        enterPanel.add(new JPanel());
        enterPanel.add(new JPanel());
        enterPanel.add(new JPanel());
        enterPanel.add(new JPanel());
        return enterPanel;
    }

    /**
     * Creates the available time panel
     * @return available time panel
     */
    public JPanel createAvailableTimePanel()
    {
        JPanel timePanel = new JPanel();
        timeCombo = new JComboBox();
        timeCombo.setEditable(false);
        timeCombo.setPreferredSize(new Dimension(150, 20));
        JLabel timeLabel = new JLabel("Available Time: ");
        timePanel.add(timeLabel);
        timePanel.add(timeCombo);
        return timePanel;
    }

    /**
     * Creates the panel for making appointment button
     * @return the make appointment panel
     */
    public JPanel createMakeAppointmentPanel()
    {
        /**
         * This class is the listener of the appointment class that will make the appointment
         */
        class ApptListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                String faculty = (String) personCombo.getSelectedItem();
                String dayOfWeek = (String) dayCombo.getSelectedItem();
                String time = (String) timeCombo.getSelectedItem();
                appointmentFile.makeAppointment(faculty, dayOfWeek, time);
                JOptionPane.showMessageDialog(null, "Appointment Made Successfully" + "\n"
                + "Thank you for your visit");
                sendEmail(true, faculty, dayOfWeek, time);
                sendEmail(false, faculty, dayOfWeek, time);
            }
        }

        /**
         * This class is the listener for end button to end the program
         */
        class EndListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }
        }

        JPanel makeAppointmentPanel = new JPanel();
        makeAppointmentPanel.setLayout(new GridLayout(2,4));

        JButton apptButton = new JButton("Make Appointment");
        JButton endButton = new JButton("End");

        ActionListener apptListener = new ApptListener();
        ActionListener endListener = new EndListener();
        apptButton.addActionListener(apptListener);
        endButton.addActionListener(endListener);

        makeAppointmentPanel.add(new JPanel());
        makeAppointmentPanel.add(new JPanel());
        makeAppointmentPanel.add(endButton);
        makeAppointmentPanel.add(apptButton);
        return makeAppointmentPanel;
    }

    /**
     * This method will send the email to the professor or the student
     * @param toFaculty true if sending to the faculty, false if sending to student
     * @param faculty the faculty that is sending to
     * @param dayOfWeek the day of the appointment
     * @param time the time of the appointment
     */
    public void sendEmail(boolean toFaculty, String faculty, String dayOfWeek, String time)
    {
        final String password = "16955";
        final String username = "ywang5197@tcusd.net";
        final String to, content;
        if(toFaculty) {
            to = "ywang254@go.pasadena.edu";
            content = "Student Name: " + firstName + " " + lastName + "\n" + "ID: " + ID + "\n" + "Email: " + email
                    + "\n" + "Making appointment with you at " + time + " on " + dayOfWeek;
        }
        else {
            to = email;
            content = "Successfully making appointment with " + faculty + " at " + time + " on " + dayOfWeek;
        }
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject("Make Appointment");
            message.setText(content);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the appointment panel
     * @return the appointment panel
     */
    public JPanel getAppointmentPanel()
    {
        return appointmentPanel;
    }
}
