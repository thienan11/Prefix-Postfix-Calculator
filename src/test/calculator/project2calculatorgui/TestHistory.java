package calculator.project2calculatorgui;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests for History
 */
public class TestHistory {

    private History history;

    @Before
    public void setUp() {
        history = new History();
    }

    @Test
    public void testRemoveExpression() {
        // Test removing an expression at a valid index
        Expression ast1 = new Digit(1);
        Expression ast2 = new Digit(2);
        history.addExpression(ast1);
        history.addExpression(ast2);
        history.removeExpression(0);
        List<Expression> expressions1 = history.getExpressions();
        assertEquals(1, expressions1.size());

        // Test removing an expression at an invalid index
        history.removeExpression(1);
        history.removeExpression(-1);
        List<Expression> expressions2 = history.getExpressions();
        assertEquals(1, expressions2.size());
    }

    @Test
    public void testAddExpression() {
        // Test adding expressions up to the maximum size
        for (int i = 1; i <= 10; i++) {
            Expression ast = new Digit(1);
            history.addExpression(ast);
            List<Expression> expressions = history.getExpressions();
            assertEquals(i, expressions.size());
            assertTrue(expressions.contains(ast));
        }

        // Test adding expression when the history is already at maximum size
        Expression ast27 = new Digit(2);
        history.addExpression(ast27);
        List<Expression> expressions3 = history.getExpressions();
        assertEquals(10, expressions3.size());
        assertTrue(expressions3.contains(ast27));
    }

    @Test
    public void testRemoveOldestExpression() {
        // Test removing the oldest expression when the history is empty
        history.removeOldestExpression();
        List<Expression> expressions1 = history.getExpressions();
        assertTrue(expressions1.isEmpty());

        // Add expressions up to the maximum size
        for (int i = 1; i <= 10; i++) {
            Expression ast = new Digit(1);
            history.addExpression(ast);
        }

        // Test removing the oldest expression when the history is full
        history.removeOldestExpression();
        List<Expression> expressions2 = history.getExpressions();
        assertEquals(9, expressions2.size());

        // Test removing the oldest expression multiple times
        for (int i = 1; i <= 10; i++) {
            history.removeOldestExpression();
        }
        List<Expression> expressions3 = history.getExpressions();
        assertTrue(expressions3.isEmpty());
    }
}
