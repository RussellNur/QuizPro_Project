package questions;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GetScoreMultipleChoiceQuestionTest {

	@Test
	public void test() {
		ArrayList<String> q1 = new ArrayList<String>();
		q1.add("A");
		q1.add("B");
		MultipleChoiceQuestion test = new MultipleChoiceQuestion("Question", "options", q1);
		String userChoice = "A B";
		double output = test.getScore(userChoice);
		assertEquals(1, output, 0);
		userChoice = "A";
		output = test.getScore(userChoice);
		assertEquals(0.5, output, 0);
		userChoice = "C";
		output = test.getScore(userChoice);
		assertEquals(0, output, 0);
		userChoice = "A C";
		output = test.getScore(userChoice);
		assertEquals(0.4, output, 0);
	}

}
