package questions;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Formatter;
import java.io.FileNotFoundException;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;

public class Main {

	/*
	 * main() method to display the QuizPro's main menu, read data from the file, run the quiz and write the result and the summary to the output file.
	 * Alternatively, the program exits if the user is willing to do so.
	 */
	public static void main(String[] args)
	{
		// Run the program in the loop
		boolean running = true;
		while (running == true){
			try {
				// Greet the user and select the quiz
				String nameOfTheQuiz = greeting();
				startAndGradeTheQuiz(nameOfTheQuiz);		
			} 
			
			catch (EmptyFileException ex) {
				System.out.println(ex.getMessage());
				System.exit(0);
				ex.printStackTrace();
			}
			
			catch (IOException e1) {
				System.out.println("There was a mistake. Try to run the program again.");
				e1.printStackTrace();
			} 

			
	  }
	}
	/*
	 * writeToFile(String, String) method to write the result and the summary to the file
	 */
	public static void writeToFile(String result, String summary)
	{
		Formatter outfile = null;
		
		try
		{
			outfile = new Formatter("result.txt"); // open file
		}
		catch (FileNotFoundException ex)
		{
			System.err.println("Cannot open file ... quitting");
		}
		// Write to the file: result and summary
		outfile.format(result, null);
		outfile.format(summary, null);
		outfile.close();
		System.out.println("The result is written to the file 'result.txt'. Thank you for taking the quiz!\n");
	}
	
	
	/*
	 * readFromFile() method to read data from the file
	 * Questions are read either as a single choice question or as a multiple-choice question. 
	 * Corresponding answers are read as well
	 */
	public static ArrayList<Question> readFromFile(String input) throws IOException
	{
	       Scanner infile = new Scanner(new File(input)); 
	       ArrayList<String> arrayFromFile = new ArrayList<>();
	       ArrayList<Question> str = new ArrayList<>();
	       while (infile.hasNext())
	       {    
	    	   arrayFromFile.add(infile.nextLine());
	       }
	       int i = 0;
	       // Iterate through the array
	       for (int j = 0; j <= arrayFromFile.size() - 1; j = j + 2) {
	    	   // For a single choice question
	    	   if (arrayFromFile.get(j + 1).length() == 1) {
	    		   str.add(i, new SingleChoiceQuestion(arrayFromFile.get(j), arrayFromFile.get(j + 1)));
	    		   i++;
	    	   }
	    	   // For multiple choice question
	    	   else if (arrayFromFile.get(j + 1).equals("OEQ")) {
	    		   str.add(i, new OpenEndedQuestion(arrayFromFile.get(j), arrayFromFile.get(j + 1)));
	    		   i++;
	    	   }
	    	   else {
	    			ArrayList<String> q1 = new ArrayList<String>();
	    			// Split the correct answers
	    			String[] splitted = arrayFromFile.get(j + 1).split(" ");
	    			// Add answers to q1
	    			for (int k = 0; k <= splitted.length - 1; k++) {
	    				q1.add(splitted[k]);
	    			}
	    		   str.add(i, new MultipleChoiceQuestion(arrayFromFile.get(j), q1));
	    		   i++;
	    	   }
	       }
	       infile.close();  // close the file
	       // Return ArrayList of Question objects
	       return str;
	   }     
	
	/*
	 * gradeTheQuiz() method to run the quiz and write the result and the summary to the output file "result.txt"
	 */
	public static void startAndGradeTheQuiz(String quizName) throws IOException {
		double totalScore = 0; // totalScore for the quiz
		// Summary String for the quiz
		String summaryString = "\n-----------------\nSUMMARY\n-----------------\n";
		String result = null;
		// Used to mark the Question (Q) number in the summary (e.g. Q5)
		int questionCounter = 1;
		boolean needsGradingCondition = false;
		ArrayList<Question> questionsArr = new ArrayList<>(); 
		questionsArr = readFromFile(quizName);
		for (Question elem : questionsArr)
		{	
			// Check whether the element is not null, and it is the instance of SingleChoiceQuestion class
			if (elem != null && elem instanceof SingleChoiceQuestion)
			{
				// ask the question
				((SingleChoiceQuestion)elem).askQuestion(); 
				// Prompt user for the answer
				String userChoice = elem.userAnswer(); 
				// Add score for the question to the totalScore
				totalScore = totalScore + ((SingleChoiceQuestion)elem).getScore(userChoice); 
				// Add summary for the question to the summaryString
				summaryString = summaryString + "\nQ" + Integer.toString(questionCounter) +
						". " + ((SingleChoiceQuestion)elem).getSummary(userChoice);
				// Increase questionCounter by 1
				questionCounter++;
				
			}
			
			// Check whether the element is not null, and it is the instance of MultipleChoiceQuestion class
			else if (elem != null && elem instanceof MultipleChoiceQuestion)
			{
				// ask the question
				((MultipleChoiceQuestion)elem).askQuestion();
				// Prompt user for the answer
				String userChoice = elem.userAnswer();
				// Add score for the question to the totalScore
				totalScore = totalScore + ((MultipleChoiceQuestion)elem).getScore(userChoice);
				// Add summary for the question to the summaryString
				summaryString = summaryString + "\nQ" + Integer.toString(questionCounter) +
						". " + ((MultipleChoiceQuestion)elem).getSummary(userChoice);
				// Increase questionCounter by 1
				questionCounter++;
			}
			else if (elem != null && elem instanceof OpenEndedQuestion)
			{
				// ask the question
				((OpenEndedQuestion)elem).askQuestion();
				// Prompt user for the answer
				String userChoice = elem.userAnswer();
				// Add score for the question to the totalScore if the answer is blank or skipped
				if (((OpenEndedQuestion)elem).getScore(userChoice).equals("No answer was given") || ((OpenEndedQuestion)elem).getScore(userChoice).equals("You skipped the question")) 
				{
					// Add summary for the question to the summaryString
					summaryString = summaryString + "\nQ" + Integer.toString(questionCounter) +
							". " + "0";
					// Increase questionCounter by 1
					questionCounter++;
				}
				else {
					// Add summary for the question to the summaryString
					summaryString = summaryString + "\nQ" + Integer.toString(questionCounter) +
							". " + ((OpenEndedQuestion)elem).getSummary(userChoice);
					// Increase questionCounter by 1
					questionCounter++;
					needsGradingCondition = true;
				}

			}
			// Iterate through the questions in the quiz

			// Print the result
			result =  ("-----------------\nRESULT\n-----------------\n" + "Total score is: " + totalScore + " out of " + questionsArr.size());
			if (needsGradingCondition == true) { 
				result += "\n!!!\nGrade might change due to the fact that some Open-Ended Questions need grading."; 
			}
		}
		//System.out.println(result);
		// Print the summary
		//System.out.println(summaryString);
		System.out.println(result);
		System.out.println(summaryString);
		writeToFile(result, summaryString);
	}
	
	
	/*
	 * greeting() method to greet the user, display all the available quizzes from 'quizzes.txt', 
	 * and prompt the user to select the quiz by typing it (or exit)
	 */
	public static String greeting() throws FileNotFoundException, EmptyFileException {
		System.out.println("\nWelcome to QuizPro!\nAvailable quizzes are: \n");
		Scanner infile = new Scanner(new File("quizzes.txt")); // Open the file
		// Check whether quizzes file is empty
		File quizzesFile = new File("quizzes.txt");
			if (quizzesFile.length() == 0) 
			{
				infile.close();
				throw new EmptyFileException("File '" + quizzesFile.getName() + "' is empty!\nAdd quizzes first, then restart the program.\nBye!");
			}
		// Display all the available quizzes
		while (infile.hasNext())
		{
			System.out.println(infile.nextLine());
		}
		infile.close(); // Close the file
		System.out.println("\nPlease type the quiz you'd like to try or type 'Exit' to exit:");
		// Prompt user to type the quiz name or exit
		Scanner reader = new Scanner(System.in);
		String quizChosen = reader.nextLine();
		quizChosen = quizChosen.toLowerCase();
		if (quizChosen.equals("exit"))
		{
			System.out.println("Thank you for using QuizPro!");
			System.exit(0);
		}
		// Add the extension '.txt' to the file name
		quizChosen += ".txt";
		// Return the file name (of the quiz)
		return quizChosen;
	}
}

	

