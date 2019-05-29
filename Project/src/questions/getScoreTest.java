package questions;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import questions.SingleChoiceQuestion;

public class getScoreTest {
	
	@Test
	public void test() {
		SingleChoiceQuestion test = new SingleChoiceQuestion("Question", "A");
		String userChoice = "A";
		double output = test.getScore(userChoice);
		assertEquals(1, output, 0);
	}
}
