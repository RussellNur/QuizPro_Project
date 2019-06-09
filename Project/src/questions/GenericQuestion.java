package questions;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/*
 * Generic class Generic question which is bounded to be the subclass of the Question Class
 */
public class GenericQuestion <T extends Question> implements Serializable{
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
	
	final String BINARY_IO = "binary.dat";
	
	try {
	Formatter outfile = new Formatter(quizName + ".txt"); // open file with a quiz name
		try (ObjectInputStream infile = new ObjectInputStream(new FileInputStream(BINARY_IO));)      
		    {
			try (ObjectOutputStream questionFile = new ObjectOutputStream
	        		(new FileOutputStream(BINARY_IO));)  	
	        {	
       	
		    
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
			answer = answer.toUpperCase();
			outfile.format(answer + "\n", null);
			// Create new instance of the GenericQuestion, of type SingleChoiceQuestion
			GenericQuestion<SingleChoiceQuestion> gSCQuestion = new GenericQuestion<SingleChoiceQuestion>();
			// Use generic method set() to set question's question and answer
			gSCQuestion.set(new SingleChoiceQuestion(question, answer));
			// Use generic method get() to display the question
			System.out.printf("Following question is added to the quiz:" + gSCQuestion.get().get());
 
			questionFile.writeObject(gSCQuestion);  
	        
		}
		// 2 - to add Multiple-Choice Question
		else if (userChoice.equals("2"))
		{
			System.out.println("Enter the question:");
			String question = reader.nextLine();
			outfile.format(question + "\n", null);
			System.out.println("Enter the answer(s) separated by space:");
			String answer = reader.nextLine();
			answer = answer.toUpperCase();
			outfile.format(answer + "\n", null);
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
			
			questionFile.writeObject(gMCQuestion); 
	        
			
		}
		// 3 - to add Open-Ended Question
		else if (userChoice.equals("3"))
		{
			System.out.println("Enter the question:");
			String question = reader.nextLine();
			outfile.format(question + "\n", null);
			String answer = "OEQ" + "\n";
			outfile.format(answer, null);
			// Create new instance of the GenericQuestion, of type OpenEndedQuestion
			GenericQuestion<OpenEndedQuestion> gOEQuestion = new GenericQuestion<OpenEndedQuestion>();
			// Use generic method set() to set question's question and answer
			gOEQuestion.set(new OpenEndedQuestion(question, answer));
			// Use generic method get() to display the question
			System.out.printf("Following question is added to the quiz:" + gOEQuestion.get().get());

			questionFile.writeObject(gOEQuestion);  
	        
		}
	}
	outfile.close(); // Close the file
	
	System.out.println("The new quiz added consists of these questions:");
	while (true)
	{
		System.out.println((((GenericQuestion<? extends Question>) (infile.readObject())).get()));
	} 
}
}
	
}
	
	catch (EOFException ex)
    {
        System.out.println("EOF reached in binary.dat");    
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

