package calculator.project2calculatorgui;

import java.util.ArrayList;
import java.util.List;

/**
 * CSC 305 Project 2
 * Represents the calculator's expression history
 *
 * @author Thien An Tran
 * @version 2.0
 */
public class History {
    private final List<Expression> expressions;
    private final int maxSize;
    private int currentSize;

    /**
     * Constructor for History object
     */
    public History() {
        this.maxSize = 10;
        this.currentSize = 0;
        this.expressions = new ArrayList<>(maxSize);
    }

    /**
     * Returns the List of Expressions
     *
     * @return a List of Expressions
     */
    public List<Expression> getExpressions(){
        return expressions;
    }

    /**
     * Removes an expression from the history at the specified index.
     *
     * @param index an int representing the index of the Expression to be removed.
     */
    public void removeExpression(int index) {
        if (index >= 0 && index < currentSize) {
            expressions.remove(index);
            currentSize--;
        }
    }

    /**
     * Adds an expression typed by the user to the history
     *
     * @param ast a string representing the inputted expression
     */
    public void addExpression(Expression ast) {
        if (currentSize == maxSize){
            removeOldestExpression();
        }

        expressions.add(0, ast);
        currentSize++;
    }

    /**
     * Removes the oldest expression from the history
     * If the history is empty, does nothing
     */
    public void removeOldestExpression() {
        if (!expressions.isEmpty()) {
            expressions.remove(currentSize - 1);
            currentSize--;
        }
    }
}
