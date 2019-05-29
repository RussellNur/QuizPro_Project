package questions;

import java.util.Scanner;

/*
 * Abstract class Question
 */
public abstract class Question {
	
	/*
	 * Abstract method to calculate the score for the quiz (overridden in the subclasses)
	 */
	public abstract double getScore(String userChoice);
	
	/*
	 * Abstract method to collect the summary for the quiz (overridden in the subclasses)
	 */
	public abstract String getSummary(String userChoice);
	
	/*
	 * Abstract void method to ask the question (overridden in the subclasses)
	 */
	public abstract void askQuestion();
	
	/* 
	 * Concrete method to skip the question
	 */
	public static boolean skipQuestion(String skip)
	{
		if (skip.equals("SKIP")) {
			return true;
		}
		else return false;
	}
	
	/*
	 * Concrete method to prompt the user for the answer and return that answer
	 */
	public String userAnswer()
	{
		Scanner reader = new Scanner(System.in);
		String userChoice = reader.nextLine();
		userChoice = userChoice.toUpperCase();
		return userChoice; // String userChoice is returned
	}
	
}
