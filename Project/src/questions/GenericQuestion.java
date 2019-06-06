package questions;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/*
 * Generic class Generic question which is bounded to be the subclass of the Question Class
 */
public class GenericQuestion <T extends Question>{
	// Constructor includes variable question - which is the instance of the class Question or one of its subclasses
	private T question;
	
	public void set(T question) // Set method to set the question - which is an instance class
	{ 
		this.question = question; 
	}
	public T get() // Get method to return the question. 
	{ 
		return question; 
	}

/* 
 * Main() method for demonstrative purposes to show that generics work and it is possible to create an instance of generic class and apply its methods
 */
public static void main(String[] args)
	{
		// Create an instance of the SingleChoiceQuestion
		GenericQuestion<SingleChoiceQuestion> gSCQuestion = new GenericQuestion<SingleChoiceQuestion>();
		gSCQuestion.set(new SingleChoiceQuestion("What's 2+2= ?\nA. 5, B. 7, C. 8, D. 4", "D"));
		System.out.printf(gSCQuestion.get().get());
		
		// Create an instance of the MultipleChoiceQuestion
		GenericQuestion<MultipleChoiceQuestion> gMCQuestion = new GenericQuestion<MultipleChoiceQuestion>();
		ArrayList<String> q1 = new ArrayList<String>();
		q1.add("A");
		q1.add("D");
		gMCQuestion.set(new MultipleChoiceQuestion("What are the programming languages?\nA. Java, B. Manila, C. Python, D. Cobra", q1));
		System.out.printf(gMCQuestion.get().get());
		
		// Create an instance of the OpenEndedQuestion
		GenericQuestion<OpenEndedQuestion> gOEQuestion = new GenericQuestion<OpenEndedQuestion>();
		gOEQuestion.set(new OpenEndedQuestion("How programming transformed your life", "OEQ"));
		System.out.printf(gOEQuestion.get().get());
	}

/*
 * Add quiz allows to add the name of the quiz, populate it with questions and answers,
 * and add its name to the list of available quizzes
 */
public static void addQuiz() 
{
	// Prompt the user for the name of the quiz
	System.out.println("Enter the name of the new quiz:");
	Scanner reader = new Scanner(System.in);
	String quizName = reader.nextLine();
	quizName = quizName.toLowerCase();
	try {
	Formatter outfile = new Formatter(quizName + ".txt"); // open file with a quiz name
	
	while (true) 
	{
		// Prompt the user to enter the type of question to add or complete the quiz creation
		System.out.println("\nWhat's the next question?\nSingle Choice Question - 1\nMultiple-Choice Question - 2\nOpen Ended Question - 3\n\nEnter 'done' to complete input");
		String userChoice = reader.nextLine();
		// done - to complete the quiz creation
		if (userChoice.equals("done")) break;
		// 1 - to add Single Choice Question
		if (userChoice.equals("1"))
		{
			System.out.println("Enter the question:");
			String question = reader.nextLine();
			outfile.format(question + "\n", null);
			System.out.println("Enter the answer:");
			String answer = reader.nextLine();
			outfile.format(answer, null);
			// Create new instance of the GenericQuestion, of type SingleChoiceQuestion
			GenericQuestion<SingleChoiceQuestion> gSCQuestion = new GenericQuestion<SingleChoiceQuestion>();
			// Use generic method set() to set question's question and answer
			gSCQuestion.set(new SingleChoiceQuestion(question, answer));
			// Use generic method get() to display the question
			System.out.printf("Following question is added to the quiz:" + gSCQuestion.get().get());
		}
		// 2 - to add Multiple-Choice Question
		else if (userChoice.equals("2"))
		{
			System.out.println("Enter the question:");
			String question = reader.nextLine();
			outfile.format(question + "\n", null);
			System.out.println("Enter the answer(s) separated by space:");
			String answer = reader.nextLine();
			outfile.format(answer, null);
			// Create new instance of the GenericQuestion, of type MultipleChoiceQuestion
			GenericQuestion<MultipleChoiceQuestion> gMCQuestion = new GenericQuestion<MultipleChoiceQuestion>();
			ArrayList<String> q1 = new ArrayList<String>();
			// Split the correct answers
			String[] splitted = answer.split(" ");
			// Add answers to q1
			for (int k = 0; k <= splitted.length - 1; k++) {
				q1.add(splitted[k]);
			}
			// Use generic method set() to set question's question and answer
			gMCQuestion.set(new MultipleChoiceQuestion(question, q1));
			// Use generic method get() to display the question
			System.out.printf("Following question is added to the quiz:" + gMCQuestion.get().get());
		}
		// 3 - to add Open-Ended Question
		else if (userChoice.equals("3"))
		{
			System.out.println("Enter the question:");
			String question = reader.nextLine();
			outfile.format(question + "\n", null);
			String answer = "OED";
			outfile.format(answer, null);
			// Create new instance of the GenericQuestion, of type OpenEndedQuestion
			GenericQuestion<OpenEndedQuestion> gOEQuestion = new GenericQuestion<OpenEndedQuestion>();
			// Use generic method set() to set question's question and answer
			gOEQuestion.set(new OpenEndedQuestion(question, answer));
			// Use generic method get() to display the question
			System.out.printf("Following question is added to the quiz:" + gOEQuestion.get().get());
		}
	}
	outfile.close(); // Close the file
	}
		
	catch (FileNotFoundException ex)
	{
		System.err.println("Cannot open file ... quitting");
	}
	/*
	 * Add quiz name to the list of available quizzes quizzes.txt
	 */
	try(FileWriter fw = new FileWriter("quizzes.txt", true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw))
		{
		    out.print("\n" + quizName);
			System.out.println("Quiz " + quizName + " is added to the system!\n");
		} 
	
	catch (IOException e) {
		   System.out.println("Something went wrong adding the quiz to the list of quizzes quizzes.txt");
		}
	
}
}