package calculator.project2calculatorgui;

import java.util.Map;

/**
 * CSC 305 Project 2
 * Handles parsing depending on given strategy
 *
 * @author Thien An Tran
 * @version 2.0
 */
public class Parser {
    private ParseStrategy parseStrategy;

    /**
     * Sets the parsing strategy for parsing
     *
     * @param parseStrategy Parsing strategy to use
     */
    public void setParseStrategy(ParseStrategy parseStrategy) {
        this.parseStrategy = parseStrategy;
    }

    /**
     * Parses given input into an Expression object using the set parsing strategy
     *
     * @param input a string representing an expression
     * @param environment a Map as the shared environment containing variable bindings
     * @return an Expression object
     */
    public Expression parse(String input, Map<String, Double> environment) {
        return parseStrategy.parseInput(input, environment);
    }
}
