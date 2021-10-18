import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Author: Person X
 * Trails:
 * - No comments
 * - Unnecessary complication of code
 * - Difficult to understand, unconventional code
 * What cannot be changed:
 * - Class name
 * - Any method names, input and return type
 * - quoteHandler field name and type
 * Naming requirement:
 * - QuoteFactory class field must be named quoteFactory (can be in either RequestHandler or QuoteHandler)
 */
public class RequestHandler {
    String[] valids = { "PHILOSOPHICAL", "MOTIVATIONAL", "DISCOURAGEMENT", "HAPPY", "SAD" };
    public final QuoteHandler quoteHandler;
    RequestHandler() {
        quoteHandler = new QuoteHandler();
    };

    public String[] getQuotes() {
        return Stream.of(QuoteHandler.get().toArray(new String[0])).toArray(String[]::new);
    }

    public String[] getQuotes(String type) {
        return Arrays.asList(valids).contains(type.toUpperCase()) ? Stream.of(QuoteHandler.get(type).toArray(new String[0])).toArray(String[]::new) : new String[0];
    }

    public boolean addQuote(String quote, String type) {
        if (!Arrays.asList(valids).contains(type.toUpperCase()))
            return false;
        QuoteHandler.add(quote, type);
        return true;
    }
}