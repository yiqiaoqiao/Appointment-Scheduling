import java.util.ArrayList;

/**
 * This class saves the time of a day that a day for a faculty
 */
public class Date
{
    private String dayOfWeek;
    private ArrayList<Integer> times;

    /**
     * This constructor saves the hours in an array list
     * @param dayOfWeek the day of the week that the day is saving
     */
    public Date(String dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;
        times = new ArrayList<>();
        fillTimes();
    }

    /**
     * Fill array with hours from 8-17
     */
    public void fillTimes()
    {
        int time = 8;
        for(int i = 0; i < 10; i++)
        {
            times.add(time);
            time++;
        }
    }

    /**
     * Delete the time because making the appointment
     * @param time the time that will have an appointment
     */
    public void setTime(int time)
    {
        for(int i = 0; i < times.size(); i++)
        {
            if(times.get(i) == time)
            {
                times.remove(i);
                break;
            }
        }
    }

    /**
     * The available time in a day of week
     * @return times arrayList
     */
    public ArrayList<Integer> getAvailableTime()
    {
        return times;
    }

    /**
     * Get the day of week that is saving
     * @return the day of week
     */
    public String getDayOfWeek()
    {
        return dayOfWeek;
    }
}
