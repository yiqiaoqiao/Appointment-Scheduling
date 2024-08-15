import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class reads from a file and saves all the faculty names
 */
public class AppointmentFile
{
    private String fileName;
    private ArrayList<Faculty> faculties;
    private ArrayList<String> saveCopy;

    /**
     * This constructor saves the file name and initialize the faculty and copy arrayList
     * @param major the major that the user chose
     */
    public AppointmentFile(String major)
    {
        if(major.equals("CS"))
            fileName = "CSAppt.txt";
        else
            fileName = "MathAppt.txt";

        faculties = new ArrayList<>();
        saveCopy = new ArrayList<>();
        readFile();
    }

    /**
     * Reads the file and save it into the faculty array list
     */
    public void readFile()
    {
        boolean isStore = false;
        int position = 0;
        try(Scanner in = new Scanner(new File(fileName)))
        {
            while (in.hasNextLine())
            {
                String line = in.nextLine();
                saveCopy.add(line);
                String[] parts = line.split(" ");
                if(parts.length == 3)
                {
                    for(int i = 0; i < faculties.size(); i++)
                    {
                        if(faculties.get(i).getFirstName().equals(parts[0]) && faculties.get(i).getLastName().equals(""))
                        {
                            isStore = true;
                            position = i;
                            break;
                        }
                    }
                    if(isStore)
                        faculties.get(position).setTime(parts[2], parts[3]);
                    else
                        faculties.add(new Faculty("Dean", "", parts[1], parts[2]));
                }
                else if(parts.length == 4)
                {
                    for(int i = 0; i < faculties.size(); i++)
                    {
                        if(faculties.get(i).getFirstName().equals(parts[0]) && faculties.get(i).getLastName().equals(parts[1]))
                        {
                            isStore = true;
                            position = i;
                            break;
                        }
                    }
                    if(isStore)
                        faculties.get(position).setTime(parts[2], parts[3]);
                    else
                        faculties.add(new Faculty(parts[0], parts[1], parts[2], parts[3]));
                }
                else
                {
                    for(int i = 0; i < faculties.size(); i++)
                    {
                        if(faculties.get(i).getFirstName().equals("Chair of Department"))
                        {
                            isStore = true;
                            position = i;
                            break;
                        }
                    }
                    if(isStore)
                        faculties.get(position).setTime(parts[3], parts[4]);
                    else
                    {
                        faculties.add(new Faculty("Chair of Department", "", parts[3], parts[4]));
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    /**
     * Returns the available time that this faculty has
     * @param names the name of the faculty
     * @param dayOfWeek the day that wants to check availability
     * @return the array list of integer of time
     */
    public ArrayList<Integer> getAvailableTime(String names, String dayOfWeek)
    {
        ArrayList<Integer> availableTime;
        String faculty = names;
        if(names.equals("Dean") || names.equals("Chair of Department"))
            faculty = names + " ";
        for(int i = 0; i < faculties.size(); i++)
        {
            if(faculties.get(i).getName().equals(faculty))
            {
                availableTime = faculties.get(i).getAvailableTime(dayOfWeek);
                return availableTime;
            }
        }

        if(names.equals("Dean") || names.equals("Chair of Department"))
        {
            faculties.add(new Faculty(names, "", dayOfWeek, "0"));
        }
        else
        {
            String[] splitNames = names.split(" ");
            faculties.add(new Faculty(splitNames[0], splitNames[1], dayOfWeek, "0"));
        }

        availableTime = faculties.get(faculties.size() - 1).getAvailableTime(dayOfWeek);
        return availableTime;
    }

    /**
     * Make the appointment with a faculty
     * @param name the name of the faculty
     * @param dayOfWeek the day of the appointment
     * @param time the time of the appointment
     */
    public void makeAppointment(String name, String dayOfWeek, String time)
    {
        boolean setAppt = false;
        for(int i = 0; i < faculties.size(); i++)
        {
            if (faculties.get(i).getName().equals(name))
            {
                faculties.get(i).setTime(dayOfWeek, time);
                setAppt = true;
            }
        }

        if(!setAppt)
        {
            if(name.equals("Dean") || name.equals("Chair of Department"))
            {
                faculties.add(new Faculty(name, "", dayOfWeek, time));
            }
        }

        writeFile(name, dayOfWeek, time);
    }

    /**
     * Writes everything to the file
     * @param name the name of the last line writing to the file
     * @param dayOfWeek the name of the last line writing to the file
     * @param time the name of the last line writing to the file
     */
    public void writeFile(String name, String dayOfWeek, String time)
    {
        try (PrintWriter out = new PrintWriter(fileName))
        {
            for(int i = 0; i < saveCopy.size(); i++)
            {
                out.println(saveCopy.get(i));
            }
            out.println(name + " " + dayOfWeek + " " + time.charAt(0));
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Missing output File");
        }
    }
}
