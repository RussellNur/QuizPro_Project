package questions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GenericQuestionTest {

	@Test 
	/*
	 * Test whether the get() method outputs the SingleChoiceQuestion class
	 */
	public void testGet() {
		GenericQuestion<SingleChoiceQuestion> test = new GenericQuestion<SingleChoiceQuestion>();
		test.set(new SingleChoiceQuestion("How are you? A. Great B. Terrible", "A"));
		SingleChoiceQuestion output = test.get();
		assertEquals(output.getClass(), test.get().getClass());
	}

}
