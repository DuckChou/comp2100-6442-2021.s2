import java.util.ArrayList;

/**
 * Author: Z
 * Traits:
 * - Helpful comments
 * - Did not understand project leading to unnecessary or unused code beyond class purpose
 * - Does not use make use of exceptions
 */
public class Author {
    // Fields
    private final String name;
    private ArrayList<String> quotes;

    // Constructors
    public Author(String name, ArrayList<String> quotes) {
        this.name = name;
        this.quotes = quotes;
    }

    public Author(String name) {
        this.name = name;
    }

    // Get methods
    public String getName() {
        return name;
    }

    public ArrayList<String> getQuotes() {
        return quotes;
    }

    // Set method
    public void setQuotes(ArrayList<String> quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", quotes=" + quotes +
                '}';
    }
}
