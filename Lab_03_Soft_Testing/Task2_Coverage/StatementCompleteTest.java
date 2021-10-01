import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Task 2.1
 * You are given the class StatementComplete with the method sumAndMulti.
 *  Implement the minimum number of JUnit tests to achieve statement complete
 *  for the method sumAndMulti.
 *
 * This first mini task should be simple and give you an idea of how to write such tests
 *  as well as visualise inputs moving through the code. Statements are labelled and
 *  you can think of inputs and run through the code to ensure the inputs would
 *  provide statement complete.
 *
 * Please write your test in the sumAndMultiplyTest() method. Your tests should pass.
 *  Finally, you are not allowed to alter the signatures of the given methods and the package
 *  structures of the given classes.
 *
 */
public class StatementCompleteTest {

    @Test(timeout = 1000)
    public void sumAndMultiplyTest() {
        // Implement your test cases
        // START YOUR CODE

        // For statement 1, number is positive
        assertEquals(0,StatementComplete.sumAndMultiply(-7));
        // For statement 2, number is negative
        assertEquals(75,StatementComplete.sumAndMultiply(5));


        // END YOUR CODE
    }
}