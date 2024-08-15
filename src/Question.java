import java.util.ArrayList;
import java.util.Collections;

/**
 * This class sets the question in a file
 */
public class Question
{
    private ArrayList<String> keyWords;
    private String question;
    private String answer;

    /**
     * Saves the question and the answer
     * @param question the question that is asked
     * @param answer the answer for the question
     */
    public Question(String question, String answer)
    {
        this.question = question;
        this.answer = answer;
        keyWords = new ArrayList<>();
        String[] keyWord;
        keyWord = question.split(" ");
        Collections.addAll(keyWords, keyWord);
        checkKeyWords();
    }

    /**
     * Saves the keywords
     */
    public void checkKeyWords()
    {
        int count = keyWords.size();
        for(int i = 0; i < count; i++)
        {
            if(keyWords.get(i).equals("a") || keyWords.get(i).equals("the") || keyWords.get(i).equals("am") || keyWords.get(i).equals("is")
            || keyWords.get(i).equals("are") || keyWords.get(i).equals("of") || keyWords.get(i).equals("as") || keyWords.get(i).equals("for")
            || keyWords.get(i).equals("in") || keyWords.get(i).equals("on") || keyWords.get(i).equals("to") || keyWords.get(i).equals("before")
            || keyWords.get(i).equals("have") || keyWords.get(i).equals("has") || keyWords.get(i).equals("or") || keyWords.get(i).equals("and"))
            {
                keyWords.remove(i);
                count--;
            }
        }
    }

    /**
     * Gets the question
     * @return the question
     */
    public String getQuestion()
    {
        return question;
    }

    /**
     * Gets the answer
     * @return the answer
     */
    public String getAnswer()
    {
        return answer;
    }

    /**
     * Gets the key words of the question
     * @return the key words array list
     */
    public ArrayList<String> getKeyWords()
    {
        return keyWords;
    }
}
