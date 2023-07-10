package calculator.project2calculatorgui;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PrefixParser and default methods of ParseStrategy interface
 */
public class TestPrefixParser {

    private PrefixParser parser;
    private Map<String, Double> env;
    @Before
    public void setup(){
        parser = new PrefixParser();
        env = new HashMap<>();
    }

    @AfterEach
    public void teardown(){
        env = new HashMap<>();
    }

    @Test
    public void testParseAdd() {
        Expression result = parser.parseInput("+ 1 2", env);
        Assertions.assertEquals(3.0, result.evaluate());
    }

    @Test
    public void testParseSubtract() {
        Expression result = parser.parseInput("- 5 3", env);
        Assertions.assertEquals(2.0, result.evaluate());
    }

    @Test
    public void testParseDivide() {
        Expression result = parser.parseInput("/ 6 3", env);
        Assertions.assertEquals(2.0, result.evaluate());
    }

    @Test
    public void testParseMultiply() {
        Expression result = parser.parseInput("* 2 3", env);
        Assertions.assertEquals(6.0, result.evaluate());
    }

    @Test
    public void testParseExponentiation() {
        Expression result = parser.parseInput("^ 2 3", env);
        Assertions.assertEquals(8.0, result.evaluate());
    }

    @Test
    public void testParseModulus() {
        Expression result = parser.parseInput("% 5 2", env);
        Assertions.assertEquals(1.0, result.evaluate());
    }


    @Test
    public void testIsValidPrefixNotationWithSingleNumber() {
        String[] tokens = {"123"};
        boolean result = parser.isValidPrefixNotation(tokens);
        assertTrue(result);
    }

    @Test
    public void testInvalidPrefixNotation() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseInput("1 2 +", env));

        assertThrows(IllegalArgumentException.class, () -> parser.parseInput("+ 1", env));

        assertThrows(IllegalArgumentException.class, () -> parser.parseInput("2 +", env));

        assertThrows(IllegalArgumentException.class, () -> parser.parseInput("1 + 2 3 *", env));
    }


    @Test
    public void parseTest1(){
        Expression expected = parser.parseInput("+ 3 0.2", env);
        assertEquals(3.2, expected.evaluate());
    }

    @Test
    public void parseTest2(){
        Expression expected = parser.parseInput("+ 3 / * 12 0.5 2", env);
        assertEquals(6, expected.evaluate());
    }

    @Test
    public void parseTest3(){
        Expression expected = parser.parseInput("- + 5 / 10 5 5", env);
        assertEquals(2, expected.evaluate());
    }

    @Test
    public void parseTest4(){
        Expression expected = parser.parseInput("/ / / 16 4 2 1", env);
        assertEquals(2, expected.evaluate());
    }

    @Test
    public void parseTest5(){
        Expression expected = parser.parseInput("+ 9 * 3 / 8 4", env);
        assertEquals(15, expected.evaluate());
    }

    @Test
    public void parseTest6(){
        Expression expected = parser.parseInput("- + 1 2 * 3 / 6 2", env);
        assertEquals(-6, expected.evaluate());
    }

    @Test
    public void parseTest7(){
        Expression expected = parser.parseInput("- * 1 5 / * / 6 3 6 2", env);
        assertEquals(-1, expected.evaluate());
    }

    @Test
    public void parseTest8(){
        Expression expected = parser.parseInput("* / + 1 2 / 4 2 + 3 5", env);
        assertEquals(12, expected.evaluate());
    }

    @Test
    public void parseTest9(){
        Expression expected = parser.parseInput("- -5 2", env);
        assertEquals(-7, expected.evaluate());
    }

    @Test
    public void parseTest10(){
        Expression expected = parser.parseInput("+ -5 2", env);
        assertEquals(-3, expected.evaluate());
    }

    @Test
    public void parseTest11(){
        Expression expected = parser.parseInput("- 5 -2", env);
        assertEquals(7, expected.evaluate());
    }

    @Test
    public void parseTest12(){
        Expression expected = parser.parseInput("+ 5 -2", env);
        assertEquals(3, expected.evaluate());
    }

    @Test
    public void testParseInputInvalidInput() {
        String invalidInput = "+ 3";
        assertThrows(IllegalArgumentException.class, () -> parser.parseInput(invalidInput, env));
    }

    @Test
    public void testIsValidPrefixNotationInvalidOperator1() {
        String[] invalidTokens = {"+", "3", "2"};
        assertTrue(parser.isValidPrefixNotation(invalidTokens));
    }

    @Test
    public void testIsValidPrefixNotationInvalidOperator2() {
        String[] invalidTokens = {"a"};
        assertTrue(parser.isValidPrefixNotation(invalidTokens));
    }

    @Test
    public void testIsValidPrefixNotationNotEnoughOperands() {
        String[] invalidTokens = {"+", "3"};
        assertFalse(parser.isValidPrefixNotation(invalidTokens));
    }

    @Test
    public void testIsValidPrefixNotationOther() {
        String[] invalidTokens = {"_", "3"};
        assertFalse(parser.isValidPrefixNotation(invalidTokens));
    }

    @Test
    public void testIsValidPrefixNotationEmpty() {
        String[] invalidTokens = {};
        assertFalse(parser.isValidPrefixNotation(invalidTokens));
    }

    @Test
    public void testIsValidPrefixNotationValidNumber() {
        String[] validTokens = {"3.14"};
        assertTrue(parser.isValidPrefixNotation(validTokens));
    }

    @Test
    public void testCreateOperationInvalidOperator() {
        Expression left = new Digit(5);
        Expression right = new Digit(0);
        assertThrows(IllegalArgumentException.class, () -> parser.createOperation("invalid", left, right));
    }

    @Test
    public void testIsValidNumberInvalidNumberFalse() {
        assertFalse(parser.isValidNumber("hi"));
    }

    @Test
    public void testIsValidOperatorInvalidOperatorFalse() {
        assertFalse(parser.isValidOperator("hi"));
    }

    @Test
    public void testIsValidOperatorInvalidOperator1() {
        assertFalse(parser.isValidOperator("+-"));
    }

    @Test
    public void testVarBinding1() {
        env.put("a", 1.0);
        Expression result = parser.parseInput("+ a 2", env);
        assertEquals(3.0, result.evaluate());
        //assertEquals("+ a 2.0", result.toString());
    }

    @Test
    public void testVarBinding2() {
        env.put("var", 2.0);
        Expression result = parser.parseInput("+ var 2", env);
        assertEquals(4.0, result.evaluate());
    }
}
