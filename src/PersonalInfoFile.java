import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

/**
 * This class reads the file of Personal Information file
 */
public class PersonalInfoFile
{
    private ArrayList<String> IDList;
    private ArrayList<String> firstNameList;
    private ArrayList<String> lastNameList;
    private ArrayList<String> emailList;
    private final String fileName = "PersonalInfo.txt";
    private String firstName;
    private String lastName;
    private String email;

    /**
     * This constructor sets the personal info file class
     */
    public PersonalInfoFile()
    {
        IDList = new ArrayList<>();
        firstNameList = new ArrayList<>();
        lastNameList = new ArrayList<>();
        emailList = new ArrayList<>();
        firstName = "";
        lastName = "";
        try {
            fillArrayList();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    /**
     * This method fills the array from the file
     * @throws FileNotFoundException
     * @throws NoSuchElementException
     */
    public void fillArrayList() throws FileNotFoundException, NoSuchElementException
    {
        try (Scanner in = new Scanner(new File(fileName)))
        {
            while (in.hasNextLine())
            {
                String ID = in.next();
                IDList.add(ID);
                firstNameList.add(in.next());
                lastNameList.add(in.next());
                emailList.add(in.next());
            }
        }
        catch (NoSuchElementException e)
        {
            System.out.println("No Such Element");
        }
    }

    /**
     * This method sets the names and ID to the list
     * @param ID
     * @param firstName
     * @param lastName
     * @throws FileNotFoundException
     */
    public void setPersonalInfo(String ID, String firstName, String lastName, String email) throws FileNotFoundException
    {
        IDList.add(ID);
        firstNameList.add(firstName);
        lastNameList.add(lastName);
        emailList.add(email);
        try (PrintWriter out = new PrintWriter(fileName))
        {
            for(int i = 0 ; i < emailList.size(); i++)
            {
                out.print(IDList.get(i) + " ");
                out.print(firstNameList.get(i) + " ");
                out.print(lastNameList.get(i) + " ");
                out.print(emailList.get(i));
                out.println();
            }
        }
    }

    /**
     * This methods finds the name by the ID
     * @param ID
     */
    public boolean findNames(String ID)
    {
        for(int i = 0; i < firstNameList.size(); i++)
        {
            if(ID.equals(IDList.get(i)))
            {
                firstName = firstNameList.get(i);
                lastName = lastNameList.get(i);
                email = emailList.get(i);
                return true;
            }
        }
        return false;
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
}
