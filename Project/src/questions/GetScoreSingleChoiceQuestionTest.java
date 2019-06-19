package questions;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import questions.SingleChoiceQuestion;

public class GetScoreSingleChoiceQuestionTest {
	
	@Test
	public void test() {
		SingleChoiceQuestion test = new SingleChoiceQuestion("Question", "options", "A");
		String userChoice = "A";
		double output = test.getScore(userChoice);
		assertEquals(1, output, 0);
	}
}
