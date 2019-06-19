package questions;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Formatter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.stream.Stream;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {

	/*
	 * main() method to display the QuizPro's main menu, read data from the file, run the quiz and write the result and the summary to the output file.
	 * Alternatively, the program exits if the user is willing to do so.
	 */
	public static void main(String[] args)
	{
	
		// Run the program in the loop
		while (true){
			try {
				// Greet the user and select the quiz
				String nameOfTheQuiz = greeting();
				if (!nameOfTheQuiz.equals("add.dat"))
				{
					startAndGradeTheQuiz(nameOfTheQuiz);	
				}
				else main(args);
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
	public static void writeToFile(String result, String summary, String fileName)
	{
		Formatter outfile = null;
		
		try
		{
			outfile = new Formatter(fileName); // open file
		}
		catch (FileNotFoundException ex)
		{
			System.err.println("Cannot open file ... quitting");
		}
		// Write to the file: result and summary
		outfile.format(result, null);
		outfile.format(summary, null);
		outfile.close();
		System.out.println("The result is written to the file "+ fileName + ". Thank you for your time!\n");
	}
	
	
	/*
	 * readFromFile() method to read data as objects from the object file
	 * Questions are read either as a single choice question, as a multiple-choice question, or as an open-ended question
	 */
	public static ArrayList<Question> readFromFile(String input) throws IOException
	{
	       ArrayList<Question> str = new ArrayList<>();
	       int i = 0;
	   		try (ObjectInputStream binaryInfile = new ObjectInputStream(new FileInputStream(input));)      
		    {
				while (true)
				{
					str.add((((GenericQuestion<? extends Question>) (binaryInfile.readObject())).get()));
	
				} 	

	}
	   
	catch (EOFException ex)
    {
        //System.out.println("EOF reached in binary.dat");    
    }
	catch (FileNotFoundException ex)
    {
        System.out.println("FileNotFoundException"); 
        ex.printStackTrace();   
    }

    catch (IOException ex)
    {
        System.out.println("IOException");
        ex.printStackTrace();    
    }
	
	catch (ClassNotFoundException ex)
    {
        System.out.println("ClassNotFoundException");
        ex.printStackTrace();    
    }
	   		// Example of using streams and lambdas
	   		// Create the stream of the questions in the quiz
	   		Stream<Question> questionsStream = str.stream();
	   		// Display the number of questions in the quiz
	   		System.out.println("Number of questions in the selected quiz:  " + str.stream().count());
	   		System.out.print("Question types in the selected quiz are: ");
	   		System.out.println();
	   		// Display the type of questions in the quiz
	   		questionsStream.forEach(s -> System.out.println(s.getClass()));
	        System.out.println();
	        System.out.println("Good luck!\n");
	
	return str;
	}

	/*
	 * gradeTheQuiz() method to run the quiz and write the result and the summary to the output file "result.txt"
	 */
	public static double startAndGradeTheQuiz(String quizName) throws IOException {
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
		writeToFile(result, summaryString, "result.txt");
		return totalScore;
	}
	
	
	/*
	 * greeting() method to greet the user, display all the available quizzes from 'quizzes.txt', 
	 * and prompt the user to select the quiz by typing it (or exit)
	 */
	public static String greeting() throws FileNotFoundException, EmptyFileException {
		System.out.println("\nWelcome to QuizPro!\n");//  \nAvailable quizzes are: \n");
		System.out.println("Please enter your name:");
		Scanner reader = new Scanner(System.in);
		Scanner infile = new Scanner(new File("quizzes.txt")); // Open the file
//		// Check whether quizzes file is empty
//		File quizzesFile = new File("quizzes.txt");
//			if (quizzesFile.length() == 0) 
//			{
//				infile.close();
//				throw new EmptyFileException("File '" + quizzesFile.getName() + "' is empty!\nAdd quizzes first, then restart the program.\nBye!");
//			}
//		// Display all the available quizzes
//		while (infile.hasNext())
//		{
//			System.out.println(infile.nextLine());
//		}
//		infile.close(); // Close the file
		
		//DatabaseAssignment.go(null);
		
//		System.out.println("\nPlease enter your name:");
//		Scanner reader = new Scanner(System.in);
		
		String userName = reader.next(); // Record the user name
		System.out.println("User's name is " + userName);
		
		String programmerBoolean = "";
		System.out.println("Are you a programmer? (type 1 for 'yes and 0 for 'no')");
		programmerBoolean = reader.next();
		
		
		System.out.println("\nAvailable quizzes are:\n");
		//
		// Check whether quizzes file is empty
		File quizzesFile = new File("quizzes.txt");
			if (quizzesFile.length() == 0) 
			{
				infile.close();
				throw new EmptyFileException("File '" + quizzesFile.getName() + "' is empty!\nAdd quizzes first, then restart the program.\nBye!");
			}
		// Display all the available quizzes
		int quizNumber = 0;
		ArrayList<String> quizN = new ArrayList();
		String quizNam = "";
		while (infile.hasNext())
		{
			quizNumber++;
			quizNam = infile.nextLine();
			System.out.println(quizNumber + " " + quizNam);
			quizN.add(quizNam);
		}
		infile.close(); // Close the file
		//
		System.out.println("\nPlease type the quiz number you'd like to try \nOR\ntype 'Exit' to exit\nOR\nType 'Add' to add your own quiz\nOR\nType 'LB' to open Leaderboard ('LBJAVA' for leaderboard on java quiz):");
		// Prompt user to type the quiz name OR exit OR add the quiz
//		Scanner reader = new Scanner(System.in);
		String quizChosen = reader.next(); // Record the quiz number
		if (quizChosen.toLowerCase().equals("exit"))
		{
			return "exit" + " " + "exit" + " " + "exit";
		}
		else if (quizChosen.toLowerCase().equals("add"))
		{
			GenericQuestion.addQuiz();
			return "add" + " " + "add" + " " + "add";
		}
		else if (quizChosen.toLowerCase().equals("lb"))
		{
			return "lb" + " " + "lb" + " " + "lb";
		}
		else if (quizChosen.toLowerCase().equals("lbjava"))
		{
			return "lbjava" + " " + "lbjava" + " " + "lbjava";
		}
		quizChosen = quizN.get(Integer.parseInt(quizChosen) - 1);
		quizChosen = quizChosen.toLowerCase();
		System.out.println("Chosen quiz is " + quizChosen);

		// Add the extension '.dat' to the file name
		quizChosen += ".dat";
		// Return the file name (of the quiz)
		return quizChosen + " " + userName + " " + programmerBoolean;
	}
}
