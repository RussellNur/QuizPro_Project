package test;

import static org.junit.Assert.*;

import org.junit.Test;

import questions.SingleChoiceQuestion;

public class getScoreTest {

	@Test
	public void test() {
		SingleChoiceQuestion test = new SingleChoiceQuestion("Question", "A");
		String userChoice = "A";
		double output = test.getScore(userChoice);
		assertEquals(1, output, 0.1);
	}

}
