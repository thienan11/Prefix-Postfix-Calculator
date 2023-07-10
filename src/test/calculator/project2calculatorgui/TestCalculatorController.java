package calculator.project2calculatorgui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for non-GUI and non-void methods for CalculatorController
 */
public class TestCalculatorController {
    private CalculatorController calculator;

    @Before
    public void setUp() {
        calculator = new CalculatorController();
    }

    @Test
    public void testIsValidVariable() {
        assertTrue(calculator.isValidVariable("variable"));
    }

    @Test
    public void testIsValidVariable2() {
        assertFalse(calculator.isValidVariable("22"));
    }

    @Test
    public void testIsValidVariable3() {
        assertTrue(calculator.isValidVariable("asdf14543_"));
    }

    @Test
    public void testIsValidVariable4() {
        assertTrue(calculator.isValidVariable("a"));
    }

    @Test
    public void testIsValidValue() {
        assertTrue(calculator.isValidNumber("10"));
    }

    @Test
    public void testIsValidValue2() {
        assertFalse(calculator.isValidNumber("aasdfasdf"));
    }

    @Test
    public void testIsValidValue3() {
        assertTrue(calculator.isValidNumber("-3.04"));
    }

    @Test
    public void testCheckValidResult1(){
        // warning for the purpose of testing the function
        assertThrows(NumberFormatException.class, () -> calculator.checkValidResult(Double.MAX_VALUE * 2));
    }

    @Test
    public void testCheckValidResult2(){
        assertThrows(NumberFormatException.class, () -> calculator.checkValidResult(Double.POSITIVE_INFINITY));
    }

    @Test
    public void testCheckValidResult3(){
        assertThrows(NumberFormatException.class, () -> calculator.checkValidResult(Double.NaN));
    }

    @Test
    public void testCheckValidResult4(){
        double d = calculator.checkValidResult(2);
        assertEquals(2, d);
    }
}
