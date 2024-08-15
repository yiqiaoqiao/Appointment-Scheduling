import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This class creates the question panel to display
 */
public class QuestionPanel
{
    private JPanel questionPanel;
    private String question;
    private QuestionFile questionFile;
    private JTextArea textArea;
    private JComboBox possibleComboBox;
    private ArrayList<String> possibleQuestions;
    private ArrayList<String> possibleAnswers;
    private String fileName;
    private String email;
    private String ID;
    private String firstName;
    private String lastName;

    /**
     * Creates constructor that adds everything into the question panel
     */
    public QuestionPanel(String majorChoose, String email, String firstName, String lastName, String ID)
    {
        this.email = email;
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        if(majorChoose.equals("CS"))
            fileName = "CSQuestion.txt";
        else
            fileName = "MathQuestion.txt";
        questionFile = new QuestionFile(fileName);
        questionPanel = new JPanel();
        questionPanel.setLayout(new GridLayout(4,1));
        questionPanel.add(createQuestionInput());
        questionPanel.add(createComboBoxPanel());
        questionPanel.add(createTextAreaPanel());
        questionPanel.add(createButtonPanel());
    }

    /**
     * Create question input panel that display the question text box for the user to input
     * @return the question input panel
     */
    public JPanel createQuestionInput()
    {
        JLabel questionLabel = new JLabel("Question");
        JTextField questionTextField = new JTextField();

        /**
         * This class listens when the user click on enter
         */
        class EnterListener implements ActionListener
        {
            public void actionPerformed (ActionEvent event)
            {
                question = questionTextField.getText();
                if(questionFile.haveExactQuestion(question))
                {
                    textArea.setText(questionFile.getExactQuestion() + "\n" + questionFile.getAnswer());
                }

                else
                {
                    class ComboListener implements ActionListener
                    {
                        public void actionPerformed(ActionEvent event)
                        {
                            String questionChoose = (String) possibleComboBox.getSelectedItem();
                            for(int i = 0; i < possibleQuestions.size(); i++)
                            {
                                if(questionChoose.equals(possibleQuestions.get(i)))
                                {
                                    textArea.setText(possibleQuestions.get(i) + "\n" + possibleAnswers.get(i));
                                    questionPanel.repaint();
                                    break;
                                }
                            }
                        }
                    }

                    if (questionFile.findPossibleQuestions(question)) {
                        int count = 0;
                        possibleQuestions = questionFile.getPossibleQuestionLists();
                        possibleAnswers = questionFile.getPossibleAnswers();
                        for(int i = 0; i < possibleQuestions.size(); i++)
                        {
                            possibleComboBox.addItem(possibleQuestions.get(i));
                            count ++;
                            if(count == 3)
                                break;
                        }
                        ComboListener comboListener = new ComboListener();
                        possibleComboBox.addActionListener(comboListener);
                        possibleComboBox.setEditable(true);
                        questionPanel.repaint();
                    }
                    else {
                        possibleComboBox.removeAllItems();
                        possibleComboBox.addItem("None");
                        possibleComboBox.setEditable(false);
                        questionPanel.repaint();
                    }

                }
            }
        }

        questionTextField.setPreferredSize(new Dimension(400, 20));
        JPanel panel = new JPanel();
        panel.add(questionLabel);
        panel.add(questionTextField);

        JPanel totalPanel = new JPanel();
        JPanel enterPanel = new JPanel();
        JButton enterButton = new JButton("Enter");

        ActionListener enterListener = new EnterListener();
        enterButton.addActionListener(enterListener);

        enterPanel.setLayout(new GridLayout(1,4));
        enterPanel.add(new JPanel());
        enterPanel.add(new JPanel());
        enterPanel.add(new JPanel());
        enterPanel.add(enterButton);

        totalPanel.setLayout(new GridLayout(2,1));
        totalPanel.add(panel);
        totalPanel.add(enterPanel);
        return totalPanel;
    }

    /**
     * Creates panel for the possible question combo box
     * @return the panel that will show all the possible panel
     */
    public JPanel createComboBoxPanel()
    {
        JLabel label = new JLabel("Possible Answer");
        possibleComboBox = new JComboBox();
        possibleComboBox.setPreferredSize(new Dimension(400, 20));
        possibleComboBox.setEditable(false);
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(possibleComboBox);
        return panel;
    }

    /**
     * Create a text area panel that will display the answer of the question
     * @return the text area panel
     */
    public JPanel createTextAreaPanel()
    {
        textArea = new JTextArea();
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Answer"));
        panel.add(textArea);
        return panel;
    }


    /**
     * This class sets the listener that will exit the program when the user clicks
     */
    public class EndListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }

    /**
     * This class sets the listener that will send the email when the user clicks
     */
    public class SendEmailListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            JOptionPane.showMessageDialog(null, "Email Sent");

            final String password = "16955";
            final String username = "ywang5197@tcusd.net";
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
                        InternetAddress.parse("ywang254@go.pasadena.edu")
                );
                message.setSubject("Question");
                message.setText("Student Name: " + firstName + " " + lastName + "\n" + "Email: " + email + "\n" + "ID: "
                + ID + "\n" + "Question: " + question);

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method will create a panel for the user to choose to choose to send the email or end the program
     * @return
     */
    public JPanel createButtonPanel()
    {
        JButton endButton = new JButton("End");
        ActionListener listener = new EndListener();
        endButton.addActionListener(listener);

        JButton sendEmailButton = new JButton("SendEmail");
        ActionListener sendListener = new SendEmailListener();
        sendEmailButton.addActionListener(sendListener);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,4));
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(sendEmailButton);
        panel.add(endButton);
        return panel;
    }

    /**
     * Returns the question panel
     * @return the question Panel
     */
    public JPanel getQuestionPanel()
    {
        return questionPanel;
    }
}
