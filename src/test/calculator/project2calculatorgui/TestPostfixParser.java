package calculator.project2calculatorgui;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PostfixParser and default methods of ParseStrategy interface
 */
public class TestPostfixParser {
    private Parser parser;
    private ParseStrategy postParser;
    private Map<String, Double> env;
    @Before
    public void setup(){
        postParser = new PostfixParser();
        parser = new Parser();
        parser.setParseStrategy(postParser);
        env = new HashMap<>();
    }
    @AfterEach
    public void teardown(){
        env = new HashMap<>();
    }

    @Test
    public void testParseAdd(){
        Expression result = parser.parse("3 10 7 - +", env);
        assertEquals(6.0, result.evaluate());
    }

    @Test
    public void testParse1(){
        Expression result = parser.parse("10 2 8 * + 3 -", env);
        assertEquals(23.0, result.evaluate());
    }

    @Test
    public void testParse2(){
        Expression result = parser.parse("3 4 * 2 5 * +", env);
        assertEquals(22.0, result.evaluate());
    }

    @Test
    public void testParse3(){
        Expression result = parser.parse("2 3 * 7 3 / +", env);
        assertEquals(8.333333333333334, result.evaluate());
    }

    @Test
    public void testParse4(){
        Expression result = parser.parse("2 2 2 % ^", env);
        assertEquals(1, result.evaluate());
    }

    @Test
    public void testParseInput_InvalidPostfixExpression_NotEnoughOperands() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2 +", env));
    }

    @Test
    public void testParseInput_InvalidPostfixExpression_TooManyOperands() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2 3 + 4", env));
    }

    @Test
    public void testParseInput_InvalidToken() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2 3 &", env));
    }

    @Test
    public void testInvalidPostfixNotation() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("3 4 / /", env));

        assertThrows(IllegalArgumentException.class, () -> parser.parse("// 3", env));

        assertThrows(IllegalArgumentException.class, () -> parser.parse("+ 2 3", env));
    }

    @Test
    public void testParseWithVar1(){
        String input = "a b + c d / -";
        env.put("a", 5.0);
        env.put("b", 4.0);
        env.put("c", 9.0);
        env.put("d", 3.0);
        Expression result = parser.parse(input, env);
        assertEquals(6.0, result.evaluate());
    }

    @Test
    public void testParseAddWithVariable(){
        String input = "a 10 7 - +";
        env.put("a", 3.0);
        Expression result = parser.parse(input, env);
        assertEquals(6.0, result.evaluate());
    }

    @Test
    public void testCreateOperationInvalidOperator() {
        Expression left = new Digit(1);
        Expression right = new Digit(0);
        assertThrows(IllegalArgumentException.class, () -> postParser.createOperation("=", left, right));
    }

    @Test
    public void testIsValidNumberInvalidNumberFalse() {
        assertFalse(postParser.isValidNumber("pi"));
    }

    @Test
    public void testIsValidOperatorInvalidOperatorFalse() {
        assertFalse(postParser.isValidOperator("asdf as123 "));
    }

    @Test
    public void testIsValidOperatorInvalidOperator1() {
        assertFalse(postParser.isValidOperator("+*%^-"));
    }

}
