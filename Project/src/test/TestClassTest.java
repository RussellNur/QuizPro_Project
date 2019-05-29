package test;

import static org.junit.Assert.*;

import org.junit.*;

public class TestClassTest {

	@Test
	public void testTestMethod() {
		int x = 1;
		assertEquals("X should be 1", 1, x);
	}

}
