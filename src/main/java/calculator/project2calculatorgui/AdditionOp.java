package calculator.project2calculatorgui;

/**
 * CSC 305 Project 2
 * Represents the addition binary operation
 *
 * @author Thien An Tran
 * @version 2.0
 */
public class AdditionOp extends BOperation{

    /**
     * Constructs an AdditionOp object
     *
     * @param left an Expression for the left operand
     * @param right an Expression for the right operand
     */
    public AdditionOp(Expression left, Expression right) {
        super("+", left, right);
    }

    /**
     * Calculates the left and right expressions with addition
     *
     * @return a double of the result
     */
    @Override
    public double evaluate() {
        return left.evaluate() + right.evaluate();
    }

}
