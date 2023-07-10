package calculator.project2calculatorgui;

import java.util.*;

/**
 * Represents a single variable in an expression.
 *
 * @author Thien An Tran
 * @version May 22, 2023
 */
public class Variable implements Expression {
    private final String name;
    private final Map<String, Double> environment;

    /**
     * Constructs a Variable object
     *
     * @param name a String
     * @param environment a hashmap containing the variable's binding
     */
    public Variable(String name, Map<String, Double> environment) {
        this.name = name;
        this.environment = environment;
    }

    /**
     * Returns the value of the Variable object
     *
     * @return the variable's value as a double
     * @throws NoSuchElementException when the variable is not found in the hashmap
     */
    @Override
    public double evaluate() {
        if (environment.containsKey(name)) {
            return environment.get(name);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Returns a string representation of the Variable object
     *
     * @return a string
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * Returns a string representation of the Variable object
     *
     * @return a string
     */
    @Override
    public String toPrefixString() {
        return toString();
    }

    /**
     * Returns a string representation of the Variable object
     *
     * @return a string
     */
    @Override
    public String toPostfixString() {
        return toString();
    }
}
