import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * Task 2.3
 * You are given the java class called PathComplete, which has a method
 * 	called findSomething. Implement the minimum number of JUnit test cases to achieve
 * 	path complete to the findSomething method.Write your test case(s) in the test() method
 * 	in the PathCompleteTest.java file. All test cases should pass the JUnit test. You are not
 * 	allowed to alter the signatures of the given methods and the package
 * 	structures of the given classes.
 *
 */
public class PathCompleteTest {

	@Test(timeout = 1000)
	public void test() {
		// Implement your test cases
		// START YOUR CODE

		// PATH1, a<=b, b>c, return b
		assertEquals(5,PathComplete.findSomething(3,5,1));
		// PATH2, a<=b, b<=c, return c
		assertEquals(7,PathComplete.findSomething(3,5,7));
		// PATH3, a>b, a>c, return a
		assertEquals(5,PathComplete.findSomething(5,3,2));
		// PATH4, a>b, a<=c, return c
		assertEquals(7,PathComplete.findSomething(5,3,7));

		
		// END YOUR CODE
	}
}
