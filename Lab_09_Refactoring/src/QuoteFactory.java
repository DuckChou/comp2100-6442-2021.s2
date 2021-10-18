/**
 * Author: Z
 * Traits:
 * - Helpful comments
 * - Did not understand project
 * - Did not understand project leading to unnecessary or unused code beyond class purpose
 * - Does not use make use of exceptions
 */
public class QuoteFactory {
    /**
     * Creates a new quote
     * @param quote string
     * @param type string representing type of quote
     * @return Quote
     */
    public Quote createQuote(String quote, String type) {
        return new Quote(quote, tokenize(type));
    }

    /**
     * Converts a string into a QuoteType
     * @param type string of the type
     * @return QuoteType or error
     */
    public Quote.QuoteType tokenize(String type) {
        switch (type.toLowerCase()) {
            case "philosophical":
                return Quote.QuoteType.PHILOSOPHICAL;
            case "motivational":
                return Quote.QuoteType.MOTIVATIONAL;
            case "discouragement":
                return Quote.QuoteType.DISCOURAGEMENT;
            case "happy":
                return Quote.QuoteType.HAPPY;
            case "sad":
                return Quote.QuoteType.SAD;
            default:
                return null;
        }
    }

    /**
     * String of tokens with space (' ') between tokens
     * @param input String of tokens
     * @return QuoteType[]
     */
    public Quote.QuoteType[] tokenizeString(String input) {
        String[] split = input.split(" ");
        Quote.QuoteType[] quoteTypes = new Quote.QuoteType[split.length];
        for (int i = 0; i < quoteTypes.length; i++) {
            quoteTypes[i] = tokenize(split[i]);
        }
        return quoteTypes;
    }

    /**
     * Checks whether the input string is the same as a given token
     * @param s String input
     * @param type QuoteType
     * @return true if the tokenized string is of the given QuoteType
     */
    public boolean isStringQuoteType(String s, Quote.QuoteType type) {
        return tokenize(s) == type;
    }
}