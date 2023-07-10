package calculator.project2calculatorgui;

/**
 * Interface for mathematical expressions
 *
 * @author Thien An Tran
 * @version May 22, 2023
 */
public interface Expression {

    /**
     * Calculates the expression and returns its numeric value.
     *
     * @return the numeric value of the expression
     */
    double evaluate();

    /**
     * Returns a string representation of an Expression object in prefix notation
     *
     * @return a string
     */
    String toPrefixString();

    /**
     * Returns a string representation of an Expression object in postfix notation
     *
     * @return a string
     */
    String toPostfixString();

    /**
     * Returns a string representation of an Expression object in infix notation
     *
     * @return a string
     */
    String toString();
}
