package questions;

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
		GenericQuestion<SingleChoiceQuestion> gQuestion = new GenericQuestion<SingleChoiceQuestion>();
		gQuestion.set(new SingleChoiceQuestion("What's 2+2=?\nA. 5, B. 7, C. 8, D. 4", "D"));
		System.out.printf(gQuestion.get().get());
	}
}