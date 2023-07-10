package calculator.project2calculatorgui;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * Handles parsing inputs in Prefix notation
 *
 * @author Thien An Tran
 * @version May 22, 2023
 */
public class PrefixParser implements ParseStrategy {
    private Map<String, Double> env;

    /**
     * Takes a String input, splits it and determines if it should be parsed or not
     *
     * @param input a String
     * @return an Expression
     * @throws IllegalArgumentException when the input is not in prefix notation
     */
    @Override
    public Expression parseInput(String input, Map<String, Double> env) {
        this.env = env;
        String[] tokens = input.trim().split("\\s+");
        if (!isValidPrefixNotation(tokens)) {
            throw new IllegalArgumentException("Input is not in prefix notation.");
        } else{
            return parseExpression(tokens, new int[]{0});
        }
    }

    /**
     * Determines if a given string array represents a valid prefix notation
     *
     * @param tokens a string array
     * @return a boolean, true if the expression is valid, false otherwise
     */
    public boolean isValidPrefixNotation(String[] tokens) {
        Deque<Expression> stack = new ArrayDeque<>();
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (isValidOperator(token)) {
                if (stack.size() < 2) {
                    return false;
                }
                stack.pop();
                stack.pop();
                stack.push(new Digit(0)); // push a dummy value onto the stack
            } else if (isValidNumber(token)) {
                double operand = Double.parseDouble(token);
                stack.push(new Digit(operand));
            } else if (isValidVariable(token)){
                stack.push(new Variable(token, env));
            } else {
                return false;
            }
        }
        return stack.size() == 1;
    }

    /**
     * Parses an Expression tree recursively from an array of string tokens.
     *
     * @param tokens an array of Strings representing the Expression tree in prefix notation
     * @param index an array of integers representing the current index in the token array
     * @return an Expression object representing an expression tree of the prefix notation
     */
    public Expression parseExpression(String[] tokens, int[] index) {
        String token = tokens[index[0]];
        index[0]++;

        if (isValidNumber(token)) {
            double value = Double.parseDouble(token);
            return new Digit(value);
        } else if (isValidVariable(token)) {
            return new Variable(token, env);
        } else {
            Expression left = parseExpression(tokens, index);
            Expression right = parseExpression(tokens, index);
            return createOperation(token, left, right);
        }
    }
}
