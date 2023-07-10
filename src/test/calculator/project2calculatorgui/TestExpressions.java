package calculator.project2calculatorgui;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for anything implementing Expressions and their subclasses
 */
public class TestExpressions {

    @Test
    public void digitTest1(){
        Digit d = new Digit(3);
        assertEquals(3, d.evaluate());
    }

    @Test
    public void additionTest1(){
        Digit left = new Digit(4);
        Digit right = new Digit(5);
        AdditionOp a = new AdditionOp(left, right);
        assertEquals(9, a.evaluate());
    }

    @Test
    public void additionTest2(){
        AdditionOp a = new AdditionOp(new AdditionOp(new Digit(2), new Digit(3)),
                                        new Digit(5));
        assertEquals(10, a.evaluate());
    }

    @Test
    public void additionTest3(){
        AdditionOp a = new AdditionOp(new Digit(2), new AdditionOp(new Digit(3), new Digit(5)));
        assertEquals(10, a.evaluate());
    }

    @Test
    public void subtractionTest1(){
        Digit left = new Digit(4);
        Digit right = new Digit(5);
        SubtractionOp s = new SubtractionOp(left, right);
        assertEquals(-1, s.evaluate());
    }

    @Test
    public void subtractionTest2(){
        Digit left = new Digit(5);
        Digit right = new Digit(5);
        SubtractionOp s = new SubtractionOp(left, right);
        assertEquals(0, s.evaluate());
    }

    @Test
    public void divisionTest1(){
        Digit left = new Digit(5);
        Digit right = new Digit(5);
        DivisionOp d = new DivisionOp(left, right);
        assertEquals(1, d.evaluate());
    }

    @Test
    public void divisionTest2(){
        // divide by zero
        Digit left = new Digit(5);
        Digit right = new Digit(0);
        DivisionOp d = new DivisionOp(left, right);
        assertThrows(ArithmeticException.class, d::evaluate);
    }

    @Test
    public void exponentiationTest1(){
        Digit left = new Digit(5);
        Digit right = new Digit(2);
        ExponentiationOp e = new ExponentiationOp(left, right);
        assertEquals(25, e.evaluate());
    }

    @Test
    public void exponentiationTest2(){
        Digit left = new Digit(0);
        Digit right = new Digit(1);
        ExponentiationOp e = new ExponentiationOp(left, right);
        assertEquals(0, e.evaluate());
    }

    @Test
    public void exponentiationTest3(){
        Digit left = new Digit(0);
        Digit right = new Digit(-1);
        ExponentiationOp e = new ExponentiationOp(left, right);
        assertThrows(ArithmeticException.class, e::evaluate);
    }

    @Test
    public void multiplicationTest1(){
        Digit left = new Digit(2);
        Digit right = new Digit(0);
        MultiplicationOp m = new MultiplicationOp(left, right);
        assertEquals(0, m.evaluate());
    }

    @Test
    public void multiplicationTest2(){
        Digit left = new Digit(0);
        Digit right = new Digit(0);
        MultiplicationOp m = new MultiplicationOp(left, right);
        assertEquals(0, m.evaluate());
    }

    @Test
    public void moduloTest1(){
        Digit left = new Digit(1);
        Digit right = new Digit(2);
        ModuloOp m = new ModuloOp(left, right);
        assertEquals(1, m.evaluate());

    }

    @Test
    public void moduloTest2(){
        Digit left = new Digit(1);
        Digit right = new Digit(0);
        ModuloOp m = new ModuloOp(left, right);
        assertThrows(ArithmeticException.class, m::evaluate);
    }

    @Test
    public void variableTest1(){
        Map<String, Double> env = new HashMap<>();
        Variable var = new Variable("a", env);
        env.put("a", 2.0);
        assertEquals(2.0, var.evaluate());
    }

    @Test
    public void variableTest2(){
        Map<String, Double> env = new HashMap<>();
        Variable var = new Variable("a", env);
        assertThrows(NoSuchElementException.class, var::evaluate);
    }

    @Test
    public void toStringTest1(){
        Digit left = new Digit(0);
        Digit right = new Digit(0);
        MultiplicationOp m = new MultiplicationOp(left, right);
        assertEquals("0.0 * 0.0", m.toString());
    }

    @Test
    public void toStringTest2(){
        Digit left = new Digit(0);
        assertEquals("0.0", left.toString());
    }
    @Test
    public void toStringTest3(){
        Map<String, Double> env = new HashMap<>();
        Variable v = new Variable("var", env);
        assertEquals("var", v.toString());
    }

    @Test
    public void toStringTest4(){
        Map<String, Double> env = new HashMap<>();
        Variable v = new Variable("var", env);
        assertEquals("var", v.toPostfixString());
    }

    @Test
    public void toStringTest5(){
        Map<String, Double> env = new HashMap<>();
        Variable v = new Variable("var", env);
        assertEquals("var", v.toPrefixString());
    }

    @Test
    public void toPostfixStringTest(){
        Digit left = new Digit(2);
        Digit right = new Digit(3);
        AdditionOp m = new AdditionOp(left, right);
        assertEquals("2.0 3.0 +", m.toPostfixString());
    }

    @Test
    public void toPrefixStringTest(){
        Digit left = new Digit(2);
        Digit right = new Digit(3);
        AdditionOp m = new AdditionOp(left, right);
        assertEquals("+ 2.0 3.0", m.toPrefixString());
    }
}
