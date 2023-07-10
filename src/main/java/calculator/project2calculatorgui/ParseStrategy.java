package calculator.project2calculatorgui;

import java.util.Map;

/**
 * CSC 305 Project 2
 * Interface for parsing strategies
 *
 * @author Thien An Tran
 * @version 2.0
 */
public interface ParseStrategy {

    /**
     * Parses an Expression tree from a given string
     *
     * @param input a string of the expression
     * @param env a Map representing the shared environment of variable bindings
     * @return an Expression object representing an expression tree of the given string
     */
    Expression parseInput(String input, Map<String, Double> env);

    /**
     * Creates an Expression object based on the operator and the left and right operand
     *
     * @param operator a String representing an operator
     * @param left an Expression representing the left operand
     * @param right an Expression representing the right operand
     * @return an Expression, particularly a BOperation
     * @throws IllegalArgumentException when the operator is not one of the supported ones
     */
    default Expression createOperation(String operator, Expression left, Expression right) {
        return switch (operator) {
            case "+" -> new AdditionOp(left, right);
            case "-" -> new SubtractionOp(left, right);
            case "*" -> new MultiplicationOp(left, right);
            case "/" -> new DivisionOp(left, right);
            case "%" -> new ModuloOp(left, right);
            case "^" -> new ExponentiationOp(left, right);
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    /**
     * Checks if a string is a valid number
     *
     * @param token a String
     * @return a boolean, true if the string is a number, false otherwise
     */
    default boolean isValidNumber(String token) {
        return token.matches("[+-]?\\d+(\\.\\d+)?([eE][-+]?\\d+)?");
    }

    /**
     * Checks if a string is a valid operator
     *
     * @param token a String
     * @return a boolean, true if the string is an operator, false otherwise
     */
    default boolean isValidOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") ||
                token.equals("%") || token.equals("^");
    }

    /**
     * Checks if a string is a valid variable name
     *
     * @param token a String
     * @return a boolean, true if the string is a valid variable name, false otherwise
     */
    default boolean isValidVariable(String token) {
        return token.matches("[a-zA-Z]\\w*");
    }
}
