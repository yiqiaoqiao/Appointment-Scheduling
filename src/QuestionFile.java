import java.io.FileNotFoundException;
import java.io.File;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class reads the question file
 */
public class QuestionFile
{
    private String fileName;
    private ArrayList<Question> questions;
    private ArrayList<String> possibleQuestionLists;
    private ArrayList<String> possibleAnswers;
    private String exactQuestion;
    private String answer;

    /**
     * This constructor reads the questions and save it into question array list
     * @param fileName the file name that will be open
     */
    public QuestionFile(String fileName) {
        this.fileName = fileName;
        questions = new ArrayList<>();
        possibleAnswers = new ArrayList<>();
        possibleQuestionLists = new ArrayList<>();
        getQuestions();
    }

    /**
     * Gets all the questions from the file
     */
    public void getQuestions()
    {
        try(Scanner in = new Scanner(new File(fileName)))
        {
            String ans = "";
            while (in.hasNextLine())
            {
                String question = in.nextLine();
                question = question.trim();
                if(question.contains("?"))
                    question = question.substring(0, question.length() - 1);
                question = question.toLowerCase();
                if(in.hasNextLine()) {
                    ans = in.nextLine();
                }
                questions.add(new Question(question, ans));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    /**
     * Check if there's exact match
     * @param userQuestion the question that the user choose
     * @return true/false
     */
    public boolean haveExactQuestion(String userQuestion)
    {
        userQuestion = userQuestion.trim();
        if(userQuestion.contains("?"))
            userQuestion = userQuestion.substring(0, userQuestion.length() - 1);
        userQuestion = userQuestion.toLowerCase();
        for(int i = 0; i < questions.size(); i++)
        {
            if(userQuestion.equals(questions.get(i).getQuestion()))
            {
                exactQuestion = userQuestion;
                answer = questions.get(i).getAnswer();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there's a possible question to it
     * @param userQuestion the question that the user asks
     * @return true/ false
     */
    public boolean findPossibleQuestions(String userQuestion)
    {
        ArrayList<String> userKeyWords;
        userKeyWords = findKeyWords(userQuestion);

        int count;
        for(int i = 0; i < questions.size(); i++)
        {
            ArrayList<String> questionKeyWords = questions.get(i).getKeyWords();
            count = 0;
            int desiredMatch;
            desiredMatch = userKeyWords.size() / 2;

            for(int j = 0; j < questionKeyWords.size(); j++)
            {
                for(int k = 0; k < userKeyWords.size(); k++)
                {
                    if(questionKeyWords.get(j).equals(userKeyWords.get(k)))
                    {
                        count++;
                        break;
                    }
                }

                if(count == desiredMatch)
                {
                    possibleQuestionLists.add(questions.get(i).getQuestion());
                    possibleAnswers.add(questions.get(i).getAnswer());
                    break;
                }
            }
        }

        if(possibleQuestionLists.size() >= 1)
            return true;
        else
            return false;
    }

    /**
     * Finds all the key words for the suer question
     * @param userQuestion the question that the user asks
     * @return the array list of key words
     */
    public ArrayList<String> findKeyWords(String userQuestion)
    {
        userQuestion.trim();
        userQuestion = userQuestion.toLowerCase();
        String[] words;
        words = userQuestion.split(" ");
        ArrayList<String> keyWords = new ArrayList<>();
        int count = keyWords.size();
        Collections.addAll(keyWords, words);
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
        return keyWords;
    }

    /**
     * Gets the answer of the question
     * @return the answer
     */
    public String getAnswer()
    {
        return answer;
    }

    /**
     * Gets the exact question
     * @return the exact question
     */
    public String getExactQuestion()
    {
        return exactQuestion;
    }

    /**
     * Gets the possible question that might be what the user asks
     * @return the possible question array list
     */
    public ArrayList<String> getPossibleQuestionLists()
    {
        return possibleQuestionLists;
    }

    /**
     * Gets the answers for those possible question
     * @return the possible answer Array list
     */
    public ArrayList<String> getPossibleAnswers()
    {
        return possibleAnswers;
    }
}
