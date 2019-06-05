package questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Concrete class MultipleChoiceQustion. Subclass of Question.
 */
public class MultipleChoiceQuestion extends Question<Number>
{
	private String question;
	private ArrayList<String> answer;
	
	/*
	 * Constructor: question and answer are passed as arguments
	 */
	public MultipleChoiceQuestion(String q, ArrayList<String> ans)
	{
		question = q;
		answer = ans;
	}
	
	public String get() {
		return("Question:\n" + question + "\nAnswer:\n" + answer);
	}
	
	/*
	 * Method askQuestion overrides the abstract method in Question class.
	 * Prints the question 
	 */
	public void askQuestion()
	{
		System.out.println(question + "\nChoose all the options that apply (In the following format: A B C). Your answer: ");
	}
	
	/*
	 * Method getScore overrides the abstract method in Question class.
	 * Returns the value between 0 and 1 (depends on the number of correct/incorrect answers).
	 */
	public Double getScore(String userChoice)
	{
		int counterCorrect = 0;
		int counterWrong = 0;
		double score = 0;
		
		// Check if the user skipped the question. Return 0 if it's true.
		if (skipQuestion(userChoice)) 
		{
			return (double) 0;
		}
		
		// Split the user's input by space and store it in the items array
		String[] items = userChoice.split(" ");
		// itemList is a list that contains user's input/answer
		List<String> itemList = Arrays.asList(items);
		// Iterate through the itemList and check the answer
		for (int i = 0; i <= itemList.size() - 1; i++)
		{
			// If the answer is correct, increase the number of correct options (counterCorrect variable)
			if (answer.contains(itemList.get(i))) {
				counterCorrect++;
			}
			// If the answer is incorrect, increase the number of incorrect options (counterWrong variable)
			else counterWrong++;
		}
			// Calculate the score (max. 1) for the question using this formula:
			// One correct answer weight is (1/(the number of correct options)). One wrong option reduces the score by 0.1
			score = counterCorrect * ((1 / (double)answer.size())) - counterWrong * 0.1;
			// If score for the question drops below 0, return 0
			if (score <= 0) 
			{
				score = 0;
			}

	// Return the total score for the question
	return score;	
	}
	
	/*
	 * Method getSummary overrides the abstract method in Question class.
	 * Returns the summary as a String for a particular question and user's answer to that question.
	 */
	public String getSummary(String userChoice)
	{
		String summary = "";
		return summary += question + "\nMultiple-Choice Question\n" + "Correct answer(s): " + answer + ". Your answer(s): " + userChoice + "\n" + Double.toString(getScore(userChoice)) + "/1 points earned.\n";
	}
}
