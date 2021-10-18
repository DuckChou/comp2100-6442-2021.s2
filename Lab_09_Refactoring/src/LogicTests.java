import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class LogicTests {

    /*
    The following tests are directed towards the program logic. These should all pass before and after
    refactoring.

    Note: different variations of uppercase and lowercase of quote type string is used to check that the
    implementation handles them correctly.
     */

    // To be used in each test
    private RequestHandler requestHandler;

    @Before
    public void setRequestHandler() {
        /*
         The following code is setup so that regardless of whether the singleton design pattern is implemented,
         the logic tests will still run.
         */
        // Reinitialise quoteHandler
        try {
            // Must reset quoteHandler as it is a singleton
            Field quoteHandler = QuoteHandler.class.getDeclaredField("quoteHandler");
            quoteHandler.setAccessible(true);
            quoteHandler.set(QuoteHandler.class, null);
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        // Reinitialise RequestHandler
        try {
            // Simply use the base constructor if available
            Constructor<RequestHandler> requestHandlerConstructor = RequestHandler.class.getDeclaredConstructor();
            requestHandlerConstructor.setAccessible(true);
            requestHandler = requestHandlerConstructor.newInstance();
        } catch (Exception e) {
            System.err.println("Something is wrong with the RequestHandler constructor (should still work even if private).");
            e.printStackTrace();
        }
    }

    @Test(timeout = 1000)
    public void getEmptyTest() {
        // No quotes added
        assertArrayEquals("Expected empty String[]", new String[0], requestHandler.getQuotes());
        assertArrayEquals("Expected empty String[]", new String[0], requestHandler.getQuotes("sad"));

        requestHandler.addQuote("Hello, my name is Eric... - Eric M. 2015, former COMP2100/6442 Course Convenor",
                Quote.QuoteType.PHILOSOPHICAL.toString());
        assertArrayEquals("Expected empty String[]", new String[0], requestHandler.getQuotes("sad"));
    }

    @Test(timeout = 1000)
    public void getSingleQuoteTest() {
        // Add single quote
        requestHandler.addQuote("https://www.anu.edu.au/students/academic-skills/academic-integrity/plagiarism",
                Quote.QuoteType.DISCOURAGEMENT.toString());
        assertArrayEquals("Expecting String[] with 1 value: https://www.anu.edu.au/students/academic-skills/academic-integrity/plagiarism",
                new String[] {"https://www.anu.edu.au/students/academic-skills/academic-integrity/plagiarism"},
                requestHandler.getQuotes());
        assertArrayEquals("Expecting String[] with 1 value: https://www.anu.edu.au/students/academic-skills/academic-integrity/plagiarism",
                new String[] {"https://www.anu.edu.au/students/academic-skills/academic-integrity/plagiarism"},
                requestHandler.getQuotes("disCoUrAGEMENT"));
    }

    @Test(timeout = 1000)
    public void getMultipleQuotesTest() {
        String[] quotes = {
                "Study well for the final exam - Tutor",
                "Know the difference between == and .equals() - Tutor",
                "Ask yourself, why does '==' work on null? - Tutor",
                "I am slightly more satisfied in life - students who complete SELTs",
                "You will need 19 out of 21 to pass - 2019 S1 first lecture of COMP2100/6442"
        };

        // Add a bunch of quotes
        requestHandler.addQuote(quotes[0],
                Quote.QuoteType.MOTIVATIONAL.toString().toUpperCase());
        requestHandler.addQuote(quotes[1],
                Quote.QuoteType.MOTIVATIONAL.toString());
        requestHandler.addQuote(quotes[2],
                Quote.QuoteType.MOTIVATIONAL.toString());
        requestHandler.addQuote(quotes[3],
                Quote.QuoteType.MOTIVATIONAL.toString());
        requestHandler.addQuote(quotes[4],
                Quote.QuoteType.SAD.toString());


        assertArrayEquals("Expected 4 motivational quotes",
                Arrays.copyOf(quotes, 4), requestHandler.getQuotes("Motivational"));
        assertArrayEquals("Expected 1 sad quote",
                quotes, requestHandler.getQuotes());
    }

    @Test(timeout = 1000)
    public void validTypeTest() {
        // Try to add a quote of a valid type
        boolean success = requestHandler.addQuote("To study or not to study?", Quote.QuoteType.PHILOSOPHICAL.toString());
        assertTrue("Valid type provided on add request. Should have returned true", success);
    }

    @Test(timeout = 1000)
    public void invalidTypeTest() {
        // Try to add a quote of an invalid type
        boolean success = requestHandler.addQuote("0.1 + 0.2 != 0.3", "ridiculous");
        assertFalse("Invalid type provided on add request. Should have returned false", success);

        assertArrayEquals("Invalid type provided on add request. Should not have added quote",
                new String[0], requestHandler.getQuotes());

        assertArrayEquals("Invalid type provided on get request. Should return empty String[]",
                new String[0], requestHandler.getQuotes("ridiculous"));
    }
}