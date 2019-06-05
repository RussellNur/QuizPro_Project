package questions;

/*
 * Concrete class SingleChoiceQustion. Subclass of Question.
 */
public class SingleChoiceQuestion extends Question<Number>
{
	private String question;
	private String answer;

	/*
	 * Constructor: question and answer are passed as arguments
	 */
	public SingleChoiceQuestion(String q, String ans)
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
		System.out.println(question + "\nChoose only one (!) option. Your answer: ");
	}
	
	/*
	 * Method getScore overrides the abstract method in Question class.
	 * Returns either 0 or 1, as long as the answer is only one.
	 */
	public Integer getScore(String userChoice)
	{
		// Check if the user skipped the question. Return 0 if it's true.
		if (skipQuestion(userChoice)) 
		{
			return 0;
		}
		
		// Check if the answer is correct and return 1 if true, 0 if false
		if (userChoice.equals(answer)) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	/*
	 * Method getSummary overrides the abstract method in Question class.
	 * Returns the summary as a String for a particular question and user's answer to that question.
	 */
	public String getSummary(String userChoice)
	{
		String summary = "";
		return summary += question + "\nSingle Choice Question\n" + "Correct answer: " + answer + ". Your answer: " + userChoice + "\n" + getScore(userChoice) + "/1 points earned.\n";
	}
	
}
