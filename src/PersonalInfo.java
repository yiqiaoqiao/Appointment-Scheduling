import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * This class draws the personal information panel
 */
public class PersonalInfo
{
    private JPanel personalPanel;
    private JPanel radioPanel;
    private JRadioButton newButton;
    private JRadioButton beenHereButton;
    private PersonalInfoFile file;
    private String ID;
    private String firstName;
    private String lastName;
    private String email;
    private JTextField IDText;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField emailText;

    /**
     * This constructor saves the file
     */
    public PersonalInfo()
    {
        ID = "";
        email = "";
        firstName = "";
        lastName = "";
        file = new PersonalInfoFile();
        createPersonalPanel();
    }

    /**
     * This class listens for the new button and sets the ID and name
     */
    public class NewListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            ID = JOptionPane.showInputDialog("Please enter your ID");
            firstName = JOptionPane.showInputDialog("Please enter your first name");
            lastName = JOptionPane.showInputDialog("Please enter your last name");
            email = JOptionPane.showInputDialog("Please enter your email");
            try {
                file.setPersonalInfo(ID, firstName, lastName, email);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File not found");
            }
            IDText.setText(ID);
            firstNameText.setText(firstName);
            lastNameText.setText(lastName);
            emailText.setText(email);
            personalPanel.repaint();
        }
    }

    /**
     * This class listens fot the been here button
     */
    public class BeenListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            ID = JOptionPane.showInputDialog("Please enter your ID");
            if(file.findNames(ID))
            {
                IDText.setText(ID);
                firstName = file.getFirstName();
                lastName = file.getLastName();
                email = file.getEmail();
                firstNameText.setText(firstName);
                lastNameText.setText(lastName);
                emailText.setText(email);
                personalPanel.repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Cannot find matching ID");
            }
        }
    }

    /**
     * This method creates the panel that shows the radio buttons for new and been here buttons
     */
    public void createRadioPanel()
    {
        newButton = new JRadioButton("New");
        ActionListener newListener = new NewListener();
        newButton.addActionListener(newListener);
        beenHereButton = new JRadioButton("Been Here");
        ActionListener beenListener = new BeenListener();
        beenHereButton.addActionListener(beenListener);

        //Group together
        ButtonGroup group = new ButtonGroup();
        group.add(newButton);
        group.add(beenHereButton);

        //Add in a panel
        radioPanel = new JPanel();
        radioPanel.add(newButton);
        radioPanel.add(beenHereButton);
    }

    /**
     * This method creates panel for the ID text field
     * @return the IDPanel
     */
    public JPanel createIDPanel()
    {
        JLabel IDLabel = new JLabel("ID: ");
        IDText = new JTextField();
        IDText.setEditable(false);
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new GridLayout(1, 4));
        idPanel.add(IDLabel);
        idPanel.add(IDText);
        return idPanel;
    }

    /**
     * This method creates panel for the email text field
     * @return the IDPanel
     */
    public JPanel createEmailPanel()
    {
        JLabel emailLabel = new JLabel("Email: ");
        emailText = new JTextField();
        emailText.setEditable(false);
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new GridLayout(1, 4));
        emailPanel.add(emailLabel);
        emailPanel.add(emailText);
        return emailPanel;
    }

    /**
     * This method creates panel for the first name
     * @return firstNamePanel
     */
    public JPanel createFirstNamePanel()
    {
        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameText = new JTextField();
        firstNameText.setEditable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        panel.add(firstNameLabel);
        panel.add(firstNameText);
        return panel;
    }

    /**
     * This method creates panel that shows the last name text field
     * @return lastNamePanel
     */
    public JPanel createLastNamePanel()
    {
        JLabel lastNameLabel = new JLabel("Last Name: ");
        lastNameText = new JTextField();
        lastNameText.setEditable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        panel.add(lastNameLabel);
        panel.add(lastNameText);
        return panel;
    }

    /**
     * This method creates panel for the whole part of personal information
     */
    public void createPersonalPanel()
    {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(4,1));
        textPanel.add(createIDPanel());
        textPanel.add(createFirstNamePanel());
        textPanel.add(createLastNamePanel());
        textPanel.add(createEmailPanel());
        personalPanel = new JPanel();
        createRadioPanel();
        personalPanel.setBorder(new TitledBorder(new EtchedBorder(), "Personal Information"));
        personalPanel.setLayout(new GridLayout(2, 1));
        personalPanel.add(radioPanel, BorderLayout.NORTH);
        personalPanel.add(textPanel, BorderLayout.CENTER);
    }

    /**
     * This method returns that personal panel
     * @return
     */
    public JPanel getPersonalPanel()
    {
        return personalPanel;
    }

    /**
     * Returns the first name
     * @return first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Returns the last name
     * @return last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Returns the email
     * @return email
     */
    public String getEmail(){return  email;}

    /**
     * Returns the ID
     * @return ID
     */
    public String getID(){return  ID;}
}
