package calculator.project2calculatorgui;

/**
 * CSC 305 Project 2
 * Interface for mathematical expressions
 *
 * @author Thien An Tran
 * @version 2.0
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
