package questions;


/*
 * Concrete class OpenEndedQustion. Subclass of Question.
 */
public class OpenEndedQuestion extends Question 
{
	private String question;
	private String answer;
	
	
	/*
	 * Constructor: question and answer are passed as arguments
	 */
	public OpenEndedQuestion(String q, String ans)
	{
		question = q;
		answer = ans;
	}
	
	/*
	 * Method askQuestion overrides the abstract method in Question class.
	 * Prints the question 
	 */
	public void askQuestion()
	{
		System.out.println(question + "\nAnswer using your own words. Your answer: ");
	}
	
	/*
	 * Method getScore overrides the abstract method in Question class.
	 * Returns either 0 or 1, as long as the answer is only one.
	 */
	public String getScore(String userChoice)
	{
		// Check if the user skipped the question. Return 0 if it's true.
		if (skipQuestion(userChoice)) 
		{
			return "You skipped the question";
		}
		
		// Check if the answer is given. 
		else if (!userChoice.isEmpty()){
			return "Awaits grading";
		}
		else return "No answer was given";
	}
	
	/*
	 * Method getSummary overrides the abstract method in Question class.
	 * Returns the summary as a String for a particular question and user's answer to that question.
	 */
	public String getSummary(String userChoice)
	{
		String summary = "";
		return summary += question + "\nOpen-Ended Question\n" + "Your answer: " + userChoice + "\n" + getScore(userChoice) + "\n";
	}
}

