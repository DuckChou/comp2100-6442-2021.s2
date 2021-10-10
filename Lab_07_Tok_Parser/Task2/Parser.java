import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Note: You will need to have completed task 1 to complete this task.
 * <p>
 * Welcome to task 2. In this task your job is to implement a simple parser.
 * It should be able to parser the following grammar rule:
 * <exp>    ::=  <term> | <term> + <exp> | <term> - <exp>
 * <term>   ::=  <factor> | <factor> * <term> | <factor> / <term>
 * <factor> ::=  <unsigned integer> | ( <exp> )
 * <p>
 * Here are some rules you must abide by for this task:
 * 1. You may NOT modify any other classes in this task 2 package.
 * 2. You may create additional fields or methods to finish you implementation within this class.
 * <p>
 * Parsing, within the context of this lab, is the process of taking a bunch of tokens and
 * evaluating them. You will not need to 'evaluate' them within this class, instead, just
 * return an expression which can be evaluated.
 */
public class Parser {
    /**
     * The following exception should be thrown if the parse is faced with series of tokens that do not
     * correlate with any possible production rule.
     */
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    // The tokenizer (class field) this parser will use.
    Tokenizer tokenizer;
    int timeOfCheck=0;

    /**
     * Parser class constructor
     * Simply sets the tokenizer field.
     * **** please do not modify this part ****
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * To help you both test and understand what this parser is doing, we have included a main method
     * which you can run. Once running, provide a mathematical string to the terminal and you will
     * receive back the result of your parsing.
     */
    public static void main(String[] args) {
        // Create a scanner to get the user's input.
        Scanner scanner = new Scanner(System.in);

        /*
         Continue to get the user's input until they exit.
         To exit press: Control + D or providing the string 'q'
         Example input you can try: ((1 + 2) * 5)/2
         Note that evaluations will round down to negative infinity (because they are integers).
         */
        System.out.println("Provide a mathematical string to be parsed:");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            // Check if 'quit' is provided.
            if (input.equals("q"))
                break;

            // Create an instance of the tokenizer.
            Tokenizer tokenizer = new Tokenizer(input);

            // Print out the expression from the parser.
            Parser parser = new Parser(tokenizer);
            Exp expression = parser.parseExp();
            System.out.println("Parsing: " + expression.show());
            System.out.println("Evaluation: " + expression.evaluate());
        }
    }

    public boolean checkValid(List<Token> check){


        int numOfLBRA = 0;
        int numOfRBRA = 0;

        int numOfOperator = 0;

        for(int i=0;i<check.size();i++){
            if(check.get(i).getType().equals(Token.Type.SUB)||check.get(i).getType().equals(Token.Type.ADD)||check.get(i).getType().equals(Token.Type.MUL)||check.get(i).getType().equals(Token.Type.DIV)){
                if(i==check.size()-1||(check.get(i+1).getType().equals(Token.Type.SUB)||check.get(i+1).getType().equals(Token.Type.ADD)||check.get(i+1).getType().equals(Token.Type.MUL)||check.get(i+1).getType().equals(Token.Type.DIV)))
                    return false;
                else
                    numOfOperator++;
            }

            if(check.get(i).getType().equals(Token.Type.LBRA))
                numOfLBRA++;
            if(check.get(i).getType().equals(Token.Type.RBRA))
                numOfRBRA++;

        }
        if(numOfLBRA!=numOfRBRA||numOfOperator==0){
            return false;
        }
        return true;
    }

    /**
     * Adheres to the grammar rule:
     * <exp>    ::= <term> | <term> + <exp> | <term> - <exp>
     *
     * @return type: Exp.
     */
    public Exp parseExp() {
        /*
         TODO: Implement parse function for <exp>.
         TODO: Throw an IllegalProductionException if provided with tokens not conforming to the grammar.
         Hint 1: you know that the first item will always be a term (according to the grammar).
         Hint 2: the possible grammar return '<term> + <exp>' correlates with the class (AddExp(term, exp)).
         */
        // ########## YOUR CODE STARTS HERE ##########

        List<Token> check = new ArrayList<>();

        while(tokenizer.hasNext()){
        check.add(tokenizer.current());
        tokenizer.next();
        }

        if(timeOfCheck==0&&!checkValid(check)){
            timeOfCheck++;
            throw new IllegalProductionException("");
        }else {
            for(int i=0;i<check.size();i++){
                if(check.get(i).getType().equals(Token.Type.ADD)){
                    return new AddExp(parseTerm(check.subList(0,i)),parseTerm(check.subList(i+1,check.size())));
                }
                else if (check.get(i).getType().equals(Token.Type.SUB)){
                    return new SubExp(parseTerm(check.subList(0,i)),parseTerm(check.subList(i+1,check.size())));
                }
            }
            return parseTerm(check);

        }

        //return null; // Change this return (if you want). It is simply a placeholder to prevent an error.
        // ########## YOUR CODE ENDS HERE ##########
    }

    /**
     * Adheres to the grammar rule:
     * <term>   ::=  <factor> | <factor> * <term> | <factor> / <term>
     *
     * @return type: Exp.
     */
    public Exp parseTerm(List<Token> list) {
        /*
         TODO: Implement parse function for <term>.
         TODO: Throw an IllegalProductionException if provided with tokens not conforming to the grammar.
         Hint: you know that the first item will always be a factor (according to the grammar).
         */
        // ########## YOUR CODE STARTS HERE ##########



            for(int i=0;i<list.size();i++){
                if(list.get(i).getType().equals(Token.Type.MUL)){
                    return new MultExp(parseTerm(list.subList(0,i)),parseTerm(list.subList(i+1,list.size())));
                }
                else if (list.get(i).getType().equals(Token.Type.DIV)){
                    return new DivExp(parseTerm(list.subList(0,i)),parseTerm(list.subList(i+1,list.size())));
                }
            }
        return parseFactor(list.get(0));




        //return null; // Change this return (if you want). It is simply a placeholder to prevent an error.
        // ########## YOUR CODE ENDS HERE ##########
    }

    /**
     * Adheres to the grammar rule:
     * <factor> ::= <unsigned integer> | ( <exp> )
     *
     * @return type: Exp.
     */
    public Exp parseFactor(Token token) {
        /*
         TODO: Implement parse function for <factor>.
         TODO: Throw an IllegalProductionException if provided with tokens not conforming to the grammar.
         Hint: you can use Integer.parseInt() to convert a string into an integer.
         Fun fact: Integer.parseInt() is using a parser!
         */
        // ########## YOUR CODE STARTS HERE ##########


        return new IntExp(Integer.parseInt(token.getToken())); // Change this return (if you want). It is simply a placeholder to prevent an error.
        // ########## YOUR CODE ENDS HERE ##########
    }
}
