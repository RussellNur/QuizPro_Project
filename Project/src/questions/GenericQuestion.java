package questions;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class GenericQuestion <T extends Question>{
	private T question;
	
	public void set(T question) 
	{ 
		this.question = question; 
	}
	public T get() 
	{ 
		return question; 
	}


public static void main(String[] args)
	{
		GenericQuestion<SingleChoiceQuestion> gSCQuestion = new GenericQuestion<SingleChoiceQuestion>();
		gSCQuestion.set(new SingleChoiceQuestion("What's 2+2= ?\nA. 5, B. 7, C. 8, D. 4", "D"));
		System.out.printf(gSCQuestion.get().get());
		
		GenericQuestion<MultipleChoiceQuestion> gMCQuestion = new GenericQuestion<MultipleChoiceQuestion>();
		ArrayList<String> q1 = new ArrayList<String>();
		q1.add("A");
		q1.add("D");
		gMCQuestion.set(new MultipleChoiceQuestion("What are the programming languages?\nA. Java, B. Manila, C. Python, D. Cobra", q1));
		System.out.printf(gMCQuestion.get().get());
		
		GenericQuestion<OpenEndedQuestion> gOEQuestion = new GenericQuestion<OpenEndedQuestion>();
		gOEQuestion.set(new OpenEndedQuestion("How programming transformed your life", ""));
		System.out.printf(gOEQuestion.get().get());
	}

public static void addQuiz() 
{
	System.out.println("Enter the name of the new quiz:");
	Scanner reader = new Scanner(System.in);
	String quizName = reader.nextLine();
	quizName = quizName.toLowerCase();
	try {
	Formatter outfile = new Formatter(quizName + ".txt"); // open file
	
	while (true) 
	{
		System.out.println("\nWhat's the next question?\nSingle Choice Question - 1\nMultiple-Choice Question - 2\nOpen Ended Question - 3\n\nEnter 'done' to complete input");
		String userChoice = reader.nextLine();
		if (userChoice.equals("done")) break;
		if (userChoice.equals("1"))
		{
			System.out.println("Enter the question:");
			String question = reader.nextLine();
			outfile.format(question + "\n", null);
			System.out.println("Enter the answer:");
			String answer = reader.nextLine();
			outfile.format(answer, null);
			GenericQuestion<SingleChoiceQuestion> gSCQuestion = new GenericQuestion<SingleChoiceQuestion>();
			gSCQuestion.set(new SingleChoiceQuestion(question, answer));
			System.out.printf("Following question is added to the quiz:" + gSCQuestion.get().get());
		}
		
		else if (userChoice.equals("2"))
		{
			System.out.println("Enter the question:");
			String question = reader.nextLine();
			outfile.format(question + "\n", null);
			System.out.println("Enter the answer(s) separated by space:");
			String answer = reader.nextLine();
			outfile.format(answer, null);
			GenericQuestion<MultipleChoiceQuestion> gMCQuestion = new GenericQuestion<MultipleChoiceQuestion>();
			ArrayList<String> q1 = new ArrayList<String>();
			// Split the correct answers
			String[] splitted = answer.split(" ");
			// Add answers to q1
			for (int k = 0; k <= splitted.length - 1; k++) {
				q1.add(splitted[k]);
			}
			gMCQuestion.set(new MultipleChoiceQuestion(question, q1));
			System.out.printf("Following question is added to the quiz:" + gMCQuestion.get().get());
		}
		else if (userChoice.equals("3"))
		{
			System.out.println("Enter the question:");
			String question = reader.nextLine();
			outfile.format(question + "\n", null);
			String answer = "OED";
			outfile.format(answer, null);
			GenericQuestion<OpenEndedQuestion> gOEQuestion = new GenericQuestion<OpenEndedQuestion>();
			gOEQuestion.set(new OpenEndedQuestion(question, answer));
			System.out.printf("Following question is added to the quiz:" + gOEQuestion.get().get());
		}
	}
	outfile.close();
	}
		
	catch (FileNotFoundException ex)
	{
		System.err.println("Cannot open file ... quitting");
	}
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