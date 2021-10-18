/**
 * Author: Z
 * Traits:
 * - Helpful comments
 * - Did not understand project leading to unnecessary or unused code beyond class purpose
 * - Does not use make use of exceptions
 */
public class Quote {
    // Defines the types of quotes
    public enum QuoteType {
        PHILOSOPHICAL, MOTIVATIONAL, DISCOURAGEMENT, HAPPY, SAD;
    }

    // Fields
    private final String quote;
    private final QuoteType type;
    private String quoteAuthor;

    // Constructors
    public Quote(String quote, QuoteType type) {
        this.quote = quote;
        this.type = type;
        quoteAuthor = "";
    }

    public Quote(String quote, QuoteType type, String quoteAuthor) {
        this.quote = quote;
        this.type = type;
        this.quoteAuthor = quoteAuthor;
    }

    /**
     * Sets quote author
     * @param quoteAuthor String
     */
    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    /**
     * @return String quote
     */
    public String getQuote() {
        return quote;
    }

    /**
     * @return QuoteType
     */
    public QuoteType getType() {
        return type;
    }

    /**
     * @return String author name
     */
    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quote='" + quote + '\'' +
                ", type=" + type +
                '}';
    }
}
