import java.util.ArrayList;

/**
 * This class saves the faculty and the time
 */
public class Faculty
{
    private ArrayList<Date> dates;
    private String firstName;
    private String lastName;

    /**
     * Initialize the name, last name, day of week and the time that has the appointment
     * @param firstName the first name of the faculty
     * @param lastName the last name of the faculty
     * @param dayOfWeek the day that the faculty has the appointment
     * @param time the time that the faculty has the appointment
     */
    public Faculty(String firstName, String lastName, String dayOfWeek, String time)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        dates = new ArrayList<>();
        fillArrayList();
        setTime(dayOfWeek, time);
    }

    /**
     * Fill the array list with 5 days
     */
    public void fillArrayList()
    {
        dates.add(new Date("Monday"));
        dates.add(new Date("Tuesday"));
        dates.add(new Date("Wednesday"));
        dates.add(new Date("Thursday"));
        dates.add(new Date("Friday"));
    }

    /**
     * Set the time for this faculty
     * @param dayOfWeek the day that the faculty will have appointment
     * @param time the time that the faculty will have appointment
     */
    public void setTime(String dayOfWeek, String time)
    {
        String timeStr = "" + time.charAt(0);
        if(time.length() > 1)
        {
            if(Character.isDigit(time.charAt(1)))
            {
                timeStr += time.charAt(1);
            }
        }
        int timeInt = Integer.parseInt(timeStr);
        for(int i = 0; i < dates.size(); i++)
        {
            if(dates.get(i).getDayOfWeek().equals(dayOfWeek))
            {
                dates.get(i).setTime(timeInt);
            }
        }
    }

    /**
     * Returns the available time for this faculty
     * @param dayOfWeek the day that wants to check
     * @return the time array list
     */
    public ArrayList<Integer> getAvailableTime(String dayOfWeek)
    {
        ArrayList<Integer> times = new ArrayList<>();
        for(int i = 0; i < dates.size(); i++)
        {
            if(dayOfWeek.equals(dates.get(i).getDayOfWeek()))
            {
                times = dates.get(i).getAvailableTime();
            }
        }
        return times;
    }

    /**
     * Return the first name of the faculty
     * @return the first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Gets the last name of the faculty
     * @return the last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Gets the full name of the faculty
     * @return the full name
     */
    public String getName()
    {
        return (firstName + " " + lastName);
    }

}
