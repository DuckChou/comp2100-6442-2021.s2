import java.util.ArrayList;
import java.util.List;

/**
 * Author: Person Y
 * Traits:
 * - Unhelpful comments
 * - Unsure of difference between static and non-static
 * - Commented out unfinished code
 * What cannot be changed:
 * - Class name
 * - Any method names
 * - quoteStorage field name
 * Naming requirement:
 * - QuoteFactory class field must be named quoteFactory (can be in either RequestHandler or QuoteHandler)
 */
public class QuoteHandler {
    // Quote storage
    static List<String> quoteStorage = new ArrayList<>();
    static List<String> quoteTypes = new ArrayList<>();
    QuoteHandler() {
        // Need to reset storage? Not sure why.
        quoteStorage = new ArrayList<>();
        quoteTypes = new ArrayList<>();
    }

    // Add method
    public static void add(String quote, String type) {
        quoteStorage.add(quote);
        quoteTypes.add(type);
    }

    //

    // Get method
    public static List<String> get() {
        return quoteStorage; //
    }

    // Another get method but with type input
    public static List<String> get(String type) {
        List<String> filtered = new ArrayList<>();
        for (int i = 0; i < quoteTypes.size(); i++) {
            if (quoteTypes.get(i).equalsIgnoreCase(type)) {
                filtered.add(quoteStorage.get(i));
            }
        }
        return filtered;
    }

//    // Does not work?
//    public static List<Quote> get(Quote.QuoteType type) {
//        // Test removeIf
//        quoteStorage.removeIf(x -> x.getType() == type);
//        return quoteStorage;
//    }
}
