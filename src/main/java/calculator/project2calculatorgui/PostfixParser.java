package calculator.project2calculatorgui;

import java.util.*;

/**
 * Handles parsing inputs in Postfix notation
 *
 * @author Thien An Tran
 * @version May 22, 2023
 */
public class PostfixParser implements ParseStrategy {

    /**
     * Parses an Expression tree from a given string
     *
     * @param input a string of the expression in postfix notation
     * @param env a Map representing the shared environment of variable bindings
     * @return an Expression object representing an expression tree of the postfix string
     */
    @Override
    public Expression parseInput(String input, Map<String, Double> env) {
        String[] tokens = input.trim().split("\\s+");
        Deque<Expression> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (isValidNumber(token)) {
                double value = Double.parseDouble(token);
                stack.push(new Digit(value));
            } else if (isValidVariable(token)) {
                stack.push(new Variable(token, env));
            } else if (isValidOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expression: not enough operands");
                }
                Expression right = stack.pop();
                Expression left = stack.pop();
                stack.push(createOperation(token, left, right));
            } else {
                throw new IllegalArgumentException("Invalid input: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression: too many operands");
        }

        return stack.pop();
    }

}
